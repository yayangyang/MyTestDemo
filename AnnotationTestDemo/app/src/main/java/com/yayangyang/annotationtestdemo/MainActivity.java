package com.yayangyang.annotationtestdemo;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//直接在定义的变脸前添加定义不会有错误提示,set和get有错误提示
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Person person = new Person();
        person.setSex(aa);

        @SEX int sex=aa;//这样写不会提示
    }
    class Person {
        @SEX
        private int sex=1;
        public void setSex(@SEX int sex) {
            this.sex = sex;
        }
        @SEX
        public int getSex() {
            return sex;
        }
        public String getSexDes() {
            if (sex == SEX.MALE) {
                return "男";
            } else {
                return "女";
            }
        }
    }

    private final static int aa=-1;

    @IntDef({SEX.MALE, SEX.FEMALE,aa})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SEX {
        int MALE = 0;
        int FEMALE = 1;
    }
}
