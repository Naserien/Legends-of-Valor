/*
* Read files and return <name: <attr: value>>
* */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class FileReader {

    public static HashMap<String, HashMap<String, Integer>> readStats(String filepath) {
        HashMap<String, HashMap<String, Integer>> charactersStats = new HashMap<>();

        System.out.printf("Loading \"%s\" ... ", filepath);

        // Check whether there are blank lines
        //

        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(filepath));
            String line = reader.readLine();

            if (line != null) {
                String[] keys = line.trim().split("/");
                while ((line = reader.readLine()) != null) {
                    String[] values = line.trim().split("\\s+");
                    HashMap<String, Integer> stats = new HashMap<>();
                    for (int i = 1; i < keys.length; i++) {
                        stats.put(keys[i], Integer.parseInt(values[i]));
                    }
                    charactersStats.put(values[0], stats);
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.err.printf("%s: File not found.\n", filepath);
        } catch (IOException e) {
            System.err.printf("%s: An error occurred while reading the file.\n", filepath);
        } catch (NumberFormatException e) {
            System.out.printf("%s: Error parsing integer values from the file.\n", filepath);
        }

        System.out.println("Done!");

        return charactersStats;
    }

    public static HashMap<String, HashMap<String, String>> readStatsToString(String filepath) {
        HashMap<String, HashMap<String, String>> charactersStats = new HashMap<>();

        System.out.printf("Loading \"%s\" ... ", filepath);

        // Check whether there are blank lines
        //

        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(filepath));
            String line = reader.readLine();

            if (line != null) {
                String[] keys = line.trim().split("/");
                while ((line = reader.readLine()) != null) {
                    String[] values = line.trim().split("\\s+");
                    HashMap<String, String> stats = new HashMap<>();
                    for (int i = 1; i < keys.length; i++) {
                        stats.put(keys[i], values[i]);
                    }
                    charactersStats.put(values[0], stats);
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.err.printf("%s: File not found.\n", filepath);
        } catch (IOException e) {
            System.err.printf("%s: An error occurred while reading the file.\n", filepath);
        }

        System.out.println("Done!");

        return charactersStats;
    }
}



