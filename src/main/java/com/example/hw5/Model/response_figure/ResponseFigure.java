package com.example.hw5.Model.response_figure;

import java.io.Serializable;

public record ResponseFigure(ResponseFigureType type, ResponseRotation angle) implements Serializable {
}
