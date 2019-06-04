import model.LoginModel;
import presenter.LoginPresenter;
import view.LoginView;

public class Main {
    public static void main(String[] args) {

        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel();
        LoginPresenter loginPresenter = new LoginPresenter(loginModel,loginView);
        loginPresenter.start();
    }


}
