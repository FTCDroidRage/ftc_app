package org.firstinspires.ftc.teamcode.autonomous.wip;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.actions.ActionEncoderDrive;
import org.firstinspires.ftc.teamcode.hardware.Hardware12285;
import org.firstinspires.ftc.teamcode.util.Team;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="[WIP] Auto Blue Beacon", group="Linear Opmode")
public class AutoBlueBeacon extends LinearOpMode {

    private Hardware12285 robot = new Hardware12285();
    // private ActionEncoderDrive actionEncoderDrive;

    private double whiteTapeLightNormal = 0.6842619745845553;
    private double whiteTapeLightRaw = 3.421309872922776;
    private double inchesToBeacon = 0.0;

    @Override
    public void runOpMode() {
        // this.actionEncoderDrive = new ActionEncoderDrive(Team.TEAM_12285, 1440, 2.0, 0.6, 0.5);

            // send the info back to driver station using telemetry function.
            if (robot.getOpticalDistanceSensor() != null) {
                telemetry.addData("Normal", robot.getOpticalDistanceSensor().getLightDetected());

                telemetry.update();
            }

            /*if (robot.getOpticalDistanceSensor().getLightDetected() == whiteTapeLight) {

            } else {
                actionEncoderDrive.drive(0.6, inchesToBeacon, inchesToBeacon, this);
            }*/

        // raw:     normal:

    }

}