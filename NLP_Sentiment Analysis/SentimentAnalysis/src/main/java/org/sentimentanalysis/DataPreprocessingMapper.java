package org.sentimentanalysis;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DataPreprocessingMapper extends Mapper<LongWritable, Text, Text, Text> {
    private Set<String> stopWords;

    public void setup(Context context) throws IOException, InterruptedException {
        // Read stop words from file
        stopWords = new HashSet<String>();
        Path[] files = DistributedCache.getLocalCacheFiles(context.getConfiguration());
        for (Path file : files) {
            if (file.getName().equals("stopwords.txt")) {
                BufferedReader reader = new BufferedReader(new FileReader(file.toString()));
                String word;
                while ((word = reader.readLine()) != null) {
                    stopWords.add(word);
                }
                reader.close();
            }
        }
    }

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String line = value.toString();
        // Skip the header line
        if (line.startsWith("Summary")) {
            return;
        }
        // Split the line by comma
        String[] fields = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        if (fields.length < 6) {
            return;
        }
        String summary = fields[0];
        String jobTitle = fields[1];
        String date = fields[2];
        String overallRating = fields[3];
        String pros = fields[4];
        String cons = fields[5];
        // Remove special characters
        //summary = summary.replaceAll("[^a-zA-Z0-9 ]", "");    not used
        jobTitle = jobTitle.replaceAll("[^a-zA-Z ]", " ");
        pros = pros.replaceAll("[^a-zA-Z ]", " ").trim();
        cons = cons.replaceAll("[^a-zA-Z ]", " ").trim();
        // Remove stop words
        //String[] words = (summary + " " + jobTitle + " " + pros + " " + cons).toLowerCase().split("\\s+");    not used
        String[] words = (pros + " " + cons).toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!stopWords.contains(word)) {
                sb.append(word).append(" ");
            }
        }
        String cleanedText = sb.toString().trim();
        if (!cleanedText.isEmpty()) {
            context.write(new Text(jobTitle.toLowerCase()), new Text(cleanedText));
        }
        //debugging stuff
        //System.out.println(pros);
        //System.out.println(cons);
        System.out.println(jobTitle+" - "+cleanedText);
    }
}