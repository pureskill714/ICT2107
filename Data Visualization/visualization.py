import os
import streamlit as st
import pandas as pd
import statistics as stats
import numpy as np
import plotly_express as px
import altair as alt
from sys import platform

from st_aggrid import AgGrid

global df

# Page Configs
st.set_page_config(
    page_title="Data Visualization",
    page_icon="🐠",
)

st.title("📊 Data Visualization 📊")
st.sidebar.info("This page allows visualize the data from your csv files")
csv_path = "results-nlp"
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
            AgGrid(df, editable=False, enable_enterprise_modules=True, exportDataAsCsv=True,
                   getDataAsCsv=True)
        elif platform == "darwin":
            df = pd.read_csv(f"{csv_path}/{option}")
            # st.dataframe(df)
            AgGrid(df, editable=False, enable_enterprise_modules=True, exportDataAsCsv=True,
                   getDataAsCsv=True)

        total_positive = df["Positive"].sum()
        st.write("Total positive:", total_positive)

        total_neutral = df["Neutral"].sum()
        st.write("Total neutral:", total_neutral)

        total_negative = df["Negative"].sum()
        st.write("Total negative:", total_negative)


        # Create example dataframe
        data = {
            "Sentiment": ["Positve", "Neutral", "Negative"],
            "Value": [total_positive, total_neutral, total_negative]
        }
        df2 = pd.DataFrame(data)

        # Create pie chart using Plotly Express
        fig = px.pie(df2, values='Value', names='Sentiment')

        # Display pie chart using Streamlit
        st.plotly_chart(fig)

        st.title('Select an option to view analysis on job positions')
        # Define options for the dropdown menu
        options = ['Positive', 'Neutral', 'Negative']

        # Use the st.selectbox() function to create the dropdown menu
        selected_option = st.selectbox('Select an option:', options)

        job_list = df['Job Title'].tolist()
        positive_list = df['Positive'].tolist()

        if selected_option == 'Positive':
            st.write('Displaying positive scores on job positions')
            # create bar chart using Altair
            chart = alt.Chart(df).mark_bar().encode(
                x='Job Title',
                y='Positive'
            )
            st.altair_chart(chart, use_container_width=True)

        if selected_option == 'Neutral':
            st.write('Displaying neutral scores on job positions')
            # create bar chart using Altair
            chart = alt.Chart(df).mark_bar().encode(
                x='Job Title',
                y='Neutral'
            )
            st.altair_chart(chart, use_container_width=True)

        if selected_option == 'Negative':
            st.write('Displaying negative scores on job positions')
            # create bar chart using Altair
            chart = alt.Chart(df).mark_bar().encode(
                x='Job Title',
                y='Negative'
            )
            st.altair_chart(chart, use_container_width=True)

            # display chart using streamlit
            #st.altair_chart(chart, use_container_width=True)






    #except:
        #st.text("This file is not a CSV file!")
