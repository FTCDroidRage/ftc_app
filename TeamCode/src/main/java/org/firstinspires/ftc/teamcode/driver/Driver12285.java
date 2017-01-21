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
        double left;
        double right;

        robot.init(hardwareMap);

        telemetry.addData("Say", "Hello Driver");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            left = Range.clip(gamepad1.left_stick_y, -1, 1);
            right = Range.clip(-gamepad1.right_stick_y, -1, 1);

            robot.getWheelLeft().setPower(left);
            robot.getWheelRight().setPower(right);

            if (gamepad2.dpad_down) {  // out
                robot.getLauncher1().setPower(-1.0);
                robot.getLauncher2().setPower(1.0);

                robot.getLift().setPower(1.0);
                robot.getSweeper().setPower(1.0);
            } else if (gamepad2.dpad_up) { // in
                robot.getLauncher1().setPower(1.0);
                robot.getLauncher2().setPower(-1.0);

                robot.getLift().setPower(-1.0);
                robot.getSweeper().setPower(-1.0);
            } else {
                robot.getLauncher1().setPower(0.0);
                robot.getLauncher2().setPower(0.0);

                robot.getLift().setPower(0.0);
                robot.getSweeper().setPower(0.0);
            }
        }
    }

}