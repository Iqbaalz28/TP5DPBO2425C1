# TP5DPBO2425C1
Project ini merupakan aplikasi manajemen data produk berbasis Java Swing yang terintegrasi dengan database MySQL. Aplikasi ini dibuat untuk memenuhi tugas praktikum TP5 DPBO 2024/2025 (C1) dengan tujuan mempraktikkan konsep CRUD (Create, Read, Update, Delete) pada sistem berbasis GUI. Pengguna dapat menambahkan, memperbarui, menghapus, dan melihat daftar produk secara langsung melalui antarmuka grafis yang interaktif. Setiap perubahan data secara otomatis disimpan dan diambil dari database db_product.

## 🙏🏻 Janji
Saya Iqbal Rizky Maulana dengan NIM 2408622 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## 💡 Fitur Utama
- Menampilkan data produk dalam tabel GUI.
- Menambahkan produk baru ke database (Create).
- Memperbarui data produk yang sudah ada (Update).
- Menghapus data produk dari database (Delete).
- Validasi input agar tidak ada kolom kosong dan ID tidak boleh duplikat.
- Antarmuka modern dengan komponen JTable, JComboBox, dan JOptionPane.

## 🗄️ Struktur Database

**Database:** `db_product`  
**Tabel:** `product`

| Kolom      | Tipe Data      | Keterangan                 |
|------------|----------------|----------------------------|
| `id`       | `VARCHAR(10)`  | Primary Key                |
| `nama`     | `VARCHAR(255)` | Nama produk                |
| `harga`    | `DOUBLE`       | Harga produk               |
| `kategori` | `VARCHAR(50)`  | Jenis produk               |
| `kondisi`  | `VARCHAR(20)`  | Status produk (Baru/Bekas) |

## 🧰 Teknologi yang Digunakan
| Komponen | Keterangan |
|-----------|------------|
| ☕ **Java Swing** | Untuk membangun tampilan GUI |
| 🗄️ **MySQL** | Sebagai database penyimpanan data produk |
| 🔗 **JDBC (Java Database Connectivity)** | Menghubungkan Java dengan MySQL |
| 💻 **IntelliJ IDEA** | IDE utama pengembangan project |
| 📦 **mysql-connector-j-9.4.0.jar** | Library konektor untuk MySQL |

## 📁 Class
| Nama Class        | Fungsi Utama                                     |
| ----------------- | ------------------------------------------------ |
| **`Product`**     | Menyimpan struktur data produk (model/entitas).  |
| **`Database`**    | Mengatur koneksi dan eksekusi query ke MySQL.    |
| **`ProductMenu`** | Mengelola tampilan GUI dan logika CRUD aplikasi. |

## 🧠 Atribut dan Deskripsi
- Product.java
  | Atribut    | Tipe Data | Deskripsi                   |
  | ---------- | --------- | --------------------------- |
  | `id`       | `String`  | ID unik produk              |
  | `nama`     | `String`  | Nama produk                 |
  | `harga`    | `double`  | Harga produk                |
  | `kategori` | `String`  | Jenis kategori produk       |
  | `kondisi`  | `String`  | Kondisi produk (Baru/Bekas) |

- Database.java
  | Atribut      | Tipe Data    | Deskripsi                  |
  | ------------ | ------------ | -------------------------- |
  | `connection` | `Connection` | Menyimpan koneksi ke MySQL |
  | `statement`  | `Statement`  | Menjalankan query SQL      |

  | Method                                | Deskripsi                                                    |
  | ------------------------------------- | ------------------------------------------------------------ |
  | `Database()`                          | Konstruktor, membuka koneksi JDBC ke MySQL                   |
  | `selectQuery(String sql)`             | Menjalankan perintah **SELECT**, mengembalikan `ResultSet`   |
  | `insertUpdateDeleteQuery(String sql)` | Menjalankan perintah **INSERT**, **UPDATE**, atau **DELETE** |
  | `getStatement()`                      | Mengembalikan objek statement aktif                          |

- ProductMenu.java
  | Atribut                                                                             | Komponen            | Deskripsi                                 |
  | ----------------------------------------------------------------------------------- | ------------------- | ----------------------------------------- |
  | `mainPanel`                                                                         | `JPanel`            | Panel utama aplikasi                      |
  | `idField`, `namaField`, `hargaField`                                                | `JTextField`        | Input data produk                         |
  | `kategoriComboBox`, `kondisiComboBox`                                               | `JComboBox<String>` | Pilihan kategori & kondisi                |
  | `productTable`                                                                      | `JTable`            | Menampilkan data produk dari database     |
  | `addUpdateButton`, `deleteButton`, `cancelButton`                                   | `JButton`           | Tombol aksi CRUD                          |
  | `titleLabel`, `idLabel`, `namaLabel`, `hargaLabel`, `kategoriLabel`, `kondisiLabel` | `JLabel`            | Label teks GUI                            |
  | `database`                                                                          | `Database`          | Objek koneksi database                    |
  | `selectedIndex`                                                                     | `int`               | Menyimpan indeks baris tabel yang dipilih |

## 🪟 Komponen GUI

<img width="1365" height="720" alt="image" src="https://github.com/user-attachments/assets/dff76897-efae-42df-8247-05902db9c1fe" />

