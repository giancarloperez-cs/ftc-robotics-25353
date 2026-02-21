package org.firstinspires.ftc.teamcode.robolutionaries;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="JustMove", group="Competition")
public class MovementPointAuto extends LinearOpMode {

    private DcMotor leftDrive;
    private DcMotor rightDrive;

    @Override
    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");

        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        // drive forward for ~1 second
        leftDrive.setPower(0.4);
        rightDrive.setPower(0.4);
        sleep(1000);

        // stop
        leftDrive.setPower(0.0);
        rightDrive.setPower(0.0);
    }
}