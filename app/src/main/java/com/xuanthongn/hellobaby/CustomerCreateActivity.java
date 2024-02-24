package com.xuanthongn.hellobaby;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerCreateActivity extends AppCompatActivity {
    private Button saveButton;
    private EditText fullName;
    private EditText dateOfBirth;;
    private EditText homeAddress;
    private EditText phoneNumber;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_create);

        saveButton = findViewById(R.id.buttonSave);
        fullName = findViewById(R.id.textInputEditTextName);
        dateOfBirth = findViewById(R.id.textInputEditTextDateOfBirth);
        homeAddress = findViewById(R.id.textInputEditTextHometown);
        phoneNumber = findViewById(R.id.textInputEditTextPhoneNumber);

        // Handle the click event on the save button to show all in log cat
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Save", "Full Name: " + fullName.getText().toString());
                Log.d("Save", "Date of Birth: " + dateOfBirth.getText().toString());
                Log.d("Save", "Home Address: " + homeAddress.getText().toString());
                Log.d("Save", "Phone Number: " + phoneNumber.getText().toString());
            }
        });

    }
}
