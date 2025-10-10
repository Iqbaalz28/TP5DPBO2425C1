import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ProductMenu extends JFrame {
    public static void main(String[] args) {
        // Membuat window utama
        ProductMenu menu = new ProductMenu();

        // Atur ukuran window
        menu.setSize(750, 600);

        // Letakkan di tengah layar
        menu.setLocationRelativeTo(null);

        // Set panel utama
        menu.setContentPane(menu.mainPanel);

        // Ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // Tampilkan window
        menu.setVisible(true);

        // Tutup program saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Variabel
    private int selectedIndex = -1;
    private ArrayList<Product> listProduct;

    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField hargaField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel namaLabel;
    private JLabel hargaLabel;
    private JLabel kategoriLabel;

    // Tambahan komponen untuk atribut kondisi
    private JComboBox<String> kondisiComboBox;
    private JLabel kondisiLabel;

    // Constructor
    public ProductMenu() {
        // Inisialisasi data produk
        listProduct = new ArrayList<>();
        populateList();

        // Isi tabel produk
        productTable.setModel(setTable());

        // Styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // Isi ComboBox kategori
        String[] kategoriData = {"???", "Smartphone", "Laptop", "Elektronik", "Makanan", "Minuman", "Pakaian", "Alat Tulis" };
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // Isi ComboBox kondisi
        String[] kondisiData = {"Baru", "Bekas"};
        kondisiComboBox.setModel(new DefaultComboBoxModel<>(kondisiData));

        // Sembunyikan tombol delete
        deleteButton.setVisible(false);

        // Tombol Add/Update
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // Tombol Delete
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 🔒 Konfirmasi sebelum hapus
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Apakah Anda yakin ingin menghapus data ini?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });

        // Tombol Cancel
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // Klik pada tabel
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = productTable.getSelectedRow();

                String curId = productTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curNama = productTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curHarga = productTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curKategori = productTable.getModel().getValueAt(selectedIndex, 4).toString();
                String curKondisi = productTable.getModel().getValueAt(selectedIndex, 5).toString();

                idField.setText(curId);
                namaField.setText(curNama);
                hargaField.setText(curHarga);
                kategoriComboBox.setSelectedItem(curKategori);
                kondisiComboBox.setSelectedItem(curKondisi);

                addUpdateButton.setText("Update");
                deleteButton.setVisible(true);
            }
        });
    }

    // === Membuat model tabel ===
    public final DefaultTableModel setTable() {
        Object[] cols = {"No", "ID Produk", "Nama", "Harga", "Kategori", "Kondisi"};
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        for (int i = 0; i < listProduct.size(); i++) {
            Object[] row = {
                    i + 1,
                    listProduct.get(i).getId(),
                    listProduct.get(i).getNama(),
                    String.format("%.2f", listProduct.get(i).getHarga()),
                    listProduct.get(i).getKategori(),
                    listProduct.get(i).getKondisi()
            };
            tmp.addRow(row);
        }
        return tmp;
    }

    // === INSERT ===
    public void insertData() {
        try {
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String kondisi = kondisiComboBox.getSelectedItem().toString();

            listProduct.add(new Product(id, nama, harga, kategori, kondisi));
            productTable.setModel(setTable());
            clearForm();

            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // === UPDATE ===
    public void updateData() {
        try {
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String kondisi = kondisiComboBox.getSelectedItem().toString();

            listProduct.get(selectedIndex).setId(id);
            listProduct.get(selectedIndex).setNama(nama);
            listProduct.get(selectedIndex).setHarga(harga);
            listProduct.get(selectedIndex).setKategori(kategori);
            listProduct.get(selectedIndex).setKondisi(kondisi);

            productTable.setModel(setTable());
            clearForm();

            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // === DELETE ===
    public void deleteData() {
        listProduct.remove(selectedIndex);
        productTable.setModel(setTable());
        clearForm();

        JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
    }

    // === CLEAR FORM ===
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

    // === POPULATE LIST ===
    private void populateList() {
        listProduct.add(new Product("P001", "iPhone 15 Pro", 21000000.0, "Smartphone", "Baru"));
        listProduct.add(new Product("P002", "Samsung Galaxy S24", 18000000.0, "Smartphone", "Baru"));
        listProduct.add(new Product("P003", "Asus ROG Phone 8", 16500000.0, "Smartphone", "Baru"));
        listProduct.add(new Product("P004", "Xiaomi 14 Ultra", 15000000.0, "Smartphone", "Baru"));
        listProduct.add(new Product("P005", "OPPO Reno 11", 9000000.0, "Smartphone", "Bekas"));
        listProduct.add(new Product("P006", "Google Pixel 8", 13000000.0, "Smartphone", "Baru"));
        listProduct.add(new Product("P007", "Vivo X100", 11000000.0, "Smartphone", "Bekas"));
    }
}