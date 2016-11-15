package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Hardware12285;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous 12285", group="Linear Opmode")
public class Autonomous12285 extends LinearOpMode {

    private Hardware12285 robot = new Hardware12285();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Ready to run"); // Let the driver know the robot is waiting
        telemetry.update();

        waitForStart(); // Wait for driver to press play

        // Step 1:  Drive forward for 3 seconds

        double forwardSpeed = 0.6;

        robot.getLeftWheel().setPower(forwardSpeed);
        robot.getRightWheel().setPower(forwardSpeed);
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