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

        double whiteTapeLightRaw = 3.421309872922776;
        double whiteTapeLightNormal = 0.6842619745845553;

        AutonomousPhase phase = AutonomousPhase.DRIVING_FROM_START;


        telemetry.addData("Status", "Driving");
        telemetry.update();

        while (opModeIsActive()) {
            switch (phase) {
                case DRIVING_FROM_START:
                    double inchesToBeacon = 5.0; // TODO: Measure field

                    driveWithEncoders(1.0, inchesToBeacon, inchesToBeacon, 5);
                    phase = AutonomousPhase.TURNING_TO_BEACON1;

                    break;
                case TURNING_TO_BEACON1:
                    /*if (robot.getOpticalDistanceSensor1().getLightDetected() >= whiteTapeLightNormal
                            && robot.getOpticalDistanceSensor1().getRawLightDetected() >= whiteTapeLightRaw
                            || robot.getOpticalDistanceSensor2().getLightDetected() >= whiteTapeLightNormal
                            && robot.getOpticalDistanceSensor2().getRawLightDetected() >= whiteTapeLightRaw) {*/
                        turnWithEncoders(0.5, 45.0, 1.0);
                    //}
                    phase = AutonomousPhase.DRIVING_TO_BEACON1;

                    break;
                case DRIVING_TO_BEACON1:
                    driveWithEncoders(1.0, 12.0, 12.0, 5.0);
                    phase = AutonomousPhase.BACKING_UP1;

                    break;
                case BACKING_UP1:
                    driveWithEncoders(1.0, -12.0, -12.0, 5.0);
                    turnWithEncoders(0.5, -90.0, 1.0);
                    phase = AutonomousPhase.DRIVING_FROM_BEACON1;

                    break;
                case DRIVING_FROM_BEACON1:
                    driveWithEncoders(1.0, 24.0, 24.0, 5.0);
                    phase = AutonomousPhase.TURNING_TO_BEACON2;

                    break;
                case TURNING_TO_BEACON2:
                    /*if (robot.getOpticalDistanceSensor1().getLightDetected() >= whiteTapeLightNormal
                            && robot.getOpticalDistanceSensor1().getRawLightDetected() >= whiteTapeLightRaw
                            || robot.getOpticalDistanceSensor2().getLightDetected() >= whiteTapeLightNormal
                            && robot.getOpticalDistanceSensor2().getRawLightDetected() >= whiteTapeLightRaw) {*/
                        turnWithEncoders(0.5, 45.0, 1.0);
                    //}
                    phase = AutonomousPhase.DRIVING_TO_BEACON2;

                    break;
                case DRIVING_TO_BEACON2:
                    driveWithEncoders(1.0, 12.0, 12.0, 5.0);
                    phase = AutonomousPhase.BACKING_UP2;

                    break;
                case BACKING_UP2:
                    driveWithEncoders(1.0, -12.0, -12.0, 5.0);
                    phase = AutonomousPhase.TURNING_TO_CENTER;

                    break;
                case TURNING_TO_CENTER:
                    turnWithEncoders(0.5, 135.0, 1.0);
                    phase = AutonomousPhase.DRIVING_TO_CENTER;

                    break;
                case DRIVING_TO_CENTER:
                    driveWithEncoders(1.0, 36.0, 36.0, 5.0);
                    phase = AutonomousPhase.TURNING_TO_CORNER;

                    break;
                case TURNING_TO_CORNER:
                    turnWithEncoders(0.5, -90.0, 1.0);
                    phase = AutonomousPhase.DRIVING_TO_CORNER;

                    break;
                case DRIVING_TO_CORNER:
                    driveWithEncoders(1.0, 36.0, 36.0, 5.0);
                    phase = AutonomousPhase.COMPLETED;

                    break;
                case COMPLETED:
                    telemetry.addData("Status", "Complete");
                    telemetry.update();
                    break;
            }
        }
    }

    public void driveWithEncoders(double speed, double inchesLeft, double inchesRight, double timeout) throws InterruptedException {
        int newLeftTarget;
        int newRightTarget;

        if (opModeIsActive()) {
            newLeftTarget = robot.getLeftWheel().getCurrentPosition() + (int)(inchesLeft * countsPerInch);
            newRightTarget = robot.getRightWheel().getCurrentPosition() + (int)(-inchesRight * countsPerInch);

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

}