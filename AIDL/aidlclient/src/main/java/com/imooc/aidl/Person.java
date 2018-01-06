package com.imooc.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/5.
 */

public class Person implements Parcelable{

    private String name;
    private int age;

    public Person(Parcel source) {
        this.name=source.readString();//哪个先加哪个先取
        this.age=source.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    public static final Creator<Person> CREATOR=new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[0];
        }
    };

    @Override
    public String toString() {
        return "Person{"+
                "name='"+name+'\''+
                ",age="+age+
                '}';
    }

}
