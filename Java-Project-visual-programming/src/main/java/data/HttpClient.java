package data;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpClient {
    public static String apiUrl = "https://covidapi.info/api/v1/";

    public static void specificDataForCountry(String country, String date) {
        String url = date == "" ? apiUrl + "country/" + country : apiUrl + "country/" + country + "/" + date;

        Path output = Paths.get("output");

        String json = NewHttpGet(url);

        if(!Files.exists(output.toAbsolutePath())) {
            try {
                Files.createDirectories(output.toAbsolutePath());
                output = output.resolve("output.json");
                BufferedWriter writer = Files.newBufferedWriter(output);
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                System.out.println("Not there");
            }
        } else {
            try {
                output = output.resolve("output.json");
                BufferedWriter writer = Files.newBufferedWriter(output);
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                System.out.println("Not happening");
            }
        }


    }

    public static void golbalStats(String date1, String date2) {
        String url;
        if (date1 == "") {
            url = apiUrl + "global";
        } else if (date2 == "") {
            url = apiUrl + "global/" + date1;
        } else {
            url = apiUrl + "global/" + date1 + "/" + date2;
        }
        Path output = Paths.get("output");

        String json = NewHttpGet(url);

        if(!Files.exists(output.toAbsolutePath())) {
            try {
                Files.createDirectories(output.toAbsolutePath());
                output = output.resolve("worldStats.json");
                BufferedWriter writer = Files.newBufferedWriter(output);
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                System.out.println("Not there");
            }
        } else {
            try {
                output = output.resolve("worldStats.json");
                BufferedWriter writer = Files.newBufferedWriter(output);
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                System.out.println("Not happening");
            }
        }

    }

    public static void timeSeriesGolbalStats(String country, String date1, String date2) {
        String url = country == "" ? apiUrl + "global/timeseries/" + date1 + "/" + date2 : apiUrl + "country/" + country + "/timeseries/" + date1 + "/" + date2;
        System.out.println(url);
        HttpGet(url);
    }

    // Obed, I made this one to play around with your method. Yours is below this one. Sorry if i messed it up a bit.
    public static String NewHttpGet(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse resp = null;
        try {
            resp = client.execute(get);
            HttpEntity entity = resp.getEntity();

            String json = EntityUtils.toString(entity);

            return json;
        } catch (IOException ioe) {
            System.err.println("Something went wrong getting the weather: ");
            ioe.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unknown error: ");
            e.printStackTrace();
        }
        return null;
    }

    public static void HttpGet(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse resp = null;
        try {
            resp = client.execute(get);
            HttpEntity entity = resp.getEntity();
            System.out.println("Json response");
            System.out.println(EntityUtils.toString(entity));
        } catch (IOException ioe) {
            System.err.println("Something went wrong getting the weather: ");
            ioe.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unknown error: ");
            e.printStackTrace();
        }
    }
}
