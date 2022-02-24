package com.example.medicalrecordf107646.it;

import static com.example.medicalrecordf107646.contants.ApiConstants.DOCTOR_API;
import static com.example.medicalrecordf107646.contants.ApiConstants.PATIENT_API;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

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
import com.example.medicalrecordf107646.model.Patient;
import com.example.medicalrecordf107646.utils.spring.ApiError;

@ActiveProfiles(value = "it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientControllerIT extends AbstractTestNGSpringContextTests {

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
    public void test_create() throws Exception {
        String payload = loadResource("patient-post-request.json");

        MvcResult response = mockMvc.perform(post(PATIENT_API)
                                                     .contentType(MediaType.APPLICATION_JSON)
                                                     .content(payload))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Patient patient = getResponseBody(response, Patient.class);

        Assert.assertEquals(patient.getName(), "Mario");
        Assert.assertEquals(patient.getIdenticalNumber(), "001122334455");
    }

    @Test(dependsOnMethods = "test_create")
    public void test_create_patientAlreadyExist() throws Exception {
        String payload = loadResource("patient-post-request.json");

        MvcResult response = mockMvc.perform(post(PATIENT_API)
                                                     .contentType(MediaType.APPLICATION_JSON)
                                                     .content(payload))
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assert.assertEquals(getErrorMessage(response),
                            "Entity with id 001122334455 already exist");
    }

    @Test(dependsOnMethods = "test_create")
    public void test_update_entityNotExist() throws Exception {
        String payload = loadResource("patient-put-request.json");

        MvcResult response = mockMvc.perform(put(PATIENT_API + "/001122334456")
                                                     .contentType(MediaType.APPLICATION_JSON)
                                                     .content(payload))
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assert.assertEquals(getErrorMessage(response),
                            "Entity with id 001122334456 does not exist");
    }

    @Test(dependsOnMethods = "test_create")
    public void test_update() throws Exception {
        String payload = loadResource("patient-put-request.json");

        MvcResult response = mockMvc.perform(put(PATIENT_API + "/001122334455")
                                                     .contentType(MediaType.APPLICATION_JSON)
                                                     .content(payload))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Patient patient = getResponseBody(response, Patient.class);

        Assert.assertEquals(patient.getName(), "Petur");
    }

    @Test(dependsOnMethods = "test_update")
    public void test_getById() throws Exception {
        MvcResult response = mockMvc.perform(get(PATIENT_API + "/001122334455")
                                                     .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Patient patient = getResponseBody(response, Patient.class);

        Assert.assertEquals(patient.getName(), "Petur");
    }

    @Test
    public void test_delete_entityNotExist() throws Exception {
        MvcResult response = mockMvc.perform(delete(PATIENT_API + "/001122334456")
                                                     .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assert.assertEquals(getErrorMessage(response),
                            "Entity with id 001122334456 does not exist");
    }

    @Test(dependsOnMethods = "test_getById")
    public void test_delete() throws Exception {
        mockMvc.perform(delete(PATIENT_API + "/001122334455")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test(dependsOnMethods = "test_delete")
    public void test_getById_entityNotExist() throws Exception {
        MvcResult response = mockMvc.perform(get(PATIENT_API + "/001122334455")
                                                     .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assert.assertEquals(getErrorMessage(response),
                            "Entity with id 001122334455 does not exist");
    }

    private String getErrorMessage(MvcResult result) throws UnsupportedEncodingException {
        ApiError apiError = getResponseBody(result, ApiError.class);
        return apiError.getMessage();
    }

    private <T> T getResponseBody(MvcResult result, Class<?> type) throws UnsupportedEncodingException {
        return (T) TestUtil.deserialize(result.getResponse().getContentAsString(), type);
    }

    private String loadResource(String fileName) {
        return TestUtil.loadResource("src/test/resources/mocks/" + fileName);
    }
}
