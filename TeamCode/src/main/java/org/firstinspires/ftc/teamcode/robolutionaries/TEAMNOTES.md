Giancarlo - 2/5/26
Today I did a few things:
 - figured out servo/motor configuration
 - connected android studio to driver hub
 - created testing TeleOp (myFirstTeleOp)

Servo / Motor Configuration:
    1. 

Android Studio connection:
    When in android studio, if you want to upload your code to the driver hub you'll
    need to:
    1. Connect to the wifi for it (25353-DS)
    2. In Android Studio, enter the terminal (located at the bottom left corner) and
    enter: "adb connect 192.168.43.1", this will connect Android Studio to the driver
    hub
    3. Check if you're connected by entering the following in to the terminal:
    "adb devices", if something like this comes up, you should be good: 
    "List of devices attached"
    "192.168.43.1:5555       device"
    4. Run your code by clicking the green play button at the top of your screen or by
    pressing control + R at the same time, this will upload the code to the driver hub
    NOTE* press options and O or X at the same time on the controller in order to turn
    it on

IMPORTANT INFO 

mainConfig on Driver Hub

Motors:
Port 0: leftDrive
Port 1: rightDrive 
port 2: flywheelMotor

Servos:
Port 0: flywheelServo
Port 1: leftOrange
Port 2: rightOrange