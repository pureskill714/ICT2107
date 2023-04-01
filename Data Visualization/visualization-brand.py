import os
import streamlit as st
import pandas as pd
import statistics as stats
import numpy as np
import plotly_express as px
import altair as alt
from sys import platform
import matplotlib.pyplot as plt
import numpy as np
import plotly.graph_objects as go

from st_aggrid import AgGrid

global df

# Page Configs
st.set_page_config(
    page_title="Data Visualization For Sentiment Analysis",
    page_icon="🐠",
)

st.title("Data Visualization For Brand Analysis")
st.sidebar.info("This page allows visualize the data from your csv files")
csv_path = "results-brand"
container1 = st.empty()
file_list = os.listdir(csv_path)

instruction_guide = st.expander("Expand or Collapse", True)
instruction_guide.write('###')  # Line break
instruction_guide.markdown("""
        ### Page Guide:
        Select the CSV file you would like to view from the dropdown menu. These are the data extracted from the MapReduce program. 
        ###
        """)
st.markdown("###")

if len(file_list) == 0:
    st.warning("""The CSV results folder is currently **empty!**""")

else:
    #try:
        option = st.selectbox(
            'Which CSV file would you like to view?',
            file_list)
        if platform == "win32" or platform == "win64":
            df = pd.read_csv(f"{csv_path}\\{option}")
            # st.dataframe(df)
            #AgGrid(df, editable=False, enable_enterprise_modules=True, exportDataAsCsv=True,
                   #getDataAsCsv=True)
        elif platform == "darwin":
            df = pd.read_csv(f"{csv_path}/{option}")
            # st.dataframe(df)
            #AgGrid(df, editable=False, enable_enterprise_modules=True, exportDataAsCsv=True,
                   #getDataAsCsv=True)

        job_list = df['Company'].tolist()
        percentage_list = df['Overall Percentage'].tolist()

        # Create some data
        x = job_list
        y = percentage_list
        # Create a bar chart
        fig = go.Figure(go.Bar(x=x, y=y))
        #ax.bar(x, y)
        #ax.set_xlabel('X-axis')
        #ax.set_ylabel('Y-axis')
        #ax.set_title('Bar Graph')

        fig.update_layout(
            xaxis_title="Companies",
            yaxis_title="Percentage (%)",
            title="Percentage of employee reviews with brand synonyms"
        )
        # Set the tickangle property of the x-axis to 0
        fig.update_xaxes(tickangle=0)

        # Display the chart using Streamlit
        st.plotly_chart(fig)


        # Display the chart using Streamlit
        #st.pyplot(fig)









    #except:
        #st.text("This file is not a CSV file!")
