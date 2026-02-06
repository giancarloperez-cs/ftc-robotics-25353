package org.firstinspires.ftc.teamcode.robolutionaries;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Auto Test without Gamepad", group="Testing")
public class myFirstTeleOp extends OpMode {

    private DcMotor testMotor;
    private Servo testServo;
    private double timeElapsed = 0;

    @Override
    public void init() {
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
        testServo = hardwareMap.get(Servo.class, "testServo");

        telemetry.addData("Status", "Ready to auto-test!");
        telemetry.addData("Instructions", "Press START to begin");
        telemetry.update();
    }

    @Override
    public void loop() {
        timeElapsed += 0.05; // Rough time tracking

        // Motor cycles: 2 sec forward, 2 sec stopped, 2 sec backward, repeat
        int cycle = ((int)timeElapsed / 2) % 3;

        if (cycle == 0) {
            testMotor.setPower(0.5);  // Forward at 50% power
            telemetry.addData("Motor", "FORWARD (50%)");
        } else if (cycle == 1) {
            testMotor.setPower(0.0);  // Stopped
            telemetry.addData("Motor", "STOPPED");
        } else {
            testMotor.setPower(-0.5); // Backward at 50% power
            telemetry.addData("Motor", "BACKWARD (50%)");
        }

        // Servo cycles: moves between 0.0 and 1.0 every 3 seconds
        int servoCycle = ((int)timeElapsed / 3) % 2;
        if (servoCycle == 0) {
            testServo.setPosition(0.0);
            telemetry.addData("Servo", "Position 0.0");
        } else {
            testServo.setPosition(1.0);
            telemetry.addData("Servo", "Position 1.0");
        }

        telemetry.addData("Time", "%.1f seconds", timeElapsed);
        telemetry.addData("", "Watch your motor and servo move!");
        telemetry.update();
    }
}