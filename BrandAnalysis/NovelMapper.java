package org.example;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class NovelMapper extends Mapper<Text, Text, Text, IntWritable> {
    private HashMap<String, HashSet<String>> thesaurus;
    private HashSet<String> synonymSet;
    private ArrayList<String> companyValues;

    public void setup(Context context) throws IOException, InterruptedException {
        thesaurus = new HashMap<String, HashSet<String>>();

        Path[] docFiles = DistributedCache.getLocalCacheFiles(context.getConfiguration());
        String valueFile = context.getConfiguration().get("valuefile");

        for (Path docFile : docFiles) {
            if (docFile.getName().equals("WordnetSynonyms.csv")) {
                BufferedReader reader = new BufferedReader(new FileReader(docFile.toString()));
                String line;

                while ((line = reader.readLine()) != null) {
                    // Split by commas
                    String[] tokens = line.split(",");
                    // Get its synonyms
                    String[] synonyms = tokens[3].split("[;|]");

                    // Check if thesaurus already has key
                    if (thesaurus.containsKey(tokens[0])){
                        synonymSet = thesaurus.get(tokens[0]);
                    } else {
                        synonymSet = new HashSet<String>();
                    }

                    for (String synonym : synonyms) {
                        // Remove leading and trailing whitespaces and make everything lowercase
                        synonymSet.add(synonym.trim().toLowerCase());
                    }
                    thesaurus.put(tokens[0], synonymSet);
                }
                reader.close();
            }

            if (docFile.getName().equals(valueFile)) {
                BufferedReader reader = new BufferedReader(new FileReader(docFile.toString()));
                String line;
                companyValues = new ArrayList<String>();

                while ((line = reader.readLine()) != null) {
                    // Get company values
                    companyValues.add(line.toLowerCase());
                }
                reader.close();
            }
        }
    }

    public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        // List of words from reviews
        String[] words = value.toString().trim().split("\\s+");

        for (String word : words) {
            String lowerWord = word.toLowerCase();
            if (companyValues.contains(lowerWord)) {
                context.write(new Text("Count"), new IntWritable(1));
                return;
            }
            for (String cValue : companyValues) {
                if (synonymSearch(cValue, lowerWord)) {
                    context.write(new Text("Count"), new IntWritable(1));
                    return;
                }
            }
        }
    }

    // This function checks if word is a synonym of wordCheck
    private boolean synonymSearch(String word, String wordCheck) {
        boolean success = false;

        if (thesaurus.containsKey(word)){
            if (thesaurus.get(word).contains(wordCheck)) {
                success = true;
            }
        }
        if (thesaurus.containsKey(wordCheck)){
            if (thesaurus.get(wordCheck).contains(word)) {
                success = true;
            }
        }

        return success;
    }
}