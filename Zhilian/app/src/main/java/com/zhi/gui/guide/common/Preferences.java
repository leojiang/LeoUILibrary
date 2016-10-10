package com.zhi.gui.guide.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.zhi.gui.guide.data.UserBasicInfo;

public final class Preferences {
    public static final String PREFERENCE_NAME = "zhilian_app";
    public static final String KEY_USERNAME = "login_username";
    public static final String KEY_COLLEGE = "college";
    public static final String KEY_EDUCATION = "education";
    public static final String KEY_GRADE = "grade";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_MAJOR = "major";
    public static final String KEY_NICKNAME = "nick_name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_IS_LOGGED_IN = "is_logged_in";

    public static void setUserBasicInfo(Context context, UserBasicInfo info) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).edit();
        editor.putString(KEY_USERNAME, info.user_name);
        editor.putString(KEY_COLLEGE, info.college);
        editor.putString(KEY_EDUCATION, info.education);
        editor.putString(KEY_GRADE, info.grade);
        editor.putString(KEY_MAIL, info.mail);
        editor.putString(KEY_MAJOR, info.major);
        editor.putString(KEY_NICKNAME, info.nick_name);
        editor.putString(KEY_PHONE, info.phone);
        editor.commit();
    }

    public static void clearUserInfo(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).edit();
        editor.putString(KEY_USERNAME, "");
        editor.putString(KEY_COLLEGE, "");
        editor.putString(KEY_EDUCATION, "");
        editor.putString(KEY_GRADE, "");
        editor.putString(KEY_MAIL, "");
        editor.putString(KEY_MAJOR, "");
        editor.putString(KEY_NICKNAME, "");
        editor.putString(KEY_PHONE, "");
        editor.commit();
    }

    public static void setUserLoggedInState(Context context, boolean isLoggedIn) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public static boolean getUserLoggedInState(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND);
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }


    public static void setUserName(Context context, String username) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).edit();
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND);
        return preferences.getString(KEY_USERNAME, "");
    }

}
