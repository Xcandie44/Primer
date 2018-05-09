package com.example.user.project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.SimpleTimeZone;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity{
    private TextView mInfoTextView;
    ListView lv;
    ArrayList<Currency> arr = new ArrayList<Currency>();

    String[] symbols;
    String[] prices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInfoTextView = (TextView) findViewById(R.id.textView);
        lv = (ListView) findViewById(R.id.list);
    }

    public void onClick(View view) {
        new send().execute();
    }



    class send extends AsyncTask<Object,Object,StringBuilder> {
        HttpsURLConnection connection = null;
        StringBuilder sb = new StringBuilder();
        String query;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            query="https://api.binance.com/api/v3/ticker/price";
            mInfoTextView.setText("Начало");
        }

        @Override
        protected StringBuilder doInBackground(Object... objects) {
            Cursor cursor ;
            try{
                URL url = new URL(query);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.connect();
                if (HttpsURLConnection.HTTP_OK == connection.getResponseCode()){
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while((line = in.readLine())!= null) {
                        sb.append(line);
                        sb.append("\n");
                    }
                    JSONArray data = new JSONArray(sb.toString());
                    symbols = new String[data.length()];
                    prices = new String[data.length()];
                    for(int n = 0; n < data.length(); n++)
                    {
                        JSONObject object = data.getJSONObject(n);
                       symbols[n] = object.getString("symbol");
                       prices[n] = object.getString("price");
                    }
                    //SimpleAdapter adapter = new SimpleAdapter(this,symbols,);
                    //SimpleAdapter adapter = new SimpleAdapter();
                    String [] r = symbols.toArray(new String[0]);

                    Currency[] c = new Currency[data.length()];
                    for(int n = 0;n<data.length();n++){
                      c[n]=new Currency(symbols[n],prices[n]);
                      arr.add(c[n]);
                    }
                    Adapter adapter = new Adapter(arr,MainActivity.this);
                    lv.setAdapter(adapter);

                }
            }catch (Throwable cause){
                cause.printStackTrace();
                mInfoTextView.setText("Ошибка");
            }
            finally {
                if (connection != null) {
                    connection.disconnect();//
                }
            }



            return sb;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, symbols);
           // lv.setAdapter(adapter);
            Adapter adapter = new Adapter(arr,MainActivity.this);
            lv.setAdapter(adapter);
            mInfoTextView.setText("Конец");
          //  mInfoTextView.setText(sb.toString());
        }
    }
}
