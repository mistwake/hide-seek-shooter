package tmd.main;

import tmd.view.MenuWindow;
import tmd.presenter.MenuPresenter;

public class Main {
    public static void main(String[] args) {
        MenuWindow menu = new MenuWindow();
        new MenuPresenter(menu);
    }
}