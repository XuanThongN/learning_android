package com.xuanthongn.hellobaby;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginButton = findViewById(R.id.submitLoginButton);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);

        // Handle the click event on the login button to check the login credentials if email = admin and password = admin then navigate to the Contact page
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check the login credentials
                if (email.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    Intent intent = new Intent(LoginActivity.this, ContactActivity.class);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Thông tin đăng nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
