import view.LoginView;

public class TestGfx {
    public static void main(String[] params) {
        LoginView view = new LoginView();
        view.show();
        view.showState(LoginView.LoginState.AskNewProfile);
    }
}
