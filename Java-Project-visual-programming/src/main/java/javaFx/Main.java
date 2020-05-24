package javaFx;

import data.CountryCovidStats;
import data.IndividualStat;
import data.Tools;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Main extends Application {
    Stage window;

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String date = dateFormat.format(cal.getTime());
            List<CountryCovidStats> myCountries = new ArrayList<>();

            myCountries.add(new CountryCovidStats("JPN", date));
            myCountries.add(new CountryCovidStats("ITA", date));
            myCountries.add(new CountryCovidStats("ESP", date));
            myCountries.add(new CountryCovidStats("CHN", date));
            myCountries.add(new CountryCovidStats("USA", date));


            window = primaryStage;
            window.setTitle("Project");
            Scene scene = new Scene(createBorderPane(myCountries, date));
            window.setScene(scene);
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            window.setX(bounds.getMinX());
            window.setY(bounds.getMinY());
            window.setWidth(bounds.getWidth());
            window.setHeight(bounds.getHeight());
            window.show();
        } catch (ParseException e) {
            System.out.println("Cannot parse json. Check date");
        }

    }

    public BorderPane createBorderPane(List<CountryCovidStats> countries, String date) throws IOException, ParseException {
        BorderPane borderPane = new BorderPane();
        BorderPane centerPane = new BorderPane();
        BorderPane centerPane2 = new BorderPane();

        centerPane2.setLeft(GetPieLeft.pieChart(countries, IndividualStat.DEATHS));
        centerPane2.setCenter(GetPieCenter.pieChart(countries, IndividualStat.CONFIRMED));
        centerPane2.setRight(GetPieRight.pieChart(countries, IndividualStat.RECOVERED));

        centerPane.setTop(GetMultiBarChart.multiBar(countries, date));
        centerPane.setCenter(centerPane2);

        borderPane.setTop(DetailedStats.DetailedBox());
        borderPane.setCenter(menuLeft(countries, date));
        borderPane.setBottom(centerPane);
        return borderPane;
    }

    public HBox menuLeft(List<CountryCovidStats> currentCountries, String date) throws IOException {
        HBox hBox = new HBox();
        Map<String, String> countries = Tools.createMap();
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(countries.keySet()));
        comboBox.setValue("United States of America (the)");
        Label AddCountry = new Label("Add Counrtry:    ");


        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String countryAbbr = countries.get(comboBox.getValue());
                try {
                    CountryCovidStats newCountry = new CountryCovidStats(countryAbbr, date);

                    double tempValueDEATHS = IndividualStat.DEATHS.getValue(newCountry);
                    double tempValueCONFIRMED = IndividualStat.CONFIRMED.getValue(newCountry);
                    double tempValueRECOVERED = IndividualStat.RECOVERED.getValue(newCountry);

                    GetPieLeft.addData(0, newCountry.getCountryName(), tempValueDEATHS);
                    GetPieCenter.addData(0, newCountry.getCountryName(), tempValueCONFIRMED);
                    GetPieRight.addData(0, newCountry.getCountryName(), tempValueRECOVERED);


                    GetMultiBarChart.addData(0, newCountry.getConfirmed(), newCountry.getCountryName());
                    GetMultiBarChart.addData2(0, newCountry.getDeaths(), newCountry.getCountryName());
                    GetMultiBarChart.addData3(0, newCountry.getRecovered(), newCountry.getCountryName());
                } catch (ParseException error) {
                    System.out.println("Not a valid country or date");
                }
            }
        };

        comboBox.setOnAction(event);
        hBox.getChildren().addAll(AddCountry, comboBox);
        hBox.setPadding(new Insets(5));

        return hBox;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
