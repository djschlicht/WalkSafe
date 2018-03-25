package com.coders.djjs.walksafe;

import org.json.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import android.content.Intent;
import android.view.View;
import android.net.Uri;

import java.net.ProtocolException;
import java.net.URL;
import android.app.Activity;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.protocol.HTTP;


public class UriActivity extends Activity {

    private String client_id = "m5qXF5ztOdT4cdQtUbZT2grBhF187vw6";
    private String client_secret = "3DLoc7yAVfRYALPgby6fz5kNwgniKoHrJOi2BMDtQzFRIE1YyRvmKjb7_NyupHHE";
    private String redirectUri = "https://walk-safe1.harokuapp.com/callback";

    public void authorize() { //called from loginactivity
        //sends user to safetrek with our haroku as redirect
        String url = "https://account-sandbox.safetrek.io/authorize?lient_id=" +
                "m5qXF5ztOdT4cdQtUbZT2grBhF187vw6&scope=openid%20phone%20offline" +
                "_access&response_type=code&redirect_uri=" + redirectUri;
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        System.out.println(url);
        startActivity(i);
    }

    public void getAccessToken(String authCode){

        try {
            HttpURLConnection con = (HttpURLConnection) (new URL("https://login-sandbox.safetrek.io/oauth/token").openConnection());
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("grant_type", "authorization_code");
            con.setRequestProperty("code", authCode);
            con.setRequestProperty("client_id", client_id);
            con.setRequestProperty("client_secret", client_secret);
            con.setRequestProperty("redirect_uri", redirectUri);
            con.setRequestMethod("POST");
            con.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onNewIntent(Intent intent){
        Uri data = intent.getData();

        if(data.getQueryParameter("code") != null)
            getAccessToken(data.getQueryParameter("code"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = getIntent().getData();
        if(uri != null) {
            final String accessToken = uri.getQueryParameter("access_token");
            System.out.println("3 accessToken: " + accessToken);
        }
    }

}

