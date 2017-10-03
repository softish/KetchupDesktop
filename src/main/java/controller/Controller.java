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
        if(timerModel.isTimerActive()) {
            timerModel.stopTimer();
            ketchupDesktopView.setChangeStateButtonText("Start");
        } else {
            timerModel.startTimer();
            ketchupDesktopView.setChangeStateButtonText("Stop");
        }
    }

    public void resetTimer() {
        ketchupDesktopView.setTimeLabel("timer was reset");
    }
}
