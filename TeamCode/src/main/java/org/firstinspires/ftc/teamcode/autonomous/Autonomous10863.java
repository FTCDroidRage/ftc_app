package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Hardware10863;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous 10863", group="Linear Opmode")
public class Autonomous10863 extends LinearOpMode {

    private Hardware10863 robot = new Hardware10863();
    private ElapsedTime runtime = new ElapsedTime();

    private static final double FORWARD_SPEED = 0.6;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Ready to run"); // Let the driver know the robot is waiting
        telemetry.update();

        waitForStart(); // Wait for driver to press play

        // Step 1:  Drive forward for 3 seconds

        robot.getLeftWheel().setPower(FORWARD_SPEED);
        robot.getRightWheel().setPower(FORWARD_SPEED);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }

        // Step 2:  Stop and close the claw.

        robot.getLeftWheel().setPower(0);
        robot.getRightWheel().setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
        idle();
    }

}