# import the libraries
import time
import csv

from bs4 import BeautifulSoup
from urllib.request import Request, urlopen


# create a function to scrape any Glassdoor company review page
def review_scraper(url):
    # scraping the web page content
    hdr = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36'}
    req = Request(url, headers=hdr)
    page = urlopen(req)
    #time.sleep(10)
    soup = BeautifulSoup(page, "html.parser")


    name = soup.find_all('h2', {'class': 'my-0 css-16jzkgq'})
    print(name[0].text)

    summary = soup.find_all('span', {'data-test': 'employerDescription'})
    print(summary[0].text)

    details = soup.find_all('div', {'class': 'css-19hiur5 css-dwl48b css-1cnqmgc'})
    print(details[0].text)  # Location
    print(details[1].text)  # no. of employees
    print(details[2].text)  # Date
    print(details[3].text)  # Company Type
    print(details[4].text)  # Revenue

    industry = soup.find_all('a', {'class': 'css-1cnqmgc'})
    print(industry[1].text)

    rating = soup.find_all('div', {'class': 'mr-xsm css-1c86vvj eky1qiu0'})
    print(rating[0].text)

    competitors = soup.find('p', {'class': 'd-flex flex-column flex-md-row css-dwl48b css-1cnqmgc',
                                  'data-test': 'employerCompetitors'})
    print(competitors.span.text)

    # print(competitors)

    pro = soup.find_all('span', {'class': 'css-dwl48b css-1cnqmgc', 'data-test': ""})
    print(pro[0].text)

    con = soup.find_all('span', {'class': 'css-dwl48b css-1cnqmgc', 'data-test': ""})
    print(con[1].text)

    data = [
            {'Name':name[0].text,'Summary':summary[0].text,'Location':details[0].text,'Employees':details[1].text,'Founded':details[2].text,
             'Type':details[3].text,'Revenue':details[4].text,'Industry':industry[1].text,'Rating':rating[0].text,'Competitors':competitors.span.text,
             'Pro':pro[0].text,'Con':con[1].text}
           ]

    with open("data.csv",mode="a")as csvfile:
        fieldnames = data[0].keys()
        writer =csv.DictWriter(csvfile,fieldnames=fieldnames)
        for row in data:
            writer.writerow(row)

    # write the data to CSV
    #with open('output.csv', 'w', newline='') as csvfile:
        #writer = csv.writer(csvfile)
        #writer.writerow(['Name', 'Summary', 'Location', 'Employees', 'Founded','Type','Revenue','Industry','Rating','Competitors','Pro','Con'])  # add column headers if desired
        #for data in data_list:
            #writer.writerows([data])

url_root1 = 'https://www.glassdoor.com/Overview/Working-at-Amazon-EI_IE6036.11,17.htm'
url_root2 = 'https://www.glassdoor.com/Overview/Working-at-Deloitte-EI_IE2763.11,19.htm'
url_root3 = 'https://www.glassdoor.com/Overview/Working-at-McDonald-s-EI_IE432.11,21.htm'
url_root4 = 'https://www.glassdoor.sg/Reviews/AmerisourceBergen-Reviews-E2703.htm'
review_scraper(url_root4)
