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

        //double[] dataHideri = {12038, 16787, 19887, 23899, 27644, 30806, 34400, 37131, 40162, 42771, 44814, 45232, 60384, 66912, 69055, 71238, 73273, 75155, 75655, 76216, 76846, 78608, 78990, 79558, 80412, 81384, 82728, 84152, 86023};
        double[] dataHideri = {
                3370950, 3449173, 3523204, 3600809, 3680465, 3770907, 3859461, 3949874, 4034115, 4109328, 4185505, 4270891, 4355306, 4450950, 4547281, 4641188, 4719536, 4808676, 4904285, 5009707, 5115662, 5222914, 5327021, 5420228, 5508439, 5600797, 5703352, 5823344, 5944818, 6079862, 6182722, 6283007, 6405752, 6517005, 6655331, 6788893, 6918226, 7029236, 7132289, 7258292, 7395486, 7531678, 7659166, 7793075, 7925019, 8049957, 8193863, 8337068, 8480214, 8661206, 8847077, 8940122, 9082467, 9250645, 9422124, 9601643, 9794139, 9970562, 10139020, 10291742, 10475496, 10690640, 10895640, 11098304, 11288042, 11474321, 11642572, 11852203, 12067468, 12292055, 12525185, 12738051, 12931019, 13125559, 13346622, 13576502, 13821455, 14058797, 14294106, 14505988, 14714176, 14957222, 15232596, 15512280, 15795865, 16045400, 16257212, 16491647, 16755516, 17025804, 17307583, 17595167
        };
        String[] dates = {
                "5/1/20", "5/2/20", "5/3/20", "5/4/20", "5/5/20", "5/6/20", "5/7/20", "5/8/20", "5/9/20", "5/10/20", "5/11/20", "5/12/20", "5/13/20", "5/14/20", "5/15/20", "5/16/20", "5/17/20", "5/18/20", "5/19/20", "5/20/20", "5/21/20", "5/22/20", "5/23/20", "5/24/20", "5/25/20", "5/26/20", "5/27/20", "5/28/20", "5/29/20", "5/30/20", "5/31/20", "6/1/20", "6/2/20", "6/3/20", "6/4/20", "6/5/20", "6/6/20", "6/7/20", "6/8/20", "6/9/20", "6/10/20", "6/11/20", "6/12/20", "6/13/20", "6/14/20", "6/15/20", "6/16/20", "6/17/20", "6/18/20", "6/19/20", "6/20/20", "6/21/20", "6/22/20", "6/23/20", "6/24/20", "6/25/20", "6/26/20", "6/27/20", "6/28/20", "6/29/20", "6/30/20", "7/1/20", "7/2/20", "7/3/20", "7/4/20", "7/5/20", "7/6/20", "7/7/20", "7/8/20", "7/9/20", "7/10/20", "7/11/20", "7/12/20", "7/13/20", "7/14/20", "7/15/20", "7/16/20", "7/17/20", "7/18/20", "7/19/20", "7/20/20", "7/21/20", "7/22/20", "7/23/20", "7/24/20", "7/25/20", "7/26/20", "7/27/20", "7/28/20", "7/29/20", "7/30/20", "7/31/20"
        };
        System.out.println(dataHideri.length + "" + dates.length);
        //double[] dataHideri = {514193727, 514651578, 515316911, 515933560, 516521732, 517139536, 517486797, 517768926, 518482846, 519113564, 519799535, 520432217, 520997976, 521442474, 521817972, 522424241, 523018431, 523765650, 524351747, 524971342, 525342261, 525659877, 526168761, 526747772, 527382252, 527886546, 528454598, 528742750, 529020011, 529425907, 530044396, 530736510, 531260938, 531802990, 532055897, 532322063, 532758581, 533408808, 534108642, 534673577, 535217846, 535501323, 535704735, 536267825, 537111264, 537830270, 538351574, 538936864, 539165303, 539440474, 540231496, 541043665, 541849702, 542621625, 543380157, 543696818, 543983931, 544908711, 545841942, 546773441, 547831266, 548768113, 549126851, 549487312, 550385627, 551574162, 552804289, 553839430, 554824033, 555326905, 555752958, 556861685, 558056840, 559508051, 560466883, 561482523, 562034421, 562493547, 563729333, 565253457, 566850244, 567974005, 569053104, 569689482, 570239283, 571266022, 572437705, 573845726, 574940954, 576297260, 576872204, 577417212};
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