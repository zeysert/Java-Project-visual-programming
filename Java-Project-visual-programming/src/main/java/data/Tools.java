package data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tools {

    public static Path getPath(String filename) {
        Path path = null;
        try {
            path = Paths.get(ClassLoader.getSystemResource(filename).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static Map<String, String> createMap() throws IOException {
        Stream<String> fileStream = Files.lines(getPath("ISO.txt"));

        // Get each line in text file and add it to a list
        List<String[]> list = fileStream.map(line -> line.split("\n"))
                .collect(Collectors.toList());

        List<String> countryNames = new ArrayList<>();

        for (String[] strings : list) {
            countryNames.addAll(Arrays.asList(strings));
        }

        // Stream through newly created list and split it at "====="
        List<String[]> testList = countryNames.stream()
                .map(row -> row
                        .split("====="))
                .collect(Collectors.toList());


        // Create map where key is name of country and value is the
        // three letter abbreviation of country
        // Ex. Australia -> AUS
        Map<String, String> countryMap = new HashMap<>();
        for (String[] row : testList) {
            countryMap.put(row[0], row[2]);
        }

        fileStream.close();
        return countryMap;
    }

}
