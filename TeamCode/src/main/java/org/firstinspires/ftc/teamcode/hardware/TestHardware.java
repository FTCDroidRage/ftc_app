package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Hardware;

public class TestHardware implements Hardware {

    private ElapsedTime elapsedTime;
    private HardwareMap robot;

    private OpticalDistanceSensor ods1;
    private OpticalDistanceSensor ods2;

    private ColorSensor colorSensor;

    @Override
    public void init(HardwareMap hardwareMap) {
        this.elapsedTime = new ElapsedTime();
        this.robot = hardwareMap;

        this.ods1 = hardwareMap.opticalDistanceSensor.get("ods1");
        this.ods2 = hardwareMap.opticalDistanceSensor.get("ods2");

        this.colorSensor = hardwareMap.colorSensor.get("cs");

        this.ods1.enableLed(true);
        this.ods2.enableLed(true);
        this.colorSensor.enableLed(true);
    }

    @Override
    public void waitForTick(long milliseconds) throws InterruptedException {
        long  remaining = milliseconds - (long) elapsedTime.milliseconds();

        if (remaining > 0)
            Thread.sleep(remaining);

        elapsedTime.reset();
    }

    @Override
    public HardwareMap getHardwareMap() {
        return robot;
    }

    public OpticalDistanceSensor getOds1() {
        return ods1;
    }

    public OpticalDistanceSensor getOds2() {
        return ods2;
    }

    public ColorSensor getColorSensor() {
        return colorSensor;
    }

}