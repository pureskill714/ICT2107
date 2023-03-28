package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class SentimentReducer extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int totalSentimentScore = 0;
        int numPositiveSentiments = 0;
        int numNegativeSentiments = 0;
        int numNeutralSentiments = 0;

        for (Text value : values) {
            String[] tokens = value.toString().split("\t");
            String sentiment = tokens[0];
            int score = Integer.parseInt(tokens[1]);
            if (sentiment.equals("positive")) {
                numPositiveSentiments++;
            } else if (sentiment.equals("negative")) {
                numNegativeSentiments++;
            } else {
                numNeutralSentiments++;
            }
            totalSentimentScore += score;
        }
        String overallSentiment = getOverallSentiment(numPositiveSentiments, numNegativeSentiments, numNeutralSentiments);
        String output = String.format("positive:%d,negative:%d,neutral:%d\ttotal_score:%d\toverall_sentiment:%s", numPositiveSentiments, numNegativeSentiments, numNeutralSentiments, totalSentimentScore, overallSentiment);
        context.write(key, new Text(output));


    }
    private String getOverallSentiment(int numPositiveSentiments, int numNegativeSentiments, int numNeutralSentiments) {
        if (numPositiveSentiments > numNegativeSentiments && numPositiveSentiments > numNeutralSentiments) {
            return "positive";
        } else if (numNegativeSentiments > numPositiveSentiments && numNegativeSentiments > numNeutralSentiments) {
            return "negative";
        } else {
            return "neutral";
        }
    }

}
