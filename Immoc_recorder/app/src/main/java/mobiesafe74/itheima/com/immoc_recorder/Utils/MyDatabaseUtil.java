package mobiesafe74.itheima.com.immoc_recorder.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity;
import mobiesafe74.itheima.com.immoc_recorder.bean.Recorder;
import mobiesafe74.itheima.com.immoc_recorder.bean.conversation;
import mobiesafe74.itheima.com.immoc_recorder.bean.user;

import static android.R.attr.type;
import static com.avos.avoscloud.Messages.OpType.members;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.db;


public class MyDatabaseUtil extends SQLiteOpenHelper {
	private String createConversationTable="create table if not exists conversation" +
			"(username,conversationId,name,members,creator,createdAt,updatedAt,headPortrait int,isGroupChat)";
	//sendTime发送方表示点击发送的时刻,接收方表示接收到的时刻
	private String createMessageTable="create table if not exists message" +
			"(username,conversationId,members,sender,isSelf,type,filePath,audioTime,sendTime)";
	//这是存储用户好友的表
	private String createUserTable="create table if not exists user" +
			"(username,friendname,createdAt,updatedAt)";

	public MyDatabaseUtil(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createConversationTable);
		db.execSQL(createMessageTable);
		db.execSQL(createUserTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {}

//	public static void updateConversation(SQLiteDatabase db,String updatedAt,String conversationId){
//		db.execSQL("update conversation set updatedAt=? where conversationId=?", new String[]{updatedAt,conversationId});
//	}

//	public static void updateMessage(SQLiteDatabase db,String sendTime,String messageId){
//		db.execSQL("update conversation set updateTime=?: where messageId=?", new String[]{sendTime,messageId});
//	}

	public static void insertConversation(SQLiteDatabase db,String conversationId,String name,String members,
					   String creator,String createdAt,String updatedAt,int headPortrait,boolean isGroupChat){
		Log.e("insertConversation",conversationId);
		db.execSQL("insert into conversation values (?,?,?,?,?," +
					"?,?,?,?)",new String[]{LoginActivity.myName,conversationId,
					name,members,creator,createdAt,updatedAt,headPortrait+"",isGroupChat+""});
	}

	public static void insertMessage(SQLiteDatabase db,String conversationId,String members,
							  String sender,boolean isSelf,String type,String filePath,String audioTime,String sendTime){
		db.execSQL("insert into message values (?,?,?,?," +
				"?,?,?,?,?)",new String[]{
				LoginActivity.myName,conversationId,members,sender,isSelf+"",type,filePath,audioTime,sendTime});
		Log.e("msg","555555555555");
	}

	public static void insertUser(SQLiteDatabase db,String username,String friendname,String createdAt,
									 String updatedAt){
		db.execSQL("insert into user values (?,?,?,?)",new String[]{
				username,friendname,createdAt,updatedAt});
	}

	public static List<conversation> getConversation(SQLiteDatabase db,String size){
		Cursor cursor = db.rawQuery("select * " +
						"from conversation where username= ? order by rowid desc limit 0,?",
				new String[]{LoginActivity.myName,size});
		List<conversation> mlist=new ArrayList<conversation>();
		while(cursor.moveToNext()){
			conversation talk = new conversation();
			talk.setConversationId(cursor.getString(cursor.getColumnIndex("conversationId")));
			talk.setName(cursor.getString(cursor.getColumnIndex("name")));
			talk.setMembers(cursor.getString(cursor.getColumnIndex("members")));
			Log.e("talk.getMembers()",talk.getMembers());
			talk.setCreator(cursor.getString(cursor.getColumnIndex("creator")));
			talk.setCreatedAt(cursor.getString(cursor.getColumnIndex("createdAt")));
			talk.setUpdatedAt(cursor.getString(cursor.getColumnIndex("updatedAt")));
			talk.setHeadPortrait(Integer.parseInt(cursor.getString(cursor.getColumnIndex("headPortrait"))));
			talk.setGroupChat(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("isGroupChat"))));
			Log.e("isGroupChat",talk.isGroupChat()+"");
			mlist.add(talk);
		}
		cursor.close();
		return mlist;
	}

	public static List<Recorder> getMessage(SQLiteDatabase db,String conversationId,String limit,String size){
		Cursor cursor = db.rawQuery("select * " +
				"from message where conversationId=? and username=? limit ?,?",
				new String[]{conversationId,LoginActivity.myName,limit,size});
		List<Recorder> mlist=new ArrayList<Recorder>();
		while(cursor.moveToNext()){
			Recorder recorder = new Recorder();
//			recorder.setMessageId(cursor.getString(cursor.getColumnIndex("messageId")));
			recorder.setConversationId(cursor.getString(cursor.getColumnIndex("conversationId")));
			recorder.setMembers(cursor.getString(cursor.getColumnIndex("members")));
			recorder.setFrom(cursor.getString(cursor.getColumnIndex("sender")));
			if(cursor.getString(cursor.getColumnIndex("isSelf")).equals("true")){
				recorder.setSelf(true);
			}else{
				recorder.setSelf(false);
			}
			int type = Integer.parseInt(cursor.getString(cursor.getColumnIndex("type")));
//			Log.e("类型",cursor.getString(cursor.getColumnIndex("type"))+"");
			recorder.setType(type);
			recorder.setFilePath(cursor.getString(cursor.getColumnIndex("filePath")));
//			Log.e("文字",cursor.getString(cursor.getColumnIndex("filePath")));
			recorder.setWz(cursor.getString(cursor.getColumnIndex("filePath")));
			String audioTime = cursor.getString(cursor.getColumnIndex("audioTime"));
			recorder.setTime(Float.parseFloat(audioTime));
			recorder.setSendTime(cursor.getString(cursor.getColumnIndex("sendTime")));
			mlist.add(recorder);
		}
		cursor.close();
		return mlist;
	}

	public static List<user> getUser(SQLiteDatabase db){
		Cursor cursor = db.rawQuery("select friendname,createdAt,updatedAt from user where username=?",
				new String[]{LoginActivity.myName});
		ArrayList<user> users = new ArrayList<>();
		while(cursor.moveToNext()){
			user user=new user();
			user.setUsername(cursor.getString(cursor.getColumnIndex("friendname")));
			user.setCreatedAt(cursor.getString(cursor.getColumnIndex("createdAt")));
			user.setUpdatedAt(cursor.getString(cursor.getColumnIndex("updatedAt")));
			users.add(user);
		}
		cursor.close();
		return users;
	}

	public static boolean isFriend(SQLiteDatabase db,String friendname){
		Boolean isFriend=false;
		Cursor cursor = db.rawQuery("select count(*) from user where username=? and friendname=?",
				new String[]{LoginActivity.myName, friendname});
		if(cursor.moveToNext()){
			int anInt = cursor.getInt(cursor.getColumnIndex("count(*)"));
			if(anInt>0) isFriend=true;
		}
		cursor.close();
		return isFriend;
	}

	public static int getMessageCount(SQLiteDatabase db,String conversationId){
//		Log.e("搜索1",conversationId+"");
		Cursor cursor = db.rawQuery("select count(*) " +
				"from message where conversationId=? and username=?",
				new String[]{conversationId,LoginActivity.myName});
//		Log.e("搜索2","ff");
		int anInt=0;
		if(cursor.moveToNext()){
			anInt = cursor.getInt(cursor.getColumnIndex("count(*)"));
		}
		cursor.close();
		return anInt;
	}

	public static int getConversationCount(SQLiteDatabase db,String conversationId){
		Cursor cursor = db.rawQuery("select count(*) " +
						"from conversation where conversationId=? and username=?",
				new String[]{conversationId,LoginActivity.myName});
		int anInt=0;
		if(cursor.moveToNext()){
			anInt = cursor.getInt(cursor.getColumnIndex("count(*)"));
		}
		cursor.close();
		Log.e("anInt",anInt+"");
		return anInt;
	}

	public static String getConversationId(SQLiteDatabase db,List<String> members){
		Cursor cursor = db.rawQuery("select conversationId,isGroupChat " +
				"from conversation where members=?", new String[]{members.toString()});
		String conversationId="-1";
		String isGroupChat="";
		while (cursor.moveToNext()){
			isGroupChat = cursor.getString(cursor.getColumnIndex("isGroupChat"));
			if(isGroupChat.equals("false")){
				return cursor.getString(cursor.getColumnIndex("conversationId"));
			}
		}
		cursor.close();
		return conversationId;
	}

	//取出群聊会话
	public static List<conversation> getGroupChatConversation(SQLiteDatabase db){
		Cursor cursor = db.rawQuery("select * from conversation where username=? and isGroupChat=?"
				, new String[]{LoginActivity.myName, "true"});
		List<conversation> mlist=new ArrayList<conversation>();
		while(cursor.moveToNext()){
			conversation talk = new conversation();
			talk.setConversationId(cursor.getString(cursor.getColumnIndex("conversationId")));
			talk.setName(cursor.getString(cursor.getColumnIndex("name")));
			talk.setMembers(cursor.getString(cursor.getColumnIndex("members")));
			Log.e("talk.getMembers()",talk.getMembers());
			talk.setCreator(cursor.getString(cursor.getColumnIndex("creator")));
			talk.setCreatedAt(cursor.getString(cursor.getColumnIndex("createdAt")));
			talk.setUpdatedAt(cursor.getString(cursor.getColumnIndex("updatedAt")));
			talk.setHeadPortrait(Integer.parseInt(cursor.getString(cursor.getColumnIndex("headPortrait"))));
			talk.setGroupChat(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("isGroupChat"))));
			Log.e("isGroupChat",talk.isGroupChat()+"");
			mlist.add(talk);
		}
		cursor.close();
		return mlist;
	}

	public static void deleteAllFriend(SQLiteDatabase db){
		db.execSQL("delete from user");
	}

}
