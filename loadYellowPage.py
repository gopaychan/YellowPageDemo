from urllib.request import urlopen
import datetime
import sqlite3
import json
import threading

url = 'http://zhwnlapi.etouch.cn/Ecalender/openapi/huangli/{}?key=ePOevdSflpi43wdL9lk3mfG7l4xkT3Okx'

def loadYellowPage():
	startDate = datetime.datetime(1970,1,1)
	endDate = datetime.datetime(2040,12,31)
	maxDateDelta = (endDate - startDate).days

	conn = sqlite3.connect('yellow_page.db')
	cursor = conn.cursor()
	cursor.execute('drop table if exists yellowPage')
	cursor.execute('create table if not exists yellowPage (_id int auto increament primary key,desc Text,status Text, pengzu Text, wuxing Text, xingxiu Text, cong Text, nongli Text, date Text, tgdz Text, xingqi Text, shichen Text,ji_new Text,ji_old Text,jieri Text,jieqi Text,taishen Text,fanwei Text,yi_new Text,yi_old Text)')

	for dateDelta in range(0,maxDateDelta + 1):
	# for dateDelta in range(0,10):
		date_ = (startDate + datetime.timedelta(dateDelta)).strftime("%Y-%m-%d")
		print(date_)
		with urlopen(url.format(date_)) as response:
			for line in response:
				line = line.decode("utf-8")
				jsonObject = json.loads(line)
				desc = jsonObject["desc"]
				status = jsonObject["status"]

				data = jsonObject["data"]
				pengzu = data["pengzu"]
				wuxing = data["wuxing"]
				xingxiu = data["xingxiu"]
				cong = data["cong"]
				nongli = data["nongli"]
				date = data["date"]
				tgdz = data["tgdz"]
				xingqi = data["xingqi"]

				shichen_ = data["shichen"]
				shichen = ""
				for index in range(0,len(shichen_)):
					shichen = shichen + shichen_[index] + "\n"
				shichen = shichen[0:len(shichen)-1]

				ji = data["ji"]
				ji_new = ""
				ji_old = ""
				for jiIndex in range(0,len(ji)):
					ji_new = ji_new + ji[jiIndex]["new"] + "\n"
					ji_old = ji_old + ji[jiIndex]["old"] + "\n"
				ji_new = ji_new[0:len(ji_new)-1]
				ji_old = ji_old[0:len(ji_old)-1]

				jieri = data["jieri"]
				jieqi = data["jieqi"]
				taishen = data["taishen"]
				fanwei = data["fanwei"]

				yi = data["yi"]
				yi_new = ""
				yi_old = ""
				for yiIndex in range(0,len(yi)):
					yi_new = yi_new + yi[yiIndex]["new"] + "\n"
					yi_old = yi_old + yi[yiIndex]["old"] + "\n"
				yi_new = yi_new[0:len(yi_new)-1]
				yi_old = yi_old[0:len(yi_old)-1]

				insertStr = "'{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}'".format(dateDelta,desc,status,pengzu,wuxing,xingxiu,cong,nongli,date,tgdz,xingqi,shichen,ji_new,ji_old,jieri,jieqi,taishen,fanwei,yi_new,yi_old)
				cursor.execute("insert into yellowPage values({})".format(insertStr))
				conn.commit()

	cursor.close()
	conn.close()

class LoadYellowPageThread(threading.Thread):
	def __init__(self):
		threading.Thread.__init__(self)

	def run(self):
		loadYellowPage()


if __name__ == "__main__":
	background = LoadYellowPageThread()
	background.start()
	background.join()

