public class Main {
    public static void main(String[] args) {


        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel();
        LoginPresenter loginPresenter = new LoginPresenter(loginView, loginModel);
        loginPresenter.start();
    }
}
