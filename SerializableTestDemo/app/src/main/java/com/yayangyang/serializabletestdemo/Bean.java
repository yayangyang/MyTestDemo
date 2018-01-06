package com.yayangyang.serializabletestdemo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/30.
 */

public class Bean implements Serializable{

    //当类发生常规性改变的时候仍能取到已保存的对象的值,不然返回null
    public static final long serialVersionUID=123L;

    public String id;
    public String name;
    public String sender;

}
