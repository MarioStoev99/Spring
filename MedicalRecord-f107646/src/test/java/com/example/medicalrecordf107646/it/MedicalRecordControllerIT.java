package com.example.medicalrecordf107646.it;

import static com.example.medicalrecordf107646.contants.ApiConstants.DOCTOR_API;
import static com.example.medicalrecordf107646.contants.ApiConstants.MEDICAL_RECORD_API;
import static com.example.medicalrecordf107646.contants.ApiConstants.PATIENT_API;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.medicalrecordf107646.TestUtil;
import com.example.medicalrecordf107646.model.MedicalRecordResponse;
import com.example.medicalrecordf107646.utils.spring.ApiError;

@ActiveProfiles(value = "it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MedicalRecordControllerIT extends AbstractTestNGSpringContextTests {

    private MockMvc mockMvc;

    @BeforeClass
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext) applicationContext)
                .build();
    }

    @Test
    public void test_create_doctor() throws Exception {
        String payload = loadResource("doctor-post-request.json");

        mockMvc.perform(post(DOCTOR_API)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload))
                .andExpect(status().is2xxSuccessful());
    }

    @Test(dependsOnMethods = "test_create_doctor")
    public void test_create_patient() throws Exception {
        String payload = loadResource("patient-post-request.json");

        mockMvc.perform(post(PATIENT_API)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload))
                .andExpect(status().is2xxSuccessful());
    }

    @Test(dependsOnMethods = "test_create_patient")
    public void test_create_medical_record() throws Exception {
        String payload = loadResource("medical-record-post-request.json");

        MvcResult result = mockMvc.perform(post(MEDICAL_RECORD_API)
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(payload))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        MedicalRecordResponse response = getResponseBody(result, MedicalRecordResponse.class);

        Assert.assertEquals(response.getDiagnosis(), "diagnosis");
        Assert.assertEquals(response.getSickDays(), 5);
        Assert.assertEquals(response.getTreatment(), "treatment");
        Assert.assertEquals(response.getId(), UUID.fromString("369ce4f5-3405-43ba-b968-ee331b98ff59"));
    }


    @Test(dependsOnMethods = "test_create_medical_record")
    public void test_get_medical_record() throws Exception {
        MvcResult result = mockMvc.perform(get(MEDICAL_RECORD_API + "/patient/001122334455")
                                                   .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        MedicalRecordResponse[] records = getResponseBody(result, MedicalRecordResponse[].class);
        MedicalRecordResponse response = records[0];

        Assert.assertEquals(response.getDiagnosis(), "diagnosis");
        Assert.assertEquals(response.getSickDays(), 5);
        Assert.assertEquals(response.getTreatment(), "treatment");
        Assert.assertEquals(response.getId(), UUID.fromString("369ce4f5-3405-43ba-b968-ee331b98ff59"));
    }

    @Test(dependsOnMethods = "test_create_medical_record")
    public void test_get_medical_record_not_exist() throws Exception {
        MvcResult result = mockMvc.perform(get(MEDICAL_RECORD_API + "/patient/001122334456")
                                                   .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        MedicalRecordResponse[] records = getResponseBody(result, MedicalRecordResponse[].class);

        Assert.assertEquals(records.length, 0);
    }

    @Test(dependsOnMethods = "test_create_medical_record")
    public void test_get_medical_record_by_diagnosis() throws Exception {
        MvcResult result = mockMvc.perform(get(MEDICAL_RECORD_API + "/diagnosis/diagnosis")
                                                   .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        MedicalRecordResponse[] records = getResponseBody(result, MedicalRecordResponse[].class);
        MedicalRecordResponse response = records[0];

        Assert.assertEquals(response.getDiagnosis(), "diagnosis");
        Assert.assertEquals(response.getSickDays(), 5);
        Assert.assertEquals(response.getTreatment(), "treatment");
        Assert.assertEquals(response.getId(), UUID.fromString("369ce4f5-3405-43ba-b968-ee331b98ff59"));
    }

    @Test(dependsOnMethods = "test_create_medical_record")
    public void test_get_medical_record_by_diagnosis_not_exist() throws Exception {
        MvcResult result = mockMvc.perform(get(MEDICAL_RECORD_API + "/diagnosis/notExist")
                                                   .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        MedicalRecordResponse[] records = getResponseBody(result, MedicalRecordResponse[].class);

        Assert.assertEquals(records.length, 0);
    }

    @Test(dependsOnMethods = "test_create_medical_record")
    public void test_get_all_medical_records_by_doctorId() throws Exception {
        MvcResult result = mockMvc.perform(get(MEDICAL_RECORD_API + "/doctor/2a8b7bea-1046-4dc6-823c-b437ca2b8467")
                                                   .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        MedicalRecordResponse[] records = getResponseBody(result, MedicalRecordResponse[].class);
        MedicalRecordResponse response = records[0];

        Assert.assertEquals(response.getDiagnosis(), "diagnosis");
        Assert.assertEquals(response.getSickDays(), 5);
        Assert.assertEquals(response.getTreatment(), "treatment");
        Assert.assertEquals(response.getId(), UUID.fromString("369ce4f5-3405-43ba-b968-ee331b98ff59"));
    }

    @Test(dependsOnMethods = "test_create_medical_record")
    public void test_get_all_medical_records_by_doctorId_emptyList() throws Exception {
        MvcResult result = mockMvc.perform(get(MEDICAL_RECORD_API + "/doctor/2a8b7bea-1046-4dc6-823c-b437ca2b8468")
                                                   .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        ApiError apiError = getResponseBody(result, ApiError.class);

        Assert.assertEquals(apiError.getMessage(),
                            "Entity with id 2a8b7bea-1046-4dc6-823c-b437ca2b8468 does not exist");
    }

    @Test(dependsOnMethods = "test_create_medical_record")
    public void test_get_all_medical_records_by_doctorId_visits() throws Exception {
        MvcResult result =
                mockMvc.perform(get(MEDICAL_RECORD_API + "/doctor/visits/2a8b7bea-1046-4dc6-823c-b437ca2b8467")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();

        Integer res = getResponseBody(result, Integer.class);

        Assert.assertEquals(res.intValue(), 1);
    }

    @Test(dependsOnMethods = "test_create_medical_record")
    public void test_get_all_medical_records_by_doctorId_visits_zero() throws Exception {
        MvcResult result =
                mockMvc.perform(get(MEDICAL_RECORD_API + "/doctor/visits/2a8b7bea-1046-4dc6-823c-b437ca2b8468")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is4xxClientError())
                        .andReturn();

        ApiError apiError = getResponseBody(result, ApiError.class);

        Assert.assertEquals(apiError.getMessage(),
                            "Entity with id 2a8b7bea-1046-4dc6-823c-b437ca2b8468 does not exist");
    }

    private <T> T getResponseBody(MvcResult result, Class<?> type) throws UnsupportedEncodingException {
        return (T) TestUtil.deserialize(result.getResponse().getContentAsString(), type);
    }

    private String loadResource(String fileName) {
        return TestUtil.loadResource("src/test/resources/mocks/" + fileName);
    }
}
