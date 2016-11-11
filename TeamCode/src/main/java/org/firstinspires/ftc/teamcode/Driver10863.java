package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@TeleOp(name="Pushbot: 10863 Driver", group="Pushbot")
public class Driver10863 extends LinearOpMode {

    HardwarePushbot robot = new HardwarePushbot();

    double clawOffset  = 0.0 ; // Servo mid position

    final double clawSpeed  = 0.02 ; // Sets rate to move servo

    @Override
    public void runOpMode() throws InterruptedException {
        double left;
        double right;
        double max;

        DcMotor leftIntake = hardwareMap.dcMotor.get("left intake");
        DcMotor rightIntake = hardwareMap.dcMotor.get("right intake");

        DcMotor leftWheel = hardwareMap.dcMotor.get("left wheel");
        DcMotor rightWheel = hardwareMap.dcMotor.get("right wheel");

        CRServo arm = hardwareMap.crservo.get("arm");

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

            leftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            leftIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            leftWheel.setPower(left);
            rightWheel.setPower(right);

            // Use (Y) and (A) to do intake
            if (gamepad1.y) {
                leftIntake.setPower(1.0);
                rightIntake.setPower(1.0);
            } else if (gamepad1.a) {
                leftIntake.setPower(-1.0);
                rightIntake.setPower(-1.0);
            } else {
                leftIntake.setPower(0.0);
                rightIntake.setPower(0.0);
            }

            // Use gamepad bumpers to move arm up and down
            if (gamepad1.b) {
                clawOffset += clawSpeed;
            } else if (gamepad1.x) {
                clawOffset -= clawSpeed;
            }

            // Move servo in channel 1 to new position
            clawOffset = Range.clip(clawOffset, -0.5, 0.5);
            arm.getController().setServoPosition(1, clawOffset);

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
        }
    }
}
