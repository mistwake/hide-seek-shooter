/*
saya Anas Miftakhul Falah mengerjakan evaluasi tugas masa depan dalam mata kuliah
desain dan pemrograman berorientasi objek untuk keberkahannya maka saya
tidak melakukan kecurangan seperti yang telah dispesifikasikan. aamiin.
*/

package tmd.main;

import tmd.view.MenuWindow;
import tmd.presenter.MenuPresenter;

public class Main {
    public static void main(String[] args) {
        // bikin window buat menu awal pas aplikasi dibuka
        MenuWindow menu = new MenuWindow();

        // panggil presenter buat ngatur logika di menu itu
        new MenuPresenter(menu);
    }
}