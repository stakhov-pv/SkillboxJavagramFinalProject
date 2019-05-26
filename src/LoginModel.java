import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;

import java.io.IOException;

public class LoginModel {
    private LoginView.LoginState state;
    private TelegramApiBridge bridge;
    private String phoneNumber;

    public LoginModel(TelegramApiBridge bridge) {
        this.bridge = bridge;
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
        AuthCheckedPhone authCheckedPhone = bridge.authCheckPhone(phoneNumber);
        return authCheckedPhone.isRegistered();
    }

    void sendCode(String phoneNumber) throws IOException {
        bridge.authSendCode(phoneNumber);
    }

    AuthAuthorization signIn(String code) throws IOException {
        AuthAuthorization authorization = bridge.authSignIn(code);
        return authorization;
    }

}
