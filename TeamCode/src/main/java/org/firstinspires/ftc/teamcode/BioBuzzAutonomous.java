package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "BioBuzz: Auto Parking", group = "BioBuzz")
public class BioBuzzAutonomous extends LinearOpMode {

    BioBuzzHardware robot = new BioBuzzHardware();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // DRIVE TO PARKING ZONE

        telemetry.addData("Step", "Driving to parking zone");
        telemetry.update();

        robot.leftFront.setPower(0.4);
        robot.leftBack.setPower(0.4);
        robot.rightFront.setPower(0.4);
        robot.rightBack.setPower(0.4);
        sleep(1800);

        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);

        // DROP SCORING ELEMENT

        telemetry.addData("Step", "Opening scoring gate");
        telemetry.update();

        robot.scoringGate.setPosition(1.0);
        sleep(500);

        // STOP

        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
        robot.pollenIntake.setPower(0);

        telemetry.addData("Step", "Parked");
        telemetry.update();
    }
}
