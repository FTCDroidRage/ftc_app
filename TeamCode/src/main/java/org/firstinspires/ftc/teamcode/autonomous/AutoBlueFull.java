package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.wip.AutonomousPhase;
import org.firstinspires.ftc.teamcode.hardware.Hardware10863;

@Autonomous(name = "Blue Full", group = "Autonomous")
public class AutoBlueFull extends LinearOpMode {

    private Hardware10863 robot = new Hardware10863();
    private AutonomousPhase phase;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        this.phase = AutonomousPhase.WAITING;

        waitForStart();

        this.phase = AutonomousPhase.DRIVING_FROM_START;

        while (opModeIsActive()) {
            if (phase == AutonomousPhase.DRIVING_FROM_START) {
                if (robot.getOds1().getLightDetected() > 0.6 && robot.getOds2().getLightDetected() <= 0.6) {
                    drive(-0.1, -0.1);
                } else if (robot.getOds2().getLightDetected() > 0.6 && robot.getOds1().getLightDetected() <= 0.6) {
                    drive(0.1, 0.1);
                } else if (robot.getOds2().getLightDetected() <= 0.6 && robot.getOds1().getLightDetected() <= 0.6) {
                    if (robot.getColorSensor().red() != 0 || robot.getColorSensor().blue() != 0) {
                        drive(0.0, 0.0);

                        robot.getElapsedTime().reset();
                        this.phase = AutonomousPhase.DRIVING_TO_BEACON1;
                    } else {
                        drive(0.2, -0.2);
                    }
                }
            } else if (phase == AutonomousPhase.DRIVING_TO_BEACON1) {
                if (robot.getElapsedTime().seconds() <= 2) {
                    if (robot.getColorSensor().red() > robot.getColorSensor().blue()) {
                        drive(0.1, 0.1);
                    } else {
                        drive(-0.1, -0.1);
                    }
                } else {
                    drive(0.0, 0.0);

                    robot.getElapsedTime().reset();
                    this.phase = AutonomousPhase.BACKING_UP1;
                }
            } else if (phase == AutonomousPhase.BACKING_UP1) {
                if (robot.getElapsedTime().seconds() <= 2) {
                    if (robot.getOds1().getLightDetected() > 0.6 && robot.getOds2().getLightDetected() <= 0.6) {
                        drive(0.1, 0.1);
                    } else if (robot.getOds2().getLightDetected() > 0.6 && robot.getOds1().getLightDetected() <= 0.6) {
                        drive(-0.1, -0.1);
                    } else {
                        drive(-0.2, 0.2);
                    }
                } else {
                    drive(0.0, 0.0);

                    robot.getElapsedTime().reset();
                    this.phase = AutonomousPhase.TURNING_FROM_BEACON1;
                }
            } else if (phase == AutonomousPhase.TURNING_FROM_BEACON1) {
                if (robot.getElapsedTime().seconds() <= 1) {
                    drive(-0.1, -0.1);
                } else {
                    drive(0.0, 0.0);

                    robot.getElapsedTime().reset();
                    this.phase = AutonomousPhase.DRIVING_FROM_BEACON1;
                }
            } else if (phase == AutonomousPhase.DRIVING_FROM_BEACON1) {
                if (robot.getOds1().getLightDetected() > 0.6 && robot.getOds2().getLightDetected() <= 0.6) {
                    drive(-0.1, -0.1);
                } else if (robot.getOds2().getLightDetected() > 0.6 && robot.getOds1().getLightDetected() <= 0.6) {
                    drive(0.1, 0.1);
                } else if (robot.getOds2().getLightDetected() <= 0.6 && robot.getOds1().getLightDetected() <= 0.6) {
                    if (robot.getColorSensor().red() != 0 || robot.getColorSensor().blue() != 0) {
                        drive(0.0, 0.0);

                        robot.getElapsedTime().reset();
                        this.phase = AutonomousPhase.DRIVING_TO_BEACON2;
                    } else {
                        drive(0.2, -0.2);
                    }
                }
            } else if (phase == AutonomousPhase.DRIVING_TO_BEACON2) {
                if (robot.getElapsedTime().seconds() <= 2) {
                    if (robot.getColorSensor().red() > robot.getColorSensor().blue()) {
                        drive(0.1, 0.1);
                    } else {
                        drive(-0.1, -0.1);
                    }
                } else {
                    drive(0.0, 0.0);

                    robot.getElapsedTime().reset();
                    this.phase = AutonomousPhase.BACKING_UP2;
                }
            } else if (phase == AutonomousPhase.BACKING_UP2) {
                if (robot.getElapsedTime().seconds() <= 2) {
                    if (robot.getOds1().getLightDetected() > 0.6 && robot.getOds2().getLightDetected() <= 0.6) {
                        drive(0.1, 0.1);
                    } else if (robot.getOds2().getLightDetected() > 0.6 && robot.getOds1().getLightDetected() <= 0.6) {
                        drive(-0.1, -0.1);
                    } else {
                        drive(-0.2, 0.2);
                    }
                } else {
                    drive(0.0, 0.0);

                    robot.getElapsedTime().reset();
                    this.phase = AutonomousPhase.TURNING_TO_CENTER;
                }
            } else if (phase == AutonomousPhase.TURNING_TO_CENTER) {
                if (robot.getElapsedTime().seconds() <= 1) {
                    drive(0.1, 0.1);
                } else {
                    drive(0.0, 0.0);

                    robot.getElapsedTime().reset();
                    this.phase = AutonomousPhase.ADJUSTING_TO_SHOOT;
                }
            } else if (phase == AutonomousPhase.ADJUSTING_TO_SHOOT) {
                if (robot.getElapsedTime().seconds() <= 1) {
                    drive(1.0, -1.0);
                } else {
                    drive(0.0, 0.0);

                    robot.getElapsedTime().reset();
                    this.phase = AutonomousPhase.SHOOTING_PARTICLES;
                }
            } else if (phase == AutonomousPhase.SHOOTING_PARTICLES) {
                if (robot.getElapsedTime().seconds() <= 3) {
                    drive(0.0, -0.0);

                    robot.getFlicker().setPower(-1.0);
                    robot.getLift().setPower(1.0);
                } else {
                    drive(0.0, 0.0);

                    robot.getFlicker().setPower(0.0);
                    robot.getLift().setPower(0.0);

                    robot.getElapsedTime().reset();
                    this.phase = AutonomousPhase.DRIVING_TO_CENTER;
                }
            } else if (phase == AutonomousPhase.DRIVING_TO_CENTER) {
                if (robot.getElapsedTime().seconds() <= 1) {
                    drive(1.0, -1.0);
                } else {
                    drive(0.0, 0.0);

                    robot.getElapsedTime().reset();
                    this.phase = AutonomousPhase.COMPLETED;
                }
            } else if (phase == AutonomousPhase.COMPLETED) {
                robot.getElapsedTime().reset();
                drive(0.0, 0.0);
            }
        }
    }

    public void drive(double left, double right) {
        robot.getFrontWheelLeft().setPower(left);
        robot.getFrontWheelRight().setPower(right);
        robot.getBackWheelLeft().setPower(left);
        robot.getBackWheelRight().setPower(right);
    }

}