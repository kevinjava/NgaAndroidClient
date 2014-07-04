package com.github.kevinjava.ngaclient.model;

public class Item {

    public String mTitle;
    public int mIconRes;

    public Item(String title, int iconRes) {
        mTitle = title;
        mIconRes = iconRes;
    }

    public Item(String title) {
        mTitle = title;

    }
}
