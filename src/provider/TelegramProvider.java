package provider;

import org.javagram.TelegramApiBridge;
import org.javagram.core.StaticContainer;
import org.javagram.handlers.IncomingMessageHandler;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.MessagesMessages;
import org.javagram.response.MessagesSentMessage;
import org.javagram.response.object.*;
import org.javagram.response.object.inputs.InputContact;
import org.javagram.response.object.inputs.InputUserOrPeerContact;
import org.telegram.api.*;
import org.telegram.api.contacts.TLImportedContacts;
import org.telegram.api.requests.TLRequestContactsImportContacts;
import org.telegram.api.requests.TLRequestMessagesGetHistory;
import org.telegram.tl.TLVector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TelegramProvider {
    private static TelegramProvider instance;
    private TelegramApiBridge bridge;
    private User user;
    private BufferedImage userSmallPic;
    private BufferedImage userPic;
    private IncomingMessageListener incomingMessageListener;

    public static TelegramProvider getInstance() {
        if (instance==null) {
            synchronized (TelegramProvider.class) {
                instance = new TelegramProvider();
            }
        }
        return instance;
    }

    public interface IncomingMessageListener {
        void onIncomingMessage(int userId, String message);
    }

    private TelegramProvider() {
        bridge = initBridge();
        bridge.setIncomingMessageHandler(new IncomingMessageHandler() {
            @Override
            public Object handle(int userId, String message) {
                if (incomingMessageListener!=null) {
                    incomingMessageListener.onIncomingMessage(userId, message);
                }
                return null;
            }
        });
    }

    private TelegramApiBridge initBridge() {

        boolean workOnTestServer = true;

        String testAddr = "149.154.167.40:443";
        String workAddr = "149.154.167.50:443";
        String hostAddr = workOnTestServer?testAddr:workAddr;

        //String testPhoneNumber = "+9996624444";
        //String phoneNumber = testPhoneNumber;

        //My data
        Integer myAppId = 57190;
        String myAppHash = "967ac20acae40702f3c31e5041048f59";

        //From tdlib
        Integer testAppId = 94575;
        String testAppHash = "a3406de8d171bb422bb6ddf3bbd800e2";

        Integer appId = workOnTestServer?testAppId:myAppId;
        String appHash = workOnTestServer?testAppHash:myAppHash;


        TelegramApiBridge bridge = null;
        try {
            bridge = new TelegramApiBridge(hostAddr, appId, appHash);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bridge;
    }

    public boolean checkPhoneRegisteredStatus(String phoneNumber) throws IOException {
        AuthCheckedPhone authCheckedPhone = bridge.authCheckPhone(phoneNumber);
        return authCheckedPhone.isRegistered();
    }

    public void requestCode(String phoneNumber) throws IOException {
        bridge.authSendCode(phoneNumber);
    }

    public boolean signIn(String code) throws IOException {
        AuthAuthorization authorization = bridge.authSignIn(code);
        if (authorization==null) return false;
        user = authorization.getUser();
        getProfilePics();
        return true;
    }

    //TODO: maybe I don't need to load pictures here?
    private void getProfilePics() {
        try {
            userSmallPic = ImageIO.read(new ByteArrayInputStream(user.getPhoto(true)));
            userPic = ImageIO.read(new ByteArrayInputStream(user.getPhoto(false)));
        } catch (Exception e) {
            e.printStackTrace();
            userSmallPic = Res.getImage("your-face.png");
            userPic = Res.getImage("your-face.png");
        }
    }

    public boolean signUp(String code, String firstName, String lastName) throws IOException {
        AuthAuthorization authorization = bridge.authSignUp(code, firstName, lastName);
        if (authorization==null) return false;
        user = authorization.getUser();
        getProfilePics();
        return true;
    }

    public int getUserId() {
        return user.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserFirstName() {
        return user.getFirstName();
    }

    public String getUserLastName() {
        return user.getLastName();
    }

    public String getUserPhone() {
        return user.getPhone();
    }

    public String getUserName() {
        return (user.getFirstName()+" "+user.getLastName()).trim();
    }

    public BufferedImage getUserPic() {
        return userPic;
    }

    public BufferedImage getUserSmallPic() {
        return userSmallPic;
    }

    public BufferedImage getUserPic(User user, boolean small) {
        return user.getPhoto(bridge, small);
    }

    public void attachIncomingMessageListener(IncomingMessageListener incomingMessageListener) {
        this.incomingMessageListener = incomingMessageListener;
    }

    public void detachIncomingMessageListener() {
        incomingMessageListener = null;
    }

    public void updateProfile(String firstName, String lastName) {
        for (;;) {
            try {
                User updatedUser = bridge.accountUpdateProfile(firstName, lastName);
                user = updatedUser;
                return;
            } catch (Exception e) {
                e.printStackTrace();
                waitBeforeRepeat();
            }
        }
    }

    public List<MessagesDialog> getDialogs() {
        for (;;) {
            try {
                ArrayList<MessagesDialog> dialogs = bridge.messagesGetDialogs();
                return dialogs;
            } catch (Exception e) {
                e.printStackTrace();
            }
            waitBeforeRepeat();
        }

    }

    public ArrayList<Message> getMessages(ArrayList<Integer> messageIds) {
        for (;;) {
            try {
                ArrayList<Message> messages = bridge.messagesGetMessages(messageIds);
                return messages;
            } catch (Exception e) {
                e.printStackTrace();
            }
            waitBeforeRepeat();
        }
    }

    public ArrayList<User> getUsers(ArrayList<Integer> userIds) {
        for (;;) {
            try {
                ArrayList<InputUser> inputUsers = userIds.stream().map(InputUserOrPeerContact::new).
                                        collect(Collectors.toCollection(ArrayList::new));
                return bridge.usersGetUsers(inputUsers);

            } catch (Exception e) {
                e.printStackTrace();
            }
            waitBeforeRepeat();
        }
    }

    public User getUser(int userId) {
        ArrayList<Integer> userIds = new ArrayList<>();
        userIds.add(userId);
        ArrayList<User> users = getUsers(userIds);
        if (users.size()>0) return users.get(0);
        else return null;
    }

    public List<Message> getConversationsMessages(int userId) {
        for (;;) {
            try {
                TLRequestMessagesGetHistory request = new TLRequestMessagesGetHistory(new TLInputPeerContact(userId), 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
                TLVector<TLAbsMessage> tlAbsMessages = StaticContainer.getTelegramApi().doRpcCall(request).getMessages();
                List<Message> messages = new LinkedList<>();
                for (TLAbsMessage tlMsg : tlAbsMessages) {
                    messages.add(0, new Message(tlMsg));
                }
                return messages;
            } catch (IOException e) {
                e.printStackTrace();
                if ("PEER_ID_INVALID".equals(e.getMessage())) return new ArrayList<Message>();
            }
            waitBeforeRepeat();
        }
    }

    public boolean sendMessage(int userId, String message) {
        int randomInt = (int)(Math.random()*Integer.MAX_VALUE);
        InputPeer inputPeer = new InputUserOrPeerContact(userId);
        try {
            MessagesSentMessage sent = bridge.messagesSendMessage(inputPeer, message, randomInt);
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    public Integer contactsImportContact(InputContact inputContact, boolean replace) {
        for (;;) {
            try {
                TLVector<TLInputContact> inputContacts = new TLVector();
                inputContacts.add(inputContact.createTLInputContact());
                TLRequestContactsImportContacts tlRequestContactsImportContacts = new TLRequestContactsImportContacts(inputContacts, replace);
                TLImportedContacts tlImportedContacts = (TLImportedContacts)StaticContainer.getTelegramApi().doRpcCall(tlRequestContactsImportContacts);
                if (tlImportedContacts.getImported().size() == 0) {
                    return null;
                } else {
                    TLImportedContact tlImportedContact = (TLImportedContact)tlImportedContacts.getImported().get(0);
                    return tlImportedContact.getClientId() == inputContact.getClientId() ? tlImportedContact.getUserId() : null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            waitBeforeRepeat();
        }
    }

    public boolean contactsDeleteContact(int userId) {
        for (;;) {
            try {
                return bridge.contactsDeleteContact(userId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            waitBeforeRepeat();
        }
    }

    public ArrayList<UserContact> contactsGetContacts() {
        for (;;) {
            try {
                return bridge.contactsGetContacts();
            } catch (IOException e) {
                e.printStackTrace();
            }
            waitBeforeRepeat();
        }
    }

    public MessagesMessages messagesSearch(String query) {
        for (;;) {
            try {
                return bridge.messagesSearch(query,0,Integer.MAX_VALUE,Integer.MAX_VALUE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            waitBeforeRepeat();
        }
    }

    private void waitBeforeRepeat() {
        try {
            Thread.sleep(1000);
        } catch (Exception e1) {}
    }

    public void logoff() {
        try {
            bridge.authLogOut();
        } catch (Exception e) {
            //TODO: need repeat???
        }
    }

}
