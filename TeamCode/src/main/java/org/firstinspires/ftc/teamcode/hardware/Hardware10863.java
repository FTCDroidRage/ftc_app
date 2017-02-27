package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Hardware;

public class Hardware10863 implements Hardware {

    private HardwareMap hardwareMap;
    private ElapsedTime elapsedTime;

    private DcMotor frontWheelLeft;
    private DcMotor frontWheelRight;
    private DcMotor backWheelLeft;
    private DcMotor backWheelRight;

    private DcMotor lift;
    private DcMotor flicker;

    private OpticalDistanceSensor ods1;
    private OpticalDistanceSensor ods2;

    private ColorSensor colorSensor;

    private double direction;
    private double multi;

    @Override
    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.elapsedTime = new ElapsedTime();

        this.frontWheelLeft = hardwareMap.dcMotor.get("front wheel left");
        this.frontWheelRight = hardwareMap.dcMotor.get("front wheel right");
        this.backWheelLeft = hardwareMap.dcMotor.get("back wheel left");
        this.backWheelRight = hardwareMap.dcMotor.get("back wheel right");

        this.lift = hardwareMap.dcMotor.get("lift");
        this.flicker = hardwareMap.dcMotor.get("paddle");

        this.ods1 = hardwareMap.opticalDistanceSensor.get("ods1");
        this.ods2 = hardwareMap.opticalDistanceSensor.get("ods2");

        this.colorSensor = hardwareMap.colorSensor.get("cs");

        this.ods1.enableLed(true);
        this.ods2.enableLed(true);
        this.colorSensor.enableLed(true);

        this.frontWheelLeft.setPower(0.0);
        this.frontWheelRight.setPower(0.0);
        this.backWheelLeft.setPower(0.0);
        this.backWheelRight.setPower(0.0);

        this.lift.setPower(0.0);
        this.flicker.setPower(0.0);

        this.frontWheelLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.frontWheelRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.backWheelLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.backWheelRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.flicker.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.direction = 1.0;
        this.multi = 1.0;
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
        return hardwareMap;
    }

    public ElapsedTime getElapsedTime() {
        return elapsedTime;
    }

    public DcMotor getFrontWheelLeft() {
        return frontWheelLeft;
    }

    public DcMotor getFrontWheelRight() {
        return frontWheelRight;
    }

    public DcMotor getBackWheelLeft() {
        return backWheelLeft;
    }

    public DcMotor getBackWheelRight() {
        return backWheelRight;
    }

    public DcMotor getLift() {
        return lift;
    }

    public DcMotor getFlicker() {
        return flicker;
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

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getMulti() {
        return multi;
    }

    public void setMulti(double multi) {
        this.multi = multi;
    }
}