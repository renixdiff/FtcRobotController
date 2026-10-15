package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Mecanum TeleOp. Gamepad 1: left stick drives/strafes, right stick turns,
 * right bumper = slow mode.
 * Config names: frontLeft, frontRight, backLeft, backRight.
 */
@TeleOp(name = "BioBuzz Driving", group = "BioBuzz")
public class BioBuzzDriving extends LinearOpMode {

    private static final double SLOW_MODE_SCALE = 0.4;
    private static final double STRAFE_CORRECTION = 1.1;

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    @Override
    public void runOpMode() {
        initHardware();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x * STRAFE_CORRECTION;
            double turn = gamepad1.right_stick_x;
            double scale = gamepad1.right_bumper ? SLOW_MODE_SCALE : 1.0;

            setMecanumPower(drive, strafe, turn, scale);

            telemetry.addData("Drive", "%.2f", drive);
            telemetry.addData("Strafe", "%.2f", strafe);
            telemetry.addData("Turn", "%.2f", turn);
            telemetry.addData("Slow mode", gamepad1.right_bumper);
            telemetry.update();
        }

        setMecanumPower(0, 0, 0, 1.0);
    }

    private void initHardware() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        // Flip these if the robot drives backward or strafes the wrong way.
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        DcMotor[] motors = {frontLeft, frontRight, backLeft, backRight};
        for (DcMotor motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    private void setMecanumPower(double drive, double strafe, double turn, double scale) {
        double fl = drive + strafe + turn;
        double fr = drive - strafe - turn;
        double bl = drive - strafe + turn;
        double br = drive + strafe - turn;

        // Scale all wheels together so no power exceeds 1.0 and direction is preserved.
        double max = Math.max(1.0,
                Math.max(Math.max(Math.abs(fl), Math.abs(fr)),
                         Math.max(Math.abs(bl), Math.abs(br))));

        frontLeft.setPower(fl / max * scale);
        frontRight.setPower(fr / max * scale);
        backLeft.setPower(bl / max * scale);
        backRight.setPower(br / max * scale);
    }
}