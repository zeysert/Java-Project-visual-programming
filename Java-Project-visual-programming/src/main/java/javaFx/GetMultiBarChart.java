package javaFx;

import data.CountryCovidStats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

import java.util.List;

public class GetMultiBarChart {
    public static ObservableList<XYChart.Data> s;
    public static ObservableList<XYChart.Data> s2;
    public static ObservableList<XYChart.Data> s3;
    public static XYChart.Series se = new XYChart.Series();

    public static VBox multiBar(List<CountryCovidStats> countries, String date) {
        // Our top 5 countries. Use this info to fill in the graphs
        s = FXCollections.observableArrayList();
        s2 = FXCollections.observableArrayList();
        s3 = FXCollections.observableArrayList();

        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final BarChart<Number, String> bc = new BarChart<Number, String>(xAxis, yAxis);
        bc.setCategoryGap(6.5);
        bc.setBarGap(1.0);

        bc.setTitle("Countries Summary as of " + date);
        xAxis.setLabel("Values");
        yAxis.setLabel("Country");


        for (CountryCovidStats country : countries) {
            s.add(new XYChart.Data(country.getConfirmed(), country.getCountryName()));
            s2.add(new XYChart.Data(country.getDeaths(), country.getCountryName()));
            s3.add(new XYChart.Data(country.getRecovered(), country.getCountryName()));

//            series1.getData().add(new XYChart.Data(country.getConfirmed(), country.getCountryName()));
//            series2.getData().add(new XYChart.Data(country.getDeaths(), country.getCountryName()));
//            series3.getData().add(new XYChart.Data(country.getRecovered(), country.getCountryName()));
        }

        XYChart.Series series1 = new XYChart.Series(s);
        series1.setName("Confirmed");

        XYChart.Series series2 = new XYChart.Series(s2);
        series2.setName("Deaths");

        XYChart.Series series3 = new XYChart.Series(s3);
        series3.setName("Recovered");
//        series1.getData().add(s);


        bc.getData().addAll(series1, series2, series3);
        VBox vbox = new VBox(bc);
        vbox.setPrefWidth(700);
        vbox.setPrefHeight(400);
        vbox.setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 0;" +
                "-fx-border-color: grey;");
        return vbox;
    }

    public static void addData(int position, double value, String name) {
        s.set(position, new XYChart.Data(value, name));
    }

    public static void addData2(int position, double value, String name) {
        s2.set(position, new XYChart.Data(value, name));
    }

    public static void addData3(int position, double value, String name) {
        s3.set(position, new XYChart.Data(value, name));
    }
}
