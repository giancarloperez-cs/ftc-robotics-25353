package org.firstinspires.ftc.teamcode.robolutionaries;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class SampleOpMode extends OpMode {

    @Override
    public void init(){
        telemetry.addData("testing-caption", "testing-value");
    }

    @Override
    public void loop(){
         
    }
}
