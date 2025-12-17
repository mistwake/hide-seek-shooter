package tmd.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TBenefitModel {
    private DBConnection db;

    public TBenefitModel() {
        // pas model dibuat langsung connect ke db
        db = new DBConnection();
    }

    // ambil semua data player buat ditampilin di tabel highscore
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        try {
            // ambil data urut dari skor paling gede
            String sql = "SELECT * FROM tbenefit ORDER BY skor DESC";
            ResultSet rs = db.getStatement().executeQuery(sql);

            while (rs.next()) {
                // masukin data baris per baris ke list
                Player p = new Player(
                        rs.getString("username"),
                        rs.getInt("skor"),
                        rs.getInt("peluru_meleset"),
                        rs.getInt("sisa_peluru")
                );
                players.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    // fungsi buat nambah player baru pas tombol play ditekan
    public void addPlayerIfNew(String username) {
        try {
            // cek dulu username nya udah ada belum
            String checkSql = "SELECT * FROM tbenefit WHERE username = '" + username + "'";
            ResultSet rs = db.getStatement().executeQuery(checkSql);

            if (!rs.next()) {
                // kalau ga ada baru kita insert data baru
                String insertSql = "INSERT INTO tbenefit (username, skor, peluru_meleset, sisa_peluru) VALUES ('" + username + "', 0, 0, 0)";
                db.getStatement().executeUpdate(insertSql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // fungsi baru buat ambil sisa peluru user dari db
    // ini dipake pas game mulai biar peluru lanjut dari game sebelumnya
    public int getSisaPeluru(String username) {
        int sisa = 0;
        try {
            String sql = "SELECT sisa_peluru FROM tbenefit WHERE username = '" + username + "'";
            ResultSet rs = db.getStatement().executeQuery(sql);
            if (rs.next()) {
                sisa = rs.getInt("sisa_peluru");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sisa;
    }

    public void updatePlayerData(String username, int skorBaru, int melesetBaru, int sisaPeluruBaru) {
        try {
            // query update data berdasarkan username
            // skor dan peluru meleset ditambahin ke nilai yang lama akumulasi
            // sisa peluru ditimpa dengan nilai terbaru karena itu kondisi inventory terakhir
            String sql = "UPDATE tbenefit SET " +
                    "skor = skor + " + skorBaru + ", " +
                    "peluru_meleset = peluru_meleset + " + melesetBaru + ", " +
                    "sisa_peluru = " + sisaPeluruBaru +
                    " WHERE username = '" + username + "'";

            db.getStatement().executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}