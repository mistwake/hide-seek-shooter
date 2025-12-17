package tmd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {
    // config database
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/db_dpbo";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection conn;
    private Statement stmt;

    public DBConnection() {
        try {
            // registrasi driver dan buka koneksi
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (Exception e) {
            // print error connection
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return stmt;
    }
}