package com.qafinal.api.context;

import io.restassured.response.Response;

/**
 * Menyimpan Response terakhir agar bisa dibaca lintas step definition class
 * (misal: UserApiSteps/TagApiSteps yang mengeksekusi request, lalu CommonApiSteps yang
 * melakukan validasi/assert). Cucumber membuat instance baru per class per scenario,
 * jadi kita perlu tempat penyimpanan bersama seperti ini.
 */
public class ApiTestContext {

    private static final ThreadLocal<Response> LAST_RESPONSE = new ThreadLocal<>();

    private ApiTestContext() {
    }

    public static void setResponse(Response response) {
        LAST_RESPONSE.set(response);
    }

    public static Response getResponse() {
        Response response = LAST_RESPONSE.get();
        if (response == null) {
            throw new IllegalStateException("Belum ada response API yang tersimpan. " +
                    "Pastikan step 'When' sudah dijalankan sebelum step 'Then'.");
        }
        return response;
    }

    public static void clear() {
        LAST_RESPONSE.remove();
    }
}
