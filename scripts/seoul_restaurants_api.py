import os
from time import sleep
import re
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.common.exceptions import ElementNotInteractableException, NoSuchElementException
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
import time
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

# 모든 데이터를 하나의 파일에 저장
fileName = '디저트.csv'
file = open(fileName, 'w', encoding='utf-8')
file.write("지역|카테고리|디저트_식당이름|주소|영업시간|전화번호|대표사진주소|평점|메뉴\n")
file.close()

gu_list = ['마포구','서대문구','은평구','종로구','중구','용산구','성동구','광진구', '동대문구','성북구','강북구','도봉구','노원구','중랑구','강동구','송파구','강남구','서초구','관악구','동작구','영등포구','금천구','구로구','양천구','강서구']

def restart_driver():
    service = Service(executable_path="C:/chromedriver-win64/chromedriver.exe")
    options = Options()
    options.add_argument('lang=ko_KR')
    driver = webdriver.Chrome(service=service, options=options)
    return driver

def extract_menu(driver):
    menus_dic = {}
    try:
        menulist = WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.CLASS_NAME, 'list_menu'))
        )
        menus = menulist.find_elements(By.CLASS_NAME, 'loss_word')
        menus_price = menulist.find_elements(By.CLASS_NAME, 'price_menu')
        for a, b in zip(menus, menus_price):
            if a.text.strip() and b.text.strip():
                menus_dic[a.text.strip()] = b.text.strip()
    except Exception:
        menus_dic = {}
    return menus_dic

for index, gu_name in enumerate(gu_list):
    driver = restart_driver()
    driver.get('https://map.kakao.com/')
    search_area = driver.find_element(By.XPATH, '//*[@id="search.keyword.query"]')
    search_area.send_keys(gu_name + ' 카페')
    driver.find_element(By.XPATH, '//*[@id="search.keyword.submit"]').send_keys(Keys.ENTER)
    driver.implicitly_wait(3)

    try:
        more_page = driver.find_element(By.ID, "info.search.place.more")
        more_page.send_keys(Keys.ENTER)
        time.sleep(1)
    except NoSuchElementException:
        driver.quit()
        continue

    next_btn = driver.find_element(By.ID, "info.search.page.next")
    has_next = "disabled" not in next_btn.get_attribute("class").split(" ")
    total_count = 0

    while has_next and total_count < 5:
        file = open(fileName, 'a', encoding='utf-8')
        time.sleep(1)
        for i in range(1, 6):
            try:
                page = driver.find_element(By.XPATH, f'//*[@id="info.search.page.no{i}"]')
                page.send_keys(Keys.ENTER)
            except ElementNotInteractableException:
                break
            sleep(3)
            place_lists = driver.find_elements(By.CSS_SELECTOR, '#info\\.search\\.place\\.list > li')
            for p in place_lists:
                store_html = p.get_attribute('innerHTML')
                store_info = BeautifulSoup(store_html, "html.parser")
                place_name = store_info.select('.head_item > .tit_name > .link_name')
                if not place_name:
                    continue
                place_name = place_name[0].text
                place_address = store_info.select('.info_item > .addr > p')[0].text
                place_hour = store_info.select('.info_item > .openhour > p > a')[0].text if store_info.select('.info_item > .openhour > p > a') else ""
                place_tel = store_info.select('.info_item > .contact > span')[0].text if store_info.select('.info_item > .contact > span') else ""
                food_score = store_info.select('.rating > .score > .num')[0].text if store_info.select('.rating > .score > .num') else ""
                if not all([place_name, place_address, food_score]):
                    continue
                place_photo = ""
                try:
                    detail = p.find_element(By.CSS_SELECTOR, 'div.info_item > div.contact > a.moreview')
                    detail.send_keys(Keys.ENTER)
                    driver.switch_to.window(driver.window_handles[-1])
                    menus_dic = extract_menu(driver)
                    WebDriverWait(driver, 10).until(
                        EC.presence_of_element_located((By.CLASS_NAME, 'link_photo'))
                    )
                    photo = driver.find_element(By.CLASS_NAME, 'link_photo')
                    photo_url = photo.get_attribute('style')
                    m = re.search(r'url\((["\']?)(.*?)\1\)', photo_url)
                    if m:
                        place_photo = m.group(2)
                except Exception:
                    place_photo = ""
                driver.close()
                driver.switch_to.window(driver.window_handles[0])
                menu_str = ", ".join(f"{k}: {v}" for k, v in menus_dic.items())
                file.write(f"{gu_name}|디저트|{place_name}|{place_address}|{place_hour}|{place_tel}|{place_photo}|{food_score}|{menu_str}\n")
                total_count += 1
                if total_count >= 5:
                    break
            if total_count >= 5:
                break
        next_btn = driver.find_element(By.ID, "info.search.page.next")
        has_next = "disabled" not in next_btn.get_attribute("class").split(" ")
        if not has_next or total_count >= 5:
            driver.close()
            file.close()
            break
        else:
            next_btn.send_keys(Keys.ENTER)
    print(f"End of Crawl for {gu_name}, Total places fetched: {total_count}")
