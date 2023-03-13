import streamlit as st
import pandas as pd

lazada_sentiment = {"Lazada Sentiment":["Positive","Negative","Neutral"],"Value":[100,100,100]}
st.write(lazada_sentiment)
lazada_sentiment = pd.DataFrame(lazada_sentiment)
lazada_sentiment = lazada_sentiment.set_index("Lazada Sentiment")
st.write(lazada_sentiment)
st.bar_chart(lazada_sentiment)


shopee_sentiment = {"Shopee Sentiment":["Positive","Negative","Neutral"], "Value":[100,100,100]}
st.write(shopee_sentiment)
shopee_sentiment = pd.DataFrame(shopee_sentiment)
shopee_sentiment = shopee_sentiment.set_index("Shopee Sentiment")
st.write(shopee_sentiment)
st.bar_chart(shopee_sentiment)