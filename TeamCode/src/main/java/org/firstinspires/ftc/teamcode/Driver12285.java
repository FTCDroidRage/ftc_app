package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@TeleOp(name="Pushbot: 12285 Driver", group="Pushbot")
public class Driver12285 extends LinearOpMode {

    HardwarePushbot robot = new HardwarePushbot();

    @Override
    public void runOpMode() throws InterruptedException {
        double left;
        double right;

        DcMotor lift = hardwareMap.dcMotor.get("lift");
        DcMotor sweeper = hardwareMap.dcMotor.get("sweeper");

        robot.init(hardwareMap);

        telemetry.addData("Say", "Hello Driver"); // Let the driver know the robot is waiting
        telemetry.update();

        waitForStart(); // Wait for the driver to press play

        while (opModeIsActive()) {
            // this is now tank FUCKING DRIVE
            right  = -gamepad1.right_stick_y;
            left = -gamepad1.left_stick_y;

            right = Range.clip(right, -1, 1);
            left = Range.clip(left, -1, 1);

            right = (float) scaleInput(right);
            left = (float) scaleInput(left);

            lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            sweeper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            robot.leftMotor.setPower(left);
            robot.rightMotor.setPower(right);

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

    private double scaleInput(double dVal) {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24, 0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        int index = (int)(dVal * 16.0);

        if (index < 0) {
            index = -index;
        }

        if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;

        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }

}
