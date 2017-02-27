package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Hardware10863;

@Autonomous(name = "White Tape Tracking", group = "Autonomous")
public class FollowWhiteTape extends LinearOpMode {

    private Hardware10863 robot = new Hardware10863();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (robot.getOds1().getLightDetected() > 0.6) {
                robot.getFrontWheelLeft().setPower(0.3);
                robot.getFrontWheelRight().setPower(-0.3);
                robot.getBackWheelLeft().setPower(0.3);
                robot.getBackWheelRight().setPower(-0.3);
            } else {
                if ((robot.getElapsedTime().seconds() % 2) == 0) {
                    if (robot.getFrontWheelLeft().getPower() == -0.3 && robot.getFrontWheelRight().getPower() == -0.3) {
                        robot.getFrontWheelLeft().setPower(0.3);
                        robot.getFrontWheelRight().setPower(0.3);
                        robot.getBackWheelLeft().setPower(0.3);
                        robot.getBackWheelRight().setPower(0.3);
                    } else if (robot.getFrontWheelLeft().getPower() == 0.3 && robot.getFrontWheelRight().getPower() == 0.3) {
                        robot.getFrontWheelLeft().setPower(-0.3);
                        robot.getFrontWheelRight().setPower(-0.3);
                        robot.getBackWheelLeft().setPower(-0.3);
                        robot.getBackWheelRight().setPower(-0.3);
                    }
                }
            }
        }
    }

}