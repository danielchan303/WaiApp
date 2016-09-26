package tk.gengwai.waiapp.model;

public class Contract {
    public static final String EXTRA_KEY_CHATROOM = "EXTRA_KEY_CHATROOM";
    public static final String EXTRA_NAME_CHATROOM = "EXTRA_NAME_CHATROOM";

    public static final String FIREBASE_MESSAGE = "/message/";
    public static final String FIREBASE_CHATROOM = "/chatroom/";

    public static String getFirebaseChatroomLastUpdated(String chatRoomKey) {
        return FIREBASE_CHATROOM + chatRoomKey + "/lastUpdated/";
    }

    public static String getFirebaseChatroom(String chatRoomKey) {
        return FIREBASE_CHATROOM + chatRoomKey + "/";
    }

    public static String getFirebaseMessage(String chatRoomKey) {
        return FIREBASE_MESSAGE + chatRoomKey + "/";
    }

}
