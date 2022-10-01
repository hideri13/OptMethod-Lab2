package co.hideri.fxkaballab0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.util.Pair;

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
        List<Double> zStar = new ArrayList<>();
        List<Double> e = new ArrayList<>();
        System.out.println("b: " + b);
        System.out.println("a: " + a);
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

        int lg = log2(t);
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
                for ( int j = 1; j <= intervals.size() - 1; j++) {
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
        System.out.println("Intervals Valid" + intervals);

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
        if (Kp.getSecond() < 0.05) {
            out += "p < 0.05. Критерий не верен!";
        } else {
            out += "p > 0.05. Гипотеза верна!";
        }


        return out;
    }
    public List<Double> CountY(double[] data, int t) {
        z = lnArr(data);
        System.out.println("Z: " + z);

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
        System.out.println("tq: " + tq);
        Double ztAvg = getAverage(zt);
        Double tqAvg = getAverage(tq);
        System.out.println("zAvg: " + zAvg);
        System.out.println("ztAvg: " + ztAvg);
        System.out.println("tqAvg: " + tqAvg);
        System.out.println("tAvg: " + tAvg);

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