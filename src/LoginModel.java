import org.javagram.response.AuthAuthorization;

import java.io.IOException;

public class LoginModel {
    private LoginView.LoginState state;
    //TODO: save phone number in storage to remember in second start
    private String phoneNumber;

    public LoginModel() {

    }

    public LoginView.LoginState getState() {
        return state;
    }

    public void setState(LoginView.LoginState state) {
        this.state = state;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void fakeGetData(DataReady listener) {
        Thread waiter = new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                   Thread.sleep(2000);
               } catch (Exception e) {}
               listener.dataReadyListener(null);
            }
        });
        waiter.start();
    }

    public interface DataReady {
        void dataReadyListener(Object result);
    }

    boolean getRegisteredStatus(String phoneNumber) throws IOException {
        return TelegramProvider.getInstance().checkPhoneRegisteredStatus(phoneNumber);
    }

    void sendCode(String phoneNumber) throws IOException {
        TelegramProvider.getInstance().requestCode(phoneNumber);
    }

    boolean signIn(String code) throws IOException {
        return TelegramProvider.getInstance().signIn(code);
    }

}
