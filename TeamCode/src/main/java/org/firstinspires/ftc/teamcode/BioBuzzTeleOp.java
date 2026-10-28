package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "BioBuzz: Driver Control", group = "BioBuzz")
public class BioBuzzTeleOp extends LinearOpMode {

    BioBuzzHardware robot = new BioBuzzHardware();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // MECANUM DRIVE

            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            double leftFrontPower = drive + strafe + turn;
            double rightFrontPower = drive - strafe - turn;
            double leftBackPower = drive - strafe + turn;
            double rightBackPower = drive + strafe - turn;

            // Keeps all powers under 1.0
            double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }

            robot.leftFront.setPower(leftFrontPower);
            robot.rightFront.setPower(rightFrontPower);
            robot.leftBack.setPower(leftBackPower);
            robot.rightBack.setPower(rightBackPower);

            // POLLEN INTAKE

            if (gamepad1.right_trigger > 0.05) {
                robot.pollenIntake.setPower(gamepad1.right_trigger);
            } else if (gamepad1.left_trigger > 0.05) {
                robot.pollenIntake.setPower(-gamepad1.left_trigger);
            } else {
                robot.pollenIntake.setPower(0);
            }

            // SCORING GATE

            if (gamepad1.a) {
                robot.scoringGate.setPosition(1.0);
            } else if (gamepad1.b) {
                robot.scoringGate.setPosition(0.0);
            }

            // TELEMETRY

            telemetry.addData("Left Front Power", robot.leftFront.getPower());
            telemetry.addData("Right Front Power", robot.rightFront.getPower());
            telemetry.addData("Left Back Power", robot.leftBack.getPower());
            telemetry.addData("Right Back Power", robot.rightBack.getPower());
            telemetry.addData("Pollen Intake Power", robot.pollenIntake.getPower());
            telemetry.addData("Scoring Gate Position", robot.scoringGate.getPosition());
            telemetry.update();
        }
    }
}
