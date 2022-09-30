package co.hideri.kaballab0fx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Exp {
//    static double[] dataHideri = {12038, 16787, 19887, 23899, 27644, 30806, 34400, 37131, 40162, 42771, 44814, 45232, 60384, 66912, 69055, 71238, 73273, 75155, 75655, 76216, 76846, 78608, 78990, 79558, 80412, 81384, 82728, 84152, 86023};
    //int[] dataIlya = { };
    public List<Double> z = new ArrayList<>();
    public Double b = 0.0;
    public Double a = 0.0;
    private int log2(int N) {
        return (int)(Math.log(N) / Math.log(2));
    }
    private double getAverage(List<Double> list) {
        return list.stream()
                .mapToDouble(a -> a)
                .average().orElse(0);
    }
    private List<Double> lnArr(double[] arr) {
        List<Double> z = new ArrayList<>();
        for (double e : arr) {
            Exp.this.z.add(Math.log(e));
        }
        return Exp.this.z;
    }

    public void CheckHyp(int t) {
        List<Double> zStar = new ArrayList<>();
        List<Double> e = new ArrayList<>();

        for (int i = 1; i <= t; i++) {
            zStar.add(b*i+a);
            e.add(z.get(i-1) - zStar.get(i-1));
        }
        System.out.println(zStar);
        System.out.println(e);

        int lg = log2(t) + 1;
        Double minList = e.get(e.indexOf(Collections.min(e)));
        Double maxList = e.get(e.indexOf(Collections.max(e)));
        Double deltaList = maxList - minList;

        Double step = deltaList / lg;
        List<Double> intervals = new ArrayList<>();
        Double tmplist = minList;
        for (int i = 0; i < lg; i++) {
            tmplist = tmplist + step;
            intervals.add(tmplist);
        }
        System.out.println("Intervals - " + intervals);

        //ChiSquare chi = new ChiSquare();

    }
    public List<Double> CountY(double[] data, int t) {
        z = lnArr(data);
        //System.out.println(z);

        Double zAvg = getAverage(z);
        //System.out.println(zMid);


        Double tSum = (double) ((t * (t + 1)) / 2);
        Double tAvg = tSum / t;
        //System.out.println(tAvg);

        List<Double> zt = new ArrayList<>();
        List<Double> tq = new ArrayList<>();
        for (int i = 1; i <= t; i++) {
            zt.add(z.get(i - 1) * i);
            tq.add(Math.pow(i,2));
        }
        Double ztAvg = getAverage(zt);
        Double tqAvg = getAverage(tq);

        b = (ztAvg - (zAvg * tAvg)) / (tqAvg - Math.pow(tAvg, 2));
        a = (zAvg - (b * tAvg));
        Double A = Math.exp(a);
        //System.out.println("A=" + A + " b=" + b + " a=" + a + " ztAvg=" + ztAvg + " zAvg=" + zAvg + " tAvg=" + tAvg + " tqAvg=" + tqAvg);

        List<Double> yList = new ArrayList<>();
        for (int i = 1; i <= t; i++) {
            yList.add(A * Math.exp(b*i));
        }
        return yList;
    }
}