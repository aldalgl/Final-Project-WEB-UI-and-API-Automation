package com.qafinal.api.client;

import com.qafinal.api.config.ApiConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Wrapper untuk endpoint Tag pada DummyAPI (https://dummyapi.io/docs/tag):
 * - GET /tag -> getTags (daftar tag)
 */
public class TagApiClient {

    public Response getTags() {
        return given()
                .header("app-id", ApiConfig.getAppId())
                .when()
                .get(ApiConfig.BASE_URL + "/tag");
    }
}
