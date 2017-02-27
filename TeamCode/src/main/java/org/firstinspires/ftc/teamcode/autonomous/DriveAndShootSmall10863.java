package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Hardware10863;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Drive and Shoot - No Cap", group="Linear Opmode")
public class DriveAndShootSmall10863 extends LinearOpMode {

    private Hardware10863 robot = new Hardware10863();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        double forwardSpeed = 1.0;

        while (opModeIsActive() && runtime.seconds() < 10.0) {
            robot.getFrontWheelLeft().setPower(0.0);
            robot.getFrontWheelRight().setPower(0.0);

            robot.getBackWheelLeft().setPower(0.0);
            robot.getBackWheelRight().setPower(0.0);

            robot.getLift().setPower(0.0);
            robot.getFlicker().setPower(0.0);
        }

        while (opModeIsActive() && runtime.seconds() < 11.0) {
            robot.getFrontWheelLeft().setPower(forwardSpeed);
            robot.getFrontWheelRight().setPower(-forwardSpeed);

            robot.getBackWheelLeft().setPower(forwardSpeed);
            robot.getBackWheelRight().setPower(-forwardSpeed);
        }

        while (opModeIsActive() && runtime.seconds() >= 11.0 && runtime.seconds() <= 16.0) {
            robot.getFrontWheelLeft().setPower(0.0);
            robot.getFrontWheelRight().setPower(0.0);

            robot.getBackWheelLeft().setPower(0.0);
            robot.getBackWheelRight().setPower(0.0);

            robot.getLift().setPower(-1.0);
            robot.getFlicker().setPower(-1.0);
        }

        while (opModeIsActive() && runtime.seconds() > 16.0) {
            robot.getFrontWheelLeft().setPower(0.0);
            robot.getFrontWheelRight().setPower(0.0);

            robot.getBackWheelLeft().setPower(0.0);
            robot.getBackWheelRight().setPower(0.0);

            robot.getLift().setPower(0.0);
            robot.getFlicker().setPower(0.0);

            idle();
        }

        robot.getFrontWheelLeft().setPower(0.0);
        robot.getFrontWheelRight().setPower(0.0);

        robot.getBackWheelLeft().setPower(0.0);
        robot.getBackWheelRight().setPower(0.0);

        robot.getLift().setPower(0.0);
        robot.getFlicker().setPower(0.0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
        idle();
    }

}