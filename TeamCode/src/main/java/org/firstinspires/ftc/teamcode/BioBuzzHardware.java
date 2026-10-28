package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Central hardware map for the BioBuzz robot.
 *
 * Usage in an OpMode:
 *     BioBuzzHardware robot = new BioBuzzHardware();
 *     robot.init(hardwareMap);
 *
 * Robot configuration names (must match the Driver Station config exactly):
 *     Motors: leftFront, leftBack, rightFront, rightBack, pollenIntake
 *     Servo:  scoringGate
 */
public class BioBuzzHardware {

    // SERVO POSITIONS

    public static final double GATE_OPEN = 1.0;
    public static final double GATE_CLOSED = 0.0;

    // MECANUM DRIVETRAIN MOTORS

    public DcMotor leftFront;
    public DcMotor leftBack;
    public DcMotor rightFront;
    public DcMotor rightBack;

    // MECHANISMS

    public DcMotor pollenIntake;
    public Servo scoringGate;

    // Saved reference to the hardware map passed in from the OpMode.
    private HardwareMap hwMap;

    /**
     * Links every device to the robot configuration and sets its starting state.
     * Call this once from runOpMode() before waitForStart().
     */
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        // 1. Link devices to their configuration names

        leftFront = hwMap.get(DcMotor.class, "leftFront");
        leftBack = hwMap.get(DcMotor.class, "leftBack");
        rightFront = hwMap.get(DcMotor.class, "rightFront");
        rightBack = hwMap.get(DcMotor.class, "rightBack");
        pollenIntake = hwMap.get(DcMotor.class, "pollenIntake");
        scoringGate = hwMap.get(Servo.class, "scoringGate");

        // 2. Drive motor directions
        // Right side motors are mounted mirrored, so reverse them so positive power
        // drives every wheel forward. Swap these if the robot drives backward.

        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        // 3. Drive motor behavior
        // BRAKE makes the robot stop quickly when the sticks are released.

        DcMotor[] driveMotors = {leftFront, leftBack, rightFront, rightBack};
        for (DcMotor motor : driveMotors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setPower(0);
        }

        // 4. Mechanism starting state

        pollenIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        pollenIntake.setPower(0);

        scoringGate.setPosition(GATE_CLOSED);
    }
}
