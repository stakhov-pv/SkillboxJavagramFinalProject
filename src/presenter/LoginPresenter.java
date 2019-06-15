package presenter;

import model.LoginModel;
import model.MessengerModel;
import util.PhoneFormatter;
import view.LoginView;
import view.MessengerView;

public class LoginPresenter implements LoginView.Listener {
    private LoginView view;
    private LoginModel model;

    public LoginPresenter(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;

        model.setState(LoginView.LoginState.Init);

        view.attachListener(this);
    }

    public void start() {
        view.show();
        setState(LoginView.LoginState.AskPhone);
    }

    public void dispose() {
        view.detachListener();
        view = null;
        model = null;
    }

    public void setState(LoginView.LoginState newState) {
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
        try {
            boolean authorised = model.signIn(code);
            if (!authorised) setState(LoginView.LoginState.AskPhone);
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
                model.setSmsCode(code);
                setState(LoginView.LoginState.AskNewProfile);
            }
        }
    }

    @Override
    public void onNameButtonPressed() {
        String firstName = view.getFirstName();
        String lastName = view.getLastName();
        setState(LoginView.LoginState.ProcessingNewProfile);
        try {
            boolean authorised = model.signUp(model.getSmsCode(), firstName, lastName);
            if (!authorised) setState(LoginView.LoginState.AskPhone);
            else startMessenger();
        } catch (Exception e) {
            //TODO: show error
            e.printStackTrace();
            setState(LoginView.LoginState.AskPhone);
        }
    }

    @Override
    public void onMinimiseButtonPressed() {
        view.minimiseApp();
    }

    @Override
    public void onCloseButtonPressed() {
        view.closeApp();
    }

    private void startMessenger() {
        MessengerView messengerView = new MessengerView();
        MessengerModel messengerModel = new MessengerModel();
        MessengerPresenter messengerPresenter = new MessengerPresenter(messengerModel, messengerView);
        messengerPresenter.start();

        dispose();
    }

}
