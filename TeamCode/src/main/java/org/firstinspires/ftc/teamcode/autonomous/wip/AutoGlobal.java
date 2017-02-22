package org.firstinspires.ftc.teamcode.autonomous.wip;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.hardware.Hardware12285;
import org.firstinspires.ftc.teamcode.hardware.HardwareNull;
import org.firstinspires.ftc.teamcode.util.Configuration;

import java.util.ArrayList;
import java.util.List;

// @com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="[WIP] Complex", group="Linear Opmode")
public class AutoGlobal extends LinearOpMode {

    private Hardware12285 robot;
    private HardwareNull n;
    private ElapsedTime elapsedTime = new ElapsedTime();

    private Configuration config;

    private Configuration.Alliance alliance;
    private Configuration.Team team;
    private Configuration.Beacon beacon1Side;
    private Configuration.Beacon beacon2Side;

    private AutonomousPhase phase;

    private boolean configured;
    private boolean stonesAndChipsActivated = false;

    private double countsPerMotorRev = 1440;
    private double driveGearReduction = 2.0;
    private double wheelDiameterInches = 4.0;
    private double countsPerInch = (countsPerMotorRev * driveGearReduction) / (wheelDiameterInches * 3.1415);

    @Override
    public void runOpMode() throws InterruptedException {
        this.configured = false;

        this.phase = AutonomousPhase.WAITING;

        telemetry.addData("Status", "Waiting for alliance, team, and beacon configuration...");
        telemetry.update();

        while (!configured) {
            if (gamepad1.x) {
                this.alliance = Configuration.Alliance.BLUE;
                telemetry.addData("Alliance", "Blue");
            }

            if (gamepad1.b) {
                this.alliance = Configuration.Alliance.RED;
                telemetry.addData("Alliance", "Red");
            }

            if (gamepad1.y) {
                this.team = Configuration.Team.TEAM_12285;
                telemetry.addData("Team", "12285");
            }

            if (gamepad1.a) {
                this.team = Configuration.Team.TEAM_10863;
                telemetry.addData("Team", "10863");
            }

            if (gamepad1.left_bumper) {
                if (beacon1Side == null) {
                    this.beacon1Side = Configuration.Beacon.LEFT;
                    telemetry.addData("Beacon-1 Target", "Left");
                } else {
                    this.beacon2Side = Configuration.Beacon.LEFT;
                    telemetry.addData("Beacon-2 Target", "Left");
                }
            }

            if (gamepad1.right_bumper) {
                if (beacon1Side == null) {
                    this.beacon1Side = Configuration.Beacon.RIGHT;
                    telemetry.addData("Beacon-1 Target", "Right");
                } else {
                    this.beacon2Side = Configuration.Beacon.RIGHT;
                    telemetry.addData("Beacon-2 Target", "Right");
                }
            }

            if (gamepad1.start) {
                if (alliance == null) {
                    this.alliance = Configuration.Alliance.NULL;
                    telemetry.addData("Alliance", "Not selected");
                }

                if (team == null) {
                    this.team = Configuration.Team.NULL;
                    telemetry.addData("Team", "Not selected");
                }

                if (beacon1Side == null) {
                    this.beacon1Side = Configuration.Beacon.NULL;
                    telemetry.addData("Beacon-1 Target", "Not selected");
                }

                if (beacon2Side == null) {
                    this.beacon2Side = Configuration.Beacon.NULL;
                    telemetry.addData("Beacon-2 Target", "Not selected");
                }

                telemetry.addData("Status", "Autonomous Configured - Waiting for start");
                this.config = new Configuration(alliance, team, beacon1Side, beacon2Side);
                this.configured = true;

            }
            telemetry.update();
        }

        waitForStart();

        this.phase = AutonomousPhase.DRIVING_FROM_START;

        telemetry.addData("Status", "Driving");
        telemetry.update();

        if (config.getTeam() == Configuration.Team.TEAM_12285) {
            this.robot = new Hardware12285();

            robot.init(hardwareMap);
            telemetry.addData("Status", "Resetting Encoders");
            telemetry.update();

            robot.getWheelLeft().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getWheelRight().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            idle();

            robot.getWheelLeft().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.getWheelRight().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            this.n = new HardwareNull();
            n.init(hardwareMap);
        }

        while (opModeIsActive() && !robot.getWheelRight().isBusy() && !robot.getWheelLeft().isBusy()) {
            runAutonomous(config);
        }
    }

