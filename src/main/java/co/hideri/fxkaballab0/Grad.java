package co.hideri.fxkaballab0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Grad {
    public static final Double stepSize = 0.000346; //0.000346
    public static final Integer stepCount = 100000;  //100000
    public static void coefCount(List<Double> y) {
        Random rand = new Random();
        double a = rand.nextGaussian();
        double b = rand.nextGaussian();
        double a_grad;
        double b_grad;
        System.out.println("a_c: " + a);
        System.out.println("b_c: " + b);
        Double y_avg = Exp.getAverage(y);
        List<Double> y_hat = new ArrayList<>();
        List<Double> error = new ArrayList<>();
        List<Double> XEmult = new ArrayList<>();
        for (int i = 0; i < stepCount; i++) {
            for (int j = 1; j <= y.size(); j++) {
                y_hat.add(b * j + a);
            }
            for (int j = 0; j < y.size(); j++) {
                error.add(y.get(j) - y_hat.get(j));
            }
            a_grad = -2 * Exp.getAverage(error);
            for (int j = 1; j <= error.size(); j++) {
                XEmult.add(j * error.get(j - 1));
            }
            b_grad = -2 * Exp.getAverage(XEmult);
            a = a - stepSize * a_grad;
            b = b - stepSize * b_grad;
            y_hat.clear();
            error.clear();
            XEmult.clear();
        }
        System.out.println("a_c_end: " + a);
        System.out.println("b_c_end: " + b);
    }
}
