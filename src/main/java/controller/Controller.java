package controller;

import model.TimeFormatter;
import model.TimerModel;
import view.KetchupDesktopView;

public class Controller {

    private KetchupDesktopView ketchupDesktopView;
    private TimerModel timerModel;

    public Controller(KetchupDesktopView ketchupDesktopView, TimerModel timerModel) {
        this.ketchupDesktopView = ketchupDesktopView;
        this.timerModel = timerModel;
        this.ketchupDesktopView.setTimeLabel(TimeFormatter.getTimeLeftFormatted(timerModel));
        timerModel.subscribe(ketchupDesktopView);
    }

    public void changeTimerState() {
        timerModel.startTimer();
    }

    public void resetTimer() {
        ketchupDesktopView.setTimeLabel("timer was reset");
    }
}
