package org.firstinspires.ftc.teamcode.robolutionaries;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="Sample Auto", group="Competition")
public class SampleAuto extends LinearOpMode {

    // TUNING CONSTANTS — adjust these based on practice runs
    static final double DRIVE_POWER        = 0.5;
    static final double TURN_POWER         = 0.5;
    static final double FLYWHEEL_POWER     = 1.0;
    static final double INDEXER_POWER      = 1.0;

    static final double SPINUP_TIME        = 1.5;  // seconds to spin up flywheel before feeding
    static final double SHOOT_TIME         = 5.0;  // seconds to run indexers (shooting duration)
    static final double LEAVE_TIME         = 0.8;  // seconds to drive forward off launch line
    static final double TURN_TIME          = 0.5;  // seconds to turn toward base
    static final double DRIVE_TO_BASE_TIME = 2.5;  // seconds to drive to base
    // ============================================================

    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor flywheelMotor;
    private CRServo leftOrange;
    private CRServo rightOrange;
    private CRServo flywheelServo;

    private enum Side { RED, BLUE }
    private Side side = Side.BLUE;

    @Override
    public void runOpMode() {

        // Hardware init
        leftDrive     = hardwareMap.get(DcMotor.class,  "leftDrive");
        rightDrive    = hardwareMap.get(DcMotor.class,  "rightDrive");
        flywheelMotor = hardwareMap.get(DcMotor.class,  "flywheelMotor");
        leftOrange    = hardwareMap.get(CRServo.class,  "leftOrange");
        rightOrange   = hardwareMap.get(CRServo.class,  "rightOrange");
        flywheelServo = hardwareMap.get(CRServo.class,  "flywheelServo");

        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Side selection before match starts
        telemetry.addData("Press X", "for BLUE");
        telemetry.addData("Press B", "for RED");
        telemetry.update();

        while (!isStarted() && !isStopRequested()) {
            if (gamepad1.x) side = Side.BLUE;
            if (gamepad1.b) side = Side.RED;

            telemetry.addData("Press X", "for BLUE");
            telemetry.addData("Press B", "for RED");
            telemetry.addData("Selected Side", side);
            telemetry.update();
        }

        waitForStart();
        if (isStopRequested()) return;

        // STEP 1: Spin up flywheel while driving forward off launch line
        flywheelMotor.setPower(FLYWHEEL_POWER);
        drive(DRIVE_POWER, DRIVE_POWER, LEAVE_TIME);

        // STEP 2: Wait for flywheel to reach full speed
        sleep((long)(SPINUP_TIME * 1000));

        // STEP 3: Shoot preloaded artifacts
        flywheelServo.setPower(INDEXER_POWER);
        leftOrange.setPower(INDEXER_POWER);
        rightOrange.setPower(-INDEXER_POWER);
        sleep((long)(SHOOT_TIME * 1000));

        // Stop shooter
        stopShooter();

        // STEP 4: Turn toward base (direction depends on alliance side)
        if (side == Side.BLUE) {
            drive(TURN_POWER, -TURN_POWER, TURN_TIME);  // turn left for blue
        } else {
            drive(-TURN_POWER, TURN_POWER, TURN_TIME);  // turn right for red
        }

        // STEP 5: Drive to base
        drive(DRIVE_POWER, DRIVE_POWER, DRIVE_TO_BASE_TIME);
    }

    private void drive(double leftPower, double rightPower, double seconds) {
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        sleep((long)(seconds * 1000));
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }

    private void stopShooter() {
        flywheelMotor.setPower(0);
        flywheelServo.setPower(0);
        leftOrange.setPower(0);
        rightOrange.setPower(0);
    }
}