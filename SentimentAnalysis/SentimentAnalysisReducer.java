import java.io.IOException;
import java.text.DecimalFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class SentimentAnalysisReducer extends Reducer<Text,DoubleWritable,Text,DoubleWritable> {
    private DoubleWritable result_positive = new DoubleWritable();
    private DoubleWritable result_negative = new DoubleWritable();
    private DoubleWritable result_positivityScore = new DoubleWritable();
    private DoubleWritable result_negativityScore = new DoubleWritable();
    private final DecimalFormat df = new DecimalFormat("0.00");
    int sum = 0;
    int positive_sum = 0;
    int negative_sum = 0;
    int negative_sum2 = 0;
    double positivity_score = 0;
    double positivity_score2 = 0;
    double negativity_score = 0;


    @Override
    public void reduce(Text key,Iterable<DoubleWritable> values, Context context)
            throws IOException, InterruptedException {

        for (DoubleWritable value : values) {
            sum += value.get();

            if (value.get() > 0) {
                positive_sum += value.get();
            }
            else{
                negative_sum += value.get();
            }

        }

    }

    public void cleanup(Context context) throws IOException, InterruptedException {
        negative_sum2 = Math.abs(negative_sum);
        //positivity_score = (positive_sum/(positive_sum+negative_sum2));
        //positivity_score2 = Double.parseDouble(df.format(positivity_score));
       //negativity_score = (negative_sum2/100);


        result_positive.set(positive_sum);
        context.write(new Text("Positive score"), result_positive);

        result_negative.set(negative_sum2);
        context.write(new Text("Negative score"), result_negative);

        //result_positivityScore.set(positivity_score2);
        //context.write(new Text("Positivity score(%)"), result_positivityScore);

        //result_negativityScore.set(negativity_score);
        //context.write(new Text("Negativity score(%)"), result_negativityScore);
    }

}