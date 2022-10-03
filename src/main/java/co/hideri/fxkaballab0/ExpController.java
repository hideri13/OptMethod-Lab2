package co.hideri.fxkaballab0;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.Locale;

public class ExpController {
    @FXML private CategoryAxis botAx;
    @FXML private NumberAxis leftAx;
    @FXML private LineChart<String, Number>  expChart;

    @FXML private TextArea expText;

    @FXML public void initialize(){
        this.visualize();
    }

    private void visualize() {

        double[] dataHideri = {
                3370950, 3449173, 3523204, 3600809, 3680465, 3770907, 3859461, 3949874, 4034115, 4109328, 4185505, 4270891, 4355306, 4450950, 4547281, 4641188, 4719536, 4808676, 4904285, 5009707, 5115662, 5222914, 5327021, 5420228, 5508439, 5600797, 5703352, 5823344, 5944818, 6079862, 6182722, 6283007, 6405752, 6517005, 6655331, 6788893, 6918226, 7029236, 7132289, 7258292, 7395486, 7531678, 7659166, 7793075, 7925019, 8049957, 8193863, 8337068, 8480214, 8661206, 8847077, 8940122, 9082467, 9250645, 9422124, 9601643, 9794139, 9970562, 10139020, 10291742, 10475496, 10690640, 10895640, 11098304, 11288042, 11474321, 11642572, 11852203, 12067468, 12292055, 12525185, 12738051, 12931019, 13125559, 13346622, 13576502, 13821455, 14058797, 14294106, 14505988, 14714176, 14957222, 15232596, 15512280, 15795865, 16045400, 16257212, 16491647, 16755516, 17025804, 17307583, 17595167
        };
        String[] dates = {
                "5/1/20", "5/2/20", "5/3/20", "5/4/20", "5/5/20", "5/6/20", "5/7/20", "5/8/20", "5/9/20", "5/10/20", "5/11/20", "5/12/20", "5/13/20", "5/14/20", "5/15/20", "5/16/20", "5/17/20", "5/18/20", "5/19/20", "5/20/20", "5/21/20", "5/22/20", "5/23/20", "5/24/20", "5/25/20", "5/26/20", "5/27/20", "5/28/20", "5/29/20", "5/30/20", "5/31/20", "6/1/20", "6/2/20", "6/3/20", "6/4/20", "6/5/20", "6/6/20", "6/7/20", "6/8/20", "6/9/20", "6/10/20", "6/11/20", "6/12/20", "6/13/20", "6/14/20", "6/15/20", "6/16/20", "6/17/20", "6/18/20", "6/19/20", "6/20/20", "6/21/20", "6/22/20", "6/23/20", "6/24/20", "6/25/20", "6/26/20", "6/27/20", "6/28/20", "6/29/20", "6/30/20", "7/1/20", "7/2/20", "7/3/20", "7/4/20", "7/5/20", "7/6/20", "7/7/20", "7/8/20", "7/9/20", "7/10/20", "7/11/20", "7/12/20", "7/13/20", "7/14/20", "7/15/20", "7/16/20", "7/17/20", "7/18/20", "7/19/20", "7/20/20", "7/21/20", "7/22/20", "7/23/20", "7/24/20", "7/25/20", "7/26/20", "7/27/20", "7/28/20", "7/29/20", "7/30/20", "7/31/20"
        };
        Exp exp = new Exp();
        String fieldText = "";

        List<Double> Y = exp.CountY(dataHideri, dataHideri.length);
        fieldText = exp.CheckHyp(dataHideri.length);
        expText.setText(fieldText);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Исходные данные");
        int i = 0;
        while (i < dataHideri.length) {
            series.getData().add(new XYChart.Data(dates[i], dataHideri[i]));
            i += 1;
        }
        expChart.getData().add(series);

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Экспоненциальная функция");
        int j = 0;
        while (j < Y.size()) {
            series2.getData().add(new XYChart.Data(dates[j], Y.get(j)));
            j += 1;
        }
        expChart.getData().add(series2);
    }
}