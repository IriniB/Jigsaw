package com.example.hw5.Controllers;

import com.example.hw5.Model.Figure;
import com.example.hw5.Model.response_figure.ResponseFigure;
import com.example.hw5.Utils.FigureMapper;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class JigsawClientController {
    private static final String SERVER_URL = "http://localhost:8080/jigsaw";
    private static final String NEW_GAME = SERVER_URL + "/new-game";
    private static final String NEXT_FIGURE = SERVER_URL + "/figure?code=";
    private String userCode;
    private final OkHttpClient client = new OkHttpClient();
    public void connect() {
        Request request = new Request.Builder()
                .url(NEW_GAME)
                .build();
        try (Response response = client.newCall(request).execute()) {
            userCode = response.body() != null ? response.body().string() : "";
            System.out.println("connected to server");
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    public Figure getNextFigure() {
        Request request = new Request.Builder()
                .url(NEXT_FIGURE + userCode)
                .build();
        Figure figure = null;
        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();
            if (response.code() != 200) {
                if (response.code() == 405) {
                    System.out.println("The game is finished");
                } else if (response.code() == 404) {
                    System.out.println("No user with such user code");
                } else if (response.code() == 406) {
                    System.out.println("Not enough players");
                }
                return null;
            }
            if (response.body() != null) {
                ResponseFigure responseFigure = gson.fromJson(response.body().string(), ResponseFigure.class);
                figure = FigureMapper.fromResponseFigure(responseFigure);
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
        return figure;
    }
}
