package BrandAnalysis;

import java.net.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class Thesaurus {
    private boolean checkIfSynonym(String inputToCheck) {
        try {
            String input = "modern";
            String synonymToFind = "innovative";
            String url = "https://api.datamuse.com/words?rel_syn=" + synonymToFind;

            URL api = new URL(url);
            HttpURLConnection con = (HttpURLConnection) api.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            System.out.println("response: "+ response);

            // Parse the JSON string into a JsonArray
            JsonArray jsonArray = JsonParser.parseString(String.valueOf(response)).getAsJsonArray();

            // Create an array to store the words
            String[] words = new String[jsonArray.size()];

            // Extract the word values from each JsonObject in the array and store them in the array
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                words[i] = jsonObject.get("word").getAsString();
            }

            // Print the words array
            System.out.println("Result: " + Arrays.toString(words));
            boolean containsModern = false;
            for (String word : words) {
                if (word.equals(input)) {
                    containsModern = true;
                    break;
                }
            }

            if (containsModern) {
                return true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
