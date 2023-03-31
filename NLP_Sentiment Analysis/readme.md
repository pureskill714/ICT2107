# How to run NLP sentiment analysis?
1. Download "standford-corenlp-4.5.3" from this link:
https://huggingface.co/stanfordnlp/CoreNLP/tree/v4.5.3
2. Copy the jar files from "stanford-corenlp-4.5.3" folder into this directory in your local Hadoop:
/hadoop-3.3.4/share/hadoop/common/lib/

3. Open SentimentAnalysis folder in IntelliJ. Click on reload project just to make sure dependancies are installed.
4. Run Maven Clean and Install.
5. In IntelliJ terminal run this:
hadoop jar target/SentimentAnalysis-1.0-SNAPSHOT.jar org.sentimentanalysis.SentimentAnalysisDriver /data directory/filename /output directory/              