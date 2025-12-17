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
        this.model = new TBenefitModel(); // presenter pegang akses ke database

        // pas awal jalan, langsung isi tabelnya
        loadTableData();

        // kasih logika buat tombol play
        view.getBtnPlay().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // kasih logika buat tombol quit
        view.getBtnQuit().addActionListener(e -> System.exit(0));
    }

    private void loadTableData() {
        // minta data ke model
        List<Player> players = model.getAllPlayers();

        // ambil tabel dari view
        DefaultTableModel tableModel = view.getTableModel();

        // bersihin dulu isinya biar gak dobel
        tableModel.setRowCount(0);

        // masukin data satu per satu
        for (Player p : players) {
            tableModel.addRow(new Object[]{
                    p.getUsername(),
                    p.getSkor(),
                    p.getPeluruMeleset(),
                    p.getSisaPeluru()
            });
        }
    }

    private void startGame() {
        String username = view.getUsername();

        // cek username gaboleh kosong
        if (username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "username diisi dulu dong");
            return;
        }

        // simpan ke db lewat model
        model.addPlayerIfNew(username);

        // tutup window menu biar gak numpuk
        view.dispose();

        // kirim username ke game presenter
        new GamePresenter(username);
    }
}