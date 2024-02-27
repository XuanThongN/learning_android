package com.xuanthongn.hellobaby.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuanthongn.hellobaby.R;
import com.xuanthongn.hellobaby.UserAdapter;
import com.xuanthongn.hellobaby.dao.UserDatabaseHelper;
import com.xuanthongn.hellobaby.models.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    private Button buttonCreateUser;
    private UserDatabaseHelper db;
    private String selectedRole;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        db = new UserDatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerUserView);
        userList = db.getAllUsers(); // Replace this with your actual method to get the list of users
        userAdapter = new UserAdapter(userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
        buttonCreateUser = findViewById(R.id.buttonCreateUser);

        //Click to open user create
        buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateUserDialog();
            }
        });


    }


    private void showCreateUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create User");

        // Inflate the dialog layout
        View view = LayoutInflater.from(this).inflate(R.layout.create_user_dialog, null);
        builder.setView(view);

        // Get references to the EditText views
        EditText editTextEmail = view.findViewById(R.id.editTextEmail);
        EditText editTextPassword = view.findViewById(R.id.editTextPassword);
        Spinner roleSpinner = (Spinner) view.findViewById(R.id.roleSpinner);

        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRole = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no role is selected
                selectedRole = "";
            }
        });
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the user input from the EditText views
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String role = selectedRole;

                // Perform any validation or processing on the input data

                // Create a new User object with the input data
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setPassword(password);
                newUser.setRole(role);

                // Save the new user to the database or perform any other necessary actions
                db.addUser(newUser);

                // Refresh the user list
                getAllUsers();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Get all users
    private void getAllUsers() {
        userList.clear();
        userList.addAll(db.getAllUsers());
        userAdapter.notifyDataSetChanged();
    }

}