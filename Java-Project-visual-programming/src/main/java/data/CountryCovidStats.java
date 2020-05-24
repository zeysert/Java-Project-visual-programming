package data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CountryCovidStats {
    private String countryName;
    private String currentDate;
    private Long confirmed;
    private Long deaths;
    private Long recovered;

    public CountryCovidStats(String countryName, String currentDate) throws ParseException {
        this.countryName = countryName;
        this.currentDate = currentDate;
        HttpClient.specificDataForCountry(countryName, currentDate);
        this.extractInformation();
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public Long getConfirmed() {
        return confirmed;
    }

    public Long getDeaths() {
        return deaths;
    }

    public Long getRecovered() {
        return recovered;
    }

    private void extractInformation() throws ParseException {
        try {
            // Check if file exists. Should be named "output.json"
            Object obj = new JSONParser().parse(new FileReader("output" + File.separator + "output.json"));

            // typecasting obj to JSONObject
            JSONObject jo = (JSONObject) obj;

            // Get all results and transform them into our own HashMap<String, JsonObject>
            HashMap results = (HashMap) jo.get("result");
            JSONObject temp = (JSONObject) results.get(this.currentDate);

            // Extract confirmed cases and deaths for the date
            // assign value to instance variable
            this.confirmed = (Long) temp.get("confirmed");
            this.deaths = (Long) temp.get("deaths");
            this.recovered = (Long) temp.get("recovered");

        } catch (FileNotFoundException e) {
            System.out.println("File does not exist. Should be named output.json");
        } catch (IOException e) {
            System.out.println("IO exception");
        }
    }

    public double getDeathRate() {
        return ((double) deaths / (double) confirmed) * 100;
    }

    public double getRecoveredRate() {
        return ((double) recovered / (double) confirmed) * 100;
    }

}
