package com.xuanthongn.hellobaby;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.xuanthongn.hellobaby.dao.UserDatabaseHelper;
import com.xuanthongn.hellobaby.models.User;

public class UserUpdateDialog extends DialogFragment {

    private User user;
    private UserAdapter userAdapter;

    public UserUpdateDialog(User user, UserAdapter userAdapter) {
        this.user = user;
        this.userAdapter = userAdapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create a custom dialog layout
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.update_user_dialog);

        // Set the user details in the dialog
        EditText emailText = dialog.findViewById(R.id.updateTextEmail);
        EditText passwordText = dialog.findViewById(R.id.updateTextPassword);
        Spinner roleTextView = dialog.findViewById(R.id.roleSpinner);

        emailText.setText(user.getEmail());
        passwordText.setText(user.getPassword());
        roleTextView.setSelection(user.getRole().equals("Admin") ? 0 : user.getRole().equals("User") ? 1 : 2);

        //Set action for update button
        dialog.findViewById(R.id.updateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Update user in database
                user.setEmail(emailText.getText().toString());
                user.setPassword(passwordText.getText().toString());
                user.setRole(roleTextView.getSelectedItem().toString());
                UserDatabaseHelper db = new UserDatabaseHelper(requireContext());
                db.updateUser(user);
                userAdapter.notifyDataSetChanged();
                dismiss();
            }
        });
        return dialog;
    }
}
