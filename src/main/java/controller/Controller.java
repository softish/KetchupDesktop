package controller;

import model.Observer;
import model.TimeFormatter;
import model.TimerEvent;
import model.TimerModel;
import view.KetchupDesktopView;

public class Controller implements Observer {

    private KetchupDesktopView ketchupDesktopView;
    private TimerModel timerModel;

    public Controller(KetchupDesktopView ketchupDesktopView, TimerModel timerModel) {
        this.ketchupDesktopView = ketchupDesktopView;
        this.timerModel = timerModel;
        this.ketchupDesktopView.setTimeLabel(TimeFormatter.getTimeLeftFormatted(timerModel));
        timerModel.subscribe(this);
    }

    public void changeTimerState() {
        if(timerModel.isTimerActive()) {
            timerModel.stopTimer();
            ketchupDesktopView.setChangeStateButtonText("Start");
            ketchupDesktopView.enableResetButton();
        } else {
            timerModel.startTimer();
            ketchupDesktopView.setChangeStateButtonText("Stop");
            ketchupDesktopView.disableResetButton();
        }
    }

    public void resetTimer() {
        timerModel.resetTimer();
    }

    public void exitApplication() {
        timerModel.stopTimer();
    }

    @Override
    public void update(TimerEvent timerEvent) {
        if (timerEvent.equals(TimerEvent.TICK)) {
            ketchupDesktopView.updateTimeLabel(TimeFormatter.getTimeLeftFormatted(timerModel));
        }
        if (timerEvent.equals(TimerEvent.TIME_OUT)) {
            ketchupDesktopView.displayTimeOut();
            ketchupDesktopView.disableChangeStateButton();
            ketchupDesktopView.enableResetButton();
        }
        if (timerEvent.equals(TimerEvent.RESET)) {
            ketchupDesktopView.updateTimeLabel(TimeFormatter.getTimeLeftFormatted(timerModel));
            ketchupDesktopView.enableChangeStateButton();
            if(!timerModel.isTimerActive()) {
                ketchupDesktopView.setChangeStateButtonText("Start");
            }
        }
    }
}
