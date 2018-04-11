package com.example.nevinvarghese.projectdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import kotlin.Pair;

public class ActivityStore extends AppCompatActivity {

    Spinner sp;
    String item;
    TextView disease1,disease2,disease3;
    EditText symptoms;
    List<String> arrayList;
    TagContainerLayout mTagContainerLayout;
    Button button;
    private ArrayList<NameValuePair> nameValuePairs;
    private String datav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        arrayList = new ArrayList<>();
        Toast.makeText(ActivityStore.this, "LOADING.....", Toast.LENGTH_LONG).show();
        sp = (Spinner) findViewById(R.id.spinner);
        symptoms = (EditText) findViewById(R.id.symptoms);
        mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagContainer);
        button=(Button)findViewById(R.id.button);
        disease1=(TextView) findViewById(R.id.diseaese1);
        disease2=(TextView) findViewById(R.id.diseaese2);
        disease3=(TextView) findViewById(R.id.diseaese3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


         PostToServer();
            }
        });



        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = sp.getSelectedItem().toString();


              String pos = searchArray(arrayList, item);

                if (arrayList.size() > 0) {
                    arrayList.removeAll(Collections.singleton(pos));
                    arrayList.add(item);
                    mTagContainerLayout.setTags(arrayList);

                    //symptoms.setText(TextUtils.join(",", arrayList));
                }else{
                    arrayList.add(item);
                    mTagContainerLayout.setTags(arrayList);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {

            @Override
            public void onTagClick(int position, String text) {
                // ...
            }

            @Override
            public void onTagLongClick(final int position, String text) {
                arrayList.removeAll(Arrays.asList(text));

                mTagContainerLayout.removeTag(position);
                // ...
            }

            @Override
            public void onTagCrossClick(int position) {


                // ...
            }
        });


    }




    private void PostToServer() {


        datav= StringUtils.join(arrayList,",").replaceAll(",","%20").replaceAll(" ","%20");
        Log.v("TAG","v"+datav);


        nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("symptom", datav));

        new BackTask().execute();
    }


    public class BackTask extends AsyncTask<Void, Void, Void> {
        HttpResponse response;
        HttpEntity entity;

        String result = "";
        JSONObject jsonObject;

        String id, username, phoneno, stat, emailid, otp;
        private JSONArray jsonArray;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... params) {
            InputStream is = null;
            String url="http://192.168.43.71:5000/api/diseaseclassifier?symptom="+datav+"";

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                response = httpClient.execute(httpPost);
                entity = response.getEntity();
                is = entity.getContent();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                is.close();
                Log.i("RESPONSE", "-->" + result.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // parse json data
            try {
                jsonArray= new JSONArray(result);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            try {
                disease1.setText(jsonArray.getJSONObject(0).getString("disease"));
                disease2.setText(jsonArray.getJSONObject(1).getString("disease"));
                disease3.setText(jsonArray.getJSONObject(2).getString("disease"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }}

    private String searchArray(List<String> arrayList, String items) {


        for (String item : arrayList) {

            if (item.equals(items)) {

                return item;
            }

        }
        return null;

    }
    private boolean isItemPresent(List<String> arrayList, String items) {


        for (String item : arrayList) {

            if (item.equals(items)) {

                return true;
            }

        }
        return false;

    }


}
