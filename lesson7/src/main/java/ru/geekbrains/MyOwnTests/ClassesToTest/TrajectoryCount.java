package ru.geekbrains.MyOwnTests.ClassesToTest;

import ru.geekbrains.MyOwnTests.TestPackage.*;

import java.util.HashMap;

public class TrajectoryCount {
    // тело массы m брошено с высоты h вперёд под углом a к горизонту с начальной скоростью v.
    // через какое время t оно ударится о землю? на каком расстоянии S это произойдёт?
    private final double g = 9.81;
    private final double m;
    private final double h;
    private final double a;
    private final double V;
    private double t = -1;
    private double S;
    private double aRad;


    public TrajectoryCount(Double[] init) {
        this.m = init[Init.MASS];
        this.h = init[Init.HEIGHT];
        this.a = init[Init.ANGLE];
        this.V = init[Init.VELOCITY];
    }

    @BeforeSuite
    public void printConditions() {
        String condition = String.format("Условия задачи:%nТело массы %s кг брошено с высоты %s м вперёд под углом %s градусов к горизонту с начальной скоростью %s м/с.%n" +
                "Через какое время t, с оно ударится о землю?%n" +
                "На каком расстоянии S, м это произойдёт?", m, h, a, V);
        System.out.println(condition);

    }

    @Test
    public double countDistanceBeforeFall() {
        if (t < 0) {
            throw new ArithmeticException("Attempt to count distance without time calculation!");
        }
        S = V * Math.cos(aRad) * t;
        return S;
    }

    @Test(value = 8) // сначала рассчитывается время, а уж потом расстояние, поэтому тут приоритет выше
    public double countTimeBeforeFall() {
        aRad = a * Math.PI / 180; // градусы в радианы
        double VVert = V * Math.sin(aRad);
        double tRize = VVert / g;
        double hRize = VVert * tRize - g * Math.pow(tRize, 2) / 2;
        double tFall = Math.sqrt(2 * (h + hRize) / g);
        t = tRize + tFall;
        return t;
    }

    @AfterSuite
    public void printAnswer() {
        System.out.println("Ответ:\nВремя падения: " + t + " секунд.\nРасстояние по горизонтали: " + S + " метров.");
    }

}
