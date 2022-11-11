package co.hideri.fxkaballab0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Grad {
    // Скорость обучения
    public static final Double stepSize = 0.000346; //0.000346
    // Количество проходов
    public static final Integer stepCount = 100000;  //100000

    // Функция для нахождения параетров a и b, где y это ln(исх данн)
    public static void coefCount(List<Double> y) {
        Random rand = new Random();
        // Задаем начальыне случайные значения кэфицентом линейной регрессии
        double a = rand.nextGaussian();
        double b = rand.nextGaussian();
        // Градиенты
        double a_grad;
        double b_grad;
        // Списки
        List<Double> y_hat = new ArrayList<>();
        List<Double> error = new ArrayList<>();
        List<Double> XEmult = new ArrayList<>();

        // Основной цикл
        for (int i = 0; i < stepCount; i++) {
            /* Расчитываем результрующий массив с текущими коэф a и b
            на основе обучающей выборки */
            for (int j = 1; j <= y.size(); j++) {
                y_hat.add(b * j + a);
            }
            // Считаем отклонение нового резутьтата от обучающего
            for (int j = 0; j < y.size(); j++) {
                error.add(y.get(j) - y_hat.get(j));
            }
            // Cчитаем градиенты
            // Для коэф a
            a_grad = -2 * Exp.getAverage(error);
            // Для коэф b
            for (int j = 1; j <= error.size(); j++) {
                XEmult.add(j * error.get(j - 1));
            }
            b_grad = -2 * Exp.getAverage(XEmult);
            // Обновляем параметры используя коэф скорости обучения
            a = a - stepSize * a_grad;
            b = b - stepSize * b_grad;
            // Очищаем списки
            y_hat.clear();
            error.clear();
            XEmult.clear();
        }

        System.out.println("a_gr: " + a);
        System.out.println("b_gr: " + b);
    }
}
