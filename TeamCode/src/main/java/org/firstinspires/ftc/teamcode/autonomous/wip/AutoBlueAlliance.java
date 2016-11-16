package org.firstinspires.ftc.teamcode.autonomous.wip;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Hardware12285;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="[WIP] Auto Blue Team", group="Linear Opmode")
public class AutoBlueAlliance extends LinearOpMode {

    private Hardware12285 robot = new Hardware12285();
    private ElapsedTime elapsedTime = new ElapsedTime();

    private double countsPerMotorRev = 1440;
    private double driveGearReduction = 2.0;
    private double wheelDiameterInches = 2.5625;
    private double countsPerInch = (countsPerMotorRev * driveGearReduction) / (wheelDiameterInches * 3.1415);

    private double whiteTapeLightNormal = 0.6842619745845553;
    private double whiteTapeLightRaw = 3.421309872922776;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.getLeftWheel().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.getRightWheel().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.getLeftWheel().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.getRightWheel().setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Path0", "Starting at %7d :%7d",
                robot.getLeftWheel().getCurrentPosition(),
                robot.getRightWheel().getCurrentPosition());
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (robot.getOpticalDistanceSensor().getLightDetected() >= whiteTapeLightNormal
                    && robot.getOpticalDistanceSensor().getRawLightDetected() >= whiteTapeLightRaw) {

            } else {
                double inchesToBeacon = 0.0;

                driveWithEncoders(0.6, inchesToBeacon, inchesToBeacon, 5);
                // actionEncoderDrive.drive(0.6, inchesToBeacon, inchesToBeacon, this);
            }
        }
    }

    public void driveWithEncoders(double speed, double inchesLeft, double inchesRight, double timeout) throws InterruptedException {
        int newLeftTarget;
        int newRightTarget;

        if (opModeIsActive()) {
            newLeftTarget = robot.getLeftWheel().getCurrentPosition() + (int)(inchesLeft * countsPerInch);
            newRightTarget = robot.getRightWheel().getCurrentPosition() + (int)(inchesRight * countsPerInch);

            robot.getLeftWheel().setTargetPosition(newLeftTarget);
            robot.getRightWheel().setTargetPosition(newRightTarget);

            robot.getLeftWheel().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.getRightWheel().setMode(DcMotor.RunMode.RUN_TO_POSITION);

            elapsedTime.reset();
            robot.getLeftWheel().setPower(Math.abs(speed));
            robot.getRightWheel().setPower(Math.abs(speed));

            while (opModeIsActive() && elapsedTime.seconds() < timeout && robot.getLeftWheel().isBusy()
                    && robot.getRightWheel().isBusy()) {

                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.getLeftWheel().getCurrentPosition(),
                        robot.getRightWheel().getCurrentPosition());
                telemetry.update();

                idle();
            }

            robot.getLeftWheel().setPower(0);
            robot.getRightWheel().setPower(0);

            robot.getLeftWheel().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.getRightWheel().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void turnWithEncoders(double speed, double degrees, double timeout) throws InterruptedException {
        int newLeftTarget;
        int newRightTarget;

        double inches = 20.5 * Math.PI * degrees / 360;

        if (opModeIsActive()) {
            newLeftTarget = robot.getLeftWheel().getCurrentPosition() + (int)(inches * countsPerInch);
            newRightTarget = robot.getRightWheel().getCurrentPosition() + (int)(inches * countsPerInch);

            robot.getLeftWheel().setTargetPosition(newLeftTarget);
            robot.getRightWheel().setTargetPosition(newRightTarget);

            robot.getLeftWheel().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.getRightWheel().setMode(DcMotor.RunMode.RUN_TO_POSITION);

            elapsedTime.reset();
            robot.getLeftWheel().setPower(Math.abs(speed));
            robot.getRightWheel().setPower(Math.abs(speed));

            while (opModeIsActive() && elapsedTime.seconds() < timeout && robot.getLeftWheel().isBusy()
                    && robot.getRightWheel().isBusy()) {
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.getLeftWheel().getCurrentPosition(),
                        robot.getRightWheel().getCurrentPosition());
                telemetry.update();

                idle();
            }

            robot.getLeftWheel().setPower(0);
            robot.getRightWheel().setPower(0);

            robot.getLeftWheel().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.getRightWheel().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void detectWhiteTape(double speed, double timeout) {
        elapsedTime.reset();

        while (opModeIsActive() && elapsedTime.seconds() < timeout) {
            telemetry.addData("Normal", robot.getOpticalDistanceSensor().getLightDetected());
            telemetry.addData("Raw", robot.getOpticalDistanceSensor().getRawLightDetected());
            telemetry.update();

            if (robot.getOpticalDistanceSensor().getLightDetected() >= whiteTapeLightNormal
                    && robot.getOpticalDistanceSensor().getRawLightDetected() >= whiteTapeLightRaw) {
                // TODO: Configure two ODS sensors on bottom of robot to position the robot straddling over the tape
            }
        }
    }

}