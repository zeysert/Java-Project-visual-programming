package data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class WorldStats {
    private Long confirmed;
    private Long recovered;
    private Long deaths;

    public WorldStats() throws ParseException {
        HttpClient.golbalStats("",""); // latest info for covid-19
        this.extractInformation();
    }

    public Long getConfirmed() {
        return confirmed;
    }

    public Long getRecovered() {
        return recovered;
    }

    public Long getDeaths() {
        return deaths;
    }

    private void extractInformation() throws ParseException {
        try {
            // Check if file exists. Should be named "output.json"
            Object obj = new JSONParser().parse(new FileReader("output" + File.separator + "worldStats.json"));

            // typecasting obj to JSONObject
            JSONObject jo = (JSONObject) obj;

            // Get all results and transform them into our own HashMap<String, JsonObject>
            HashMap results = (HashMap) jo.get("result");

            // Assign values to instance variables
            this.confirmed = (Long) results.get("confirmed");
            this.deaths = (Long) results.get("deaths");
            this.recovered = (Long) results.get("recovered");

        } catch (FileNotFoundException e) {
            System.out.println("File does not exist. Should be named output.json");
        } catch (IOException e) {
            System.out.println("IO exception");
        }
    }

}
