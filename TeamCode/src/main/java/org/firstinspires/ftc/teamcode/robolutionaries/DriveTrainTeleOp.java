package org.firstinspires.ftc.teamcode.robolutionaries;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Drivetrain Control", group="Drive")
public class DriveTrainTeleOp extends OpMode{

    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor flywheelMotor;  // declaring the flywheel motor so we can use it throughout the class

    private Servo leftOrange;
    private Servo rightOrange;
    private Servo flywheelServo;

    @Override
    // overriding the init method that we inherit from OpMode (in line 9) in order to have our
    // own custom initialization (this will run when we press init on the driver hub)
    public void init(){
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        // we are assigning the left and right drives to the configurations we have
        // within the driver hub, hardwareMap.get gets the motor based on the
        // deviceName we provide after

        flywheelMotor = hardwareMap.get(DcMotor.class, "flywheelMotor");
        // getting the flywheel motor from the configuration file
        // make sure "flywheelMotor" matches exactly what you named it in the Control Hub config

        leftOrange = hardwareMap.get(Servo.class, "leftOrange");
        rightOrange = hardwareMap.get(Servo.class, "rightOrange");
        flywheelServo = hardwareMap.get(Servo.class, "flywheelServo");

        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        // this makes sure the robot will move fine and can turn

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop(){
        // --- DRIVETRAIN CONTROL ---
        double drive = -gamepad1.left_stick_y;  // gets how far forward/backward the stick is pushed
        double turn = gamepad1.right_stick_x;   // gets how far left/right the stick is pushed

        double leftPower = drive + turn;   // combining drive and turn to calculate left side power
        double rightPower = drive - turn;  // right side uses subtraction so turning works correctly

        // this next section makes sure we don't send a value greater than 1.0 or less than -1.0
        // to the motors (motors can only handle -1.0 to 1.0)
        double maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (maxPower > 1.0){
            leftPower /= maxPower;   // scale down left power proportionally
            rightPower /= maxPower;  // scale down right power proportionally
        }

        leftDrive.setPower(leftPower);   // send the calculated power to left motor
        rightDrive.setPower(rightPower); // send the calculated power to right motor

        if(gamepad1.x){
            flywheelMotor.setPower(0.75);
            flywheelServo.setPosition(1.0);
        }
        else{
            flywheelMotor.setPower(0.0);
            flywheelServo.setPosition(0.0);
        }


        if(gamepad1.y){
            leftOrange.setPosition(1.0);
            rightOrange.setPosition(1.0);
        }
        else{
            leftOrange.setPosition(0.5);
            rightOrange.setPosition(0.5);
        }
        // flywheel power
        // this sets the flywheel to spin at 75% power automatically
        // it will keep spinning the entire time the OpMode is running
        // you can change 0.75 to any value from 0.0 (stopped) to 1.0 (full speed)

        // telemetry data (displaying info on Driver Hub screen)
        telemetry.addData("Left Power", "%.2f", leftPower);
        telemetry.addData("Right Power", "%.2f", rightPower);
        telemetry.addData("Drive", "%.2f", drive);
        telemetry.addData("Turn", "%.2f", turn);
        if(gamepad1.x){
            telemetry.addData("Flywheel Motor", "ON");
            telemetry.addData("Flywheel Servo", "POS(1.0)");
        }
        else{
            telemetry.addData("Flywheel Motor", "OFF");
            telemetry.addData("Flywheel Servo", "POS(0.0)");
        }
        if(gamepad1.y){
            telemetry.addData("orangeServos", "ON");
        }
        else{
            telemetry.addData("orangeServos", "OFF");
        }
        telemetry.update();  //  sends all the data to the screen
    }
}