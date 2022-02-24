package com.example.medicalrecordf107646.it;

import static com.example.medicalrecordf107646.contants.ApiConstants.DOCTOR_API;
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
import com.example.medicalrecordf107646.model.Doctor;
import com.example.medicalrecordf107646.model.Patient;
import com.example.medicalrecordf107646.utils.spring.ApiError;

@ActiveProfiles(value = "it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorControllerIT extends AbstractTestNGSpringContextTests {

    private MockMvc mockMvc;

    @BeforeClass
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext) applicationContext)
                .build();
    }

    @Test
    public void test_create() throws Exception {
        String payload = loadResource("doctor-post-request.json");

        MvcResult response = mockMvc.perform(post(DOCTOR_API)
                                                     .contentType(MediaType.APPLICATION_JSON)
                                                     .content(payload))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Doctor doctor = getResponseBody(response, Doctor.class);
        Patient patient = doctor.getPatients().get(0);

        Assert.assertEquals(doctor.getName(), "dr. Kostadinov");
        Assert.assertEquals(doctor.getSpecialties().get(0), "test");
        Assert.assertEquals(patient.getIdenticalNumber(), "0011223344");
        Assert.assertEquals(patient.getName(), "Mario");
    }

    @Test(dependsOnMethods = "test_create")
    public void test_getPatientsNumberByDoctorId() throws Exception {
        MvcResult response = mockMvc.perform(get(DOCTOR_API + "/patients-number" + "/2a8b7bea-1046-4dc6-823c-b437ca2b8467")
                                                     .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Integer res = getResponseBody(response, Integer.class);

        Assert.assertEquals(res.intValue(), 1);
    }

    @Test(dependsOnMethods = "test_create")
    public void test_create_doctorAlreadyExist() throws Exception {
        String payload = loadResource("doctor-post-request.json");

        MvcResult response = mockMvc.perform(post(DOCTOR_API)
                                                     .contentType(MediaType.APPLICATION_JSON)
                                                     .content(payload))
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assert.assertEquals(getErrorMessage(response),
                            "Entity with id 2a8b7bea-1046-4dc6-823c-b437ca2b8467 already exist");
    }


    @Test(dependsOnMethods = "test_create")
    public void test_update_entityNotExist() throws Exception {
        String payload = loadResource("doctor-put-request.json");

        MvcResult response = mockMvc.perform(put(DOCTOR_API + "/2a8b7bea-1046-4dc6-823c-b437ca2b8468")
                                                     .contentType(MediaType.APPLICATION_JSON)
                                                     .content(payload))
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assert.assertEquals(getErrorMessage(response),
                            "Entity with id 2a8b7bea-1046-4dc6-823c-b437ca2b8468 does not exist");
    }

    @Test(dependsOnMethods = "test_create")
    public void test_update() throws Exception {
        String payload = loadResource("doctor-put-request.json");

        MvcResult response = mockMvc.perform(put(DOCTOR_API + "/2a8b7bea-1046-4dc6-823c-b437ca2b8467")
                                                     .contentType(MediaType.APPLICATION_JSON)
                                                     .content(payload))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Doctor doctor = getResponseBody(response, Doctor.class);
        Patient patient = doctor.getPatients().get(0);

        Assert.assertEquals(doctor.getName(), "dr. Kirilov");
        Assert.assertEquals(doctor.getSpecialties().get(0), "test");
        Assert.assertEquals(patient.getIdenticalNumber(), "0011223344");
        Assert.assertEquals(patient.getName(), "Mario");
    }

    @Test(dependsOnMethods = "test_update")
    public void test_getById() throws Exception {
        MvcResult response = mockMvc.perform(get(DOCTOR_API + "/2a8b7bea-1046-4dc6-823c-b437ca2b8467")
                                                     .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Doctor doctor = getResponseBody(response, Doctor.class);

        Assert.assertEquals(doctor.getName(), "dr. Kirilov");
    }

    @Test(dependsOnMethods = "test_update")
    public void test_delete_entityNotExist() throws Exception {
        MvcResult response = mockMvc.perform(delete(DOCTOR_API + "/2a8b7bea-1046-4dc6-823c-b437ca2b8468")
                                                     .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assert.assertEquals(getErrorMessage(response),
                            "Entity with id 2a8b7bea-1046-4dc6-823c-b437ca2b8468 does not exist");
    }

    @Test(dependsOnMethods = "test_getById")
    public void test_delete() throws Exception {
        mockMvc.perform(delete(DOCTOR_API + "/2a8b7bea-1046-4dc6-823c-b437ca2b8467")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test(dependsOnMethods = "test_delete")
    public void test_getById_entityNotExist() throws Exception {
        MvcResult response = mockMvc.perform(get(DOCTOR_API + "/2a8b7bea-1046-4dc6-823c-b437ca2b8467")
                                                     .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assert.assertEquals(getErrorMessage(response),
                            "Entity with id 2a8b7bea-1046-4dc6-823c-b437ca2b8467 does not exist");
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
