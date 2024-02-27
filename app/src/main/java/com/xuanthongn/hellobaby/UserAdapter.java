package com.xuanthongn.hellobaby;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.xuanthongn.hellobaby.models.User;

import java.security.AccessController;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private static List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
//        holder.userIdView.setText(String.valueOf(user.getId()));
        holder.userEmailTextView.setText(user.getEmail());
//        holder.userPasswordTextView.setText(user.getPassword());
        holder.userRoleTextView.setText(user.getRole());

        //Show dialog to show user detail when click on user item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Query user detail from database and show in dialog
                showUserDetailDialog(user, holder.itemView);

            }
        });
    }

    private void showUserDetailDialog(User user, View itemView) {
        UserDetailDialog dialog = new UserDetailDialog(user);
        dialog.show(((AppCompatActivity) itemView.getContext()).getSupportFragmentManager(), "UserDetailDialog");
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
//        TextView userIdView;
        TextView userEmailTextView;
        TextView userPasswordTextView;
        TextView userRoleTextView;
        FrameLayout updateActionLayout;



        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userEmailTextView = itemView.findViewById(R.id.textViewEmail);
            userRoleTextView = itemView.findViewById(R.id.textViewRole);
            updateActionLayout = itemView.findViewById(R.id.updateActionLayout);


            // Set click listener for the update action icon
            updateActionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        User user = userList.get(position);
                        showUpdateUserDialog(user, v);
                    }
                }
            });
        }
    }
    private static void showUpdateUserDialog(User user, View itemView) {
        UserUpdateDialog dialog = new UserUpdateDialog(user, new UserAdapter(userList));
        dialog.show(((AppCompatActivity) itemView.getContext()).getSupportFragmentManager(), "UserUpdateDialog");

    }

}
