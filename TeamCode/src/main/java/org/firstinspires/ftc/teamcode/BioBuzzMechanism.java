package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "BIOBUZZ Intake and Outtake", group = "TeleOp")
public class BioBuzzMechanism extends LinearOpMode {

    private DcMotor intakeMotor;
    private DcMotor liftMotor;
    private CRServo scoringRoller;

    @Override
    public void runOpMode() {

        intakeMotor = hardwareMap.get(DcMotor.class, "intake_motor");
        liftMotor = hardwareMap.get(DcMotor.class, "lift_motor");
        scoringRoller = hardwareMap.get(CRServo.class, "scoring_roller");

        // 2. Motor Configuration

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        telemetry.addData("Status", "goon mode on");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // MAIN INTAKE (Gamepad 2 Triggers)

            if (gamepad2.right_trigger > 0.05) {
                intakeMotor.setPower(gamepad2.right_trigger);
            } else if (gamepad2.left_trigger > 0.05) {
                intakeMotor.setPower(-gamepad2.left_trigger);
            } else {
                intakeMotor.setPower(0);
            }

            // OUTTAKE LIFT / SLIDE (Gamepad 2 Left Stick Y)

            double liftPower = -gamepad2.left_stick_y;

            if (Math.abs(liftPower) > 0.05) {
                liftMotor.setPower(liftPower);
            } else {
                liftMotor.setPower(0);
            }

            // SCORING BOX ROLLER (Gamepad 2 Bumpers)

            if (gamepad2.right_bumper) {
                scoringRoller.setPower(1.0);
            } else if (gamepad2.left_bumper) {
                scoringRoller.setPower(-1.0);
            } else {
                scoringRoller.setPower(0.0);
            }

            // TELEMETRY 

            telemetry.addData("Intake Power", intakeMotor.getPower());
            telemetry.addData("Lift Power", liftMotor.getPower());
            telemetry.addData("Scoring Roller Power", scoringRoller.getPower());
            telemetry.update();
        }
    }
}