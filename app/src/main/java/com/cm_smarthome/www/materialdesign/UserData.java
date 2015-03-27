package com.cm_smarthome.www.materialdesign;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdminPond on 28/3/2558.
 */
public class UserData {

    static String username;
    protected String name;
    protected String email;
    protected String statusLogin;

    public void CheckLogin(String mUsername, String mPassword) {
        String url = "http://www.cm-smarthome.com/reg/checkLogin.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("strUser", mUsername));
        params.add(new BasicNameValuePair("strPassword", mPassword));

        String resultServer = getHttpPost(url, params);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(resultServer);

            statusLogin = jsonObject.getString("StatusID");
            username = jsonObject.getString("Username");
            name = jsonObject.getString("Name");
            email = jsonObject.getString("Email");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String getHttpPost(String url, List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Error", "Error get Data");
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}