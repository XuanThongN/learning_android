package com.xuanthongn.hellobaby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(context, this);

        // Speak the alarm message
        speakAlarmMessage(context);
    }

    private void speakAlarmMessage(final Context context) {
        if (textToSpeech == null) {
            return;
        }

        // Get the current time
        String timeString = "..."; // Get the current time using Calendar or SimpleDateFormat

        // Speak the alarm message
        textToSpeech.speak("Đến giờ dậy rồi. Bây giờ là " + timeString,
                TextToSpeech.QUEUE_FLUSH, null, null);
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
}

