package com.xuanthongn.hellobaby;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.xuanthongn.hellobaby.models.User;

public class UserDetailDialog extends DialogFragment {

    private User user;

    public UserDetailDialog(User user) {
        this.user = user;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create a custom dialog layout
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.user_detail_dialog);

        // Set the user details in the dialog
        TextView userIdTextView = dialog.findViewById(R.id.userIdTextView);
        TextView emailTextView = dialog.findViewById(R.id.emailTextView);
        TextView passwordTextView = dialog.findViewById(R.id.passwordTextView);
        TextView roleTextView = dialog.findViewById(R.id.roleTextView);

        userIdTextView.setText("Id: " + user.getId());
        emailTextView.setText("Email: " + user.getEmail());
        passwordTextView.setText("Password: " + user.getPassword());
        roleTextView.setText("Role: " + user.getRole());

        return dialog;
    }
}
