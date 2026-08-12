# Web UI & API Automation Testing — Final Project

Repository ini berisi automation test untuk:

- **Web UI**: [https://www.demoblaze.com/](https://www.demoblaze.com/) — menggunakan Selenium WebDriver
- **API**: [https://dummyapi.io](https://dummyapi.io) (endpoint `user` & `tag`) — menggunakan REST Assured

Keduanya ditulis dalam format **BDD/Gherkin** menggunakan **Cucumber**, dijalankan lewat **TestNG**, dikelola dengan
**Gradle**, dan menghasilkan laporan lewat **Allure Report** + **Cucumber HTML/JSON report**.

---

## 1. Tech Stack

| Komponen             | Teknologi                              |
|------------------------|------------------------------------------|
| Bahasa                 | Java 21                                  |
| Build tool              | Gradle (Groovy DSL)                      |
| Test runner             | TestNG (`cucumber-testng`)               |
| BDD framework           | Cucumber (Gherkin syntax)                |
| Web automation          | Selenium WebDriver 4 + WebDriverManager  |
| API automation          | REST Assured                             |
| Design pattern           | Page Object Model (POM) — untuk Web       |
| Test report             | Allure Report + Cucumber HTML/JSON       |
| CI/CD                   | GitHub Actions                           |
| IDE                     | IntelliJ IDEA                            |
| Repository dependensi    | Maven Central                            |

---

## 2. Struktur Proyek

```
qa-final-web-api-automation/
├── build.gradle                          # Dependensi + 2 task khusus: apiTest & webTest
├── settings.gradle
├── .github/workflows/main.yml            # GitHub Actions (manual trigger + Pull Request)
├── src/test/
│   ├── java/com/qafinal/
│   │   ├── web/                          # ============ WEB UI (Selenium) ============
│   │   │   ├── pages/                        # ---- Page Object Model ----
│   │   │   │   ├── BasePage.java                 # fungsi umum + handle JS alert
│   │   │   │   ├── HomePage.java                 # navbar, modal sign up/login, kategori
│   │   │   │   ├── ProductPage.java               # halaman detail produk
│   │   │   │   └── CartPage.java                  # halaman keranjang belanja
│   │   │   ├── stepdefinitions/               # ---- Step Definitions ----
│   │   │   │   ├── AuthSteps.java                 # sign up & login
│   │   │   │   └── CartSteps.java                 # browsing produk & keranjang
│   │   │   ├── hooks/WebHooks.java             # setup/teardown WebDriver + screenshot
│   │   │   ├── runners/WebTestRunner.java      # entry point TestNG, tag @web
│   │   │   └── utils/DriverFactory.java        # pembuatan & pengelolaan WebDriver
│   │   │
│   │   └── api/                          # ============ API (REST Assured) ============
│   │       ├── client/                        # ---- HTTP client wrapper ----
│   │       │   ├── UserApiClient.java             # GET/POST/PUT/DELETE /user
│   │       │   └── TagApiClient.java              # GET /tag
│   │       ├── config/ApiConfig.java           # base URL & app-id
│   │       ├── context/ApiTestContext.java     # berbagi Response antar step class
│   │       ├── stepdefinitions/               # ---- Step Definitions ----
│   │       │   ├── UserApiSteps.java              # request Create/Get/Update/Delete User
│   │       │   ├── TagApiSteps.java               # request Get List of Tags
│   │       │   └── CommonApiSteps.java            # validasi status code & response body (shared)
│   │       ├── hooks/ApiHooks.java             # cleanup context per scenario
│   │       └── runners/ApiTestRunner.java      # entry point TestNG, tag @api
│   │
│   └── resources/
│       ├── features/
│       │   ├── web/                          # ---- Test Case Web (Gherkin) ----
│       │   │   ├── auth.feature                  # sign up & login (positive/negative/boundary)
│       │   │   └── cart.feature                   # tambah produk ke keranjang
│       │   └── api/                          # ---- Test Case API (Gherkin) ----
│       │       ├── user.feature                   # CRUD user (positive/negative/boundary)
│       │       └── tag.feature                    # get list of tags
│       └── suites/
│           ├── testng-web.xml                # dipakai oleh Gradle task `webTest`
│           └── testng-api.xml                # dipakai oleh Gradle task `apiTest`
├── .gitignore
└── README.md
```

**Poin penting struktur:** kode Java, step definitions, dan feature file untuk Web dan API dipisah total ke dalam
package/folder `web/` dan `api/` masing-masing — tidak saling bercampur.

---

## 3. Page Object Model (Web)

| Halaman             | File               | Contoh fungsi                                                        |
|-----------------------|----------------------|--------------------------------------------------------------------------|
| Home (+ modal auth)    | `HomePage.java`       | `openSignUpModal()`, `fillLoginForm()`, `selectCategory()`, `openCartPage()` |
| Detail Produk          | `ProductPage.java`    | `getProductName()`, `addToCart()`                                        |
| Keranjang               | `CartPage.java`       | `getCartRowCount()`, `getProductNamesInCart()`, `getTotalPrice()`         |

Semua locator memakai `By.id`/`By.className`/`By.linkText`/`By.cssSelector` yang stabil (bukan XPath rapuh berbasis
posisi), kecuali tombol "Add to cart" yang memang tidak memiliki id unik di demoblaze sehingga memakai XPath
berbasis teks.

`BasePage` menyediakan fungsi umum (`click`, `type`, `getText`, `isDisplayed`) plus `getAlertTextAndAccept()` khusus
untuk menangani notifikasi native JS `alert()` yang dipakai demoblaze untuk sign up/login/add-to-cart.

---

## 4. Validasi Response API

Validasi dilakukan lewat `CommonApiSteps.java` (dipakai bersama oleh `user.feature` & `tag.feature` agar tidak
duplikasi step definition):

- **Status code** — `response.getStatusCode()` dibandingkan dengan status code yang diharapkan (200, 400, 404, dst)
- **Keberadaan field** — `response.jsonPath().get(field)` dipastikan tidak null (mis. field `id`, `data`, `total`)
- **Nilai field spesifik** — `response.jsonPath().getString(field)` dibandingkan dengan nilai yang diharapkan (mis.
  memastikan `firstName` benar-benar berubah setelah Update User)

Response API disimpan sementara di `ApiTestContext` (per-thread) agar bisa diakses lintas step definition class
dalam satu scenario yang sama (mis. `UserApiSteps` yang mengirim request, lalu `CommonApiSteps` yang memvalidasi).

---

## 5. Skenario Test yang Dicakup

### Web UI — `auth.feature`
| Tipe      | Skenario                                                    |
|-----------|-----------------------------------------------------------------|
| Positive  | Sign up dengan username unik; Login dengan akun yang baru dibuat |
| Negative  | Sign up dengan username yang sudah ada; Login dengan password salah |
| Boundary  | Login dengan username yang belum pernah didaftarkan               |

### Web UI — `cart.feature`
| Tipe      | Skenario                                                        |
|-----------|----------------------------------------------------------------------|
| Positive  | Tambah produk dari kategori Phones/Laptops, cek muncul di keranjang & total harga |
| Boundary  | Keranjang kosong ketika belum ada produk ditambahkan                   |

### API — `user.feature`
| Tipe      | Skenario                                                       |
|-----------|-----------------------------------------------------------------|
| Positive  | Create User, Get User by ID, Update User, Delete User            |
| Negative  | Create User tanpa field wajib `firstName`; Get User dengan id tidak terdaftar |
| Boundary  | Get User dengan format id tidak valid                             |

### API — `tag.feature`
| Tipe      | Skenario                                              |
|-----------|-------------------------------------------------------|
| Positive  | Get List of Tags berhasil                              |
| Boundary  | Struktur response sesuai kontrak pagination (`data`, `total`, `page`, `limit`) |

---

## 6. Prasyarat

- JDK 21
- Google Chrome (untuk Web test)
- **App ID dari DummyAPI** (wajib untuk API test):
  1. Daftar gratis di [https://dummyapi.io](https://dummyapi.io)
  2. Ambil `app-id` dari halaman akun kamu
  3. Simpan untuk dipakai di langkah run test berikutnya

---

## 7. Cara Menjalankan Test

### A. Menjalankan SEMUA test (Web + API)
```bash
./gradlew clean test -Dapp.id=YOUR_APP_ID
```

### B. Menjalankan HANYA test API (tag @api)
```bash
./gradlew apiTest -Dapp.id=YOUR_APP_ID
```

### C. Menjalankan HANYA test Web UI (tag @web)
```bash
./gradlew webTest
```

> Alternatif: set environment variable `APP_ID` sekali di terminal (`export APP_ID=xxx` / `set APP_ID=xxx` di
> Windows) supaya tidak perlu mengetik `-Dapp.id=` setiap kali menjalankan test.

### Menjalankan lewat IntelliJ IDEA
Klik kanan `WebTestRunner.java` atau `ApiTestRunner.java` di folder `runners/` masing-masing → **Run**.
Untuk API test lewat IntelliJ, tambahkan VM option `-Dapp.id=YOUR_APP_ID` di Run Configuration.

---

## 8. Melihat Laporan

### Allure Report (gabungan Web + API)
```bash
./gradlew allureServe
```
<img width="1710" height="1029" alt="Screenshot 2026-08-11 at 17 50 00" src="https://github.com/user-attachments/assets/7c6f3c24-2299-47ae-946f-b2aafaab224b" />



Otomatis membuka laporan interaktif di browser: ringkasan pass/fail, breakdown per feature, waktu eksekusi, dan
screenshot otomatis untuk scenario Web yang gagal.

### Cucumber Report (HTML & JSON)
Setelah test dijalankan, laporan tersedia di:
- Web: `build/cucumber-report/web/cucumber.html` & `cucumber.json`
- API: `build/cucumber-report/api/cucumber.html` & `cucumber.json`

---

## 9. CI/CD — GitHub Actions

Workflow di `.github/workflows/main.yml` berjalan pada 2 event:
1. **Manual trigger** — buka tab **Actions** di GitHub → pilih workflow **"Web & API Automation CI"** → **Run workflow**
2. **Setiap ada Pull Request** (dibuat/diupdate) ke branch `main`/`master`

**Sebelum menjalankan workflow, set secret berikut** (Settings → Secrets and variables → Actions → New repository secret):

| Secret name | Value                                   |
|-------------|-------------------------------------------|
| `APP_ID`    | app-id akun DummyAPI kamu (dari langkah 6) |

Tahapan job: checkout → setup JDK 21 & Chrome → `./gradlew webTest` → `./gradlew apiTest` → generate Allure report →
upload 3 artefak (`allure-report`, `cucumber-report`, `raw-test-results`) yang bisa didownload dari halaman run di
tab Actions.

> **Penting:** workflow memanggil `./gradlew`, jadi pastikan file `gradlew`, `gradlew.bat`, dan folder
> `gradle/wrapper/` sudah di-generate (`gradle wrapper --gradle-version 8.10`) dan ikut di-commit ke repository.

---

## 10. Author

Alda Giot Marito Lumban Gaol
Aug2026 