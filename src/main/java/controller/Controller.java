package controller;

import exception.ErroneousCredentialsException;
import exception.NoCachedSessionException;
import exception.ServerUnreachableException;
import exception.UserAlreadyExistsException;
import javafx.util.Pair;
import model.*;
import model.api.APIDriver;
import model.api.dtos.AuthenticatedUser;
import model.api.dtos.User;
import view.KetchupDesktopView;
import view.SceneManager;
import view.SceneName;
import view.SignInView;

import java.util.Optional;

public class Controller implements Observer {

    private KetchupDesktopView ketchupDesktopView;
    private SignInView signInView;
    private TimerModel timerModel;
    private APIDriver apiDriver;

    public Controller(KetchupDesktopView ketchupDesktopView, SignInView signInView, TimerModel timerModel) {
        this.ketchupDesktopView = ketchupDesktopView;
        this.signInView = signInView;
        this.timerModel = timerModel;
        this.ketchupDesktopView.setTimeLabel(TimeFormatter.getTimeLeftFormatted(timerModel));
        timerModel.subscribe(this);
        apiDriver = new APIDriver();
        ketchupDesktopView.disableChangeStateButton();
        ketchupDesktopView.disableResetButton();

        try {
            apiDriver.setAuthenticatedUser(SessionCacheHandler.load());
            enableUIOnAuthentication();
            SceneManager.getInstance().activateScene(SceneName.Ketchup);
        } catch (NoCachedSessionException e) {
            SceneManager.getInstance().activateScene(SceneName.Auth);
        }
    }

    public void changeTimerState() {
        if (timerModel.isTimerActive()) {
            timerModel.stopTimer();
            ketchupDesktopView.setChangeStateButtonText("Start");
            ketchupDesktopView.enableResetButton();
        } else {
            if(!ketchupDesktopView.isTaskSet()) {
                ketchupDesktopView.showErrorDialog("Error", "Task must be set!");
                return;
            }

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
        Optional<Pair<String, String>> result = signInView.showLoginDialog();

        result.ifPresent(usernamePassword -> {
            User user = new User(usernamePassword.getKey(), usernamePassword.getValue());
            apiDriver.setUser(user);

            try {
                AuthenticatedUser authenticatedUser = apiDriver.authenticate(usernamePassword.getKey(), usernamePassword.getValue());
                enableUIOnAuthentication();
                apiDriver.setAuthenticatedUser(authenticatedUser);
                SessionCacheHandler.save(authenticatedUser);
                SceneManager.getInstance().activateScene(SceneName.Ketchup);

            } catch (ServerUnreachableException e) {
                ketchupDesktopView.showErrorDialog("Error", e.getMessage());
            } catch (ErroneousCredentialsException e) {
                ketchupDesktopView.showErrorDialog("Error", e.getMessage());
            }
        });
    }

    private void enableUIOnAuthentication() {
        ketchupDesktopView.enableChangeStateButton();
        ketchupDesktopView.enableResetButton();
    }

    public void registerHandler() {
        Optional<Pair<String, String>> result = signInView.showRegisterDialog();

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

    public void signOutHandler() {
        SceneManager.getInstance().activateScene(SceneName.Auth);
        SessionCacheHandler.clearCache();
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
                apiDriver.saveSession(timerModel.getSessionDurationMillis(), ketchupDesktopView.getTask());
            } catch (ServerUnreachableException e) {
                ketchupDesktopView.showErrorDialog("Error", e.getMessage());
            }
        }
        if (timerEvent.equals(TimerEvent.RESET)) {
            ketchupDesktopView.updateTimeLabel(TimeFormatter.getTimeLeftFormatted(timerModel));
            ketchupDesktopView.enableChangeStateButton();
            if (!timerModel.isTimerActive()) {
                ketchupDesktopView.setChangeStateButtonText("Start");
            }
        }
    }
}
