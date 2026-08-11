Feature: Tag API - DummyAPI
  Sebagai QA Engineer
  Saya ingin menguji endpoint Tag pada DummyAPI (https://dummyapi.io/docs/tag)
  Agar daftar tag yang dikembalikan sesuai kontrak API

  @api @positive
  Scenario: Get List of Tags - berhasil mengambil daftar tag
    When saya mengirim GET request untuk daftar tag
    Then response status code adalah 200
    And response body memuat field "data"

  @api @boundary
  Scenario: Get List of Tags - memastikan field data berisi list tag yang tidak kosong
    When saya mengirim GET request untuk daftar tag
    Then response status code adalah 200
    And field "data" pada response berupa list yang tidak kosong
