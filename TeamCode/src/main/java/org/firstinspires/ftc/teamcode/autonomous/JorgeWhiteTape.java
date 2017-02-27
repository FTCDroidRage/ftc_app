package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Hardware10863;

@Autonomous(name = "Jorge Tape Tracking", group = "Autonomous")
public class JorgeWhiteTape extends LinearOpMode {

    private Hardware10863 robot = new Hardware10863();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (robot.getOds1().getLightDetected() > 0.6) {
                robot.getFrontWheelLeft().setPower(0.2);
                robot.getFrontWheelRight().setPower(-0.2);
                robot.getBackWheelLeft().setPower(0.2);
                robot.getBackWheelRight().setPower(-0.2);
            } else {
                if (robot.getOds1().getLightDetected() > 0.6) {
                }
            }
        }
    }

}