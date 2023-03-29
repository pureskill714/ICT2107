package org.sentimentanalysis;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;



public class SentimentAnalysisDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "SentimentAnalysis");
        job.setJarByClass(SentimentAnalysisDriver.class);
        job.addCacheFile(new Path("hdfs://localhost:9000/user/project/input/stopwords/stopwords.txt").toUri());
        //job.addCacheFile(new Path("hdfs://localhost:9000/user/project/input/afinn/AFINN-en-165.txt").toUri());

        Configuration preprocessingConfig = new Configuration(false);
        ChainMapper.addMapper(job, DataPreprocessingMapper.class, LongWritable.class, Text.class, Text.class, Text.class, preprocessingConfig);
        Configuration sentimentConfig = new Configuration(false);
        ChainMapper.addMapper(job, SentimentAnalysisMapperNLP.class, Text.class, Text.class, Text.class, Text.class, sentimentConfig);

        job.setMapperClass(ChainMapper.class);
        //job.setMapperClass(SentimentAnalysisMapperNLP.class);
        job.setReducerClass(SentimentAnalysisReducerNLP.class);
        job.setNumReduceTasks(1);

        job.setMapOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);


        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true)?0:1);


    }

}

