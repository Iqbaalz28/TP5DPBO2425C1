// Import library Swing dan SQL yang diperlukan
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.sql.*;

// === CLASS UTAMA ===
public class ProductMenu extends JFrame {

    // === METHOD MAIN ===
    public static void main(String[] args) {
        // Membuat dan menampilkan window utama aplikasi
        ProductMenu menu = new ProductMenu();
        menu.setSize(750, 600);                     // Atur ukuran window
        menu.setLocationRelativeTo(null);           // Letakkan di tengah layar
        menu.setContentPane(menu.mainPanel);        // Set panel utama
        menu.getContentPane().setBackground(Color.WHITE); // Warna background putih
        menu.setVisible(true);                      // Tampilkan window
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Tutup aplikasi saat window ditutup
    }

    // === DEKLARASI VARIABEL ===
    private int selectedIndex = -1;                 // Indeks baris tabel yang sedang dipilih
    private Database database;                      // Objek koneksi database
    private JPanel mainPanel;                       // Panel utama aplikasi
    private JTextField idField;                     // Input ID produk
    private JTextField namaField;                   // Input nama produk
    private JTextField hargaField;                  // Input harga produk
    private JTable productTable;                    // Tabel untuk menampilkan data produk
    private JButton addUpdateButton;                // Tombol Add/Update data
    private JButton cancelButton;                   // Tombol Reset form
    private JButton deleteButton;                   // Tombol Delete data
    private JComboBox<String> kategoriComboBox;     // Dropdown kategori produk
    private JComboBox<String> kondisiComboBox;      // Dropdown kondisi produk
    private JLabel titleLabel;                      // Label judul
    private JLabel idLabel, namaLabel, hargaLabel, kategoriLabel, kondisiLabel; // Label form

    // === CONSTRUCTOR ===
    public ProductMenu() {
        // Inisialisasi koneksi ke database
        database = new Database();

        // Atur tabel agar langsung menampilkan data dari DB
        productTable.setModel(setTable());

        // Styling judul form
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // Isi ComboBox kategori
        String[] kategoriData = {"Pilih Kategori", "Smartphone", "Laptop", "Elektronik", "Makanan", "Minuman", "Pakaian", "Alat Tulis"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // Isi ComboBox kondisi
        String[] kondisiData = {"Baru", "Bekas"};
        kondisiComboBox.setModel(new DefaultComboBoxModel<>(kondisiData));

        // Tombol Delete awalnya disembunyikan
        deleteButton.setVisible(false);

        // === AKSI TOMBOL ADD/UPDATE ===
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData(); // Jika belum memilih data, maka INSERT
                } else {
                    updateData(); // Jika sudah memilih data, maka UPDATE
                }
            }
        });

        // === AKSI TOMBOL DELETE ===
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData(); // Hapus data produk
            }
        });

        // === AKSI TOMBOL CANCEL ===
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm(); // Reset form input
            }
        });

        // === AKSI KLIK PADA TABEL ===
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Ambil baris yang diklik
                selectedIndex = productTable.getSelectedRow();

                // Ambil nilai kolom dari baris yang dipilih
                String curId = productTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curNama = productTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curHarga = productTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curKategori = productTable.getModel().getValueAt(selectedIndex, 4).toString();
                String curKondisi = productTable.getModel().getValueAt(selectedIndex, 5).toString();

                // Tampilkan di form input
                idField.setText(curId);
                namaField.setText(curNama);
                hargaField.setText(curHarga);
                kategoriComboBox.setSelectedItem(curKategori);
                kondisiComboBox.setSelectedItem(curKondisi);

                // Ubah tombol menjadi "Update"
                addUpdateButton.setText("Update");
                deleteButton.setVisible(true);
            }
        });
    }

    // === MEMBANGUN MODEL TABEL ===
    public final DefaultTableModel setTable() {
        // Judul kolom tabel
        Object[] cols = {"No", "ID Produk", "Nama", "Harga", "Kategori", "Kondisi"};
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        try {
            // Ambil data dari tabel product di database
            ResultSet resultSet = database.selectQuery("SELECT * FROM product");
            // Nomor urut
            int i = 1;
            // formatter mata uang
            NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            while (resultSet.next()) {
                Object[] row = new Object[6];
                row[0] = i;
                row[1] = resultSet.getString("id");
                row[2] = resultSet.getString("nama");
                double harga = resultSet.getDouble("harga");
                row[3] = rupiahFormat.format(harga);
                row[4] = resultSet.getString("kategori");
                row[5] = resultSet.getString("kondisi");
                tmp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil data: " + e.getMessage());
        }

        return tmp;
    }

    // === INSERT DATA ===
    public void insertData() {
        try {
            // Ambil nilai input dari form
            String id = idField.getText().trim();
            String nama = namaField.getText().trim();
            String hargaText = hargaField.getText().trim();
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String kondisi = kondisiComboBox.getSelectedItem().toString();

            // ✅ Validasi input kosong
            if (id.isEmpty() || nama.isEmpty() || hargaText.isEmpty() || kategori.equals("Pilih Kategori")) {
                JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ✅ Cek apakah ID sudah ada di database
            ResultSet rs = database.selectQuery("SELECT * FROM product WHERE id = '" + id + "'");
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "ID Produk sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parsing harga
            double harga = Double.parseDouble(hargaText);

            // Query SQL Insert
            String sqlQuery = "INSERT INTO product (id, nama, harga, kategori, kondisi) VALUES ('" +
                    id + "', '" + nama + "', " + harga + ", '" + kategori + "', '" + kondisi + "')";

            // Jalankan query insert
            database.insertUpdateDeleteQuery(sqlQuery);

            // Refresh tabel dan bersihkan form
            productTable.setModel(setTable());
            clearForm();

            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Kesalahan database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // === UPDATE DATA ===
    public void updateData() {
        try {
            // Ambil data dari form
            String id = idField.getText().trim();
            String nama = namaField.getText().trim();
            String hargaText = hargaField.getText().trim();
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String kondisi = kondisiComboBox.getSelectedItem().toString();

            // ✅ Validasi input kosong
            if (id.isEmpty() || nama.isEmpty() || hargaText.isEmpty() || kategori.equals("Pilih Kategori")) {
                JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double harga = Double.parseDouble(hargaText);

            // Query update data di database
            String sqlQuery = "UPDATE product SET " +
                    "nama = '" + nama + "', harga = " + harga + ", kategori = '" + kategori + "', kondisi = '" + kondisi + "' " +
                    "WHERE id = '" + id + "'";

            // Jalankan query
            database.insertUpdateDeleteQuery(sqlQuery);

            // Refresh tabel dan bersihkan form
            productTable.setModel(setTable());
            clearForm();

            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // === DELETE DATA ===
    public void deleteData() {
        try {
            String id = idField.getText().trim();

            // Pastikan ada ID yang dipilih
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Konfirmasi hapus
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Apakah Anda yakin ingin menghapus produk dengan ID: " + id + "?",
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION
            );

            // Jika user menekan YES
            if (confirm == JOptionPane.YES_OPTION) {
                // Jalankan query delete
                String sqlQuery = "DELETE FROM product WHERE id = '" + id + "'";
                database.insertUpdateDeleteQuery(sqlQuery);

                // Refresh tabel dan bersihkan form
                productTable.setModel(setTable());
                clearForm();

                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Kesalahan saat menghapus: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // === MENGHAPUS ISI FORM ===
    public void clearForm() {
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        kondisiComboBox.setSelectedIndex(0);

        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);
        selectedIndex = -1;
    }
}