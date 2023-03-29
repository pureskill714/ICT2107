import streamlit as st
import pandas as pd
import plotly.express as px

lazada_sentiment = {"Lazada Sentiment":["Positive","Negative","Neutral"],"Value":[100,100,100]}
st.write(lazada_sentiment)
lazada_sentiment = pd.DataFrame(lazada_sentiment)
lazada_sentiment = lazada_sentiment.set_index("Lazada Sentiment")
st.write(lazada_sentiment)
st.bar_chart(lazada_sentiment)


# data = {
#     'ctry': ['USA', 'PHI', 'CHN'],
#     'gold': [12, 1, 20,],
#     'silver': [4,4, 12],
#     'bronze': [8, 2, 30],
#     'sum': [24, 7, 62]
# }

#To read csv file from project file
df2 = pd.read_csv('dataset/part-r-00000.csv')
# df = pd.DataFrame(data)
# st.dataframe(df)

#To diplay columns and tables
st.dataframe(df2)

# medal_type = st.selectbox('Medal Type', ['gold', 'silver', 'bronze'])

#To display dropbown box
rating = st.selectbox('Rating Type',['Positive','Negative','Neutral'])

#To display piechart into Streamlit
fig2 = px.pie(df2,values=rating, names='Job Title',title=f'number of {rating} rating',height=300, width=200)
fig2.update_layout(margin=dict(l=20, r=20, t=30, b=0))
st.plotly_chart(fig2,use_container_width=True)

# fig = px.pie(df, values=medal_type, names='ctry', title=f'number of {medal_type} medals',height=300, width=200)
# fig.update_layout(margin=dict(l=20, r=20, t=30, b=0),)
# st.plotly_chart(fig, use_container_width=True)

# testing = {"Testing": [0.330,4.87,5.97],"radius":[2439.7,6051.8, 6387.1]}
# st.write(testing)
# df = pd.DataFrame(testing,index=['test1','test2','test3'])
# st.dataframe(df)
# # plot = df.plot.pie(y="Testing",figsize=(5, 5))
# # st.write(plot)
# # st.plotly_chart(plot)
#
# fig = px.pie(df,)

shopee_sentiment = {"Shopee Sentiment":["Positive","Negative","Neutral"], "Value":[100,100,100]}
st.write(shopee_sentiment)
shopee_sentiment = pd.DataFrame(shopee_sentiment)
shopee_sentiment = shopee_sentiment.set_index("Shopee Sentiment")
st.write(shopee_sentiment)
st.bar_chart(shopee_sentiment)