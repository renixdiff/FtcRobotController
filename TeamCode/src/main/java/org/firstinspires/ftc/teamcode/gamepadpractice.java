package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class gamepadpractice extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        // runs 50x a second
        double speedForce = -gamepad1.left_stick_y/ 2.0;
        double diffXJoysticks = gamepad1.left_stick_x - gamepad1.right_stick_x;
        double sumTrigger = gamepad1.left_trigger + gamepad1.right_trigger;

        telemetry.addData("x", gamepad1.left_stick_x);
        telemetry.addData("y", speedForce);
        telemetry.addData("right x", gamepad1.right_stick_x);
        telemetry.addData("right y", gamepad1.right_stick_y);
        telemetry.addData("difference of x", diffXJoysticks);
        telemetry.addData("sum of triggers", sumTrigger);

        telemetry.addData("a button", gamepad1.a);
        telemetry.addData("b button", gamepad1.b);

    }
}
