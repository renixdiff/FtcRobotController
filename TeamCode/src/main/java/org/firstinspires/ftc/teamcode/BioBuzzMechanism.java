package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "BIOBUZZ Intake and Outtake", group = "TeleOp")
public class BioBuzzMechanism extends LinearOpMode {

    // Hardware components tailored for Nectar/Pollen collection
    private DcMotor intakeMotor;
    private DcMotor liftMotor;
    private CRServo scoringRoller;

    @Override
    public void runOpMode() {
        // 1. Hardware Mapping (Must match your Driver Station configuration)
        intakeMotor = hardwareMap.get(DcMotor.class, "intake_motor");
        liftMotor = hardwareMap.get(DcMotor.class, "lift_motor");
        scoringRoller = hardwareMap.get(CRServo.class, "scoring_roller");

        // 2. Motor Configuration
        // The lift needs braking to fight gravity, but a roller intake
        // often benefits from coasting (FLOAT) so pieces don't get stuck.
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        telemetry.addData("Status", "goon mode on");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // --- MAIN INTAKE (Gamepad 2 Triggers) ---
            // Pulls Pollen/Nectar off the floor. Triggers allow the driver
            // to run the intake slowly if a piece is jammed.
            if (gamepad2.right_trigger > 0.05) {
                intakeMotor.setPower(gamepad2.right_trigger); // Pull elements in
            } else if (gamepad2.left_trigger > 0.05) {
                intakeMotor.setPower(-gamepad2.left_trigger); // Reverse to clear jams
            } else {
                intakeMotor.setPower(0);
            }

            // --- OUTTAKE LIFT / SLIDE (Gamepad 2 Left Stick Y) ---
            // Raises the scoring box to target heights
            double liftPower = -gamepad2.left_stick_y;

            if (Math.abs(liftPower) > 0.05) {
                liftMotor.setPower(liftPower);
            } else {
                liftMotor.setPower(0);
            }

            // --- SCORING BOX ROLLER (Gamepad 2 Bumpers) ---
            // Uses surgical tubing or wheels on a CR Servo to grab or spit elements
            if (gamepad2.right_bumper) {
                scoringRoller.setPower(1.0); // Eject Nectar/Pollen into targets
            } else if (gamepad2.left_bumper) {
                scoringRoller.setPower(-1.0); // Pull elements from the intake transfer
            } else {
                scoringRoller.setPower(0.0); // Hold elements in the box
            }

            // --- TELEMETRY ---
            telemetry.addData("Intake Power", intakeMotor.getPower());
            telemetry.addData("Lift Power", liftMotor.getPower());
            telemetry.addData("Scoring Roller Power", scoringRoller.getPower());
            telemetry.update();
        }
    }
}