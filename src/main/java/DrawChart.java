import dto.MeasurementDTO;
import dto.MeasurementsResponse;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.internal.chartpart.Chart;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DrawChart {
    public static void main(String[] args) {
        List<Double> temperatures = getMeasurements();

        drawChart(temperatures);
    }

    private static List<Double> getMeasurements() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements";

        MeasurementsResponse jsonResponse = restTemplate.getForObject(url, MeasurementsResponse.class);

        List<Double> temperatures = new ArrayList<>();
        for (MeasurementDTO measurementDTO : jsonResponse.getMeasurements())
            temperatures.add(measurementDTO.getValue());

        return temperatures;
    }

    private static void drawChart(List<Double> values) {
        double[] xData = IntStream.range(0, values.size()).asDoubleStream().toArray();
        double[] yData = values.stream().mapToDouble(x -> x).toArray();

        XYChart chart = QuickChart.getChart("Temperatures", "X", "Y", "temperature",
                xData, yData);

        new SwingWrapper(chart).displayChart();
    }
}
