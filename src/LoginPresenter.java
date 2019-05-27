import org.javagram.response.AuthAuthorization;
import util.PhoneFormatter;

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
        String desiredPhone = view.getPhoneValue().trim();
        //TODO: check phone number for consistency
        model.setPhoneNumber(desiredPhone);
        setState(LoginView.LoginState.ProcessingPhone);
        try {
            boolean registered = model.getRegisteredStatus(desiredPhone);
            if (registered) {
                view.setCodePhoneLabel(PhoneFormatter.humanReadable(desiredPhone));
                setState(LoginView.LoginState.AskCode);
                model.sendCode(model.getPhoneNumber());
            } else {
                setState(LoginView.LoginState.AskNewProfile);

            }
        } catch (Exception e) {
            //TODO: show error
        }
    }

    @Override
    public void onCodeButtonPressed() {
        String code = view.getCodeValue();
        //TODO: check code for consistency
        setState(LoginView.LoginState.ProcessingCode);
        AuthAuthorization authorization=null;
        try {
            authorization = model.signIn(code);
            if (authorization == null) setState(LoginView.LoginState.AskNewProfile);
            else startMessenger();
        } catch (Exception e) {
            //TODO: show error
            e.printStackTrace();
            //TODO: PHONE_CODE_INVALID may be user error - need to give one more chance to user - reask and retype code
            if ("PHONE_CODE_INVALID".equals(e.getMessage())) {
                //TODO: show error
                setState(LoginView.LoginState.AskPhone);
            } else if ("PHONE_NUMBER_INVALID".equals(e.getMessage())) {
                setState(LoginView.LoginState.AskPhone);
            } else if ("PHONE_NUMBER_UNOCCUPIED".equals(e.getMessage())) {
                setState(LoginView.LoginState.AskNewProfile);
            }
        }
    }

    @Override
    public void onNameButtonPressed() {
        setState(LoginView.LoginState.ProcessingNewProfile);
        model.fakeGetData(new LoginModel.DataReady() {
            @Override
            public void dataReadyListener(Object result) {
                setState(LoginView.LoginState.AskPhone);
            }
        });
    }

    void startMessenger() {
        MessengerView conversations = new MessengerView();
        Gui.getInstance().changePane(conversations.getRootPanel());
        dispose();
    }

}
