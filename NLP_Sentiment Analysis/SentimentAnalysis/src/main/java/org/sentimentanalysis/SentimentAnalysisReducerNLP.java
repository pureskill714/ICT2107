package org.sentimentanalysis;

import com.google.common.collect.Iterables;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SentimentAnalysisReducerNLP extends Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        double totalSentiment = 0;
        int sizeOfValues = 0;
        double averageSentiment;
        int finalizedSentiment;
        String sentimentType;
        System.out.println("Values: "+values);
        for (Text value : values) {
            sizeOfValues++;
            //System.out.println("Value: "+value);
            totalSentiment += Double.parseDouble(String.valueOf(value));
        }
        //System.out.println("Size of Values: "+ sizeOfValues);
        averageSentiment = Math.round(totalSentiment/sizeOfValues);
        finalizedSentiment = (int)normalizeVerySentimentScores(averageSentiment);
        //System.out.println("Final Sentiment: "+finalizedSentiment);
        //System.out.println("Total Sentiment: "+totalSentiment);
        //System.out.println("Average Sentiment: "+averageSentiment);
        //System.out.println("Average Sentiment Round Up:"+Math.round(averageSentiment));
        sentimentType = getSentimentType(finalizedSentiment);
        System.out.println(sentimentType+" - "+finalizedSentiment);
        System.out.println(key+" - "+"Overall Sentiment: "+ sentimentType+ " "+"Average Sentiment Score: "+finalizedSentiment);
        context.write(key,new Text("Overall Sentiment: "+ sentimentType+ " "+"Average Sentiment Score: "+finalizedSentiment));

    }

    private double normalizeVerySentimentScores(double averageSentiment){
        if(averageSentiment == 4.0){
            averageSentiment = 3.0;
        }
        else if(averageSentiment == 0.0){
            averageSentiment = 1.0;
        }
        return averageSentiment;
    }

    private String getSentimentType(double sentimentScore){
        String sentimentType = "";
        if(sentimentScore == 1){
            sentimentType = "Negative";
        }
        else if(sentimentScore == 2){
            sentimentType = "Neutral";
        }
        else if(sentimentScore == 3){
            sentimentType = "Positive";
        }
        return sentimentType;
    }
}
