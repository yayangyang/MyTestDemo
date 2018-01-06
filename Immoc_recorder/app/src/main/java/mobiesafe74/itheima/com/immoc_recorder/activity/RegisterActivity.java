package mobiesafe74.itheima.com.immoc_recorder.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.AVAnalytics;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil;

import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.myName;

public class RegisterActivity extends AppCompatActivity {
  private AutoCompleteTextView mUsernameView;
  private EditText mPasswordView;
  private View mProgressView;
  private View mRegisterFormView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);

    mPasswordView = (EditText) findViewById(R.id.password);
    mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.register || id == EditorInfo.IME_NULL) {
          attemptRegister();
          return true;
        }
        return false;
      }
    });

    Button musernameSignInButton = (Button) findViewById(R.id.username_register_button);
    musernameSignInButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        attemptRegister();
      }
    });

    mRegisterFormView = findViewById(R.id.register_form);
    mProgressView = findViewById(R.id.register_progress);
  }

  private void attemptRegister() {
    mUsernameView.setError(null);
    mPasswordView.setError(null);

    String username = mUsernameView.getText().toString();
    String password = mPasswordView.getText().toString();

    boolean cancel = false;
    View focusView = null;

    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
      mPasswordView.setError(getString(R.string.error_invalid_password));
      focusView = mPasswordView;
      cancel = true;
    }

    if (TextUtils.isEmpty(username)) {
      mUsernameView.setError(getString(R.string.error_field_required));
      focusView = mUsernameView;
      cancel = true;
    }

    if (cancel) {
      focusView.requestFocus();
    } else {
      showProgress(true);
      AVUser user = new AVUser();// 新建 AVUser 对象实例
      user.setUsername(username);// 设置用户名
      user.setPassword(password);// 设置密码
      user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(AVException e) {
          if (e == null) {
            // 注册成功并登录成功
            login(mUsernameView.getText().toString());
          } else {
            // 失败的原因可能有多种，常见的是用户名已经存在。
            showProgress(false);
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }

  public void login(String username){
    LoginActivity.myName=username;
    sendUtil.sendMessage(myName,null,sendUtil.login,null,false,true);
    sendUtil.setLoginListener(new sendUtil.loginListener(){
      @Override
      public void login() {
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        mCloseLoginActivityListener.closeLoginActivityListener();//关闭登录界面
        finish();
      }
    });
  }

  private boolean isPasswordValid(String password) {
    return password.length() > 4;
  }

  private void showProgress(final boolean show) {
    mProgressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    mRegisterFormView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  public static CloseLoginActivityListener mCloseLoginActivityListener;

  public static void setCloseLoginActivityListener(CloseLoginActivityListener l){
    mCloseLoginActivityListener=l;
  }

  public interface CloseLoginActivityListener{
    void closeLoginActivityListener();
  }

}

