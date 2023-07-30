package com.example.onlineshoppingapplicationacmegrademajorproject.ui.account_information;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccountInformationViewModel extends ViewModel {

    private MutableLiveData<String> userName;
    private MutableLiveData<String> userEmail;

    public AccountInformationViewModel() {
        userName = new MutableLiveData<>();
        userEmail = new MutableLiveData<>();
    }

    public LiveData<String> getUserName() {
        return userName;
    }

    public LiveData<String> getUserEmail() {
        return userEmail;
    }

    public void setUserName(String name) {
        userName.setValue(name);
    }

    public void setUserEmail(String email) {
        userEmail.setValue(email);
    }
}