| Komponen                 | Fungsi                                                          |
| ------------------------ | --------------------------------------------------------------- |
| **JPanel (`mainPanel`)** | Panel utama untuk seluruh elemen GUI                            |
| **JLabel**               | Label teks penjelas tiap field                                  |
| **JTextField**           | Input ID, Nama, dan Harga produk                                |
| **JComboBox**            | Dropdown untuk memilih Kategori dan Kondisi                     |
| **JButton**              | Tombol untuk menambah, memperbarui, menghapus, atau membatalkan |
| **JTable**               | Menampilkan seluruh data produk dari database                   |
| **JScrollPane**          | Membungkus JTable agar bisa di-scroll                           |

## ⚙️ Method Utama di ProductMenu
| Method             | Fungsi                                                     |
| ------------------ | ---------------------------------------------------------- |
| **`setTable()`**   | Menampilkan seluruh data dari database ke JTable           |
| **`insertData()`** | Menambahkan data produk baru ke database (dengan validasi) |
| **`updateData()`** | Memperbarui data produk berdasarkan ID                     |
| **`deleteData()`** | Menghapus data produk dari database berdasarkan ID         |
| **`clearForm()`**  | Menghapus semua input dan mengatur ulang tombol            |
| **`main()`**       | Menjalankan GUI utama aplikasi                             |

## 🔄 Alur Program

### 1. Program Dijalankan

- Method main() memanggil konstruktor ProductMenu().
- Window GUI utama muncul di tengah layar.

### 2. Menampilkan Data

- Saat program dimulai, method setTable() dijalankan.
- Aplikasi mengambil seluruh data produk dari tabel product di database dan menampilkannya pada JTable.

### 3. Menambah Data

- Pengguna mengisi kolom input lalu menekan tombol Add.
- Program akan:
  - Mengecek apakah ada kolom kosong.
  - Mengecek apakah ID sudah ada di database.
  - Jika valid, menjalankan query:
  ```
  INSERT INTO product (id, nama, harga, kategori, kondisi) VALUES (...);
  ```
  - Menampilkan pesan sukses dan memperbarui tabel.

### 4. Memperbarui Data

- Pengguna memilih data pada tabel → isi form otomatis terisi.
- Setelah mengubah data, klik Update.
- Program menjalankan query:
  ```
  UPDATE product SET nama='...', harga=..., kategori='...', kondisi='...' WHERE id='...';
  ```
- Data tabel diperbarui otomatis. 

### 5. Menghapus Data

- Pengguna menekan tombol Delete.
- Muncul dialog konfirmasi (JOptionPane).
- Jika disetujui, program menjalankan:
  ```
  DELETE FROM product WHERE id='...';
  ```
- Tabel diperbarui.

### 6. Membatalkan Input

- Tombol Cancel akan mengosongkan semua field input dan mengembalikan tombol Add seperti semula.

## 📸 DOKUMENTASI

### 1. Read

- Melalui IDE:

  <img width="723" height="356" alt="image" src="https://github.com/user-attachments/assets/f10e4089-96b7-4faf-aab5-56995acdc5a4" />

- Melalui PHPMyAdmin

  <img width="615" height="257" alt="image" src="https://github.com/user-attachments/assets/ad5d77e2-08ce-492f-a23e-dbe41e0d9412" />

### 2. Add

- Menambahkan Data Lengkap

  <img width="523" height="349" alt="image" src="https://github.com/user-attachments/assets/5ad15d27-6016-41ce-95f8-dbdd82045eaf" />
  
  <img width="531" height="176" alt="image" src="https://github.com/user-attachments/assets/363b0887-71a2-4fe2-99a8-3004359d5cd9" />
  
  <img width="611" height="269" alt="image" src="https://github.com/user-attachments/assets/042023ef-94e3-40f8-805b-c05390d4d425" />

- Menambahkan Data Tidak Lengkap

  <img width="719" height="365" alt="image" src="https://github.com/user-attachments/assets/eb3fd259-8aee-4291-a3a1-520bfe68c158" />

  <img width="550" height="298" alt="image" src="https://github.com/user-attachments/assets/0d104339-5916-42cf-82d2-921997abffdc" />

### 3. Update

- Update Data Lengkap

  <img width="576" height="274" alt="image" src="https://github.com/user-attachments/assets/6cd837c2-225c-4fa1-a4b3-c95e8f6962b0" />

  <img width="526" height="203" alt="image" src="https://github.com/user-attachments/assets/dc8f3f02-32d5-43cf-a7f6-edb242494d48" />

  <img width="614" height="114" alt="image" src="https://github.com/user-attachments/assets/774ba2e6-8a41-40ed-a4c6-c6b37c73177a" />

- Update Data Tidak Lengkap

  <img width="531" height="375" alt="image" src="https://github.com/user-attachments/assets/29b4359a-8095-4787-8ccd-c4ba8f7a09c7" />

  <img width="536" height="271" alt="image" src="https://github.com/user-attachments/assets/0306bf12-c22b-406b-ac4e-45b1a18f291c" />
  
### 4. Delete

  <img width="561" height="367" alt="image" src="https://github.com/user-attachments/assets/5c15659e-2bdb-46bf-85f5-a30701da2d99" />

  <img width="549" height="346" alt="image" src="https://github.com/user-attachments/assets/304a5bea-b7a7-412f-b602-faabaac4c2c7" />

