package tmd.presenter;

import tmd.model.Player;
import tmd.model.TBenefitModel;
import tmd.view.MenuWindow;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPresenter {
    private MenuWindow view;
    private TBenefitModel model;

    public MenuPresenter(MenuWindow view) {
        this.view = view;
        this.model = new TBenefitModel(); // siapin model buat koneksi database

        // muat data tabel pas pertama kali dibuka
        loadTableData();

        // kasih aksi buat tombol play
        view.getBtnPlay().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // kasih aksi buat tombol quit langsung keluar program
        view.getBtnQuit().addActionListener(e -> System.exit(0));
    }

    // fungsi buat ngambil data dari db dan nampilin ke tabel
    private void loadTableData() {
        // minta list player ke model
        List<Player> players = model.getAllPlayers();

        // ambil model tabel dari view biar bisa diisi
        DefaultTableModel tableModel = view.getTableModel();

        // kosongin dulu isinya biar ga numpuk
        tableModel.setRowCount(0);

        // loop buat ngisi baris tabel satu satu
        for (Player p : players) {
            tableModel.addRow(new Object[]{
                    p.getUsername(),
                    p.getSkor(),
                    p.getPeluruMeleset(),
                    p.getSisaPeluru()
            });
        }
    }

    // logika pas tombol play ditekan
    private void startGame() {
        String username = view.getUsername();

        // cek username gaboleh kosong
        if (username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "username diisi dulu dong");
            return;
        }

        // simpen user baru ke database kalo emang belum ada
        model.addPlayerIfNew(username);

        // tutup menu window
        view.dispose();

        // mulai masuk ke game
        new GamePresenter(username);
    }
}