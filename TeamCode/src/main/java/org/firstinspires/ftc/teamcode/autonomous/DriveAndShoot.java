package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Hardware12285;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Drive and Shoot", group="Linear Opmode")
public class DriveAndShoot extends LinearOpMode {

    private Hardware12285 robot = new Hardware12285();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        double forwardSpeed = 1.0;

        while (opModeIsActive() && runtime.seconds() < 1.8) {
            robot.getWheelLeft().setPower(forwardSpeed);
            robot.getWheelRight().setPower(-forwardSpeed);
        }

        while (opModeIsActive() && runtime.seconds() >= 1.8 && runtime.seconds() <= 6.8) {
            robot.getWheelLeft().setPower(0.0);
            robot.getWheelRight().setPower(0.0);

            robot.getPaddle().setPower(-1.0);
            robot.getLift().setPower(1.0);
        }

        while (opModeIsActive() && runtime.seconds() > 6.8 && runtime.seconds() <= 8.0) {
            robot.getWheelLeft().setPower(-forwardSpeed);
            robot.getWheelRight().setPower(forwardSpeed);

            robot.getPaddle().setPower(0.0);
            robot.getLift().setPower(0.0);
        }

        while (opModeIsActive() && runtime.seconds() > 8.3) {
            robot.getWheelLeft().setPower(0.0);
            robot.getWheelRight().setPower(0.0);

            robot.getPaddle().setPower(0.0);
            robot.getLift().setPower(0.0);

            idle();
        }

        robot.getWheelLeft().setPower(0.0);
        robot.getWheelRight().setPower(0.0);

        robot.getPaddle().setPower(0.0);
        robot.getLift().setPower(0.0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
        idle();
    }

}