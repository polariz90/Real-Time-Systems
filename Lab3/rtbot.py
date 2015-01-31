from pyrobot import *
import sys
import logging
import time
import SocketServer
import Queue

BUFFER_SIZE = 1024
COMMANDS = []
ACK = "ACK\n"
CONFIG_CMD = "__cfg_"

#=============================================================
# put defines here e.x.
# define_name = define value



#=============================================================
# define the Rtbot class to init and start itself

cmd_queue = Queue.Queue()
safety = True

class Rtbot(Create):
    def __init__(self, tty='/dev/ttyUSB0'):
        super(Create, self).__init__(tty)
        self.sci.AddOpcodes(CREATE_OPCODES)
        self.sensors = CreateSensors(self)
        self.safe = False  # Use full mode for control.

    def start(self):
        logging.debug('Starting up the Rtbot.')
        self.SoftReset()
        self.Control()

    def DriveDistance(self, vel, dist):
        count = 0
        self.DriveStraight(vel)
        while(abs(count) < abs(dist)):
           time.sleep(1/4)
           count = count + self.sensors.GetDistance()

        self.Stop()

#=============================================================
#place further functions in the Rtbot class e.x.
# def somefunction(some_argvs):
#   some code

class TCPHandler(SocketServer.BaseRequestHandler):
    def handle(self):
        data = self.request.recv(1024)
        if safety == True:
            cmd_queue.put(data)
    
        print "Received data:", data
        
        return


class RobotController(threading.Thread):
    def __init__(self, robot):
        threading.Thread.__init__(self)
        self.rtbot = robot

    def run(self):
        while True:
                   
            cmd = cmd_queue.get(block=True).strip("\n")
            print "Received cmd:", cmd
            if cmd == "Forward":
                self.rtbot.DriveStraight(300)
            elif cmd == "Backward":
                self.rtbot.DriveStraight(-300)
            elif cmd == "Safety Back":
                self.rtbot.DriveDistance(-200, 100)
                safety = True
            elif cmd == "Right":
                self.rtbot.TurnInPlace(300,'ccw')
            elif cmd == "Left":
                self.rtbot.TurnInPlace(300,'cw')
            elif cmd == "Stop":
                self.rtbot.Stop()
            elif cmd == "Quit":
                break

class SafetyController(threading.Thread):
    def __init__(self, robot):
        threading.Thread.__init__(self)
        self.rtbot = robot

    def run(self):
       
        while True:
            self.rtbot.sensors.GetAll()
           
            if(self.rtbot.sensors.GetBump() or
               self.rtbot.sensors.data['cliff-front-left'] or
               self.rtbot.sensors.data['cliff-front-right'] or
               self.rtbot.sensors.GetAnalogInput() <= 30):
                safety = False
                while(cmd_queue.empty() == False):
                    cmd_queue.get()
                cmd_queue.put("Safety Back")



