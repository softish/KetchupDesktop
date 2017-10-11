package controller;

import exception.ErroneousCredentialsException;
import exception.ServerUnreachableException;
import exception.UserAlreadyExistsException;
import javafx.util.Pair;
import model.Observer;
import model.TimeFormatter;
import model.TimerEvent;
import model.TimerModel;
import model.api.APIDriver;
import model.api.dtos.User;
import view.KetchupDesktopView;

import java.util.Optional;

public class Controller implements Observer {

    private KetchupDesktopView ketchupDesktopView;
    private TimerModel timerModel;
    private APIDriver apiDriver;

    public Controller(KetchupDesktopView ketchupDesktopView, TimerModel timerModel) {
        this.ketchupDesktopView = ketchupDesktopView;
        this.timerModel = timerModel;
        this.ketchupDesktopView.setTimeLabel(TimeFormatter.getTimeLeftFormatted(timerModel));
        timerModel.subscribe(this);
        apiDriver = new APIDriver();
        ketchupDesktopView.disableChangeStateButton();
        ketchupDesktopView.disableResetButton();
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

    public void loginHandler() {
        Optional<Pair<String, String>> result = ketchupDesktopView.showLoginDialog();

        result.ifPresent(usernamePassword -> {
            User user = new User(usernamePassword.getKey(), usernamePassword.getValue());
            apiDriver.setUser(user);

            try {
                apiDriver.authenticate(usernamePassword.getKey(), usernamePassword.getValue());
                ketchupDesktopView.disableSignInButton();
                ketchupDesktopView.disableRegisterButton();
                ketchupDesktopView.enableChangeStateButton();
                ketchupDesktopView.enableResetButton();
            } catch (ServerUnreachableException e) {
                ketchupDesktopView.showErrorDialog("Error", e.getMessage());
            } catch (ErroneousCredentialsException e) {
                ketchupDesktopView.showErrorDialog("Error", e.getMessage());
            }
        });
    }

    public void registerHandler() {
        Optional<Pair<String, String>> result = ketchupDesktopView.showRegisterDialog();

        result.ifPresent(usernamePassword -> {
            try {
                apiDriver.register(usernamePassword.getKey(), usernamePassword.getValue());
            } catch (ServerUnreachableException e) {
                ketchupDesktopView.showErrorDialog("Error", e.getMessage());
            } catch (UserAlreadyExistsException e) {
                ketchupDesktopView.showErrorDialog("Error", e.getMessage());
            }
        });
    }

    public void addTaskHandler() {
        Optional<String> tag = ketchupDesktopView.showAddTaskDialog();
        tag.ifPresent(theTag -> ketchupDesktopView.updateTask(theTag));
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

            try{
                apiDriver.saveSession(timerModel.getSessionDurationMillis());
            } catch (ServerUnreachableException e) {
                ketchupDesktopView.showErrorDialog("Error", e.getMessage());
            }
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
