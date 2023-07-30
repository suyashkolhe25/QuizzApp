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

public class LoginActivity extends AppCompatActivity {

    Button btn_sign_in;
    TextView sign_up;
    TextInputLayout textInputEmail, textInputPassword;
    TextInputEditText editTextEmail, editTextPassword;
    InputValidation inputValidation;
    DataBaseHelper dataBaseHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEmail = (TextInputLayout) findViewById(R.id.textInputEmail);
        textInputPassword = (TextInputLayout) findViewById(R.id.textInputPassword);

        editTextEmail = (TextInputEditText) findViewById(R.id.editTextEmail);
        editTextPassword = (TextInputEditText) findViewById(R.id.editTextPassword);

        btn_sign_in = (Button) findViewById(R.id.sign_in);
        sign_up = (TextView) findViewById(R.id.sign_up);

        inputValidation = new InputValidation(LoginActivity.this);
        dataBaseHelper = new DataBaseHelper(LoginActivity.this);

        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Pattern passwordPattern = Pattern.compile("^(?=.*[!@#$%^&*()+=])(?=\\S+$).{8,}$");

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editTextEmail.addTextChangedListener(new RealTimeInputValidation(editTextEmail, textInputEmail, emailPattern, "Enter Valid Email", null, null));
        editTextPassword.addTextChangedListener(new RealTimeInputValidation(editTextPassword, textInputPassword, passwordPattern, "Enter a valid password (8 characters minimum, including at least one special character)", editTextPassword, null));

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textInputEmail.setError("");
                textInputPassword.setError("");

                if (!inputValidation.isInputEditTextFilled(editTextEmail, textInputEmail, "Email field is Required")){
                    return;
                }
                if (!inputValidation.isInputEditTextFilled(editTextPassword, textInputPassword, "Password field is Required")){
                    return;
                }

                if (dataBaseHelper.checkUsernamePassword(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim())) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    editor.putString("userEmail", editTextEmail.getText().toString().trim());
                    editor.apply();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Entered Wrong Email-id or password", Toast.LENGTH_SHORT).show();
                    textInputEmail.setError("Incorrect Email-id");
                    textInputPassword.setError("Incorrect Password");
                }


            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }
}