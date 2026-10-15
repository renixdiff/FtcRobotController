package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class VariablesBiobuzz extends OpMode{
    @Override
    public void init() {
        int teamNumber = 230;
        double motorSpeed = 0.50;
        boolean clawClosed = true;
        String name = "Jd";
        telemetry.addData("Team Number", teamNumber);
        telemetry.addData("Motor Speed", motorSpeed);
        telemetry.addData("Claw Closed", clawClosed);
        telemetry.addData("name", name);

    }
    @Override
    public void loop() {

    }
}