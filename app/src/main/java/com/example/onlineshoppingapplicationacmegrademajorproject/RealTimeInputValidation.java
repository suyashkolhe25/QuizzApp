package com.example.onlineshoppingapplicationacmegrademajorproject;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RealTimeInputValidation implements TextWatcher {

    private TextInputEditText editText, editTextPassword, editTextRePassword;
    private TextInputLayout textInputLayout;
    private Pattern pattern;
    private String errorMessage;

    public RealTimeInputValidation(TextInputEditText editText, TextInputLayout textInputLayout, Pattern pattern, String errorMessage, TextInputEditText editTextPassword, TextInputEditText editTextRePassword) {
        this.editText = editText;
        this.textInputLayout = textInputLayout;
        this.pattern = pattern;
        this.errorMessage = errorMessage;
        this.editTextPassword = editTextPassword;
        this.editTextRePassword = editTextRePassword;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void afterTextChanged(Editable editable) {
        String value = editable.toString().trim();
        if (value.isEmpty()) {
            textInputLayout.setError("Required");
        } else if (!pattern.matcher(value).matches()){
            textInputLayout.setError(errorMessage);
        } else {
            textInputLayout.setError(null);
        }

        if (editText == editTextRePassword) {
            String password = editTextPassword.getText().toString().trim();
            String rePassword = value;
            if (!rePassword.equals(password)) {
                textInputLayout.setError("Passwords do not match");
            } else {
                textInputLayout.setError(null);
            }
        }
    }
}
