import java.io.*;
import java.util.*;
import java.net.URI;

import org.apache.hadoop.io.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.commons.lang3.StringUtils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TopWordMapper extends Mapper<LongWritable,Text,Text,DoubleWritable> {
    Map<String, String> dictionary = null;
    DoubleWritable one = new DoubleWritable(1);
    Text word = new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        dictionary = new HashMap<String, String>();
        URI[] cacheFiles = context.getCacheFiles();

        if (cacheFiles != null && cacheFiles.length > 0) {
            try {
                String line = "";
                FileSystem fs = FileSystem.get(context.getConfiguration());
                Path path = new Path(cacheFiles[0].toString());
                BufferedReader reader = new BufferedReader(new InputStreamReader(fs.open(path)));

                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split("\t");
                    dictionary.put(tokens[0], tokens[1]);
                }

            } catch (Exception e) {
                System.out.println("Unable to read the cached filed");
                System.exit(1);
            }
        }

    }

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //String fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
        String line = value.toString().trim();
        String[] words = line.split("\\s+");
        //int sentiment_value= 0;
        for(String temp:words)
        {
            if(dictionary.containsKey(temp))
            {
               word.set(temp);

            }

        }
        context.write(word, one);

    }
}