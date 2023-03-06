package com.example.hw5;

public class Figure {
    protected FigureType figureType;
    /* Сохраняем последний тип созданной фигуры, чтобы при некорректной попытке ее расположить на поле,
    снова генерировалась такая же фигура. */
    int lastType;

    public void createFigure(int type) {
        lastType = type;
        switch (type) {
            case 0 -> figureType = new FigureJRight();
            case 1 -> figureType = new FigureJUp();
            case 2 -> figureType = new FigureJLeft();
            case 3 -> figureType = new FigureJDown();
            case 4 -> figureType = new FigureLLeft();
            case 5 -> figureType = new FigureLUp();
            case 6 -> figureType = new FigureLRight();
            case 7 -> figureType = new FigureLDown();
            case 8 -> figureType = new FigureSUp();
            case 9 -> figureType = new FigureSRight();
            case 10 -> figureType = new FigureZUp();
            case 11 -> figureType = new FigureZLeft();
            case 12 -> figureType = new FigureLongLUp();
            case 13 -> figureType = new FigureLongLRight();
            case 14 -> figureType = new FigureLongLDown();
            case 15 -> figureType = new FigureLongLLeft();
            case 16 -> figureType = new FigureIRight();
            case 17 -> figureType = new FigureIUp();
            case 18 -> figureType = new FigurePoint();
            case 19 -> figureType = new FigureLongTUp();
            case 20 -> figureType = new FigureLongTDown();
            case 21 -> figureType = new FigureLongTRight();
            case 22 -> figureType = new FigureLongTLeft();
            case 23 -> figureType = new FigureShortLDown();
            case 24 -> figureType = new FigureShortLLeft();
            case 25 -> figureType = new FigureShortLUp();
            case 26 -> figureType = new FigureShortLRight();
            case 27 -> figureType = new FigureShortTRight();
            case 28 -> figureType = new FigureShortTDown();
            case 29 -> figureType = new FigureShortTLeft();
            case 30 -> figureType = new FigureShortTUp();
        }
        // Делаем, чтобы фигуру можно было перетаскивать.
        DraggableMaker draggableMaker = new DraggableMaker();
        draggableMaker.makeDraggable(figureType.body);
    }
}
