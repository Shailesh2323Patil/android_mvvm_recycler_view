package com.devtides.dogsapp.model;

import android.util.Patterns;

public class Authentication
{
    String strUsername;
    String strPassword;

    public Authentication(String strUsername, String strPassword)
    {
        this.strUsername = strUsername;
        this.strPassword = strPassword;
    }

    public String getStrUsername() {
        return strUsername;
    }

    public void setStrUsername(String strUsername) {
        this.strUsername = strUsername;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    public boolean isEmailValid()
    {
        return Patterns.EMAIL_ADDRESS.matcher(getStrUsername()).matches();
    }

    public boolean isPasswordValid()
    {
        return getStrPassword().length() > 3;
    }
}
