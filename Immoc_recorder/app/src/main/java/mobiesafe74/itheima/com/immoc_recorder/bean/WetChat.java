package mobiesafe74.itheima.com.immoc_recorder.bean;

import com.avos.avoscloud.im.v2.AVIMConversation;

/**
 * Created by Administrator on 2017/5/8.
 */

public class WetChat {
    private int leftImage;

    private boolean canVisivile;
    private String name;
    private String message;
    private String time;
    private String conversationId;
    private String members;
    private boolean isGroupChat;

    public boolean isGroupChat() {
        return isGroupChat;
    }

    public void setGroupChat(boolean groupChat) {
        isGroupChat = groupChat;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public int getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(int leftImage) {
        this.leftImage = leftImage;
    }

    public boolean isCanVisivile() {
        return canVisivile;
    }

    public void setCanVisivile(boolean canVisivile) {
        this.canVisivile = canVisivile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
