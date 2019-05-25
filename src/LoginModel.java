public class LoginModel {
    private LoginView.LoginState state;

    public LoginView.LoginState getState() {
        return state;
    }

    public void setState(LoginView.LoginState state) {
        this.state = state;
    }
}
