package com.qafinal.api.config;

/**
 * Konfigurasi dasar untuk API DummyAPI (https://dummyapi.io/docs).
 *
 * app-id WAJIB di-set karena DummyAPI mengharuskan header "app-id" di setiap request.
 * Cara set:
 *   1) ./gradlew apiTest -Dapp.id=YOUR_APP_ID
 *   2) atau set environment variable APP_ID sebelum menjalankan test
 */
public class ApiConfig {

    public static final String BASE_URL = "https://dummyapi.io/data/v1";

    private ApiConfig() {
    }

    public static String getAppId() {
        String appId = System.getProperty("app.id");
        if (appId == null || appId.isBlank()) {
            appId = System.getenv("APP_ID");
        }
        if (appId == null || appId.isBlank()) {
            throw new IllegalStateException(
                    "APP_ID belum di-set! Daftar akun gratis di https://dummyapi.io untuk mendapatkan app-id, " +
                    "lalu jalankan test dengan: ./gradlew apiTest -Dapp.id=YOUR_APP_ID " +
                    "(atau set environment variable APP_ID)."
            );
        }
        return appId;
    }
}
