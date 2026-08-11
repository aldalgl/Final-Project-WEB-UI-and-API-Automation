package com.qafinal.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman utama Demoblaze (https://www.demoblaze.com/),
 * termasuk elemen navbar dan modal Sign Up / Log In yang muncul di atas halaman ini.
 */
public class HomePage extends BasePage {

    private static final String URL = "https://www.demoblaze.com/";

    // Navbar
    private final By signupNavLink = By.id("signin2");
    private final By loginNavLink = By.id("login2");
    private final By cartNavLink = By.id("cartur");
    private final By nameOfUserLabel = By.id("nameofuser");

    // Modal: Sign up
    private final By signupUsernameInput = By.id("sign-username");
    private final By signupPasswordInput = By.id("sign-password");
    private final By signupSubmitButton = By.cssSelector("#signInModal button[onclick='register()']");
    private final By signupModalContainer = By.id("signInModal");
    private final By signupCloseButton = By.cssSelector("#signInModal .close");

    // Modal: Log in
    private final By loginUsernameInput = By.id("loginusername");
    private final By loginPasswordInput = By.id("loginpassword");
    private final By loginSubmitButton = By.cssSelector("#logInModal button[onclick='logIn()']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openPage() {
        driver.get(URL);
    }

    // ---------- Sign up ----------
    public void openSignUpModal() {
        // Pastikan modal sebelumnya (kalau ada) sudah benar-benar tertutup dulu,
        // supaya tidak bentrok dengan modal baru yang mau dibuka.
        waitInvisible(By.className("modal-backdrop"));
        click(signupNavLink);
        waitVisible(signupUsernameInput);
    }

    public void fillSignUpForm(String username, String password) {
        type(signupUsernameInput, username);
        type(signupPasswordInput, password);
    }

    public void submitSignUp() {
        click(signupSubmitButton);
    }

    /**
     * Menutup modal sign up secara EKSPLISIT lewat tombol close (x).
     * Diperlukan karena demoblaze tidak selalu otomatis menutup modal setelah alert
     * di-accept - kalau tidak ditutup manual, modal lama bisa bentrok saat mau dibuka lagi.
     */
    public void closeSignUpModal() {
        try {
            click(signupCloseButton);
        } catch (Exception ignored) {
            // Modal mungkin memang sudah tertutup - aman diabaikan
        }
        waitInvisible(signupModalContainer);
    }

    // ---------- Log in ----------
    public void openLoginModal() {
        waitInvisible(By.className("modal-backdrop"));
        click(loginNavLink);
        waitVisible(loginUsernameInput);
    }

    public void fillLoginForm(String username, String password) {
        type(loginUsernameInput, username);
        type(loginPasswordInput, password);
    }

    public void submitLogin() {
        click(loginSubmitButton);
    }

    public boolean isUserLoggedIn(String username) {
        try {
            // getText() sudah menunggu (wait) sampai elemen visible, beda dengan isDisplayed()
            // yang mengecek seketika tanpa menunggu update DOM setelah klik Log in.
            String actualText = getText(nameOfUserLabel);
            return actualText.contains(username);
        } catch (Exception e) {
            return false;
        }
    }

    // ---------- Kategori & navigasi produk ----------
    public void selectCategory(String categoryName) {
        click(By.linkText(categoryName));
    }

    public void openProductByName(String productName) {
        click(By.linkText(productName));
    }

    public void openCartPage() {
        click(cartNavLink);
    }
}
