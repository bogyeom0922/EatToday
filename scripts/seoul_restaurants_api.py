import os
from time import sleep
import time
import re
import json
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.common.exceptions import ElementNotInteractableException
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import pandas as pd
import urllib.request

gu_list = ['마포구','서대문구','은평구','종로구','중구','용산구','성동구','광진구', '동대문구','성북구','강북구','도봉구','노원구','중랑구','강동구','송파구','강남구','서초구','관악구','동작구','영등포구','금천구','구로구','양천구','강서구']


for index, gu_name in enumerate(gu_list):
    fileName = gu_name + '한식.csv'   # csv파일 생성
    file = open(fileName, 'w', encoding='utf-8')
    # 처음에 csv파일에 칼럼명 만들어주기
    file.write("지역" + "|" + "메뉴" + "|" + "한식_식당이름" + "|" + "주소" + "|" + "영업시간" + "|" + "전화번호" + "|" + "대표사진주소" + "|" + "평점" + "|" + "리뷰 수" + "|" + "리뷰" + "|" + "메뉴" + "\n")
    file.close()

    service = Service(executable_path ="C:/Users/82109/AppData/Local/Programs/Python/Python311")
    options = webdriver.ChromeOptions()
    options.add_argument('lang=ko_KR')
    driver = webdriver.Chrome()  # chromedriver 열기
    driver.get('https://map.kakao.com/')  # 주소 가져오기
    search_area = driver.find_element(By.XPATH,'//*[@id="search.keyword.query"]') # 검색 창
    search_area.send_keys(gu_name + ' 한식')  # 검색어 입력
    driver.find_element(By.XPATH,'//*[@id="search.keyword.submit"]').send_keys(Keys.ENTER)  # Enter로 검색
    driver.implicitly_wait(3) # 대기시간 주기
    more_page = driver.find_element(By.ID, "info.search.place.more")
    more_page.send_keys(Keys.ENTER) # 더보기
    time.sleep(1)

    next_btn = driver.find_element(By.ID, "info.search.page.next")
    has_next = "disabled" not in next_btn.get_attribute("class").split(" ")
    Page = 1
    if not has_next:
        has_next = True
    while has_next: # 다음 페이지가 있으면 loop
        file = open(fileName, 'a', encoding='utf-8')
        time.sleep(1)
        page_links = driver.find_elements(By.CSS_SELECTOR, "#info\.search\.page a")
        pages = [link for link in page_links if "HIDDEN" not in link.get_attribute("class").split(" ")]
        for i in range(1, 6):
            xPath = '//*[@id="info.search.page.no' + str(i) + '"]'
            try:
                page = driver.find_element(By.XPATH, xPath)
                page.send_keys(Keys.ENTER)
            except ElementNotInteractableException:
                print('End of Page')
                break;
            sleep(3)
            place_lists = driver.find_elements(By.CSS_SELECTOR,'#info\.search\.place\.list > li')
            for p in place_lists:
                store_html = p.get_attribute('innerHTML')
                store_info = BeautifulSoup(store_html, "html.parser")

                # BeautifulSoup
                place_name = store_info.select('.head_item > .tit_name > .link_name')
                if len(place_name) == 0:
                    continue # 광고
                place_name = store_info.select('.head_item > .tit_name > .link_name')[0].text
                place_address = store_info.select('.info_item > .addr > p')[0].text
                place_hour = store_info.select('.info_item > .openhour > p > a')[0].text
                place_tel = store_info.select('.info_item > .contact > span')[0].text
                food_score = store_info.select('.rating > .score > .num')[0].text
                place_review = store_info.select('.rating > .review')[0].text
                place_review = place_review[3:len(place_review)]
                place_review = int(re.sub(",","",place_review))
                place_review = str(place_review)

                # 매장 사진
                detail = p.find_element(By.CSS_SELECTOR,'div.info_item > div.contact > a.moreview')
                detail.send_keys(Keys.ENTER)

                driver.switch_to.window(driver.window_handles[-1])

                place_photo = ""
                try:
                    photo = driver.find_element(By.CLASS_NAME,'link_photo')
                    photo_url = photo.get_attribute('style')
                    m = re.search(r"url\(\"([^\"]+)\"", photo_url)
                    print(photo_url)
                    if m:
                        place_photo = m.group(1)
                        print(place_photo)
                    else:
                        place_photo = ""
                        print("null")
                except:
                    place_photo = ""


                # 메뉴
                menus_dic = {}
                try:
                    menulist = driver.find_element(By.CLASS_NAME, 'list_menu')
                    menus = menulist.find_elements(By.CLASS_NAME, 'loss_word')
                    menus_price = menulist.find_elements(By.CLASS_NAME, 'price_menu')
                    for a,b in zip(menus,menus_price):
                        menus_dic[a.text] = b.text
                except:
                    menus_dic = {}

                review_dic = {}
                html = driver.page_source
                soup = BeautifulSoup(html, 'html.parser')
                review_lists = soup.select('.list_evaluation > li')
                if len(review_lists) != 0 :
                    for i, review in enumerate(review_lists) :
                        reviews = review.select('.txt_comment > span')[0] # 리뷰
                        user = review.select('.unit_info > a')[0] # 작성자
                        for a,b in zip(user,reviews):
                            review_dic[a.text] = b.text

                driver.close()
                driver.switch_to.window(driver.window_handles[0])
                print(place_name + "\n")

                file.write(gu_name + "|" + "한식" + "|" + place_name + "|" + place_address + "|" + place_hour + "|" + place_tel + "|" + place_photo + "|" + food_score + "|" + place_review + "|")
                count=0
                for i in review_dic:
                    file.write("{}: {}+ ".format(i, review_dic[i]))
                    count+=1
                file.write("|")
                for i in menus_dic:
                    if "추천" not in i:
                        file.write("{}: {}, ".format(i, menus_dic[i]))
                file.write("\n")

            print(i, ' of', ' [ ' , Page, ' ] ')
        next_btn = driver.find_element(By.ID, "info.search.page.next")
        has_next = "disabled" not in next_btn.get_attribute("class").split(" ")
        if not has_next:
            print('Arrow is Disabled')
            driver.close()
            file.close()
            break # 다음 페이지 없으니까 종료
        else: # 다음 페이지 있으면
            Page += 1
            next_btn.send_keys(Keys.ENTER)

    print("End of Crawl")
