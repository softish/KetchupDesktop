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
        } else {
            timerModel.startTimer();
            ketchupDesktopView.setChangeStateButtonText("Stop");
        }
    }

    public void resetTimer() {
        ketchupDesktopView.setTimeLabel("timer was reset");
    }

    @Override
    public void update(TimerEvent timerEvent) {
        if(timerEvent.equals(TimerEvent.TICK)) {
            ketchupDesktopView.updateTimeLabel(TimeFormatter.getTimeLeftFormatted(timerModel));
        }
        if(timerEvent.equals(TimerEvent.TIME_OUT)) {
            ketchupDesktopView.displayTimeOut();
        }
    }
}
