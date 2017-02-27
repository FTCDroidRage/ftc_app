package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.Hardware10863;

@TeleOp(name="10863 Driver", group="Linear Opmode")
public class Driver10863 extends LinearOpMode {

    Hardware10863 robot = new Hardware10863();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        telemetry.addData("Say", "Hello Driver");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double left = Range.clip(gamepad1.left_stick_y, -1, 1) * robot.getDirection() * robot.getMulti();
            double right = Range.clip(-gamepad1.right_stick_y, -1, 1) * robot.getDirection() * robot.getMulti();

            robot.getFrontWheelLeft().setPower(left);
            robot.getFrontWheelRight().setPower(right);
            robot.getBackWheelLeft().setPower(left);
            robot.getBackWheelRight().setPower(right);

            if (gamepad2.dpad_down) {
                robot.getLift().setPower(1.0);
            } else if (gamepad2.dpad_up) {
                robot.getLift().setPower(-1.0);
            } else {
                robot.getLift().setPower(0.0);
            }

            if (gamepad2.a) {
                robot.getFlicker().setPower(-1.0);
            } else if (gamepad2.y) {
                robot.getFlicker().setPower(1.0);
            } else {
                robot.getFlicker().setPower(0.0);
            }

            if (gamepad1.right_bumper) {
                robot.setDirection(1.0);
            } else if (gamepad1.left_bumper) {
                robot.setDirection(-1.0);
            }

            if (gamepad1.a) {
                robot.setMulti(0.25);
            } else if (gamepad1.x) {
                robot.setMulti(0.5);
            }

            if (gamepad1.b) {
                robot.setMulti(0.75);
            } else if (gamepad1.y) {
                robot.setMulti(1.0);
            }
        }
    }

}