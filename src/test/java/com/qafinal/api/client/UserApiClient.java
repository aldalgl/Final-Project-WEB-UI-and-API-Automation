package com.qafinal.api.client;

import com.qafinal.api.config.ApiConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Wrapper untuk endpoint User pada DummyAPI (https://dummyapi.io/docs/user):
 * - GET    /user/:id      -> getUserById
 * - POST   /user/create   -> createUser
 * - PUT    /user/:id      -> updateUser
 * - DELETE /user/:id      -> deleteUser
 */
public class UserApiClient {

    public Response getUserById(String id) {
        return given()
                .header("app-id", ApiConfig.getAppId())
                .when()
                .get(ApiConfig.BASE_URL + "/user/" + id);
    }

    public Response createUser(Map<String, Object> body) {
        return given()
                .header("app-id", ApiConfig.getAppId())
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(ApiConfig.BASE_URL + "/user/create");
    }

    public Response updateUser(String id, Map<String, Object> body) {
        return given()
                .header("app-id", ApiConfig.getAppId())
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put(ApiConfig.BASE_URL + "/user/" + id);
    }

    public Response deleteUser(String id) {
        return given()
                .header("app-id", ApiConfig.getAppId())
                .when()
                .delete(ApiConfig.BASE_URL + "/user/" + id);
    }
}
