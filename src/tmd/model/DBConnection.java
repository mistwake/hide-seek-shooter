package tmd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {
    // ini konfigurasi biar bisa nyambung ke database mysql
    // pastikan nama databasenya sesuai sama yg di xampp
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/db_dpbo";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection conn;
    private Statement stmt;

    public DBConnection() {
        try {
            // daftarin driver jdbc biar dikenalin
            Class.forName(JDBC_DRIVER);

            // coba buka koneksi ke database pake url user dan password yg udah diset
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // bikin statement buat wadah eksekusi query sql nanti
            stmt = conn.createStatement();
        } catch (Exception e) {
            // kalau koneksi gagal print errornya biar ketahuan salah dimana
            e.printStackTrace();
        }
    }

    // fungsi ini buat ngambil statement biar bisa dipake di kelas lain
    public Statement getStatement() {
        return stmt;
    }
}