package com.example.hw5.Controllers;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class JigsawClientController {
    private static final String SERVER_URL = "";
    private String code;
    public void connect() {
        Request request = new Request.Builder()
                .url(SERVER_URL + "/new-game")
                .build();
        //Call call = client.newCall(request);
    }
}
