package org.sentimentanalysis;

import java.io.*;
import java.util.*;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import org.apache.hadoop.io.IntWritable;
import org.ejml.simple.SimpleMatrix;
import org.sentimentanalysis.model.SentimentClassification;
import org.sentimentanalysis.model.SentimentResult;

public class SentimentAnalysisMapperNLP extends Mapper<Text,Text,Text,Text> {
    Map<String, String> dictionary = null;
    static Properties props;
    static StanfordCoreNLP pipeline;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and sentiment
        props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    @Override
    public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        //System.out.println("key: "+key);
        //System.out.println("value: "+value);
        String line = value.toString().trim();
        //System.out.println("line: "+line);
        SentimentResult sentimentResult = new SentimentResult();
        sentimentResult = getSentimentResult(line);
        context.write(new Text(key), new Text(String.valueOf(sentimentResult.getSentimentScore())));
        System.out.println(key+" "+sentimentResult.getSentimentScore());
        System.out.println(key+" - "+line+"\nScore: "+sentimentResult.getSentimentScore()+"\n"+"Type: "+sentimentResult.getSentimentType()+"\n"
                +"Very Positive: "+sentimentResult.getSentimentClass().getVeryPositive()+"\n"+"Positive: "+sentimentResult.getSentimentClass().getPositive()+"\n"
                + "Neutral: "+sentimentResult.getSentimentClass().getNeutral()+"\n"+"Negative: "+sentimentResult.getSentimentClass().getNegative()+"\n"
                +"Very Negative: "+sentimentResult.getSentimentClass().getVeryNegative());
        /*context.write(new Text(line),new Text(""));
        context.write(new Text("Sentiment Score: "),new Text(String.valueOf(sentimentResult.getSentimentScore())));
        context.write(new Text("Sentiment Type: "),new Text(sentimentResult.getSentimentType()));
        context.write(new Text("Very positive:  "),new Text(String.valueOf(sentimentResult.getSentimentClass().getVeryPositive())+"%"));
        context.write(new Text("Positive: "),new Text(String.valueOf(sentimentResult.getSentimentClass().getPositive())+"%"));
        context.write(new Text("Neutral: "),new Text(String.valueOf(sentimentResult.getSentimentClass().getNeutral())+"%"));
        context.write(new Text("Negative: "),new Text(String.valueOf(sentimentResult.getSentimentClass().getNegative())+"%"));
        context.write(new Text("Very Negative: "),new Text(String.valueOf(sentimentResult.getSentimentClass().getVeryNegative())+"%"))*/;


    }

    public SentimentResult getSentimentResult(String text) {

        SentimentResult sentimentResult = new SentimentResult();
        SentimentClassification sentimentClass = new SentimentClassification();

        if (text != null && text.length() > 0) {

            // run all Annotators on the text
            Annotation annotation = pipeline.process(text);

            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                // this is the parse tree of the current sentence
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);
                String sentimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

                sentimentClass.setVeryPositive((double)Math.round(sm.get(4) * 100d));
                sentimentClass.setPositive((double)Math.round(sm.get(3) * 100d));
                sentimentClass.setNeutral((double)Math.round(sm.get(2) * 100d));
                sentimentClass.setNegative((double)Math.round(sm.get(1) * 100d));
                sentimentClass.setVeryNegative((double)Math.round(sm.get(0) * 100d));

                sentimentResult.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));
                sentimentResult.setSentimentType(sentimentType);
                sentimentResult.setSentimentClass(sentimentClass);
            }

        }


        return sentimentResult;
    }
}