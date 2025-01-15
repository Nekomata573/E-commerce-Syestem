package uas.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Aplikasi E-Commerce Sederhana
 * Program ini mensimulasikan sistem e-commerce sederhana dengan fitur:
 * 1. Sistem Login dan Registrasi
 * 2. Menu Admin - untuk mengelola barang dan pengguna
 * 3. Menu Pengguna - untuk transaksi jual beli
 * 
 * Dibuat oleh: [Nama Anda]
 * Tanggal: [Tanggal]
 */
public class UASJAVA {
    // ========== BAGIAN 1: VARIABEL GLOBAL ==========
    // Menyimpan data username dan password
    private static Map<String, String> akunPengguna = new HashMap<>();
    // Menyimpan data saldo setiap pengguna
    private static Map<String, Double> saldoPengguna = new HashMap<>();
    // Menyimpan daftar barang dan harganya
    private static Map<String, Double> daftarBarang = new HashMap<>();
    // Menyimpan inventori barang setiap pengguna
    private static Map<String, String> inventoriPengguna = new HashMap<>();

    // ========== BAGIAN 2: FUNGSI UTILITAS ==========
    /**
     * Membersihkan layar console
     */
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Memberikan jeda dan menunggu input Enter dari pengguna
     */
    private static void systemPause() {
        System.out.println("\nTekan Enter untuk melanjutkan...");
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.nextLine();
        }
    }

    /**
     * Memeriksa apakah string berisi angka
     */
    private static boolean isAngka(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * Memeriksa apakah string berisi angka desimal
     */
    private static boolean isAngkaDesimal(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ========== BAGIAN 3: INISIALISASI DATA ==========
    /**
     * Menginisialisasi data awal untuk testing
     * Menambahkan akun admin dan contoh data
     */
    private static void inisialisasiDataAwal() {
        // Akun default
        akunPengguna.put("admin", "admin123");
        akunPengguna.put("user1", "pass123");
        
        // Saldo awal
        saldoPengguna.put("user1", 1000.0);
        
        // Barang default
        daftarBarang.put("Laptop", 5000.0);
        daftarBarang.put("HP", 2000.0);
        daftarBarang.put("Tablet", 1500.0);
    }

    // ========== BAGIAN 4: MENU UTAMA ==========
    /**
     * Method utama yang menjalankan program
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        inisialisasiDataAwal();

        while (true) {
            System.out.println("\n+=================+");
            System.out.println("|    Menu Utama   |");
            System.out.println("+=================+");
            System.out.println("| 1. Login        |");
            System.out.println("| 2. Daftar       |");
            System.out.println("| 3. Keluar       |");
            System.out.println("+=================+");
            System.out.print("| Pilih: ");
            String pilihan = input.nextLine().trim();
            System.out.println("+=================+");
            
            if (!isAngka(pilihan)) {
                System.out.println("\n[!] Masukkan angka yang valid");
                continue;
            }
            
            int menu = Integer.parseInt(pilihan);
            switch (menu) {
                case 1:
                    prosesLogin(input);
                    break;
                case 2:
                    buatAkunBaru(input);
                    break;
                case 3:
                    System.out.println("\n+------------------+");
                    System.out.println("| Terima kasih! ♥  |");
                    System.out.println("+------------------+");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\n[!] Pilihan tidak valid");
            }
        }
    }

    // ========== BAGIAN 5: AUTENTIKASI ==========
    /**
     * Proses login pengguna
     * Memvalidasi username dan password
     */
    private static void prosesLogin(Scanner input) {
        System.out.println("\n+------------------------+");
        System.out.print("| Username: ");
        String username = input.nextLine().trim();
        System.out.print("| Password: ");
        String password = input.nextLine().trim();
        System.out.println("+------------------------+");

        if (akunPengguna.containsKey(username) && akunPengguna.get(username).equals(password)) {
            if (username.equals("admin")) {
                menuAdmin(input);
            } else {
                menuPengguna(input, username);
            }
        } else {
            System.out.println("\n[!] Username atau password salah");
        }
    }

    /**
     * Membuat akun baru
     * Memvalidasi username dan password
     */
    private static void buatAkunBaru(Scanner input) {
        System.out.println("\n+------------------------+");
        System.out.print("| Username baru: ");
        String username = input.nextLine().trim();
        
        if (username.isEmpty() || akunPengguna.containsKey(username)) {
            System.out.println("\n[!] Username tidak valid atau sudah digunakan");
            return;
        }

        System.out.print("| Password: ");
        String password = input.nextLine().trim();
        
        if (password.length() < 6) {
            System.out.println("\n[!] Password minimal 6 karakter");
            return;
        }

        akunPengguna.put(username, password);
        saldoPengguna.put(username, 0.0);
        System.out.println("\n+-------------------------+");
        System.out.println("| ✓ Akun berhasil dibuat! |");
        System.out.println("+-------------------------+");
    }

    // ========== BAGIAN 6: MENU ADMIN ==========
    /**
     * Menu khusus admin untuk manajemen sistem
     */
    private static void menuAdmin(Scanner input) {
        while (true) {
            System.out.println("\n*======================*");
            System.out.println("|      Menu Admin      |");
            System.out.println("*======================*");
            System.out.println("| 1. Tambah Barang     |");
            System.out.println("| 2. Lihat Barang      |");
            System.out.println("| 3. Hapus Barang      |");
            System.out.println("| 4. Hapus Akun User   |");
            System.out.println("| 5. Kembali           |");
            System.out.println("*======================*");
            System.out.print("| Pilih: ");
            String pilihan = input.nextLine().trim();
            System.out.println("*======================*");
            
            if (!isAngka(pilihan)) {
                System.out.println("\n[!] Masukkan angka yang valid");
                continue;
            }
            
            int menu = Integer.parseInt(pilihan);
            switch (menu) {
                case 1:
                    tambahBarang(input);
                    break;
                case 2:
                    lihatBarang();
                    break;
                case 3:
                    hapusBarang(input);
                    break;
                case 4:
                    hapusAkunPengguna(input);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("\n[!] Pilihan tidak valid");
            }
        }
    }

    // ========== BAGIAN 7: MENU PENGGUNA ==========
    /**
     * Menu untuk pengguna biasa
     * Berisi fitur-fitur transaksi
     */
    private static void menuPengguna(Scanner input, String username) {
        while (true) {
            System.out.println("\n~========================~");
            System.out.println("|     Menu Pengguna      |");
            System.out.println("~========================~");
            System.out.println("| 1. Lihat Saldo         |");
            System.out.println("| 2. Tambah Saldo        |");
            System.out.println("| 3. Beli Barang         |");
            System.out.println("| 4. Lihat Inventori     |");
            System.out.println("| 5. Jual Barang         |");
            System.out.println("| 6. Tambah Barang       |");
            System.out.println("| 7. Kembali             |");
            System.out.println("~========================~");
            System.out.print("| Pilih: ");
            String pilihan = input.nextLine().trim();
            System.out.println("~========================~");

            if (!isAngka(pilihan)) {
                System.out.println("\n[!] Masukkan angka yang valid");
                continue;
            }

            int menu = Integer.parseInt(pilihan);
            switch (menu) {
                case 1:
                    lihatSaldo(username);
                    break;
                case 2:
                    tambahSaldo(input, username);
                    break;
                case 3:
                    beliBarang(input, username);
                    break;
                case 4:
                    lihatInventori(username);
                    break;
                case 5:
                    jualBarang(input, username);
                    break;
                case 6:
                    tambahBarangLangsung(input, username);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("\n[!] Pilihan tidak valid");
            }
        }
    }

    // ========== BAGIAN 8: FUNGSI TRANSAKSI ==========
    /**
     * Menampilkan saldo pengguna
     */
    private static void lihatSaldo(String username) {
        System.out.println("\n+----------------------+");
        System.out.println("| Saldo: Rp" + String.format("%-11.2f", saldoPengguna.get(username)) + "|");
        System.out.println("+----------------------+");
    }

    /**
     * Menambah saldo pengguna
     */
    private static void tambahSaldo(Scanner input, String username) {
        System.out.print("\nMasukkan jumlah saldo: ");
        String inputJumlah = input.nextLine().trim();
        
        if (!isAngkaDesimal(inputJumlah)) {
            System.out.println("\n[!] Jumlah tidak valid");
            return;
        }
        
        double jumlah = Double.parseDouble(inputJumlah);
        if (jumlah <= 0) {
            System.out.println("\n[!] Jumlah harus lebih dari 0");
            return;
        }
        
        saldoPengguna.put(username, saldoPengguna.get(username) + jumlah);
        System.out.println("\n+--------------------------------+");
        System.out.println("| ✓ Saldo berhasil ditambahkan   |");
        System.out.println("| Total: Rp" + String.format("%-19.2f", saldoPengguna.get(username)) + "|");
        System.out.println("+--------------------------------+");
    }

    // ========== BAGIAN 9: MANAJEMEN BARANG ==========
    /**
     * Menampilkan daftar barang
     */
    private static void lihatBarang() {
        System.out.println("\n+======================+");
        System.out.println("|   Daftar Barang     |");
        System.out.println("+======================+");
        for (Map.Entry<String, Double> barang : daftarBarang.entrySet()) {
            System.out.printf("| %-18s |%n", barang.getKey());
            System.out.printf("| Harga: Rp%-10.2f |%n", barang.getValue());
            System.out.println("+----------------------+");
        }
    }

    /**
     * Menampilkan inventori pengguna
     */
    private static void lihatInventori(String username) {
        System.out.println("\n+======================+");
        System.out.println("|  Inventori Anda     |");
        System.out.println("+======================+");
        boolean adaBarang = false;
        for (Map.Entry<String, String> inventori : inventoriPengguna.entrySet()) {
            if (inventori.getKey().startsWith(username + ":")) {
                String namaBarang = inventori.getValue();
                System.out.printf("| %-18s |%n", namaBarang);
                adaBarang = true;
            }
        }
        if (!adaBarang) {
            System.out.println("| Inventori kosong    |");
        }
        System.out.println("+======================+");
    }

    /**
     * Proses pembelian barang
     */
    private static void beliBarang(Scanner input, String username) {
        lihatBarang();
        System.out.print("\nMasukkan nama barang: ");
        String namaBarang = input.nextLine().trim();
        
        if (!daftarBarang.containsKey(namaBarang)) {
            System.out.println("\n[!] Barang tidak ditemukan");
            return;
        }
        
        double hargaBarang = daftarBarang.get(namaBarang);
        double saldo = saldoPengguna.get(username);
        
        if (saldo < hargaBarang) {
            System.out.println("\n[!] Saldo tidak mencukupi");
            return;
        }
        
        saldoPengguna.put(username, saldo - hargaBarang);
        inventoriPengguna.put(username + ":" + namaBarang, namaBarang);
        System.out.println("\n+--------------------------------+");
        System.out.println("| ✓ Pembelian berhasil!          |");
        System.out.println("| Sisa saldo: Rp" + String.format("%-16.2f", saldoPengguna.get(username)) + "|");
        System.out.println("+--------------------------------+");
    }

    // ========== BAGIAN 10: FUNGSI ADMIN ==========
    /**
     * Admin menambah barang baru
     */
    private static void tambahBarang(Scanner input) {
        System.out.print("\nMasukkan nama barang: ");
        String namaBarang = input.nextLine().trim();
        System.out.print("Masukkan harga barang: ");
        String inputHarga = input.nextLine().trim();
        
        if (!isAngkaDesimal(inputHarga)) {
            System.out.println("\n[!] Harga tidak valid");
            return;
        }
        
        double harga = Double.parseDouble(inputHarga);
        if (harga <= 0) {
            System.out.println("\n[!] Harga harus lebih dari 0");
            return;
        }
        
        daftarBarang.put(namaBarang, harga);
        System.out.println("\n+--------------------------------+");
        System.out.println("| ✓ Barang berhasil ditambahkan! |");
        System.out.println("+--------------------------------+");
    }

    /**
     * Admin menghapus barang
     */
    private static void hapusBarang(Scanner input) {
        lihatBarang();
        System.out.print("\nMasukkan nama barang yang akan dihapus: ");
        String namaBarang = input.nextLine().trim();
        
        if (daftarBarang.remove(namaBarang) != null) {
            // Hapus barang dari inventori semua pengguna
            inventoriPengguna.entrySet().removeIf(entry -> entry.getValue().equals(namaBarang));
            System.out.println("\n+-----------------------------+");
            System.out.println("| ✓ Barang berhasil dihapus! |");
            System.out.println("+-----------------------------+");
        } else {
            System.out.println("\n[!] Barang tidak ditemukan");
        }
    }

    /**
     * Admin menghapus akun pengguna
     */
    private static void hapusAkunPengguna(Scanner input) {
        System.out.print("\nMasukkan username yang akan dihapus: ");
        String username = input.nextLine().trim();
        
        if (username.equals("admin")) {
            System.out.println("\n[!] Tidak dapat menghapus akun admin!");
            return;
        }
        
        if (!akunPengguna.containsKey(username)) {
            System.out.println("\n[!] Username tidak ditemukan");
            return;
        }
        
        // Hapus data pengguna
        akunPengguna.remove(username);
        saldoPengguna.remove(username);
        // Hapus inventori pengguna
        inventoriPengguna.entrySet().removeIf(entry -> entry.getKey().startsWith(username + ":"));
        
        System.out.println("\n+--------------------------------+");
        System.out.println("| ✓ Akun pengguna berhasil dihapus! |");
        System.out.println("+--------------------------------+");
    }

    // ========== BAGIAN 11: FUNGSI TAMBAHAN ==========
    /**
     * Pengguna menjual barang dari inventori
     */
    private static void jualBarang(Scanner input, String username) {
        lihatInventori(username);
        boolean adaBarang = inventoriPengguna.keySet().stream().anyMatch(key -> key.startsWith(username + ":"));
            
        if (!adaBarang) {
            System.out.println("\n[!] Tidak ada barang untuk dijual");
            return;
        }

        System.out.print("\nMasukkan nama barang yang akan dijual: ");
        String namaBarang = input.nextLine().trim();
        String kodeInventori = username + ":" + namaBarang;
        
        if (!inventoriPengguna.containsKey(kodeInventori)) {
            System.out.println("\n[!] Barang tidak ditemukan dalam inventori");
            return;
        }

        System.out.print("Masukkan harga jual: ");
        String inputHarga = input.nextLine().trim();
        
        if (!isAngkaDesimal(inputHarga)) {
            System.out.println("\n[!] Harga tidak valid");
            return;
        }
        
        double hargaJual = Double.parseDouble(inputHarga);
        if (hargaJual <= 0) {
            System.out.println("\n[!] Harga harus lebih dari 0");
            return;
        }
        
        inventoriPengguna.remove(kodeInventori);
        saldoPengguna.put(username, saldoPengguna.get(username) + hargaJual);
        System.out.println("\n+--------------------------------+");
        System.out.println("| ✓ Penjualan berhasil!          |");
        System.out.println("| Saldo: Rp" + String.format("%-20.2f", saldoPengguna.get(username)) + "|");
        System.out.println("+--------------------------------+");
    }

    /**
     * Pengguna menambah barang langsung ke inventori
     */
    private static void tambahBarangLangsung(Scanner input, String username) {
        System.out.print("\nMasukkan nama barang: ");
        String namaBarang = input.nextLine().trim();
        
        if (namaBarang.isEmpty()) {
            System.out.println("\n[!] Nama barang tidak boleh kosong");
            return;
        }
        
        inventoriPengguna.put(username + ":" + namaBarang, namaBarang);
        System.out.println("\n[✓] Barang berhasil ditambahkan ke inventori");
    }
}
