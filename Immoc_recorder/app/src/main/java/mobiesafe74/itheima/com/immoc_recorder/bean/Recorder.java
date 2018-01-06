package mobiesafe74.itheima.com.immoc_recorder.bean;

import static android.R.attr.type;

/**
 * Created by Administrator on 2017/4/30.
 */

public class Recorder{
    public static int TYPE_WORD=-1;//文字类型
    public static int TYPE_IMAGE=-2;//图片类型
    public static int TYPE_VOICE=-3;//语音类型
    public static int TYPE_VIDEO=-4;//视频类型
    public static int TYPE_LOCATION=-5;//位置类型
    public static int TYPE_FILE=-6;//文件类型

    private int type=-1;
    private float time;
    private int drawableId;
    private boolean isSelf;

    private String filePath;
    private String wz;
    private String messageId;
    private String conversationId;
    private String members;
    private String from;
    private String audioTime;
    private String sendTime;

    public String getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(String audioTime) {
        this.audioTime = audioTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public Recorder() {}

    public int getType() {
        return type;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    public String getWz() {
        return wz;
    }

    public void setWz(String wz) {
        this.wz = wz;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Recorder(float time, String filePath) {
        this.time = time;
        this.filePath = filePath;
    }

}