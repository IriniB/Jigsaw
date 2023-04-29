package com.example.hw5.Controllers;

import com.example.hw5.Model.Figure;
import com.example.hw5.Model.LiderboardRow;
import com.example.hw5.Model.response_figure.ResponseFigure;
import com.example.hw5.Utils.FigureMapper;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;


public class JigsawClientController {
    private static final String SERVER_URL = "http://localhost:8080/jigsaw";
    private static final String NEW_GAME = SERVER_URL + "/new-game";
    private static final String CAN_START = SERVER_URL + "/can-start";
    private static final String NEXT_FIGURE = SERVER_URL + "/figure?userCode=";
    private static final String STOP_GAME = SERVER_URL + "/stop?userCode=";
    private String userCode;
    private final OkHttpClient client = new OkHttpClient();

    public boolean connect() {
        Request request = new Request.Builder()
                .url(NEW_GAME)
                .build();
        try (Response response = client.newCall(request).execute()) {
            userCode = response.body() != null ? response.body().string() : "";
            System.out.println("Connected to server with user code " + userCode);

            return true;
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            return false;
        }
    }

    public boolean checkStart() {
        Request request = new Request.Builder()
                .url(CAN_START)
                .build();
        boolean canStart = false;
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                canStart = Boolean.parseBoolean(response.body().string());
            }
            return canStart;
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

        return false;
    }

    public Figure getNextFigure() {
        Request request = new Request.Builder()
                .url(NEXT_FIGURE + userCode)
                .build();
        Figure figure = null;
        System.out.println("Next fig " + userCode);
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
                var body = response.body().string();
                ResponseFigure responseFigure = gson.fromJson(body, ResponseFigure.class);
                System.out.println(responseFigure);
                figure = FigureMapper.fromResponseFigure(responseFigure);
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
        return figure;
    }

    public void endGame(LiderboardRow row) {
        System.out.println("End game " + userCode);
        RequestBody formBody = new FormBody.Builder()
                .add("name", row.getName())
                .add("moves", String.valueOf(row.getMoves()))
                .add("seconds", String.valueOf(row.getSeconds()))
                .build();
        Request request = new Request.Builder()
                .url(STOP_GAME + userCode)
                .post(formBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200) {
                System.out.println("Game stopped");
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}
