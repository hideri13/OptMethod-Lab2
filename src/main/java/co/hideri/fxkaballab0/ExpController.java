package co.hideri.kaballab0fx;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.util.List;

public class ExpController {
    @FXML private NumberAxis botAx;
    @FXML private NumberAxis leftAx;
    @FXML private LineChart<Number, Number>  expChart;

    @FXML private TextArea expText;

    @FXML public void initialize(){
        this.visualize();
    }

    private void visualize() {
        /*
        this.flipsChart.getData().clear();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Flips");
        int i = 0;
        for (String element : flipRes) {
            series.getData().add(new XYChart.Data(i, element));
            i += 1;
        }
        flipsChart.getData().add(series);
        //System.out.println(flipsChart.getData().get(0).getData());

         */
        //this.expChart.getData().clear();

        double[] dataHideri = {12038, 16787, 19887, 23899, 27644, 30806, 34400, 37131, 40162, 42771, 44814, 45232, 60384, 66912, 69055, 71238, 73273, 75155, 75655, 76216, 76846, 78608, 78990, 79558, 80412, 81384, 82728, 84152, 86023};
        Exp exp = new Exp();
        List<Double> Y = exp.CountY(dataHideri, dataHideri.length);
        exp.CheckHyp(dataHideri.length);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Исходные данные");
        int i = 0;
        for (Double element : dataHideri) {
            series.getData().add(new XYChart.Data(i, element));
            i += 1;
        }
        expChart.getData().add(series);

        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Экспоненциальная функция");
        int j = 0;
        for (Double element : Y) {
            series2.getData().add(new XYChart.Data(j, element));
            j += 1;
        }
        expChart.getData().add(series2);


    }
}