    public void driveWithEncoders(double speed, double inchesLeft, double inchesRight, double timeout) throws InterruptedException {
        if (robot == null) return;
        int newLeftTarget;
        int newRightTarget;

        if (opModeIsActive()) {
            newLeftTarget = robot.getWheelLeft().getCurrentPosition() + (int)(inchesLeft * countsPerInch);
            newRightTarget = robot.getWheelLeft ().getCurrentPosition() + (int)(inchesRight * countsPerInch);

            robot.getWheelLeft().setTargetPosition(newLeftTarget);
            robot.getWheelRight().setTargetPosition(newRightTarget);

            robot.getWheelLeft().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.getWheelRight().setMode(DcMotor.RunMode.RUN_TO_POSITION);

            elapsedTime.reset();
            robot.getWheelLeft().setPower(speed);
            robot.getWheelRight().setPower(speed);

            while (opModeIsActive() && elapsedTime.seconds() < timeout && robot.getWheelLeft().isBusy()
                    && robot.getWheelRight().isBusy()) {

                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.getWheelLeft().getCurrentPosition(),
                        robot.getWheelRight().getCurrentPosition());
                telemetry.update();

                idle();
            }

            robot.getWheelLeft().setPower(0);
            robot.getWheelRight().setPower(0);

            robot.getWheelLeft().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getWheelRight().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    public void turnWithEncoders(double speed, double degrees, double timeout) throws InterruptedException {
        if (robot == null) return;
        int newLeftTarget;
        int newRightTarget;

        double inches = 20.5 * Math.PI * degrees / 360;

        if (opModeIsActive()) {
            newLeftTarget = robot.getWheelLeft().getCurrentPosition() + (int)(inches * countsPerInch);
            newRightTarget = robot.getWheelRight().getCurrentPosition() + (int)(inches * countsPerInch);

            robot.getWheelLeft().setTargetPosition(newLeftTarget);
            robot.getWheelRight().setTargetPosition(newRightTarget);

            robot.getWheelLeft().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.getWheelRight().setMode(DcMotor.RunMode.RUN_TO_POSITION);

            elapsedTime.reset();
            robot.getWheelLeft().setPower(speed);
            robot.getWheelRight().setPower(speed);

            while (opModeIsActive() && elapsedTime.seconds() < timeout && robot.getWheelLeft().isBusy()
                    && robot.getWheelRight().isBusy()) {
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.getWheelLeft().getCurrentPosition(),
                        robot.getWheelRight().getCurrentPosition());
                telemetry.update();

                idle();
            }

            robot.getWheelLeft().setPower(0);
            robot.getWheelRight().setPower(0);

            robot.getWheelLeft().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getWheelRight().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    private void runAutonomous(Configuration config) throws InterruptedException {
        if (team == Configuration.Team.TEAM_12285) {
            if (robot == null) return;
            if (alliance == Configuration.Alliance.BLUE) {
                if (beacon1Side != null && beacon2Side != null &&
                        beacon1Side != Configuration.Beacon.NULL && beacon2Side != Configuration.Beacon.NULL) {
                    switch (phase) {
                        case DRIVING_FROM_START:
                            driveWithEncoders(1.0, 80.0, 80.0, 5.0);
                            phase = AutonomousPhase.TURNING_TO_BEACON1;

                            break;
                        case TURNING_TO_BEACON1:
                            turnWithEncoders(0.5, 15.0, 1.0);
                            phase = AutonomousPhase.DRIVING_TO_BEACON1;

                            break;
                        case DRIVING_TO_BEACON1:
                            if (beacon1Side == Configuration.Beacon.LEFT) {
                                driveWithEncoders(1.0, 1.0, 1.0, 1.0);
                                turnWithEncoders(0.5, -10.0, 1.0);
                                driveWithEncoders(-1.0, -1.0, -1.0, 1.0);
                                turnWithEncoders(0.5, 10.0, 1.0);
                            } else {
                                driveWithEncoders(1.0, 1.0, 1.0, 1.0);
                                turnWithEncoders(0.5, 10.0, 1.0);
                                driveWithEncoders(-1.0, -1.0, -1.0, 1.0);
                                turnWithEncoders(0.5, -10.0, 1.0);
                            }
                            phase = AutonomousPhase.BACKING_UP1;

                            break;
                        case BACKING_UP1:
                            driveWithEncoders(-1.0, -12.0, -12.0, 3.0);
                            phase = AutonomousPhase.TURNING_TO_BEACON1;

                            break;
                        case TURNING_FROM_BEACON1:
                            turnWithEncoders(0.5, -90.0, 1.0);
                            phase = AutonomousPhase.DRIVING_FROM_BEACON1;

                            break;
                        case DRIVING_FROM_BEACON1:
                            driveWithEncoders(1.0, 45.0, 45.0, 5.0);
                            phase = AutonomousPhase.TURNING_TO_BEACON2;

                            break;
                        case TURNING_TO_BEACON2:
                            turnWithEncoders(0.5, 45.0, 1.0);
                            phase = AutonomousPhase.DRIVING_TO_BEACON2;

                            break;
                        case DRIVING_TO_BEACON2:
                            driveWithEncoders(1.0, 11.0, 11.0, 3.0);
                            if (beacon2Side == Configuration.Beacon.LEFT) {
                                driveWithEncoders(1.0, 1.0, 1.0, 1.0);
                                turnWithEncoders(0.5, -10.0, 1.0);
                                driveWithEncoders(-1.0, -1.0, -1.0, 1.0);
                                turnWithEncoders(0.5, 10.0, 1.0);
                            } else {
                                driveWithEncoders(1.0, 1.0, 1.0, 1.0);
                                turnWithEncoders(0.5, 10.0, 1.0);
                                driveWithEncoders(-1.0, -1.0, -1.0, 1.0);
                                turnWithEncoders(0.5, -10.0, 1.0);
                            }
                            phase = AutonomousPhase.BACKING_UP2;

                            break;
                        case BACKING_UP2:
                            driveWithEncoders(-1.0, -12.0, -12.0, 3.0);
                            phase = AutonomousPhase.TURNING_TO_CENTER;

                            break;
                        case TURNING_TO_CENTER:
                            turnWithEncoders(0.5, 90.0, 1.0);
                            phase = AutonomousPhase.DRIVING_TO_CENTER;

                            break;
                        case DRIVING_TO_CENTER:
                            driveWithEncoders(1.0, 70.0, 70.0, 5.0);
                            phase = AutonomousPhase.TURNING_TO_CORNER;

                            break;
                        case TURNING_TO_CORNER:
                            turnWithEncoders(0.5, -90.0, 1.0);
                            phase = AutonomousPhase.DRIVING_TO_CORNER;

                            break;
                        case DRIVING_TO_CORNER:
                            driveWithEncoders(1.0, 80.0, 80.0, 5.0);
                            phase = AutonomousPhase.GRABBING_CORNER;

                            break;
                        case GRABBING_CORNER:
                            phase = AutonomousPhase.COMPLETED;

                            break;
                        case COMPLETED:
                            telemetry.addData("Status", "Complete");
                            telemetry.update();
                            break;
                    }
                } else {
                    robot.getWheelLeft().setPower(1.0);
                    robot.getWheelRight().setPower(1.0);
                    elapsedTime.reset();

                    while (opModeIsActive() && (elapsedTime.seconds() < 4.5)) {
                        telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", elapsedTime.seconds());
                        telemetry.update();
                        idle();
                    }

                    robot.getWheelLeft().setPower(0);
                    robot.getWheelRight().setPower(0);

                    telemetry.addData("Path", "Complete");
                    telemetry.update();
                    sleep(1000);
                    idle();
                }
            } else {
                if (alliance == Configuration.Alliance.RED) {
                    if (beacon1Side != null && beacon2Side != null &&
                            beacon1Side != Configuration.Beacon.NULL && beacon2Side != Configuration.Beacon.NULL) {
                        switch (phase) {
                            case DRIVING_FROM_START:
                                driveWithEncoders(1.0, 80.0, 80.0, 5.0);
                                phase = AutonomousPhase.TURNING_TO_BEACON1;

                                break;
                            case TURNING_TO_BEACON1:
                                turnWithEncoders(0.5, -15.0, 1.0);
                                phase = AutonomousPhase.DRIVING_TO_BEACON1;

                                break;
                            case DRIVING_TO_BEACON1:
                                if (beacon1Side == Configuration.Beacon.LEFT) {
                                    driveWithEncoders(1.0, 1.0, 1.0, 1.0);
                                    turnWithEncoders(0.5, 10.0, 1.0);
                                    driveWithEncoders(-1.0, -1.0, -1.0, 1.0);
                                    turnWithEncoders(0.5, -10.0, 1.0);
                                } else {
                                    driveWithEncoders(1.0, 1.0, 1.0, 1.0);
                                    turnWithEncoders(0.5, -10.0, 1.0);
                                    driveWithEncoders(-1.0, -1.0, -1.0, 1.0);
                                    turnWithEncoders(0.5, 10.0, 1.0);
                                }
                                phase = AutonomousPhase.BACKING_UP1;

                                break;
                            case BACKING_UP1:
                                driveWithEncoders(-1.0, -12.0, -12.0, 3.0);
                                phase = AutonomousPhase.TURNING_TO_BEACON1;

                                break;
                            case TURNING_FROM_BEACON1:
                                turnWithEncoders(0.5, 90.0, 1.0);
                                phase = AutonomousPhase.DRIVING_FROM_BEACON1;

                                break;
                            case DRIVING_FROM_BEACON1:
                                driveWithEncoders(1.0, 45.0, 45.0, 5.0);
                                phase = AutonomousPhase.TURNING_TO_BEACON2;

                                break;
                            case TURNING_TO_BEACON2:
                                turnWithEncoders(0.5, -45.0, 1.0);
                                phase = AutonomousPhase.DRIVING_TO_BEACON2;

                                break;
                            case DRIVING_TO_BEACON2:
                                driveWithEncoders(1.0, 11.0, 11.0, 3.0);
                                if (beacon2Side == Configuration.Beacon.LEFT) {
                                    driveWithEncoders(1.0, 1.0, 1.0, 1.0);
                                    turnWithEncoders(0.5, 10.0, 1.0);
                                    driveWithEncoders(-1.0, -1.0, -1.0, 1.0);
                                    turnWithEncoders(0.5, -10.0, 1.0);
                                } else {
                                    driveWithEncoders(1.0, 1.0, 1.0, 1.0);
                                    turnWithEncoders(0.5, -10.0, 1.0);
                                    driveWithEncoders(-1.0, -1.0, -1.0, 1.0);
                                    turnWithEncoders(0.5, 10.0, 1.0);
                                }
                                phase = AutonomousPhase.BACKING_UP2;

                                break;
                            case BACKING_UP2:
                                driveWithEncoders(-1.0, -12.0, -12.0, 3.0);
                                phase = AutonomousPhase.TURNING_TO_CENTER;

                                break;
                            case TURNING_TO_CENTER:
                                turnWithEncoders(0.5, -90.0, 1.0);
                                phase = AutonomousPhase.DRIVING_TO_CENTER;

                                break;
                            case DRIVING_TO_CENTER:
                                driveWithEncoders(1.0, 70.0, 70.0, 5.0);
                                phase = AutonomousPhase.TURNING_TO_CORNER;

                                break;
                            case TURNING_TO_CORNER:
                                turnWithEncoders(0.5, 90.0, 1.0);
                                phase = AutonomousPhase.DRIVING_TO_CORNER;

                                break;
                            case DRIVING_TO_CORNER:
                                driveWithEncoders(1.0, 80.0, 80.0, 5.0);
                                phase = AutonomousPhase.GRABBING_CORNER;

                                break;
                            case GRABBING_CORNER:
                                phase = AutonomousPhase.COMPLETED;

                                break;
                            case COMPLETED:
                                telemetry.addData("Status", "Complete");
                                telemetry.update();
                                break;
                        }
                    } else {
                        robot.getWheelLeft().setPower(1.0);
                        robot.getWheelRight().setPower(1.0);
                        elapsedTime.reset();

                        while (opModeIsActive() && (elapsedTime.seconds() < 4.5)) {
                            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", elapsedTime.seconds());
                            telemetry.update();
                            idle();
                        }

                        robot.getWheelLeft().setPower(0);
                        robot.getWheelRight().setPower(0);

                        telemetry.addData("Path", "Complete");
                        telemetry.update();
                        sleep(1000);
                        idle();
                    }
                }
            }
        } else if (config.getTeam() == Configuration.Team.TEAM_10863) {
            robot.getWheelLeft().setPower(0.6);
            robot.getWheelRight().setPower(0.6);
            elapsedTime.reset();

            while (elapsedTime.seconds() < 3.0) {
                telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", elapsedTime.seconds());
                telemetry.update();
                idle();
            }

            robot.getWheelLeft().setPower(0);
            robot.getWheelRight().setPower(0);

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);
            idle();
        } else {
            robot.getWheelLeft().setPower(0.6);
            robot.getWheelRight().setPower(0.6);
            elapsedTime.reset();

            while (elapsedTime.seconds() < 3.0) {
                telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", elapsedTime.seconds());
                telemetry.update();
                idle();
            }

            robot.getWheelLeft().setPower(0);
            robot.getWheelRight().setPower(0);

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);
            idle();
        }
    }

