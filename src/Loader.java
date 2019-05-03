import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.object.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Loader {
    public static void main(String[] args) throws IOException {
        //String hostAddr = "149.154.167.40:443";
        String hostAddr = "149.154.167.50:443";
        Integer appId = 57190;
        String appHash = "967ac20acae40702f3c31e5041048f59";

        TelegramApiBridge bridge = new TelegramApiBridge(hostAddr, appId, appHash);
        AuthCheckedPhone authCheckedPhone = bridge.authCheckPhone("+79024096439");
        //AuthCheckedPhone authCheckedPhone = bridge.authCheckPhone("+9996624444");
        System.out.println("Is invited? "+authCheckedPhone.isInvited());
        System.out.println("Is registered? "+authCheckedPhone.isRegistered());

        if (authCheckedPhone.isRegistered()) {
            System.out.println("Sending request for sms code...");
            bridge.authSendCode("+79024096439");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter sms code:");
            String code = br.readLine();
            AuthAuthorization authorization = bridge.authSignIn(code);
            System.out.println("Authorization: "+authorization);
            User user = authorization.getUser();
            System.out.println("UserFirstName: "+user.getFirstName()
                    +", UserLastName: "+user.getLastName()
                    +", UserID: "+user.getId()
                    +", UserPhone: "+user.getPhone());
        }

        //bridge.authLogOut();
    }
}
