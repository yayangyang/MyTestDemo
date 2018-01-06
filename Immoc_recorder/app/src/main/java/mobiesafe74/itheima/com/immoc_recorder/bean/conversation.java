package mobiesafe74.itheima.com.immoc_recorder.bean;

/**
 * Created by Administrator on 2017/5/15.
 */

public class conversation {
    private String conversationId;
    private String name;
    private String members;
    private String creator;
    private String createdAt;
    private String updatedAt;
    private int headPortrait;
    private boolean isGroupChat;

    public boolean isGroupChat() {
        return isGroupChat;
    }

    public void setGroupChat(boolean groupChat) {
        isGroupChat = groupChat;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public int getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(int headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
