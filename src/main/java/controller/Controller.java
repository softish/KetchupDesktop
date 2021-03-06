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

    private static final String ERROR = "Error";

    private final KetchupDesktopView ketchupDesktopView;
    private final SignInView signInView;
    private final TimerModel timerModel;
    private final APIDriver apiDriver;

    public Controller(KetchupDesktopView ketchupDesktopView, SignInView signInView, TimerModel timerModel, boolean devMode) {
        this.ketchupDesktopView = ketchupDesktopView;
        this.signInView = signInView;
        this.timerModel = timerModel;
        this.ketchupDesktopView.setTimeLabel(TimeFormatter.getTimeLeftFormatted(timerModel));
        timerModel.subscribe(this);
        apiDriver = new APIDriver(devMode);

        try {
            apiDriver.setAuthenticatedUser(SessionCacheHandler.load());
            ketchupDesktopView.setCurrentUser(apiDriver.getAuthenticatedUser().getUserName());
            enableUIOnAuthentication();
            SceneManager.getInstance().activateScene(SceneName.KETCHUP);
        } catch (NoCachedSessionException e) {
            SceneManager.getInstance().activateScene(SceneName.AUTH);
        }

        this.ketchupDesktopView.disableResetButton();
    }

    public void changeTimerState() {
        if (timerModel.isTimerActive()) {
            timerModel.stopTimer();
            ketchupDesktopView.setChangeStateButtonText("Start");
            ketchupDesktopView.enableResetButton();
            ketchupDesktopView.enableSetTaskButton();
        } else {
            if (!ketchupDesktopView.isTaskSet()) {
                ketchupDesktopView.showErrorDialog(ERROR, "Task must be set!");
                return;
            }

            timerModel.startTimer();
            ketchupDesktopView.setChangeStateButtonText("Stop");
            ketchupDesktopView.disableResetButton();
            ketchupDesktopView.disableSetTaskButton();
        }
    }

    public void resetTimer() {
        timerModel.resetTimer();
        ketchupDesktopView.disableResetButton();
    }

    public void exitApplication() {
        timerModel.stopTimer();
        timerModel.unSubscribe(this);
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
                ketchupDesktopView.setCurrentUser(authenticatedUser.getUserName());
                ketchupDesktopView.disableResetButton();
                SessionCacheHandler.save(authenticatedUser);
                SceneManager.getInstance().activateScene(SceneName.KETCHUP);
            } catch (ServerUnreachableException | ErroneousCredentialsException e) {
                ketchupDesktopView.showErrorDialog(ERROR, e.getMessage());
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
            } catch (ServerUnreachableException | UserAlreadyExistsException e) {
                ketchupDesktopView.showErrorDialog(ERROR, e.getMessage());
            }
        });
    }

    public void signOutHandler() {
        SceneManager.getInstance().activateScene(SceneName.AUTH);
        SessionCacheHandler.clearCache();
        resetTimer();
        ketchupDesktopView.resetTaskLabel();
    }

    public void addTaskHandler() {
        Optional<String> tag = ketchupDesktopView.showAddTaskDialog();
        tag.ifPresent(ketchupDesktopView::updateTask);
    }

    @Override
    public void update(TimerEvent timerEvent) {
        if (timerEvent.equals(TimerEvent.TICK)) {
            ketchupDesktopView.updateTimeLabel(TimeFormatter.getTimeLeftFormatted(timerModel));
        } else if (timerEvent.equals(TimerEvent.TIME_OUT)) {
            ketchupDesktopView.displayTimeOut();
            ketchupDesktopView.disableChangeStateButton();
            ketchupDesktopView.enableResetButton();
            ketchupDesktopView.enableSetTaskButton();

            try {
                apiDriver.saveSession(timerModel.getSessionDurationMillis(), ketchupDesktopView.getTask());
            } catch (ServerUnreachableException e) {
                ketchupDesktopView.showErrorDialog(ERROR, e.getMessage());
            }
        } else if (timerEvent.equals(TimerEvent.RESET)) {
            ketchupDesktopView.updateTimeLabel(TimeFormatter.getTimeLeftFormatted(timerModel));
            ketchupDesktopView.enableChangeStateButton();
            if (!timerModel.isTimerActive()) {
                ketchupDesktopView.setChangeStateButtonText("Start");
            }
        }
    }
}
