public class Main {
    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        loginView.attachListener(new LoginView.Listener() {
            @Override
            public void onPhoneButtonPressed() {
                loginView.showState(LoginView.LoginState.AskCode);

            }

            @Override
            public void onCodeButtonPressed() {
                loginView.showState(LoginView.LoginState.AskNewProfile);

            }

            @Override
            public void onNameButtonPressed() {
                loginView.showState(LoginView.LoginState.AskPhone);

            }
        });
        loginView.show();
        loginView.showState(LoginView.LoginState.AskPhone);
    }
}
