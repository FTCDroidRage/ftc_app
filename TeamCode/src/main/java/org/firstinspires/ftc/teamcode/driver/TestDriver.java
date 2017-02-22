package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.TestHardware;

// @TeleOp(name="POC: 10863 Driver", group="Linear Opmode")
public class TestDriver extends LinearOpMode {

    private TestHardware robot = new TestHardware();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        telemetry.addData("Say", "Hello Driver");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double left = Range.clip(gamepad1.left_stick_y, -1, 1);
            double right = Range.clip(-gamepad1.right_stick_y, -1, 1);

            robot.getLeftWheel().setPower(left);
            robot.getRightWheel().setPower(right);

            if (gamepad1.left_bumper) {
                robot.getLeftLaunch().setPower(1.0);
                robot.getRightLaunch().setPower(-1.0);
            } else if (gamepad1.right_bumper) {
                robot.getLeftLaunch().setPower(-1.0);
                robot.getRightLaunch().setPower(1.0);
            } else {
                robot.getLeftLaunch().setPower(0.0);
                robot.getRightLaunch().setPower(0.0);
            }
        }

    }

}