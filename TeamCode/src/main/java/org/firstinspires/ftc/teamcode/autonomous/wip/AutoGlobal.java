package org.firstinspires.ftc.teamcode.autonomous.wip;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.Autonomous12285;
import org.firstinspires.ftc.teamcode.hardware.Hardware10863;
import org.firstinspires.ftc.teamcode.hardware.Hardware12285;
import org.firstinspires.ftc.teamcode.hardware.HardwareNull;
import org.firstinspires.ftc.teamcode.util.Configuration;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="[ALPHA] Global Autonomous", group="Linear Opmode")
public class AutoGlobal extends LinearOpMode {

    private Hardware12285 robot;
    private Hardware10863 r;
    private HardwareNull n;
    private ElapsedTime elapsedTime = new ElapsedTime();

    private Configuration config;

    private Configuration.Alliance alliance;
    private Configuration.Team team;
    private Configuration.Beacon beacon1Side;
    private Configuration.Beacon beacon2Side;

    private AutonomousPhase phase;

    private boolean configured;

    private double countsPerMotorRev = 1440;
    private double driveGearReduction = 2.0;
    private double wheelDiameterInches = 1.3;
    private double countsPerInch = (countsPerMotorRev * driveGearReduction) / (wheelDiameterInches * 3.1415);

    @Override
    public void runOpMode() throws InterruptedException {
        this.configured = false;

        this.phase = AutonomousPhase.WAITING;

        telemetry.addData("Status", "Waiting for alliance team, and beacon configuration...");
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

            robot.getLeftWheel().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getRightWheel().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            idle();

            robot.getLeftWheel().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.getRightWheel().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else if (config.getTeam() == Configuration.Team.TEAM_10863) {
            this.r = new Hardware10863();
            r.init(hardwareMap);
        } else {
            this.n = new HardwareNull();
            n.init(hardwareMap);
        }

        while (opModeIsActive()) {
            if (team == Configuration.Team.TEAM_12285) {
                if (robot == null) return;
                if (alliance == Configuration.Alliance.BLUE) {
                    if (beacon1Side != null && beacon2Side != null &&
                            beacon1Side != Configuration.Beacon.NULL && beacon2Side != Configuration.Beacon.NULL) {
                        switch (phase) {
                            case DRIVING_FROM_START:
                                robot.getGrabber().getController().setServoPosition(1, 1.0);
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
                                robot.getGrabber().getController().setServoPosition(1, 0.0);
                                phase = AutonomousPhase.COMPLETED;

                                break;
                            case COMPLETED:
                                telemetry.addData("Status", "Complete");
                                telemetry.update();
                                break;
                        }
                    }
                }
            } else if (config.getTeam() == Configuration.Team.TEAM_10863) {
                robot.getLeftWheel().setPower(0.6);
                robot.getRightWheel().setPower(0.6);
                elapsedTime.reset();

                while (elapsedTime.seconds() < 3.0) {
                    telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", elapsedTime.seconds());
                    telemetry.update();
                    idle();
                }

                robot.getLeftWheel().setPower(0);
                robot.getRightWheel().setPower(0);

                telemetry.addData("Path", "Complete");
                telemetry.update();
                sleep(1000);
                idle();
            } else {
                robot.getLeftWheel().setPower(0.6);
                robot.getRightWheel().setPower(0.6);
                elapsedTime.reset();

                while (elapsedTime.seconds() < 3.0) {
                    telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", elapsedTime.seconds());
                    telemetry.update();
                    idle();
                }

                robot.getLeftWheel().setPower(0);
                robot.getRightWheel().setPower(0);

                telemetry.addData("Path", "Complete");
                telemetry.update();
                sleep(1000);
                idle();
            }
        }
    }

    public void driveWithEncoders(double speed, double inchesLeft, double inchesRight, double timeout) throws InterruptedException {
        if (robot == null) return;
        int newLeftTarget;
        int newRightTarget;

        if (opModeIsActive()) {
            newLeftTarget = robot.getLeftWheel().getCurrentPosition() + (int)(inchesLeft * countsPerInch);
            newRightTarget = robot.getLeftWheel ().getCurrentPosition() + (int)(inchesRight * countsPerInch);

            robot.getLeftWheel().setTargetPosition(newLeftTarget);
            robot.getRightWheel().setTargetPosition(newRightTarget);

            robot.getLeftWheel().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.getRightWheel().setMode(DcMotor.RunMode.RUN_TO_POSITION);

            elapsedTime.reset();
            robot.getLeftWheel().setPower(speed);
            robot.getRightWheel().setPower(speed);

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

            robot.getLeftWheel().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getRightWheel().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    public void turnWithEncoders(double speed, double degrees, double timeout) throws InterruptedException {
        if (robot == null) return;
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
            robot.getLeftWheel().setPower(speed);
            robot.getRightWheel().setPower(speed);

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

            robot.getLeftWheel().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getRightWheel().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

}