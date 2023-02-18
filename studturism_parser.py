import math

from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup
import requests
import time
import json


class Parser:

    def __init__(self, url: str):
        self.options = Options()
        self.URL = url
        # self.options.add_experimental_option("detach", True)
        self.options.add_argument('--headless')
        self.driver = webdriver.Chrome(
            options=self.options,
            executable_path=r'res\chromedriver\chromedriver.exe'
        )
        self.driver.maximize_window()

    def get_hostel_pages_count(self):
        try:
            self.driver.get(url='https://студтуризм.рф/search')
            time.sleep(3)
            last_page = self.driver.find_element(by=By.XPATH, value='//div[@class="sc-jRQBWg sc-gKclnd fjAKff iShjjx"]')\
                .find_elements(by=By.TAG_NAME, value='strong')
            group_count = list(map(lambda x: int(x.text), last_page))[-1]
            last_page[-1].click()
            time.sleep(0.5)
            last_page_banners = self.driver.find_elements(by=By.XPATH,
                                                 value='//div[@class="sc-jRQBWg sc-iCfMLu sc-eGRUor fFxOzt ktdbzG eIeixz dorm_wrapper cursor-pointer"]')
            return (group_count-1)*4+len(last_page_banners)
        except Exception as exc:
            print(f'{exc} in function get_hostel_pages_count')

    def parse_hostels(self):
        res_list = []
        n = self.get_hostel_pages_count()
        for page_number in range(1, math.ceil(n/4)+1):
            self.driver.get(url=f'https://студтуризм.рф/search?currentPage={page_number}')
            time.sleep(0.5)
            for banner_number in range(0, 4):
                try:
                    source = EC.presence_of_all_elements_located((By.XPATH,
                                                                  '//div[@class="sc-jRQBWg sc-iCfMLu sc-eGRUor fFxOzt ktdbzG eIeixz dorm_wrapper cursor-pointer"]'))
                    def_btn_box = WebDriverWait(self.driver, 3).until(source)
                    def_btn_box[banner_number].click()
                    time.sleep(0.5)
                    res = self.get_source_hostel(self.driver.page_source)
                    requests.post(url=self.URL, json=res)
                    res_list.append(res)
                    self.driver.back()
                    print(f'Спарсил объявлений {(page_number-1)*4+banner_number+1} из {n}')
                except Exception as exc:
                    print(f'{exc} in function parse_hostels')
        with open('res/hostels.json', 'w', encoding='utf-8') as file:
            json.dump(res_list, file, indent=4, ensure_ascii=False)

    def get_source_hostel(self, html_source):
        result = {
            "hostel": None,
            "university": None,
            "website": None,
            "picture_url": None,
            "region": None,
            "city": None,
            "nutrition": None,
            "address": None,
            "period": None,
            "conditions_for_organizations": None,
            "conditions_for_students": None,
            "organization": None,
            "phone": None,
            "email": None,
            "rates": None,
            "services": None
        }
        element = html_source

        soup = BeautifulSoup(element, 'lxml')
        try:
            arr1 = soup.find_all('div', class_='sc-jRQBWg sc-gjNHFA fFxOzt juCjOR')
            arr2 = soup.find_all('div', class_='sc-jRQBWg iVxfou')
            arr3 = soup.find_all('div', class_='sc-jRQBWg sc-gKclnd sc-cbTzjv fFxOzt hrZfTH eiuHnW')
            tables = soup.find_all('div', class_='sc-jRQBWg sc-jHkVzv fFxOzt jDeAce')
            rates, services = [], []
            rates = tables[0].find_all('div', class_='sc-jRQBWg sc-fmBCVi fFxOzt hbQFwJ table_tr')
            rates = [list(map(lambda x: x.get_text(), i.find_all('div')[:4])) for i in rates]
            result['rates'] = [{'room_type': i[0], 'count': i[1], 'price': i[2], 'description': i[3]} for i in rates]
            try:
                services = tables[1].find_all('div', class_='sc-jRQBWg sc-fmBCVi fFxOzt hbQFwJ table_tr')
                services = [list(map(lambda x: x.get_text(), i.find_all('div'))) for i in services]
            except Exception as exc:
                services = [[None, None, None]]
            finally:
                result['services'] = [{'service': i[0], 'price': i[1], 'description': i[2]} for i in services]
            result['hostel'] = soup.find('div', class_='flex flex-row gap-2 items-center').get_text()
            result['university'] = arr1[0].find('strong').get_text()
            result['website'] = arr1[0].find('a').get('href')
            result['picture_url'] = soup.find('div', class_='sc-jRQBWg sc-gKclnd cvfrKY bFaJuG').find('div').get('src')
            result['region'] = arr1[1].find('strong').get_text()
            result['city'] = arr1[2].find('strong').get_text()
            result['nutrition'] = arr1[3].find('strong').get_text()
            result['address'] = arr1[4].find('strong').get_text()
            result['period'] = arr1[5].find('strong').get_text()
            result['conditions_for_organizations'] = arr2[0].get_text()
            result['conditions_for_students'] = arr2[1].get_text()
            result['organization'] = list(arr3[0])[0].find_all('div')[1].get_text()
            result['phone'] = list(arr3[0])[1].find_all('div')[1].get_text()
            result['email'] = list(arr3[0])[2].find('a').get_text()
        except Exception as exc:
            print(exc)
        finally:
            return result

    def get_event_pages_count(self):
        try:
            self.driver.get(url='https://студтуризм.рф/events-search')
            time.sleep(3)
            last_page = self.driver.find_elements(by=By.XPATH, value='//div[@class="sc-jRQBWg sc-gKclnd fjAKff iShjjx"]/strong')
            group_count = list(map(lambda x: int(x.text), last_page))[-1]
            last_page[-1].click()
            time.sleep(0.5)
            last_page_banners = self.driver.find_elements(by=By.XPATH,
                                                 value='//div[@class="sc-jRQBWg sc-djWRfJ fFxOzt cIaKzG cursor-pointer"]')
            return (group_count-1)*4+len(last_page_banners)
        except Exception as exc:
            print(f'{exc} in function get_event_pages_count')

    def parse_events(self):
        res_list = []
        n = self.get_event_pages_count()
        for page_number in range(1, math.ceil(n/4)+1):
            self.driver.get(url=f'https://студтуризм.рф/events-search?currentPage={page_number}')
            time.sleep(0.5)
            for banner_number in range(0, 4):
                try:
                    source = EC.presence_of_all_elements_located((By.XPATH,
                                                                  '//div[@class="sc-jRQBWg sc-djWRfJ fFxOzt cIaKzG cursor-pointer"]'))
                    def_btn_box = WebDriverWait(self.driver, 3).until(source)
                    def_btn_box[banner_number].click()
                    time.sleep(0.5)
                    link = f'https://студтуризм.рф/events/{self.driver.current_url.split("/")[-1]}'
                    res = self.get_source_event(self.driver.page_source, link)
                    requests.post(url=self.URL, json=res)
                    res_list.append(res)
                    self.driver.back()
                    print(f'Спарсил объявлений {(page_number-1)*4+banner_number+1} из {n}')
                except Exception as exc:
                    print(f'{exc} in function parse_events')
        with open('res/events_from_studturizm.json', 'w', encoding='utf-8') as file:
            json.dump(res_list, file, indent=4, ensure_ascii=False)

    def get_source_event(self, html_source, url):
        result = {
            "track": None,
            "href": url,
            "specialization": None,
            "price": None,
            "picture_url": None,
            "date": None,
            "website": None,
            "organization": None,
            "region": None
        }
        element = html_source

        soup = BeautifulSoup(element, 'lxml')
        try:
            arr1 = soup.find_all('div', class_='sc-jRQBWg sc-eXlEPa iToVtK kORphw')
            result["track"] = arr1[0].text.split()[1]
            result["specialization"] = arr1[1].text.split()[1]
            arr2 = soup.find('div', class_='sc-jRQBWg sc-iCfMLu sc-dwsnSq ddhqsL gSNriE jHpHTT').find_all('strong')
            result["price"] = arr2[0].text
            result["date"] = arr2[1].text
            arr3 = soup.find_all('div', class_='sc-jRQBWg sc-cvZCdy jKUvjy eYhuUU')
            result["region"] = arr3[1].find('strong').text
            result["website"] = arr3[0].find('a').get('href')
            result["organization"] = arr3[0].find('strong').text
            try:
                result["picture_url"] = soup.find('div', class_='sc-jRQBWg sc-gKclnd fRcxwh dKzDP').find('div').get('src')
            except:
                pass
        except Exception as exc:
            print(exc)
        finally:
            return result

    def get_labs_pages_count(self):
        try:
            self.driver.get(url='https://студтуризм.рф/labs-search')
            time.sleep(3)
            last_page = self.driver.find_elements(by=By.XPATH, value='//div[@class="sc-jRQBWg sc-gKclnd fjAKff iShjjx"]/strong')
            group_count = list(map(lambda x: int(x.text), last_page))[-1]
            last_page[-1].click()
            time.sleep(0.5)
            last_page_banners = self.driver.find_elements(by=By.XPATH,
                                                 value='//div[@class="sc-jRQBWg sc-bUbRBg fFxOzt QTIzD cursor-pointer"]')
            return (group_count-1)*4+len(last_page_banners)
        except Exception as exc:
            print(f'{exc} in function get_labs_pages_count')

    def parse_labs(self):
        res_list = []
        n = self.get_labs_pages_count()
        for page_number in range(1, math.ceil(n/4)+1):
            self.driver.get(url=f'https://студтуризм.рф/labs-search?currentPage={page_number}')
            time.sleep(0.5)
            for banner_number in range(0, 4):
                try:
                    source = EC.presence_of_all_elements_located((By.XPATH,
                                                                  '//div[@class="sc-jRQBWg sc-bUbRBg fFxOzt QTIzD cursor-pointer"]'))
                    def_btn_box = WebDriverWait(self.driver, 3).until(source)
                    def_btn_box[banner_number].click()
                    time.sleep(0.4)
                    link = f'https://студтуризм.рф/labs/{self.driver.current_url.split("/")[-1]}'
                    res = self.get_source_labs(self.driver.page_source, link)
                    requests.post(url=self.URL, json=res)
                    res_list.append(res)
                    self.driver.back()
                    print(f'Спарсил объявлений {(page_number-1)*4+banner_number+1} из {n}')
                except Exception as exc:
                    print(f'{exc} in function parse_labs')
        with open('res/science.json', 'w', encoding='utf-8') as file:
            json.dump(res_list, file, indent=4, ensure_ascii=False)

    def get_source_labs(self, html_source, url):
        result = {
            "href": url,
            "lab": None,
            "website": None,
            "city": None,
            "year": None,
            "description": None,
            "description_of_organization": None,
            "person": None,
            "phone": None,
            "email": None,
            "picture_url": None
        }
        element = html_source

        soup = BeautifulSoup(element, 'lxml')
        try:
            arr1 = soup.find_all('div', class_='sc-jRQBWg sc-kHOZwM fFxOzt Hsdil')
            arr2 = soup.find_all('div', class_='sc-jRQBWg iVxfou')
            arr3 = soup.find_all('div', class_='sc-jRQBWg sc-gKclnd sc-jIkXHa fFxOzt hrZfTH FisIM')
            result['lab'] = soup.find('div', class_='sc-jRQBWg sc-kDTinF AfAek bgIwKR').get_text()
            try:
                result['city'] = arr1[1].find('strong').get_text()
            except:
                pass
            try:
                result['website'] = arr1[0].find('a').get('href')
            except:
                pass
            try:
                result['year'] = arr1[2].find('strong').get_text()
            except:
                pass
            try:
                result['description'] = arr1[3].find('strong').get_text()
            except:
                pass
            try:
                result['description_of_organization'] = arr2[0].get_text()
            except:
                pass
            try:
                result['picture_url'] = soup.find('div', class_='sc-jRQBWg sc-gKclnd cvfrKY bFaJuG').find('div').get('src')
            except:
                pass
            result['person'] = list(arr3[0])[0].find_all('div')[1].get_text()
            result['phone'] = list(arr3[0])[1].find_all('div')[1].get_text()
            result['email'] = list(arr3[0])[2].find('a').get_text()
            # Сохраняю коды страничек в отдельную папку (считай, что бэкап)
        except Exception as exc:
            print(exc)
        finally:
            return result

    def get_news_urls(self):
        self.driver.get(url='https://студтуризм.рф/news')
        time.sleep(1)
        while True:
            try:
                self.driver.find_element(by=By.XPATH, value='//button[@class="sc-hBUSln eDQGgy"]').click()
            except Exception as exc:
                print(exc)
                break
            time.sleep(0.2)
        urls = list(map(lambda x: x.get_attribute('href').split('/')[-1], self.driver.find_elements(by=By.XPATH, value='//div[@class="sc-jRQBWg sc-gKclnd eBeaww gchcmS"]/a')))
        return urls

    def parse_news(self):
        counter = 0
        res_list = []
        urls = self.get_news_urls()
        time.sleep(2)
        count = len(urls)
        for page_id in urls:
            counter += 1
            src={
                "href": f'https://студтуризм.рф/news/{page_id}',
                "title": None,
                "picture_url": None,
                "text": None,
                "tags": None
            }
            self.driver.get(url=f'https://студтуризм.рф/news/{page_id}')
            photo = EC.presence_of_element_located((By.XPATH, '//div[@class="sc-jRQBWg sc-cqJhZP fFxOzt gRKDwp"]/div'))
            src["picture_url"] = WebDriverWait(self.driver, 10).until(photo).get_attribute('src')
            src["title"] = self.driver.find_element(by=By.XPATH, value='//div[@class="sc-jRQBWg sc-cqJhZP fFxOzt gRKDwp"]/h2').text
            src["text"] = self.driver.find_element(by=By.XPATH, value='//div[@class="sc-eSxRXt gwBiTd"]').text
            src["tags"] = list(map(lambda x: x.text, self.driver.find_elements(by=By.XPATH, value='//div[@class="sc-hiwPVj kcxzLw"]')))
            res_list.append(src)
            requests.post(url=self.URL, json=src)
            print(f'Спарсил объявлений {counter} из {count}')
        with open('res/news.json', 'w', encoding='utf-8') as file:
            json.dump(res_list, file, indent=4, ensure_ascii=False)

    def parse_all(self):
        pars.parse_events()
        time.sleep(2)
        pars.parse_labs()
        time.sleep(2)
        pars.parse_news()
        time.sleep(2)
        pars.parse_hostels()
        time.sleep(2)


if __name__ == '__main__':
    pars = Parser(...)
    try:
        pars.parse_all()
    except Exception as exc:
        print(exc)
    finally:
        pars.driver.close()
        pars.driver.quit()
