package javaFx;

import data.WorldStats;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.json.simple.parser.ParseException;

import java.text.DecimalFormat;

public class DetailedStats {
    public static HBox DetailedBox() throws ParseException {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

        WorldStats ws = new WorldStats();
        HBox hbox = new HBox();
        hbox.setSpacing(20);
        hbox.setPadding(new Insets(5));
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setStyle("-fx-border-style: solid inside;" +
                "-fx-align: right;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 2;" +
                "-fx-border-color: gray;");
        Label cases = new Label("Total Cases:  " + decimalFormat.format(ws.getConfirmed()));
        Label deaths = new Label("Total Deaths:  " + decimalFormat.format(ws.getDeaths()));
        Label recovery = new Label("Total Recovery:  " + decimalFormat.format(ws.getRecovered()));

        hbox.getChildren().addAll(cases, deaths, recovery);
        return hbox;
    }
}
