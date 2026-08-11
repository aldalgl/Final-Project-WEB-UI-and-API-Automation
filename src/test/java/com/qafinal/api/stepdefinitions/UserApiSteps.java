package com.qafinal.api.stepdefinitions;

import com.qafinal.api.client.UserApiClient;
import com.qafinal.api.context.ApiTestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserApiSteps {

    private final UserApiClient userApiClient = new UserApiClient();
    private Map<String, Object> requestBody;
    private String userId;

    @Given("saya menyiapkan data user baru yang valid")
    public void saya_menyiapkan_data_user_baru_yang_valid() {
        requestBody = buildValidUserPayload();
    }

    @Given("saya menyiapkan data user baru tanpa field {string}")
    public void saya_menyiapkan_data_user_baru_tanpa_field(String missingField) {
        requestBody = buildValidUserPayload();
        requestBody.remove(missingField);
    }

    @When("saya mengirim request Create User")
    public void saya_mengirim_request_create_user() {
        Response response = userApiClient.createUser(requestBody);
        ApiTestContext.setResponse(response);
        if (response.getStatusCode() == 200) {
            userId = response.jsonPath().getString("id");
        }
    }

    @Given("saya sudah membuat user baru melalui API")
    public void saya_sudah_membuat_user_baru_melalui_api() {
        requestBody = buildValidUserPayload();
        Response response = userApiClient.createUser(requestBody);
        Assert.assertEquals(response.getStatusCode(), 200,
                "Precondition gagal: tidak bisa membuat user baru. Response body: " + response.getBody().asString());
        userId = response.jsonPath().getString("id");
        ApiTestContext.setResponse(response);
    }

    @Given("id user {string} tidak terdaftar di DummyAPI")
    public void id_user_tidak_terdaftar_di_dummyapi(String id) {
        userId = id;
    }

    @When("saya mengirim GET request untuk user tersebut")
    public void saya_mengirim_get_request_untuk_user_tersebut() {
        Response response = userApiClient.getUserById(userId);
        ApiTestContext.setResponse(response);
    }

    @When("saya update field {string} user tersebut menjadi {string}")
    public void saya_update_field_user_tersebut_menjadi(String field, String value) {
        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put(field, value);
        Response response = userApiClient.updateUser(userId, updateBody);
        ApiTestContext.setResponse(response);
    }

    @When("saya menghapus user tersebut")
    public void saya_menghapus_user_tersebut() {
        Response response = userApiClient.deleteUser(userId);
        ApiTestContext.setResponse(response);
    }

    private Map<String, Object> buildValidUserPayload() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        Map<String, Object> body = new HashMap<>();
        body.put("firstName", "QaAutomation");
        body.put("lastName", "Tester" + uniqueSuffix);
        body.put("email", "qa.automation." + uniqueSuffix + "@example.com");
        return body;
    }
}
