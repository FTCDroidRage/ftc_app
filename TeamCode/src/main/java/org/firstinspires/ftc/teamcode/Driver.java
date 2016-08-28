package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@TeleOp(name="Pushbot: Custom Driver", group="Pushbot")
public class Driver extends LinearOpMode {

    HardwarePushbot robot = new HardwarePushbot();

    private double clawOffset = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        double left;
        double right;
        double max;

        robot.init(hardwareMap);

        telemetry.addData("Say", "Hello Driver"); // Let the driver know the robot is waiting
        telemetry.update();

        waitForStart(); // Wait for the driver to press play

        while (opModeIsActive()) {
            left  = -gamepad1.left_stick_y + gamepad1.right_stick_x;
            right = -gamepad1.left_stick_y - gamepad1.right_stick_x;

            max = Math.max(Math.abs(left), Math.abs(right)); // Normalize

            if (max > 1.0) {
                left /= max;
                right /= max;
            }

            robot.leftMotor.setPower(left);
            robot.rightMotor.setPower(right);

            // Use gamepad left & right Bumpers to open and close the claw
            double CLAW_SPEED = 0.02;

            if (gamepad1.right_bumper)
                clawOffset += CLAW_SPEED;
            else if (gamepad1.left_bumper)
                clawOffset -= CLAW_SPEED;

            clawOffset = Range.clip(clawOffset, -0.5, 0.5);
            robot.leftClaw.setPosition(HardwarePushbot.MID_SERVO + clawOffset);
            robot.rightClaw.setPosition(HardwarePushbot.MID_SERVO - clawOffset);

            // Use gamepad buttons to move arm up (Y) and down (A)
            if (gamepad1.y)
                robot.armMotor.setPower(HardwarePushbot.ARM_UP_POWER);
            else if (gamepad1.a)
                robot.armMotor.setPower(HardwarePushbot.ARM_DOWN_POWER);
            else
                robot.armMotor.setPower(0.0);

            // Send telemetry message to signify robot running;
            telemetry.addData("claw",  "Offset = %.2f", clawOffset);
            telemetry.addData("left",  "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
        }
    }
}
