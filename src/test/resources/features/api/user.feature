Feature: User API - DummyAPI
  Sebagai QA Engineer
  Saya ingin menguji endpoint User pada DummyAPI (https://dummyapi.io/docs/user)
  Agar operasi Create, Get, Update, dan Delete user berjalan sesuai kontrak API

  # ============================== CREATE USER ==============================
  @api @positive
  Scenario: Create User - berhasil membuat user baru dengan data valid
    Given saya menyiapkan data user baru yang valid
    When saya mengirim request Create User
    Then response status code adalah 200
    And response body memuat field "id"
    And response body memuat field "firstName"

  @api @negative
  Scenario: Create User - gagal karena field wajib "firstName" tidak diisi
    Given saya menyiapkan data user baru tanpa field "firstName"
    When saya mengirim request Create User
    Then response status code adalah 400

  # ============================== GET USER BY ID ==============================
  @api @positive
  Scenario: Get User by ID - berhasil mengambil data user yang valid
    Given saya sudah membuat user baru melalui API
    When saya mengirim GET request untuk user tersebut
    Then response status code adalah 200
    And response body memuat field "id"

  @api @negative
  Scenario: Get User by ID - gagal karena id tidak terdaftar
    Given id user "000000000000000000000000" tidak terdaftar di DummyAPI
    When saya mengirim GET request untuk user tersebut
    Then response status code adalah 404

  @api @boundary
  Scenario: Get User by ID - id dengan format tidak valid (bukan MongoDB ObjectId)
    Given id user "id-format-tidak-valid-!!!" tidak terdaftar di DummyAPI
    When saya mengirim GET request untuk user tersebut
    Then response status code bukan 200

  # ============================== UPDATE USER ==============================
  @api @positive
  Scenario: Update User - berhasil mengubah data user
    Given saya sudah membuat user baru melalui API
    When saya update field "firstName" user tersebut menjadi "UpdatedName"
    Then response status code adalah 200
    And field "firstName" pada response bernilai "UpdatedName"

  # ============================== DELETE USER ==============================
  @api @positive
  Scenario: Delete User - berhasil menghapus user yang sudah dibuat
    Given saya sudah membuat user baru melalui API
    When saya menghapus user tersebut
    Then response status code adalah 200
