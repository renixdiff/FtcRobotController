package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Shared hardware for the BioBuzz robot. Call init(hardwareMap) from an OpMode.
 * Config names: frontLeft, frontRight, backLeft, backRight, pollenIntake, scoringGate.
 */
public class BioBuzzHardware {

    public static final double GATE_OPEN = 1.0;
    public static final double GATE_CLOSED = 0.0;

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotor pollenIntake;
    public Servo scoringGate;

    public void init(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        pollenIntake = hardwareMap.get(DcMotor.class, "pollenIntake");
        scoringGate = hardwareMap.get(Servo.class, "scoringGate");

        // Flip these if the robot drives backward or strafes the wrong way.
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        DcMotor[] driveMotors = {frontLeft, frontRight, backLeft, backRight};
        for (DcMotor motor : driveMotors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setPower(0);
        }

        pollenIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        pollenIntake.setPower(0);

        scoringGate.setPosition(GATE_CLOSED);
    }
}
