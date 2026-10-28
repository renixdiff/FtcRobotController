package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Driver-control TeleOp using BioBuzzHardware. All controls on gamepad 1:
 * left stick = drive/strafe, right stick X = turn,
 * right trigger = intake, left trigger = eject,
 * A = open scoring gate, B = close scoring gate.
 */
@TeleOp(name = "BioBuzz: Driver Control", group = "BioBuzz")
public class BioBuzzTeleOp extends LinearOpMode {

    private static final double TRIGGER_DEADZONE = 0.05;

    private final BioBuzzHardware robot = new BioBuzzHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // MECANUM DRIVE (stick Y is negative when pushed forward, so invert it)

            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            double fl = drive + strafe + turn;
            double fr = drive - strafe - turn;
            double bl = drive - strafe + turn;
            double br = drive + strafe - turn;

            // Scale all wheels together so no power exceeds 1.0 and direction is preserved.
            double max = Math.max(1.0,
                    Math.max(Math.max(Math.abs(fl), Math.abs(fr)),
                             Math.max(Math.abs(bl), Math.abs(br))));

            robot.frontLeft.setPower(fl / max);
            robot.frontRight.setPower(fr / max);
            robot.backLeft.setPower(bl / max);
            robot.backRight.setPower(br / max);

            // POLLEN INTAKE (right trigger collects, left trigger ejects)

            if (gamepad1.right_trigger > TRIGGER_DEADZONE) {
                robot.pollenIntake.setPower(gamepad1.right_trigger);
            } else if (gamepad1.left_trigger > TRIGGER_DEADZONE) {
                robot.pollenIntake.setPower(-gamepad1.left_trigger);
            } else {
                robot.pollenIntake.setPower(0);
            }

            // SCORING GATE (A opens, B closes)

            if (gamepad1.a) {
                robot.scoringGate.setPosition(BioBuzzHardware.GATE_OPEN);
            } else if (gamepad1.b) {
                robot.scoringGate.setPosition(BioBuzzHardware.GATE_CLOSED);
            }

            // TELEMETRY

            telemetry.addData("Front Left Power", "%.2f", robot.frontLeft.getPower());
            telemetry.addData("Front Right Power", "%.2f", robot.frontRight.getPower());
            telemetry.addData("Back Left Power", "%.2f", robot.backLeft.getPower());
            telemetry.addData("Back Right Power", "%.2f", robot.backRight.getPower());
            telemetry.addData("Pollen Intake Power", "%.2f", robot.pollenIntake.getPower());
            telemetry.addData("Scoring Gate Position", "%.2f", robot.scoringGate.getPosition());
            telemetry.update();
        }

        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
        robot.pollenIntake.setPower(0);
    }
}
