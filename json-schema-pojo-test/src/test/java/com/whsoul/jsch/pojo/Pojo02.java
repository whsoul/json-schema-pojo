package com.whsoul.jsch.pojo;

public class Pojo02 {

    public enum CATEGORY{
        A,
        B,
        C
    }

    public static class InnerPojo{
        public String str;
        public Integer integer;
    }

    public CATEGORY category;
    public InnerPojo innerPojo;
    public Pojo01 extPojo;

}
