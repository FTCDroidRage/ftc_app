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

        // ODS 1 = Right side
        // ODS 2 = Left side

        while (opModeIsActive()) {
            if (robot.getOds1().getLightDetected() > 0.6 && robot.getOds2().getLightDetected() <= 0.6) {
                robot.getFrontWheelLeft().setPower(-0.1);
                robot.getFrontWheelRight().setPower(-0.1);
                robot.getBackWheelLeft().setPower(-0.1);
                robot.getBackWheelRight().setPower(-0.1);
            } else if (robot.getOds2().getLightDetected() > 0.6 && robot.getOds1().getLightDetected() <= 0.6) {
                robot.getFrontWheelLeft().setPower(0.1);
                robot.getFrontWheelRight().setPower(0.1);
                robot.getBackWheelLeft().setPower(0.1);
                robot.getBackWheelRight().setPower(0.1);
            } else if (robot.getOds2().getLightDetected() <= 0.6 && robot.getOds1().getLightDetected() <= 0.6) {
                robot.getFrontWheelLeft().setPower(0.2);
                robot.getFrontWheelRight().setPower(-0.2);
                robot.getBackWheelLeft().setPower(0.2);
                robot.getBackWheelRight().setPower(-0.2);
            }
        }
    }

}