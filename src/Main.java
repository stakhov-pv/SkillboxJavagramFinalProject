public class Main {
    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        loginView.show();
        loginView.showState(LoginView.LoginState.AskPhone);
    }
}
