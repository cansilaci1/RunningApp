package com.example.runningapp.others;


//bir kez giriş yapan birine bir daha kayıt ol ekranı açılmıyor
public class SessionManager {
    private static boolean isLoggedIn = false;

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
