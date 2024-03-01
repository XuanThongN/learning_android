package com.xuanthongn.hellobaby;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setMax(100);

        Button downloadButton = findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadFileTask().execute("https://download1323.mediafire.com/bnab473y17xg6jU2KChDHJs0mjlZruGWrcDrCD3Hx1hFcuQouaSV-iglES1IejcxadZnQVUI5dAcYmnDOatuKr30_KSpefpF5_xVKRJgWbsCOLrm-Pwe1OP8anjwcg3o4TXjtyJJoa-0uvY7ds7NwIMpyJlrH1dMMZSZyyfrWbkMwg/ycn756648nvv3rx/Nerd+AI_v2.6.2-gocmod.com.apk");
            }
        });
    }

    private class DownloadFileTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {
            int fileLength = 0;

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // getting file length
                fileLength = connection.getContentLength();

                // input stream to read file
                InputStream input = connection.getInputStream();

                //output extension of file
                String extension = urls[0].substring(urls[0].lastIndexOf(".") + 1);

                // output stream to write file
                FileOutputStream output = openFileOutput("downloaded_file." + extension, MODE_PRIVATE);

                byte[] data = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                connection.disconnect();

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.this, "File downloaded successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
