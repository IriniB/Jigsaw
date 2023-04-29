package com.example.hw5.Utils;

import com.example.hw5.Model.AllFigures;
import com.example.hw5.Model.Figure;
import com.example.hw5.Model.response_figure.ResponseFigure;
import com.example.hw5.Model.response_figure.ResponseFigureType;

public class FigureMapper {
    public static Figure fromResponseFigure(ResponseFigure responseFigure) {
        int[][] figureCoords = getFigureCoords(responseFigure.figureType());
        for (int i = 0; i < responseFigure.angle().ordinal(); i++) {
            figureCoords = AllFigures.rotate(figureCoords);
        }
        return new Figure(10, 10, figureCoords);
    }

    private static int[][] getFigureCoords(ResponseFigureType figureType) {
        return switch (figureType) {
            case DOT -> new int[][] {
                    {0, 0, 0},
                    {0, 1, 0},
                    {0, 0, 0}
            };
            case VERTICAL_LINE -> new int[][] {
                    {0, 1, 0},
                    {0, 1, 0},
                    {0, 1, 0}
            };
            case L -> new int[][]{
                    {1, 0, 0},
                    {1, 0, 0},
                    {1, 1, 0}
            };
            case MIRROR_L -> new int[][] {
                    {0, 0, 1},
                    {0, 0, 1},
                    {0, 1, 1}
            };
            case SMALL_CORNER -> new int[][] {
                    {0, 0, 0},
                    {0, 1, 1},
                    {0, 1, 0}
            };
            case CORNER -> new int[][] {
                    {1, 1, 1},
                    {1, 0, 0},
                    {1, 0, 0}
            };
            case T -> new int[][] {
                    {1, 1, 1},
                    {0, 1, 0},
                    {0, 1, 0}
            };
            case SHORT_T -> new int[][] {
                    {1, 1, 1},
                    {0, 1, 0},
                    {0, 0, 0}
            };
            case S -> new int[][] {
                    {0, 0, 0},
                    {0, 1, 1},
                    {1, 1, 0}
            };
            case MIRROR_S -> new int[][] {
                    {0, 0, 0},
                    {1, 1, 0},
                    {0, 1, 1}
            };
        };
    }
}
