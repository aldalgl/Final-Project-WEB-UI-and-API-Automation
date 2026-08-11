Feature: Sign Up dan Login - Demoblaze
  Sebagai pengguna baru
  Saya ingin bisa mendaftar dan login ke Demoblaze
  Agar saya bisa berbelanja dengan akun saya sendiri

  Background:
    Given saya membuka halaman utama Demoblaze

  @web @positive
  Scenario: Sign up berhasil dengan username baru yang unik
    When saya mendaftar dengan username acak dan password "Password123"
    Then muncul notifikasi "Sign up successful."

  @web @negative
  Scenario: Sign up gagal karena username sudah pernah terdaftar
    Given saya sudah mendaftar dengan username acak dan password "Password123"
    When saya mendaftar ulang dengan username dan password yang sama
    Then muncul notifikasi "This user already exist."

  @web @positive
  Scenario: Login berhasil dengan akun yang baru didaftarkan
    Given saya sudah mendaftar dengan username acak dan password "Password123"
    When saya login menggunakan username dan password tersebut
    Then saya berhasil login dan nama pengguna tampil di navbar

  @web @negative
  Scenario: Login gagal dengan password yang salah
    Given saya sudah mendaftar dengan username acak dan password "Password123"
    When saya login menggunakan username tersebut dan password "PasswordSalah999"
    Then muncul notifikasi "Wrong password."

  @web @boundary
  Scenario: Login gagal dengan username acak yang belum pernah didaftarkan
    When saya login menggunakan username acak yang belum pernah didaftarkan dan password "Password123"
    Then muncul notifikasi "User does not exist."
