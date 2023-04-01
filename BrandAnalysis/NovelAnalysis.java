package org.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class NovelAnalysis {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String inputPath = "hdfs://localhost:9000/datainput/dataset/" + args[0];
        String valuePath = "hdfs://localhost:9000/input/" + args[1];
        conf.set("valuefile", args[1]);
        Job job = Job.getInstance(conf, "Novel Analysis");
        job.setJarByClass(NovelAnalysis.class);

        job.addCacheFile(new Path("hdfs://localhost:9000/input/stopwords.txt").toUri());
        job.addCacheFile(new Path("hdfs://localhost:9000/input/WordnetSynonyms.csv").toUri());
        job.addCacheFile(new Path(valuePath).toUri());

        Configuration preprocessingConfig = new Configuration(false);
        ChainMapper.addMapper(job, PreprocessingMapper.class, LongWritable.class, Text.class, Text.class, Text.class, preprocessingConfig);
        Configuration sentimentConfig = new Configuration(false);
        ChainMapper.addMapper(job, NovelMapper.class, Text.class, Text.class, Text.class, IntWritable.class, sentimentConfig);

        job.setMapperClass(ChainMapper.class);
        job.setReducerClass(NovelReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path("hdfs://localhost:9000/output/" + args[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
