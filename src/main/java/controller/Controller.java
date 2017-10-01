package controller;

import view.KetchupDesktopView;

public class Controller {

    private KetchupDesktopView ketchupDesktopView;

    public Controller(KetchupDesktopView ketchupDesktopView) {
        this.ketchupDesktopView = ketchupDesktopView;
    }

    public void changeTimerState() {
        ketchupDesktopView.setTimeLabel("timer was started");
    }

    public void resetTimer() {
        ketchupDesktopView.setTimeLabel("timer was reset");
    }
}