    private void runVuforiaTracking(Configuration config) throws InterruptedException {
        String key = "Ac+mSSz/////AAAAGX7qWAluukJCne3f/WMl3lR041QLVsuBByABDY/OoLY4P/" +
                "aqR+qbKcEfjdWYffl1UKkPGmkNiYOLwRRAjFMowUftC92bbpf7Y0N+Tsz772/ETwHx3LO" +
                "YWpCM6okjazmdOGg7umVHWc9E4GiSri3dCFvV/4qm0uJVF4OE7eEbQ9g1qwipSQVMDQCkQ" +
                "vnpsrHOJ26ya9KUw/pyTZyBwqXdfZiRK4jB5EK4a2laRO0UIuNfW+wM7lRZILMRulNVyVF2x" +
                "c5Mxo8VAj2OjwBzmUXaCZxTajj8PgrPPU6yqZT+YxXJbcCem22E70urP6yrffwy2AFD6d3LGVYc3" +
                "hA9mTJEGp5G9UFWzHPcCh9k5+kjH6o8T";

        OpenGLMatrix lastLocation = null;

        VuforiaLocalizer vuforia = null;

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(com.qualcomm.ftcrobotcontroller.R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = key;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables stonesAndChips = vuforia.loadTrackablesFromAsset("StonesAndChips");
        VuforiaTrackable redTarget = stonesAndChips.get(0);
        redTarget.setName("RedTarget");  // Stones

        VuforiaTrackable blueTarget  = stonesAndChips.get(1);
        blueTarget.setName("BlueTarget");  // Chips

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(stonesAndChips);

        float mmPerInch = 25.4f;
        float mmBotWidth = 18 * mmPerInch; // TODO: Measure bot width
        float mmFTCFieldWidth = (12*12 - 2) * mmPerInch;

        // Red location
        OpenGLMatrix redTargetLocationOnField = OpenGLMatrix.translation(-mmFTCFieldWidth/2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX, AngleUnit.DEGREES, 90, 90, 0));
        redTarget.setLocation(redTargetLocationOnField);
        RobotLog.ii(key, "Red Target=%s", format(redTargetLocationOnField));

        // Blue location
        OpenGLMatrix blueTargetLocationOnField = OpenGLMatrix.translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX, AngleUnit.DEGREES, 90, 0, 0));
        blueTarget.setLocation(blueTargetLocationOnField);
        RobotLog.ii(key, "Blue Target=%s", format(blueTargetLocationOnField));

        // Phone Orientation
        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix.translation(mmBotWidth/2,0,0)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.YZY, AngleUnit.DEGREES, -90, 0, 0));
        RobotLog.ii(key, "phone=%s", format(phoneLocationOnRobot));

        ((VuforiaTrackableDefaultListener)redTarget.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)blueTarget.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);

        // Init trackable
        if (!stonesAndChipsActivated)
            stonesAndChips.activate();

        // Run tracker
        for (VuforiaTrackable trackable : allTrackables) {
            telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible() ? "Visible" : "Not Visible");

            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
            if (robotLocationTransform != null) {
                lastLocation = robotLocationTransform;
            }
        }

        if (lastLocation != null) {
            telemetry.addData("Pos", format(lastLocation));
        } else {
            telemetry.addData("Pos", "Unknown");
        }
        telemetry.update();
        idle();
    }

    private String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }

}