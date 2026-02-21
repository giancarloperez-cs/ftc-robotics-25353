package org.firstinspires.ftc.teamcode.robolutionaries;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="test solo", group="Testing")
public class TestSoloTeleOp extends OpMode {

    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor flywheelMotor;

    private CRServo leftOrange;
    private CRServo rightOrange;
    private CRServo flywheelServo;

    @Override
    public void init() {
        leftDrive    = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive   = hardwareMap.get(DcMotor.class, "rightDrive");
        flywheelMotor = hardwareMap.get(DcMotor.class, "flywheelMotor");
        leftOrange   = hardwareMap.get(CRServo.class, "leftOrange");
        rightOrange  = hardwareMap.get(CRServo.class, "rightOrange");
        flywheelServo = hardwareMap.get(CRServo.class, "flywheelServo");

        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;

        double leftPower  = drive + turn;
        double rightPower = drive - turn;

        double maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (maxPower > 1.0) {
            leftPower  /= maxPower;
            rightPower /= maxPower;
        }

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);

        // --- Shooting ---
        // Hold X to spin up the flywheel (warm it up before shooting)
        // Hold Y to feed artifacts into the shooter
        boolean spinUp = gamepad1.x;
        boolean feed   = gamepad1.y;

        flywheelMotor.setPower(spinUp || feed ? 1.0 : 0.0);
        flywheelServo.setPower(feed ? 1.0 : 0.0);
        leftOrange.setPower(feed ?  1.0 : 0.0);
        rightOrange.setPower(feed ? -1.0 : 0.0);

        // --- Telemetry ---
        telemetry.addData("Drive",        "%.2f", drive);
        telemetry.addData("Turn",         "%.2f", turn);
        telemetry.addData("Left Power",   "%.2f", leftPower);
        telemetry.addData("Right Power",  "%.2f", rightPower);
        telemetry.addData("Flywheel",     spinUp ? "SPINNING UP" : feed ? "SHOOTING" : "OFF");
        telemetry.addData("Indexers",     feed   ? "ON" : "OFF");
        telemetry.update();
    }
}