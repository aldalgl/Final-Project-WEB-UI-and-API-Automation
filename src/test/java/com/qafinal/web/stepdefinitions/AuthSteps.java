package com.qafinal.web.stepdefinitions;

import com.qafinal.web.pages.HomePage;
import com.qafinal.web.utils.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.UUID;

public class AuthSteps {

    private String generatedUsername;
    private String generatedPassword;

    private HomePage homePage() {
        return new HomePage(DriverFactory.getDriver());
    }

    @Given("saya membuka halaman utama Demoblaze")
    public void saya_membuka_halaman_utama_demoblaze() {
        homePage().openPage();
    }

    @When("saya mendaftar dengan username acak dan password {string}")
    public void saya_mendaftar_dengan_username_acak_dan_password(String password) {
        generatedUsername = generateUniqueUsername();
        generatedPassword = password;
        registerCurrentUser(generatedUsername, generatedPassword);
    }

    @Given("saya sudah mendaftar dengan username acak dan password {string}")
    public void saya_sudah_mendaftar_dengan_username_acak_dan_password(String password) {
        generatedUsername = generateUniqueUsername();
        generatedPassword = password;
        registerCurrentUser(generatedUsername, generatedPassword);
        // tutup notifikasi "Sign up successful." lalu pastikan modal benar-benar tertutup
        // supaya tidak menghalangi/bentrok dengan aksi berikutnya (login, sign up ulang, dst)
        homePage().getAlertTextAndAccept();
        homePage().closeSignUpModal();
    }

    @When("saya mendaftar ulang dengan username dan password yang sama")
    public void saya_mendaftar_ulang_dengan_username_dan_password_yang_sama() {
        registerCurrentUser(generatedUsername, generatedPassword);
    }

    @When("saya login menggunakan username dan password tersebut")
    public void saya_login_menggunakan_username_dan_password_tersebut() {
        loginWith(generatedUsername, generatedPassword);
    }

    @When("saya login menggunakan username tersebut dan password {string}")
    public void saya_login_menggunakan_username_tersebut_dan_password(String wrongPassword) {
        loginWith(generatedUsername, wrongPassword);
    }

    @When("saya login menggunakan username acak yang belum pernah didaftarkan dan password {string}")
    public void saya_login_menggunakan_username_acak_yang_belum_pernah_didaftarkan_dan_password(String password) {
        loginWith(generateUniqueUsername(), password);
    }

    @Then("muncul notifikasi {string}")
    public void muncul_notifikasi(String expectedMessage) {
        String alertText = homePage().getAlertTextAndAccept();
        Assert.assertTrue(alertText.contains(expectedMessage),
                "Notifikasi tidak sesuai. Actual: '" + alertText + "', expected mengandung: '" + expectedMessage + "'");
    }

    @Then("saya berhasil login dan nama pengguna tampil di navbar")
    public void saya_berhasil_login_dan_nama_pengguna_tampil_di_navbar() {
        Assert.assertTrue(homePage().isUserLoggedIn(generatedUsername),
                "Navbar tidak menampilkan nama user '" + generatedUsername + "' setelah login");
    }

    private void registerCurrentUser(String username, String password) {
        HomePage home = homePage();
        home.openSignUpModal();
        home.fillSignUpForm(username, password);
        home.submitSignUp();
    }

    private void loginWith(String username, String password) {
        HomePage home = homePage();
        home.openLoginModal();
        home.fillLoginForm(username, password);
        home.submitLogin();
    }

    private String generateUniqueUsername() {
        return "qauser_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
