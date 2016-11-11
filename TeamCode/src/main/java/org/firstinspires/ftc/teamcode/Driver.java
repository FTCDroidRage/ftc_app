package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@TeleOp(name="Pushbot: Custom Driver", group="Pushbot")
public class Driver extends LinearOpMode {

    HardwarePushbot robot = new HardwarePushbot();

    @Override
    public void runOpMode() throws InterruptedException {
        double left;
        double right;
        double max;

        DcMotor lift = hardwareMap.dcMotor.get("lift");
        DcMotor sweeper = hardwareMap.dcMotor.get("sweeper");

        robot.init(hardwareMap);

        telemetry.addData("Say", "Hello Driver"); // Let the driver know the robot is waiting
        telemetry.update();

        waitForStart(); // Wait for the driver to press play

        while (opModeIsActive()) {
            right  = -gamepad1.left_stick_y + gamepad1.right_stick_x;
            left = -gamepad1.left_stick_y - gamepad1.right_stick_x;

            max = Math.max(Math.abs(left), Math.abs(right)); // Normalize

            if (max > 1.0) {
                left /= max;
                right /= max;
            }

            robot.leftMotor.setPower(left);
            robot.rightMotor.setPower(right);

            lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            sweeper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // Use dpad up and down to move the swiffer sweeper :P
            if (gamepad1.dpad_down)
                lift.setPower(-1.0);
            else if (gamepad1.dpad_up)
                lift.setPower(1.0);
            else
                lift.setPower(0.0);

            // Use gamepad bumpers to move arm up and down
            if (gamepad1.left_bumper)
                sweeper.setPower(1.0);
            else if (gamepad1.right_bumper)
                sweeper.setPower(-1.0);
            else
                sweeper.setPower(0.0);

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
        }
    }
}
