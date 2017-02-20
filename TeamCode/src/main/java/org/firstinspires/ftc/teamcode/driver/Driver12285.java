package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.Hardware12285;

@TeleOp(name="12285 Driver", group="Linear Opmode")
public class Driver12285 extends LinearOpMode {

    Hardware12285 robot = new Hardware12285();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        telemetry.addData("Say", "Hello Driver");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double left = Range.clip(gamepad1.left_stick_y, -1, 1);
            double right = Range.clip(-gamepad1.right_stick_y, -1, 1);

            robot.getWheelLeft().setPower(left);
            robot.getWheelRight().setPower(right);
            robot.getLeftWheel().setPower(left);
            robot.getRightWheel().setPower(right);

            if (gamepad2.dpad_down) {
                robot.getLift().setPower(-1.0);
            } else if (gamepad2.dpad_up) {
                robot.getLift().setPower(1.0);
                robot.getLift().setPower(0.0);
            } else {
            }

            if (gamepad2.a) {
                robot.getPaddle().setPower(-1.0);
            } else if (gamepad2.y) {
                robot.getPaddle().setPower(1.0);
            } else {
                robot.getPaddle().setPower(0.0);
            }
        }
    }

}