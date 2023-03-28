package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DataPreprocessing {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Data Preprocessing");
        job.setJarByClass(DataPreprocessing.class);

        job.addCacheFile(new Path("hdfs://localhost:9000/input/stopwords.txt").toUri());
        job.addCacheFile(new Path("hdfs://localhost:9000/input/AFINN-en-165.txt").toUri());

        Configuration preprocessingConfig = new Configuration(false);
        ChainMapper.addMapper(job, PreprocessingMapper.class, LongWritable.class, Text.class, Text.class, Text.class, preprocessingConfig);
        Configuration sentimentConfig = new Configuration(false);
        ChainMapper.addMapper(job, SentimentAnalysisMapper.class, Text.class, Text.class, Text.class, Text.class, sentimentConfig);

        job.setMapperClass(ChainMapper.class);
        job.setReducerClass(SentimentReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

