Feature: Tambah Produk ke Keranjang - Demoblaze
  Sebagai pengunjung Demoblaze
  Saya ingin menambahkan produk ke keranjang belanja
  Agar saya bisa melanjutkan proses checkout

  Background:
    Given saya membuka halaman utama Demoblaze

  @web @positive
  Scenario: Berhasil menambahkan produk ke keranjang dari kategori Phones
    When saya memilih kategori "Phones"
    And saya membuka produk "Samsung galaxy s6"
    And saya menambahkan produk tersebut ke keranjang
    Then muncul notifikasi "Product added"
    And saya membuka halaman keranjang
    And produk "Samsung galaxy s6" muncul di daftar keranjang

  @web @positive
  Scenario: Total harga di halaman keranjang tidak nol setelah menambahkan produk
    When saya memilih kategori "Laptops"
    And saya membuka produk "Sony vaio i5"
    And saya menambahkan produk tersebut ke keranjang
    And saya membuka halaman keranjang
    Then total harga di halaman keranjang lebih besar dari 0

  @web @boundary
  Scenario: Halaman keranjang kosong ketika belum ada produk ditambahkan
    When saya membuka halaman keranjang
    Then jumlah baris produk di halaman keranjang adalah 0
