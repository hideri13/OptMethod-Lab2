package co.hideri.fxkaballab0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.util.Pair;

public class Exp {
    public List<Double> z = new ArrayList<>();
    public Double b = 0.0;
    public Double a = 0.0;
    private int log2(int N) {
        return (int)(Math.log(N) / Math.log(2));
    }
    public static double getAverage(List<Double> list) {
        return list.stream()
                .mapToDouble(a -> a)
                .average().orElse(0);
    }
    private List<Double> lnArr(double[] arr) {
        List<Double> list = new ArrayList<>();
        for (double e : arr) {
            list.add(Math.log(e));
        }
        return list;
    }
    private Double FI(double x) {
        NormalDistribution distribution = new NormalDistribution();
        return distribution.cumulativeProbability(x) - 0.5;
    }
    private double[] listToprimitiveDouble(List<Double> list) {
        double[] listArr = new double[list.size()];
        for(int i = 0; i < list.size(); i++) {
            listArr[i] = list.get(i);
        }
        return listArr;
    }
    private long[] listToprimitiveLong(List<Long> list) {
        long[] listArr = new long[list.size()];
        for(int i = 0; i < list.size(); i++) {
            listArr[i] = list.get(i);
        }
        return listArr;
    }

    private Boolean checkLongGreaterThan(List<Long> list, long number) {
        for (Long el : list) {
            if (el < number) {
                return false;
            }
        }
        return true;
    }

    public String CheckHyp(int t) {
        List<Double> zStar = new ArrayList<>(); // z*
        List<Double> e = new ArrayList<>();
        for (int i = 1; i <= t; i++) {
            zStar.add(b*i+a);
            e.add(z.get(i-1) - zStar.get(i-1));
        }
        System.out.println("zStar: " + zStar);
        System.out.println("e: " + e);

        List<Double> eq = new ArrayList<>();
        for (Double el : e) {
            eq.add(Math.pow(el, 2));
        }

        Double eqAvg = getAverage(eq);
        Double eAvg = getAverage(e);
        Double disper = eqAvg - Math.pow(eAvg, 2);
        Double sigma = Math.sqrt(disper);
        System.out.println("Sigma: " + sigma);

        int lg = log2(t) + 1;
        System.out.println("lg: " + lg);
        Double minList = e.get(e.indexOf(Collections.min(e)));
        Double maxList = e.get(e.indexOf(Collections.max(e)));
        Double deltaList = maxList - minList;

        Double step = deltaList / lg;
        List<Double> intervals = new ArrayList<>();
        Double tmplist = minList;
        for (int i = 0; i < lg - 1; i++) {
            tmplist = tmplist + step;
            intervals.add(tmplist);
        }
        System.out.println("Intervals - " + intervals);

        List<Long> obs = new ArrayList<>();
        for (int i = 0; i <= intervals.size(); i++) {
            obs.add(0L);
        }
        for (int i = 0; i <= e.size() - 1; i++) {
            if (e.get(i) < intervals.get(0)) {
                obs.set(0, obs.get(0) + 1);
            } else if (e.get(i) >= intervals.get(intervals.size() - 1)) {
                obs.set(obs.size() - 1, obs.get(obs.size() - 1) + 1);
            } else {
                for ( int j = 1; j < intervals.size(); j++) {
                    if(e.get(i) < intervals.get(j)) {
                        obs.set(j, obs.get(j) + 1);
                        break;
                    }
                }
            }
        }
        System.out.println("OBS: " + obs);

        while (!checkLongGreaterThan(obs, 5) && obs.size() > 2) {
            int i = 0;
            while (i < obs.size()) {
                if (obs.get(i) < 5) {
                    if (i != obs.size() - 1) {
                        obs.set(i+1, obs.get(i+1) + obs.get(i));
                    } else {
                        obs.set(i-1, obs.get(i-1) + obs.get(i));
                    }
                    if (obs.size() > 1) {
                        obs.remove(i);
                        if (i >= intervals.size()) {
                            intervals.remove(i-1);
                        } else {
                            intervals.remove(i);
                        }
                    } else {
                        break;
                    }
                } else {
                    i++;
                }
            }
        }
        System.out.println("OBS Valid: " + obs);
        System.out.println("Intervals Valid: " + intervals);

        List<Double> P = new ArrayList<>();
        P.add(FI(intervals.get(0) / sigma) + 0.5);
        for (int i = 1; i < intervals.size(); i++) {
            P.add(FI(intervals.get(i)/sigma) - FI(intervals.get(i - 1)/sigma));
        }
        P.add(0.5 - FI(intervals.get(intervals.size() - 1)/sigma));
        System.out.println("P: " + P);

        Double Psum = 0.0;
        for (int i = 0; i < P.size(); i++) {
            Psum += P.get(i);
        }
        System.out.println("Psum: " + Psum);

        List<Double> nSt = new ArrayList<>();
        for (Double el : P) {
            nSt.add(t * el);
        }
        System.out.println("nSt: " + nSt);

        Double nStsum = 0.0;
        for (int i = 0; i < nSt.size(); i++) {
            nStsum += nSt.get(i);
        }
        System.out.println("nStsum: " + nStsum);

        ChiSquare chi = new ChiSquare();
        Pair<Double, Double> Kp = chi.chiSquareTest(listToprimitiveDouble(nSt), listToprimitiveLong(obs), 1);
        System.out.println("K: " + Kp.getFirst() + "\np: " + Kp.getSecond());
        String out = "K: " + Kp.getFirst() + "\np: " + Kp.getSecond() + '\n';

        if (Kp.getSecond() <= 0.05) {
            out += "p <= 0.05. Критерий не верен!";
        } else {
            out += "p > 0.05. Гипотеза верна!";
        }

        return out;
    }

    // функция вычисления значений экспоненциальной функции,
    // где data - исходные данные, t - количество данных
    public List<Double> CountY(double[] data, int t) {
        z = lnArr(data); // массив натуральных логарифмов элементов data
        System.out.println("Z: " + z + "\nz_size: " + z.size());

        Double zAvg = getAverage(z); // среднее арифметическое z
        Double tSum = (double) ((t * (t + 1)) / 2);
        Double tAvg = tSum / t; // среднее арифметическое t

        List<Double> zt = new ArrayList<>(); // массив произведений z и t
        List<Double> tq = new ArrayList<>(); // массив квадратов t
        for (int i = 1; i <= t; i++) { // вычисление значений элементов массивов tq и zt
            zt.add(z.get(i - 1) * i);
            tq.add(Math.pow(i,2));
        }

        Double ztAvg = getAverage(zt); // среднее арифметическое zt
        Double tqAvg = getAverage(tq); // среднее арифметическое tq

        b = (ztAvg - (zAvg * tAvg)) / (tqAvg - Math.pow(tAvg, 2)); // вычисление параметра b
        a = (zAvg - (b * tAvg)); // вычисление параметра a
        Double A = Math.exp(a); // вычисление А

        List<Double> yList = new ArrayList<>(); // вычисление Y
        for (int i = 1; i <= t; i++) {
            yList.add(A * Math.exp(b*i));
        }

        System.out.println("a_id: " + a);
        System.out.println("b_id: " + b);
        //System.out.println("A: " + A);

        return yList;
    }
}