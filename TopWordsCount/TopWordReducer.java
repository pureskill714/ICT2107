import java.io.IOException;
import java.util.TreeMap;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TopWordReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    // TreeMap to store the count of each shape
    private TreeMap<Integer, String> countToWordMap = new TreeMap<>();

    // IntWritable to hold the count of each shape
    private DoubleWritable count = new DoubleWritable();

    // Top N shapes to output
    private int N = 10;

    @Override
    public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
            throws IOException, InterruptedException {

        int sum = 0;
        for (DoubleWritable value : values) {
            sum += value.get();
        }

        // store the count and shape in the TreeMap
        countToWordMap .put(sum, key.toString());

        // if the TreeMap has more than N entries, remove the smallest one
        if (countToWordMap.size() > N) {
            countToWordMap.remove(countToWordMap .firstKey());
        }
    }


    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        // Output the top N shapes
        for (Integer count : countToWordMap.descendingKeySet()) {
            Text shape = new Text(countToWordMap.get(count));
            this.count.set(count);
            context.write(shape, this.count);
        }
    }
}