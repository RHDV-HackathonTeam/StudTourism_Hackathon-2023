import json
import requests
import time

url = 'https://66ec-2a00-1fa0-4a46-2469-7a30-9ca6-609b-b91.eu.ngrok.io/'

def post_science():
    with open('res/science.json', 'r', encoding='utf-8') as file:
        a = json.load(file)
        for i in a:
            requests.post(url=f'{url}science', json=i)
            time.sleep(0.05)

def post_news():
    with open('res/news.json', 'r', encoding='utf-8') as file:
        a = json.load(file)
        for i in a:
            requests.post(url=f'{url}news', json=i)
            time.sleep(0.05)

def post_hostels():
    with open('res/hostels.json', 'r', encoding='utf-8') as file:
        a = json.load(file)
        for i in a:
            requests.post(url=f'{url}hotels', json=i)
            time.sleep(0.05)

def post_events():
    with open('res/events.json', 'r', encoding='utf-8') as file:
        a = json.load(file)
        for i in a:
            requests.post(url=f'{url}events', json=i)
            time.sleep(0.05)

def post_ya_afisha():
    with open('res/ya_afisha.json', 'r', encoding='utf-8') as file:
        a = json.load(file)
        for i in a:
            requests.post(url=f'{url}ya_afisha', json=i)
            time.sleep(0.05)


if __name__ == '__main__':
    pass