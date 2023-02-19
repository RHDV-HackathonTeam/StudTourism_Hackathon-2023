from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from bs4 import BeautifulSoup
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time
import json


class YaAfisha:

    def __init__(self):
        self.options = Options()
        self.section_names = {'all-events-concert': 'Концерты', 'spectacle': 'Театр', 'all-events-sport': 'Спорт',
                              'all-events-art': 'Выставки', 'all-events-excursions': 'Экскурсии', 'all-events-pushkin-card': 'Пушкинская карта'}
        # self.options.add_experimental_option("detach", True)
        self.options.add_argument('--headless')
        self.driver = webdriver.Chrome(
            options=self.options,
            executable_path=r'res\chromedriver\chromedriver.exe'
        )
        self.driver.maximize_window()

    def get_city_names(self):
        flag = True
        while flag:
            try:
                self.driver.get(url='https://afisha.yandex.ru/moscow')
                time.sleep(6)
                self.driver.find_element(by=By.XPATH, value='//button[@class="Button-sc-64t7sv-1 ijxbDX"]').click()
                flag = False
            except:
                pass
            time.sleep(2)
        cities_name_keys = ['moscow']+list(map(lambda x: x.get_attribute('href').split('?')[0].split('/')[-1],
                                                            self.driver.find_elements(by=By.XPATH, value='//div[@class="CityList-rt6wcj-3 fiqasZ"]/a')))
        cities_name_values = ['Москва'] + list(map(lambda x: x.text,
                                                 self.driver.find_elements(by=By.XPATH,
                                                                           value='//div[@class="CityList-rt6wcj-3 fiqasZ"]/a')))

        return dict(zip(cities_name_keys, cities_name_values))

    def parse_activities(self):
        city_names = []
        with open('res/cities.txt', 'r', encoding='utf-8') as file:
            arr = list(map(lambda x: x.replace('\n', ''), file.readlines()))
            for element in self.get_city_names().items():
                if element[1] in arr:
                    city_names.append(element)
        counter = 0
        sections_list = list(self.section_names.items())
        res_list = []
        for section in sections_list:
            for city_name in city_names:
                counter += 1
                link = f'https://afisha.yandex.ru/{city_name[0]}/selections/{section[0]}'
                self.driver.get(url=link)
                time.sleep(2)
                banners = BeautifulSoup(self.driver.page_source, 'lxml').find_all('div', class_='event events-list__item yandex-sans')[:3]
                for banner in banners:
                    try:
                        picture_url = banner.find('img').get('src')
                        price = None
                        try:
                            if section == 'all-events-sport':
                                price = banner.find('div', class_='Bar-njdnt8-2 Footer-njdnt8-4 gbSccZ cGoOoa').get_text().replace(u'\xa0', ' ')
                            else:
                                price = banner.find('div', class_='Bar-njdnt8-2 Footer-njdnt8-4 eeLPDV hgmsQB').get_text().replace(u'\xa0', ' ')
                        except:
                            pass
                        link = f'https://afisha.yandex.ru{banner.find("a").get("href")}'
                        dates = None
                        try:
                            dates = banner.find('li', class_='DetailsItem-fq4hbj-1 ZwxkD').get_text()
                        except:
                            pass
                        res = {}
                        res = self.get_concert_source(link, city_name[1], price, picture_url, dates, section[1])
                        res_list.append(res)
                    except Exception as exc:
                        print(exc)
                print(f'Спарсил {counter} из {len(city_names)*len(sections_list)}')
        with open(f'res/events/ya_afisha.json', 'w', encoding='utf-8') as file:
            json.dump(res_list, file, indent=4, ensure_ascii=False)

    def get_concert_source(self, url, city_name, price, picture_url, dates, section):
        result = {
            "href": url,
            "address": None,
            "section": section,
            "city": city_name,
            "title": None,
            "picture_url": picture_url,
            "tags": None,
            "phone": None,
            "dates": dates,
            "description": None,
            "price": price
        }
        self.driver.get(url=url)
        time.sleep(1)
        result['title'] = self.driver.find_element(by=By.XPATH, value='//div[@class="event-concert-description__title-info"]').text
        try:
            result['address'] = self.driver.find_element(by=By.XPATH, value='//span[@class="place__address"]').text
        except:
            pass
        try:
            result['phone'] = self.driver.find_element(by=By.XPATH, value='//div[@class="place__phone"]').text
        except:
            pass
        try:
            result['description'] = self.driver.find_element(by=By.XPATH, value='//div[@class="concert-description__text-wrap"]').text
        except:
            pass
        try:
            result['tags'] = ' '.join(list(map(lambda x: x.text, self.driver.find_elements(by=By.XPATH, value='//li[@class="tags__item"]'))))
        except:
            pass
        return result


if __name__ == '__main__':
    pars = YaAfisha()
    try:
        pars.parse_activities()
    except Exception as exc:
        print(exc)
    finally:
        pars.driver.close()
        pars.driver.quit()