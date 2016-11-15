package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Hardware12285;

@TeleOp(name="Custom: 12285 Driver", group="Linear Opmode")
public class Driver12285 extends LinearOpMode {

    Hardware12285 robot = new Hardware12285();

    @Override
    public void runOpMode() throws InterruptedException {
        double left;
        double right;

        robot.init(hardwareMap);

        telemetry.addData("Say", "Hello Driver"); // Let the driver know the robot is waiting
        telemetry.update();

        waitForStart(); // Wait for the driver to press play

        while (opModeIsActive()) {
            left = -gamepad1.left_stick_y;
            right  = -gamepad1.right_stick_y;

            robot.getLeftWheel().setPower(left);
            robot.getRightWheel().setPower(right);

            // Use dpad up and down to move the swiffer sweeper :P
            if (gamepad1.dpad_down)
                robot.getLift().setPower(-1.0);
            else if (gamepad1.dpad_up)
                robot.getLift().setPower(1.0);
            else
                robot.getLift().setPower(0.0);

            // Use gamepad bumpers to move arm up and down
            if (gamepad1.left_bumper)
                robot.getSweeper().setPower(1.0);
            else if (gamepad1.right_bumper)
                robot.getSweeper().setPower(-1.0);
            else
                robot.getSweeper().setPower(0.0);

            robot.waitForTick(40);
        }
    }

}