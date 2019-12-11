package com.example.asyncpattern;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    public static String TAG = "TEST TAG";
    public static String FILE_URL = "https://www.hq.nasa.gov/alsj/a17/A17_FlightPlan.pdf";
//    public static String FILE_URL = "https://www.dre.vanderbilt.edu/~schmidt/PDF/monitor.pdf";
    DownloadTask downloadTask;
    public static final Integer notificationId = 100;
    public int textViewCount = 0;

    ArrayList<TextView> textViews = new ArrayList<>();

    Notification.Builder notificationBuilder;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        downloadTask = new DownloadTask(MainActivity.this);





        setContentView(R.layout.activity_main);
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;



        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {


            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }


                int fileLength = connection.getContentLength();

                Log.d(TAG, "File Length " + fileLength);
                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(getFilesDir() + "test.pdf");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "In on Pre Execute");
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
//            progressDialog.show();

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
//            // if we get here, length is known, now set indeterminate to false
//            progressDialog.setIndeterminate(false);
//            progressDialog.setMax(100);
//            progressDialog.setProgress(progress[0]);

            Log.d(TAG, "file progress" + progress[0]);
            notificationBuilder.setProgress(100, progress[0], false);

//Send the notification:
            notificationManager.notify(1001, notificationBuilder.build());
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();




            if (result != null) {
                notificationBuilder.setProgress(100, 100, false)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentText("File Downloaded")
                        .setContentTitle("App");

                notificationManager.notify(1001, notificationBuilder.build());
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();



        }

    }
    public void testClick(View view) {
        Log.d(TAG, "testClick: New Test Click");


        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new Notification.Builder(MainActivity.this);
        notificationBuilder.setOngoing(true)
                .setContentTitle("App")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("Downloading")
                .setProgress(100, 0, false);


        notificationManager.notify(1001, notificationBuilder.build());
        downloadTask.execute(FILE_URL);
    }

    public void addTestClick(View view) {
        Log.e(TAG, "ADD text view clicked");

        int textViewNumber = ++textViewCount;
        TextView textView = new TextView(MainActivity.this);
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(lparams);
        textView.setText("Text View Number " + textViewNumber);

//        textViews.add(textView);
        LinearLayout textViewLayout = findViewById(R.id.textViewLinearLayout);
        textViewLayout.addView(textView);

        findViewById(R.id.content).invalidate();
    }
}
