package com.example.onlineshoppingapplicationacmegrademajorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    Button btn_register;
    TextView txt_already_have_account;
    TextInputLayout textInputName, textInputEmail, textInputPassword, textInputRePassword;
    TextInputEditText editTextName, editTextEmail, editTextPassword, editTextRePassword;
    InputValidation inputValidation;
    DataBaseHelper dataBaseHelper;
    User user;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btn_register = findViewById(R.id.btn_submit);
        txt_already_have_account = findViewById(R.id.txt_already_have_account);

        textInputName = findViewById(R.id.textInputName);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputRePassword = findViewById(R.id.textInputRePassword);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextRePassword = findViewById(R.id.editTextRePassword);

        Pattern namePattern = Pattern.compile("^[a-zA-Z\\s]+$");
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Pattern passwordPattern = Pattern.compile("^(?=.*[!@#$%^&*()+=])(?=\\S+$).{8,}$");

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        inputValidation = new InputValidation(RegistrationActivity.this);
        dataBaseHelper = new DataBaseHelper(RegistrationActivity.this);

        editTextName.addTextChangedListener(new RealTimeInputValidation(editTextName, textInputName, namePattern, "Enter Full Name", null, null));
        editTextEmail.addTextChangedListener(new RealTimeInputValidation(editTextEmail, textInputEmail, emailPattern, "Enter Valid Email", null, null));
        editTextPassword.addTextChangedListener(new RealTimeInputValidation(editTextPassword, textInputPassword, passwordPattern, "Enter a valid password (8 characters minimum, including at least one special character)", editTextPassword, null));
        editTextRePassword.addTextChangedListener(new RealTimeInputValidation(editTextRePassword, textInputRePassword, Pattern.compile(".*"), "Passwords do not match", editTextPassword, editTextRePassword));

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textInputEmail.setError("");
                if (!inputValidation.isInputEditTextFilled(editTextName, textInputName, "Name field is Required")){
                    return;
                }
                if (!inputValidation.isInputEditTextFilled(editTextEmail, textInputEmail, "Email field is Required")){
                    return;
                }
                if (!inputValidation.isInputEditTextFilled(editTextPassword, textInputPassword, "Password field is Required")){
                    return;
                }
                if (!inputValidation.isInputEditTextFilled(editTextRePassword, textInputRePassword, "Confirm Password field is Required")){
                    return;
                }

                user = new User();

                if (!dataBaseHelper.checkUser(editTextEmail.getText().toString().trim())) {
                    user.setName(editTextName.getText().toString().trim());
                    user.setEmail(editTextEmail.getText().toString().trim());
                    user.setPassword(editTextPassword.getText().toString().trim());

                    dataBaseHelper.addUser(user);

                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    editor.putString("userEmail", editTextEmail.getText().toString().trim());
                    editor.apply();
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_LONG).show();
                    textInputEmail.setError("Email Already Exists");
                }
            }
        });

        txt_already_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}