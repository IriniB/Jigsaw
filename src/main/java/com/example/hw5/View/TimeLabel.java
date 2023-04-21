package com.example.hw5.View;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Date;
import java.util.TimerTask;

/**
 * Класс для представления игрового времени.
 */
public class TimeLabel extends TimerTask {
    private Label labelTime;
    private Date nowTime;
    private Date startTime;

    /**
     * Конструктор.
     * @param startTime начальное время
     * @param labelTime лейл для отображения
     */
    public TimeLabel(Date startTime, Label labelTime) {
        this.labelTime = labelTime;
        this.startTime = startTime;
    }

    /**
     * Получение лейбла.
     * @return лейбл
     */
    public Label getLabelTime() {
        return labelTime;
    }

    /**
     * Задание лейбла.
     * @param labelTime лейбл
     */
    public void setLabelTime(Label labelTime) {
        this.labelTime = labelTime;
    }

    /**
     * Получение начального времени.
     * @return начальное время
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Задание начального времени.
     * @param startTime начальное время
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Метод запуска.
     */
    @Override
    public void run() {
        Platform.runLater(() -> {
            nowTime = new Date();
            long time = (nowTime.getTime() - startTime.getTime()) / 1000;
            onTime(time);
        });
    }

    /**
     * Задание отображаемого времени на лейбле.
     * @param time время
     */
    public void onTime(long time) {
        int hour = (int) time / 3600;
        time %= 3600;
        int minutes = (int) time / 60;
        int second = (int) time % 60;
        labelTime.setText(onLabel(hour, minutes, second));
    }

    /**
     * Конструктор отображаемое строки со временем.
     * @param hour часы
     * @param minutes минуты
     * @param second секунды
     * @return строка для отобрражения
     */
    public String onLabel(int hour, int minutes, int second) {
        String s = "";
        if (hour < 10)
            s += 0;
        s += hour;
        s += ':';
        if (minutes < 10)
            s += 0;
        s += minutes;
        s += ':';
        if (second < 10)
            s += 0;
        s += second;
        return s;
    }
}
