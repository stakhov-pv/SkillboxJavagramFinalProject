public class LoginPresenter implements LoginView.Listener {
    LoginView view;
    LoginModel model;

    LoginPresenter(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;

        model.setState(LoginView.LoginState.Init);

        view.attachListener(this);
    }

    void start() {
        view.show();
        setState(LoginView.LoginState.AskPhone);
    }

    void dispose() {
        view.detachListener();
        view = null;
        model = null;
    }

    void setState(LoginView.LoginState newState) {
        model.setState(newState);
        view.showState(model.getState());
    }

    @Override
    public void onPhoneButtonPressed() {
        setState(LoginView.LoginState.ProcessingPhone);
        model.fakeGetData(new LoginModel.FakeDataReady() {
            @Override
            public void dataReadyListener() {
                setState(LoginView.LoginState.AskCode);
            }
        });
    }

    @Override
    public void onCodeButtonPressed() {
        setState(LoginView.LoginState.ProcessingCode);
        model.fakeGetData(new LoginModel.FakeDataReady() {
            @Override
            public void dataReadyListener() {
                setState(LoginView.LoginState.AskNewProfile);
            }
        });

    }

    @Override
    public void onNameButtonPressed() {
        setState(LoginView.LoginState.ProcessingNewProfile);
        model.fakeGetData(new LoginModel.FakeDataReady() {
            @Override
            public void dataReadyListener() {
                setState(LoginView.LoginState.AskPhone);
            }
        });
    }

}
