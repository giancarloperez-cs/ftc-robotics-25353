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

    @Override
    // overriding the init method that we inherit from OpMode in order to have our
    // own custom initialization (this will run when we press init on the driver hub)
    public void init(){
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        // we are assigning the left and right drives to the configurations we have
        // within the driver hub, hardwareMap.get gets the motor based on the
        // deviceName we provide after
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        // this makes sure the robot will move fine
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop(){
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        double leftPower = drive + turn;
        double rightPower = drive - turn;

        double maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (maxPower > 1.0){
            leftPower /= maxPower;
            rightPower /= maxPower;
        }
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        telemetry.addData("Left Power", "%.2f", leftPower);
        telemetry.addData("Right Power", "%.2f", rightPower);
        telemetry.addData("Drive", "%.2f", drive);
        telemetry.addData("Turn", "%.2f", turn);
        telemetry.update();
    }
}
