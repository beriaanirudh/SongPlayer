#!/usr/bin/python
import BaseHTTPServer
import urlparse
import subprocess
import Queue
import mps_youtube
import threading
import pycurl
import os
import socket

host = "192.168.1.118"
#host = socket.gethostbyname(socket.gethostname())
port = 8080

global urlList
global play
global lock
urlList = Queue.Queue()
play = False
lock = threading.Lock()

def parseLink(url):
	query = urlparse.urlparse(url).query
	return urlparse.parse_qs(query)

class playSong(threading.Thread):
	def __init__(self):
		print 'playSong called'
		threading.Thread.__init__(self)
	def run(self):
		print 'new playSong'
		global urlList
		global play
		global lock
		while True:
			lock.acquire()
			if play == False:
				print 'Not playing anymore'
				lock.release()
				return
			if urlList.empty() == True :
				print 'list empty()'
				play = False
				lock.release()
				return
			url = urlList.get()
			lock.release()
			mps_youtube.main.play_url(url[0], None)

def stopPlayer():
	os.system('killall mplayer')
	print 'killed mplayer'

def callPlaySong():
	print 'calling playSong thread'
	thread = playSong()
	thread.start()


class MyHandler(BaseHTTPServer.BaseHTTPRequestHandler):
	def do_GET(s):
		global urlList
		global play
		global lock
		s.send_response(200)
		print s.path
		print 'THE PATH IS \n\n',s.path, '\n\nENDS HERE'
		dic = parseLink(s.path)
		if 'halt' in dic:
			lock.acquire()
			if play == True:
				print 'setting play = False and calling stopPLayer'
				play = False
				stopPlayer()
			else:
				print 'setting play = True and calling callPlaySong'
				play = True
				callPlaySong()
			lock.release()
		elif 'getList' in dic:
			print 'sending list of urls'
			s.send_header("Content-type", "text/html")
			s.end_headers()
			for elements in list(urlList.queue):
				print elements[1]
				s.wfile.write(elements[1])
				s.wfile.write('\n\n')
		elif 'playNext' in dic:
		  	print 'playing next flow'
		  	stopPlayer()
		elif 'volume' in dic:
			currentVolume = int(os.popen("osascript -e 'set ovol to output volume of (get volume settings)'").read().split('\n')[0])
			finalVolume = (float(currentVolume)) * 7 / 100
			print str(currentVolume) + ' is current Volume'
			if dic['volume'][0] == '1' and currentVolume < 100:
				 finalVolume += 0.5
			elif dic['volume'][0] == '0' and currentVolume > 0 :
			 	finalVolume -= 0.5
			os.system('osascript -e "set volume %f"' % finalVolume)
			print 'volume to %f"' % finalVolume
		else :
			print 'else loop'
			lock.acquire()
			urlList.put([dic['link'][0], dic['name'][0]])
			if play == False:
				play = True
				callPlaySong()
			lock.release()
		print 'MAIN THREAD FREE'
			

server = BaseHTTPServer.HTTPServer
httpd = server((host, port), MyHandler)
try:
	httpd.serve_forever()
except KeyboardInterrupt:
	pass
httpd.server_close()

