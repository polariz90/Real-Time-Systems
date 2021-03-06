import sys
from sys import stdin
from rtbot import *
import logging
import time
import SocketServer
import socket
import threading
import Queue
import signal


HOST = ''
PORT = 50000

FORMAT = '%(asctime)s %(levelname)s [%(filename)s:%(lineno)d] %(message)s'
DATE_FORMAT = '%H%M%S'

def main():
    logging.basicConfig(level=logging.INFO, format=FORMAT, datefmt=DATE_FORMAT)
    global robot
    robot = Rtbot(sys.argv[1])
    robot.start()


    server = SocketServer.TCPServer((HOST, PORT), TCPHandler)
    server.allow_reuse_address = True

    server_thread = threading.Thread(target=server.serve_forever)
    server_thread.setDaemon(True)
    server_thread.start()

    robot_cntl = RobotController(robot)
    robot_cntl.start()

    robot_safety = SafetyController(robot)
    robot_safety.setDaemon(True)
    robot_safety.start()


   
	
if __name__ == '__main__':
  main()
