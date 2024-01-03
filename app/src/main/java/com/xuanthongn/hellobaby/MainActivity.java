package com.xuanthongn.hellobaby;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener, TimePickerFragment.TimePickerListener {

    private TextToSpeech textToSpeech;
    private Button setAlarmButton;
    private Button showToastButton;
    private Button showAlertButton;
    private Button showCustomAlertButton;
    private Button showNotificationButton;
    private TextView selectedTimeTextView;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    TextView okay_text, cancel_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, this);

        // Initialize AlarmManager
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Get reference to UI components
        setAlarmButton = findViewById(R.id.setAlarmButton);
        showToastButton = findViewById(R.id.showToastButton);
        showAlertButton = findViewById(R.id.showAlertButton);
        showCustomAlertButton = findViewById(R.id.showCustomAlertButton);
        showNotificationButton = findViewById(R.id.showNotificationButton);
        selectedTimeTextView = findViewById(R.id.selectedTimeTextView);


        // Set click listener for the button to show TimePickerDialog
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        // Set click listener for the showToastButton
        showToastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Hello, this is a toast message!"); // Replace the message with your desired toast message
            }
        });

        // Set click listener for the showAlertButton
        showAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert("Hello, this is an alert dialog!"); // Replace the message with your desired alert message
            }
        });

        Dialog dialog = new Dialog(MainActivity.this);

        showCustomAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.custom_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                okay_text = dialog.findViewById(R.id.okay_text);
                cancel_text = dialog.findViewById(R.id.cancel_text);

                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "okay clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                cancel_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();

            }
        });

        showNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });
    }


    private void showAlert(String s) {
        // Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the alert message
        builder.setMessage(s);

        // Set the positive button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when the positive button is clicked
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void showTimePickerDialog() {
        DialogFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Convert selected time to a string format
        String timeString = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);

        // Update TextView to show the selected time
        selectedTimeTextView.setText("Selected Time: " + timeString);

        // Set up the alarm for the selected time
        setAlarm(hourOfDay, minute);
    }

    private void setAlarm(int hourOfDay, int minute) {
        // Create a Calendar instance for the selected time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Create an Intent to be broadcasted when the alarm fires
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Set the alarm to trigger at the specified time
        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                alarmIntent
        );
    }


    private void addNotification() {
        // Create an intent to open the NotificationView activity
        Intent notificationIntent = new Intent(this, NotificationView.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("message", "This is a notification message");

        // Create a pending intent to wrap the notification intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create a notification channel
        String channelId = "my_channel_id";
        CharSequence channelName = "My Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

        // Get the notification manager and create the channel
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("Thông báo từ Hello Baby")
                .setContentText("Chiêm nghiệm hồng trần đại đế ! :D")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        // Show the notification
        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(new Locale("vi_VN"));

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported.");
            }
        } else {
            Log.e("TTS", "Initialization failed.");
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        // Cancel the alarm if the activity is destroyed
        if (alarmManager != null && alarmIntent != null) {
            alarmManager.cancel(alarmIntent);
        }

        super.onDestroy();
    }
}
