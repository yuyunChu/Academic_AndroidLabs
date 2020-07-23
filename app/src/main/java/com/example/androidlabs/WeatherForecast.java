package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
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
import java.util.concurrent.TimeUnit;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class WeatherForecast extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        new ForecastQuery().execute();

    }

    private void pretendToWait(int sec){
        // pretend to wait...
        // https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    //***** https://stackoverflow.com/questions/9605913/how-do-i-parse-json-in-android
//                                        Type1, Type2   Type3
private class ForecastQuery extends AsyncTask<String, Integer, String> {
    ProgressBar pb;
    private String UVJSON, UV;
    private String minTemp;
    private String maxTemp;
    private String currentTemp;
    private Bitmap image;
    private String iconName;
    private URL url;

    protected void onPreExecute() {
        super.onPreExecute();
        pb = findViewById(R.id.pBar);
        pb.setVisibility(View.VISIBLE);
    }

    protected String doInBackground(String... args) {
        Log.d("ASYNC", "START");
        String ret = null;

        HttpURLConnection connection = null;
        TextView textViewItem = null;

        try {
            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream inStream = connection.getInputStream();

            //Set up the XML parser:
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( inStream  , "UTF-8");

            //Iterate over the XML tags:
            int EVENT_TYPE;         //While not the end of the document:
            while((EVENT_TYPE = xpp.getEventType()) != XmlPullParser.END_DOCUMENT) {
                switch (EVENT_TYPE) {
                    case START_TAG:         //This is a start tag < ... >
                        String tagName = xpp.getName(); // What kind of tag?

                        //<temperature value="29.69" min="29" max="30" unit="celsius"/>
                        if (tagName.equals("temperature")) {

                            pretendToWait(1);

                            currentTemp = xpp.getAttributeValue(null, "value"); //What is the String associated with message?
                            publishProgress(25);

                            pretendToWait(1);

                            minTemp = xpp.getAttributeValue(null, "min"); //What is the String associated with message?
                            publishProgress(50);

                            pretendToWait(1);

                            maxTemp = xpp.getAttributeValue(null, "max"); //What is the String associated with message?
                            publishProgress(75);

                            pretendToWait(1);


                        } else if (tagName.equals("weather")) {
                            iconName = xpp.getAttributeValue(null, "icon"); //What is the String associated with message?

                        }
                        break;
                    case END_TAG:           //This is an end tag: </ ... >
                        break;
                    case TEXT:              //This is text between tags < ... > Hello world </ ... >
                        break;
                }
                xpp.next(); // move the pointer to next XML element
            }
        }
        catch(MalformedURLException mfe){ ret = "Malformed URL exception"; }
        catch(IOException ioe)          { ret = "IO Exception. Is the Wifi connected?";}
        catch(XmlPullParserException pe){ ret = "XML Pull exception. The XML is not properly formed" ;}

        BufferedReader reader = null;

        try {
            url = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }

            UVJSON = buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try{
            // get image
            if(!fileExistance(iconName + ".png")){
                url = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    image = BitmapFactory.decodeStream(connection.getInputStream());
                }

                FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
            } else {

                FileInputStream fis = null;
                try {
                    fis = openFileInput(iconName + ".png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                image = BitmapFactory.decodeStream(fis);
            }

            publishProgress(100);

        }
        catch(MalformedURLException mfe){ ret = "Malformed URL exception"; }
        catch(IOException ioe)          { ret = "IO Exception. Is the Wifi connected?";}

        //What is returned here will be passed as a parameter to onPostExecute:
        return ret;
    }

    @Override
    protected void onPostExecute(String result) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                TextView textViewItem = null;
                textViewItem = (TextView)findViewById(R.id.textViewCurrentTemp);
                textViewItem.setText(Math.round(Double.parseDouble(currentTemp)) + "°C");

                textViewItem = (TextView)findViewById(R.id.textViewMinTemp);
                textViewItem.setText("Min: " + Math.round(Double.parseDouble(minTemp)));

                textViewItem = (TextView)findViewById(R.id.textViewMaxTemp);
                textViewItem.setText("Max: " + Math.round(Double.parseDouble(maxTemp)));

                ImageView imageView = findViewById(R.id.imageViewID);
                imageView.setImageBitmap(image);

                JSONObject jObject;

                try {
                    jObject = new JSONObject(UVJSON);
                    UV = jObject.getString("value");
                    textViewItem = (TextView)findViewById(R.id.textViewUVID);
                    textViewItem.setText("UV rating: " + Math.round(Double.parseDouble(UV)));

                } catch (JSONException e){
                }

            }
        });

        pb.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        pb.setProgress(values[0]);
    }



  }
}