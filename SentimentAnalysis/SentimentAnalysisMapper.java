package org.example;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class SentimentAnalysisMapper extends Mapper<Text, Text, Text, Text> {
    private Map<String, Integer> afinn;

    public void setup(Context context) throws IOException, InterruptedException {
        // Read AFINN file
        afinn = new HashMap<String, Integer>();
        Path[] afinnFiles = DistributedCache.getLocalCacheFiles(context.getConfiguration());
        for (Path afinnFile : afinnFiles) {
            if (afinnFile.getName().equals("AFINN-en-165.txt")) {
                BufferedReader reader = new BufferedReader(new FileReader(afinnFile.toString()));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split("\t");
                    afinn.put(tokens[0], Integer.parseInt(tokens[1]));
                }
                reader.close();
            }
        }

    }

    public void map(Text key, Text value, Context context) throws IOException, InterruptedException {

        String[] words = value.toString().trim().split("\\s+");
        int sentimentScore = 0;
        for (String word : words) {
            if (afinn.containsKey(word)) {
                sentimentScore += afinn.get(word);
            }

        }
        String sentiment = "neutral";
        if (sentimentScore > 0) {
            sentiment = "positive";
        } else if (sentimentScore < 0) {
            sentiment = "negative";
        }
        context.write(key, new Text(sentiment + "\t" + sentimentScore));

        //debugging stuff
        System.out.println(sentimentScore);
    }
}
