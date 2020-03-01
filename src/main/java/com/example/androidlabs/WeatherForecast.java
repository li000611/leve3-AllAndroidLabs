package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class WeatherForecast extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "Weather Forecast";
    private Context thisApp;
    private EditText currentTemperatureText, minTemperatureText, maxTemperatureText, uvRatingText;
    private TextView textView;
    private ImageView weatherImage;
    private ProgressBar progressBar;
    String queryURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        thisApp = this;
        weatherImage = (ImageView) findViewById(R.id.weatherImage);
        currentTemperatureText = (EditText) findViewById(R.id.currentTemperature);
        minTemperatureText = (EditText) findViewById(R.id.minWeather);
        maxTemperatureText = (EditText) findViewById(R.id.maxWeather);
        uvRatingText = (EditText) findViewById(R.id.uvRating);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        queryURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";

        ForecastQuery networkThread = new ForecastQuery(queryURL);
        networkThread.execute(queryURL);

        textView = (TextView) findViewById(R.id.progress);


        new Thread(new Runnable() {
            int progressStatus = 0;
            private Handler handler = new Handler();

            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            textView.setText(progressStatus + "/" + progressBar.getMax());
                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        progressBar.setVisibility(View.VISIBLE);
    }


    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String responseType;
        String currentTemperature, minTemperature, maxTemperature, uvRatingValue, iconName;
        Bitmap image;

        @Override
        protected String doInBackground(String... strings) {

            String ret = null;


            try {
                URL url = new URL(queryURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();


                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inStream, "UTF-8");

                int EVENT_TYPE;
                while ((EVENT_TYPE = xpp.getEventType()) != XmlPullParser.END_DOCUMENT) {
                    switch (EVENT_TYPE) {
                        case START_TAG:
                            String tagName = xpp.getName();
                            if (tagName.equals("temperature")) {
                                currentTemperature = xpp.getAttributeValue(null, "value");
                                Log.e("AsyncTask", "Found currentTemerature: " + currentTemperature);
                                publishProgress(25);

                                minTemperature = xpp.getAttributeValue(null, "min");
                                Log.e("AsyncTask", "Found minTemerature: " + minTemperature);
                                publishProgress(50);

                                maxTemperature = xpp.getAttributeValue(null, "max");
                                Log.e("AsyncTask", "Found maxTemerature: " + maxTemperature);
                                publishProgress(75);
                            } else if (tagName.equals("weather")) {
                                iconName = xpp.getAttributeValue(null, "icon");
                                Log.e("AsyncTask", "Found iconName: " + iconName);
                                publishProgress(1);
                            }
                            break;
                        case END_TAG:
                        case TEXT:
                            break;
                    }
                    xpp.next();
                }


                File file = getBaseContext().getFileStreamPath(iconName + ".png");
                URL imageUrl = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                connection.connect();

                if (fileExistance(iconName + ".png")) {
                    Log.e(ACTIVITY_NAME, "Looking for file" + iconName + ".png");
                    Log.e(ACTIVITY_NAME, "Weather image exists, found locally");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        image = BitmapFactory.decodeStream(connection.getInputStream());
                    }

                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(iconName + ".png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bm = BitmapFactory.decodeStream(fis);

                } else {
                    Log.i(ACTIVITY_NAME, "Looking for file" + iconName + ".png");
                    Log.i(ACTIVITY_NAME, "Weather image does not exist, need to download");

                    FileOutputStream imageOutput = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, imageOutput);
                    imageOutput.flush();
                    imageOutput.close();
                }
                publishProgress(100);
                String uvratingURL = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";

                URL urlUvrating = new URL(uvratingURL);
                HttpURLConnection uvratingUrlConnection = (HttpURLConnection) urlUvrating.openConnection();
                InputStream uvStream = uvratingUrlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(uvStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject jObject = new JSONObject(result);
                Double uvValue = jObject.getDouble("value");
                uvRatingValue = uvValue + "";
                Log.i("UV is:", "" + uvValue);
                Log.e("UV is:", "" + uvRatingValue);
            } catch (JSONException | XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException mfe) {
                ret = "Malformed URL exception";
            } catch (IOException ioe) {
                ret = "IO Exception. Is the Wifi connected?";

            }

            return ret;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.i("AsyncTaskExample", "update:" + values[0]);
            progressBar.setProgress(values[0]);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            currentTemperatureText.setText(currentTemperature + "℃");
            minTemperatureText.setText(minTemperature + "℃");
            maxTemperatureText.setText(maxTemperature + "℃");
            uvRatingText.setText(uvRatingValue);
            weatherImage.setImageBitmap(image);
            progressBar.setVisibility(View.INVISIBLE);
        }


        public ForecastQuery(String s) {
            responseType = s;
        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }
    }
}
