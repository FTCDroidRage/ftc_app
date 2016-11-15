package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.Hardware10863;

@TeleOp(name="Pushbot: 10863 Driver", group="Pushbot")
public class Driver10863 extends LinearOpMode {

    Hardware10863 robot = new Hardware10863();

    double clawOffset  = 0.0 ; // Servo mid position

    final double clawSpeed  = 0.02 ; // Sets rate to move servo

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
            right  = -gamepad1.left_stick_y + gamepad1.right_stick_x;
            left = -gamepad1.left_stick_y - gamepad1.right_stick_x;

            max = Math.max(Math.abs(left), Math.abs(right)); // Normalize

            if (max > 1.0) {
                left /= max;
                right /= max;
            }

            robot.getLeftWheel().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.getRightWheel().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            robot.getLeftIntake().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.getRightIntake().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            robot.getLeftWheel().setPower(left);
            robot.getRightWheel().setPower(right);

            // Use (Y) and (A) to do intake
            if (gamepad1.y) {
                robot.getLeftIntake().setPower(1.0);
                robot.getRightIntake().setPower(-1.0);
            } else if (gamepad1.a) {
                robot.getLeftIntake().setPower(-1.0);
                robot.getRightIntake().setPower(1.0);
            } else {
                robot.getLeftIntake().setPower(0.0);
                robot.getRightIntake().setPower(0.0);
            }

            // Use gamepad bumpers to move arm up and down
            if (gamepad1.b) {
                clawOffset += clawSpeed;
            } else if (gamepad1.x) {
                clawOffset -= clawSpeed;
            }

            // Move servo in channel 1 to new position
            clawOffset = Range.clip(clawOffset, -0.5, 0.5);
            robot.getArm().getController().setServoPosition(1, clawOffset);

            robot.waitForTick(40);
        }
    }
}
