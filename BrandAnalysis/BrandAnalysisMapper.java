package BrandAnalysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringJoiner;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class BrandAnalysisMapper extends Mapper<Text, Text, Text, Text> {

	private StanfordCoreNLP pipeline;

	public void setup(Context context) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos");
		this.pipeline = new StanfordCoreNLP(props);
	}

	@Override
	public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString().trim();
		Annotation annotation = new Annotation(line);
		pipeline.annotate(annotation);
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		List<String> adjectives = new ArrayList<String>();
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				String word = token.get(CoreAnnotations.TextAnnotation.class);
				String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
				if (pos.startsWith("JJ")) {
					System.out.println("(word) Adjective: " + word);
					adjectives.add(word);
				}
			}
		}



		StringJoiner joiner = new StringJoiner(", ");
		for (String adjective : adjectives) {
			joiner.add(adjective);
		}
		String companyBrandPersonality = joiner.toString();
		System.out.println("key: " + key);
		System.out.println("value: " + value);
		System.out.println("Output company brand " + companyBrandPersonality);
		System.out.println();

		context.write(key, new Text(companyBrandPersonality));
	}



}
