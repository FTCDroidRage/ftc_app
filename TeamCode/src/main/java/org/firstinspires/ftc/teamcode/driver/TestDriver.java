package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.TestHardware;

@TeleOp(name="Sensor Testing", group="Linear Opmode")
public class TestDriver extends LinearOpMode {

    private TestHardware robot = new TestHardware();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        telemetry.addData("Say", "Hello Driver");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (robot.getOds1().getLightDetected() > 0.6) {
                telemetry.addData("ODS 1:", "White Tape Detected");
            } else {
                telemetry.addData("ODS 1:", "No tape detected");
            }

            if (robot.getOds2().getLightDetected() > 0.6) {
                telemetry.addData("ODS 2:", "White Tape Detected");
            } else {
                telemetry.addData("ODS 2:", "No tape detected");
            }

            if (robot.getColorSensor().blue() > robot.getColorSensor().red()) {
                telemetry.addData("Color Sensor:", "Blue Tape Detected");
            }

            if (robot.getColorSensor().blue() < robot.getColorSensor().red()) {
                telemetry.addData("Color Sensor:", "Red Tape Detected");
            }

            telemetry.update();
        }

    }

}