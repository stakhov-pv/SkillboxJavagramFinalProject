package presenter;

import model.LoginModel;
import model.MessengerModel;
import util.PhoneFormatter;
import view.LoginView;
import view.MessengerView;

import javax.swing.*;

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
        model.setPhoneNumber(desiredPhone);
        setState(LoginView.LoginState.ProcessingPhone);
        AsyncCheckPhone checkPhone = new AsyncCheckPhone(desiredPhone);
        checkPhone.execute();
    }

    class AsyncCheckPhone extends SwingWorker<Boolean, Void> {
        String phone;

        public AsyncCheckPhone(String phone) {
            this.phone=phone;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            boolean registered = model.getRegisteredStatus(phone);
            return registered;
        }

        @Override
        protected void done() {
            try {
                Boolean result = get();
                if (result) {
                    view.setCodePhoneLabel(PhoneFormatter.humanReadable(model.getPhoneNumber()));
                    setState(LoginView.LoginState.AskCode);
                    model.sendCode(model.getPhoneNumber());
                } else {
                    setState(LoginView.LoginState.AskNewProfile);

                }
            } catch (Exception e) {
                setState(LoginView.LoginState.AskPhone);
            }
        }
    }

    /*
    @Override
    public void onCodeButtonPressed() {
        String code = view.getCodeValue();
        setState(LoginView.LoginState.ProcessingCode);
        try {
            boolean authorised = model.signIn(code);
            if (!authorised) setState(LoginView.LoginState.AskPhone);
            else startMessenger();
        } catch (Exception e) {
            //TODO: show error
            e.printStackTrace();
            if ("PHONE_NUMBER_UNOCCUPIED".equals(e.getMessage())) {
                model.setSmsCode(code);
                setState(LoginView.LoginState.AskNewProfile);
            } else {
                setState(LoginView.LoginState.AskPhone);
            }
        }
    }
     */

    @Override
    public void onCodeButtonPressed() {
        String code = view.getCodeValue();
        setState(LoginView.LoginState.ProcessingCode);
        AsyncCheckCode asyncCheckCode = new AsyncCheckCode(code);
        asyncCheckCode.execute();
    }

    class AsyncCheckCode extends SwingWorker<Boolean, Void> {
        String code;

        public AsyncCheckCode(String code) {
            this.code=code;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            boolean authorised = model.signIn(code);
            return authorised;
        }

        @Override
        protected void done() {
            try {
                boolean authorised = get();
                if (!authorised) setState(LoginView.LoginState.AskPhone);
                else startMessenger();
            } catch (Exception e) {
                //TODO: show error
                e.printStackTrace();
                if ("PHONE_NUMBER_UNOCCUPIED".equals(e.getMessage())) {
                    model.setSmsCode(code);
                    setState(LoginView.LoginState.AskNewProfile);
                } else {
                    setState(LoginView.LoginState.AskPhone);
                }
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
