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

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        double forwardSpeed = 1.0;

        robot.getWheelLeft().setPower(forwardSpeed);
        robot.getWheelRight().setPower(forwardSpeed);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 4.5)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }

        robot.getWheelLeft().setPower(0);
        robot.getWheelRight().setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
        idle();
    }

}