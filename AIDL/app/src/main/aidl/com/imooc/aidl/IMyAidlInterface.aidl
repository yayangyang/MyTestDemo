// IMyAidlInterface.imooc.aidl
package com.imooc.aidl;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<String> basicTypes(byte aByte,int anInt, long aLong,
            boolean aBoolean, float aFloat,
            double aDouble,char aChar, String aString,in List<String> aList);
}
