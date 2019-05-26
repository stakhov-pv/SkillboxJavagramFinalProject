public class LoginModel {
    private LoginView.LoginState state;

    public LoginView.LoginState getState() {
        return state;
    }

    public void setState(LoginView.LoginState state) {
        this.state = state;
    }

    public void fakeGetData(FakeDataReady listener) {
        Thread waiter = new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                   Thread.sleep(2000);
               } catch (Exception e) {}
               listener.dataReadyListener();
            }
        });
        waiter.start();
    }

    public interface FakeDataReady {
        void dataReadyListener();
    }
}
