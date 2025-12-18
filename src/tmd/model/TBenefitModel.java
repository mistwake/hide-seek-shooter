package tmd.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TBenefitModel {
    private DBConnection db;

    public TBenefitModel() {
        // pas kelas model ini dibuat langsung kita hubungin ke database
        db = new DBConnection();
    }

    // fungsi buat ngambil semua data pemain dari tabel tbenefit
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        try {
            // kita ambil semua data dan diurutin dari skor paling gede
            String sql = "SELECT * FROM tbenefit ORDER BY skor DESC";
            ResultSet rs = db.getStatement().executeQuery(sql);

            // kita loop datanya satu per satu selama masih ada
            while (rs.next()) {
                // ambil data dari tiap kolom terus masukin ke objek player
                Player p = new Player(
                        rs.getString("username"),
                        rs.getInt("skor"),
                        rs.getInt("peluru_meleset"),
                        rs.getInt("sisa_peluru")
                );
                // masukin objek player tadi ke dalam list
                players.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    // fungsi buat nambahin pemain baru kalo belum ada di database
    public void addPlayerIfNew(String username) {
        try {
            // cek dulu username nya udah ada belum di tabel
            String checkSql = "SELECT * FROM tbenefit WHERE username = '" + username + "'";
            ResultSet rs = db.getStatement().executeQuery(checkSql);

            // kalo ternyata datanya ga ketemu (pemain baru)
            if (!rs.next()) {
                // kita masukin data baru dengan nilai awal nol semua
                String insertSql = "INSERT INTO tbenefit (username, skor, peluru_meleset, sisa_peluru) VALUES ('" + username + "', 0, 0, 0)";
                db.getStatement().executeUpdate(insertSql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // fungsi buat ngambil sisa peluru terakhir pemain dari database
    public int getSisaPeluru(String username) {
        int sisa = 0;
        try {
            // ambil cuma kolom sisa peluru aja berdasarkan username
            String sql = "SELECT sisa_peluru FROM tbenefit WHERE username = '" + username + "'";
            ResultSet rs = db.getStatement().executeQuery(sql);

            // kalo datanya ada kita simpen nilainya
            if (rs.next()) {
                sisa = rs.getInt("sisa_peluru");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sisa;
    }

    // update data skor dan peluru pemain setelah game over
    public void updatePlayerData(String username, int skorBaru, int melesetBaru, int sisaPeluruBaru) {
        try {
            // ini query buat update data
            // skor dan peluru meleset kita tambah atau di akumulasi
            // tapi sisa peluru kita timpa pake nilai terakhir karena itu kondisi inventory
            String sql = "UPDATE tbenefit SET " +
                    "skor = skor + " + skorBaru + ", " +
                    "peluru_meleset = peluru_meleset + " + melesetBaru + ", " +
                    "sisa_peluru = " + sisaPeluruBaru +
                    " WHERE username = '" + username + "'";

            // jalanin perintah updatenya
            db.getStatement().executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}