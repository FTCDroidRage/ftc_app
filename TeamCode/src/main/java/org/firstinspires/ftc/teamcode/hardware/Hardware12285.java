package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Hardware;

public class Hardware12285 implements Hardware {

    private DcMotor leftWheel;
    private DcMotor rightWheel;
    // private DcMotor sweeper;
    private DcMotor lift;

    private Servo grabber;

    // private OpticalDistanceSensor opticalDistanceSensor1;
    // private OpticalDistanceSensor opticalDistanceSensor2;

    private HardwareMap hardwareMap;
    private ElapsedTime elapsedTime = new ElapsedTime();

    public Hardware12285() {

    }

    public Servo getGrabber() {
        return grabber;
    }

    public DcMotor getLeftWheel() {
        return leftWheel;
    }

    public DcMotor getRightWheel() {
        return rightWheel;
    }

    public DcMotor getLift() {
        return lift;
    }

    /* public DcMotor getSweeper() {
        return sweeper;
    }

    public OpticalDistanceSensor getOpticalDistanceSensor1() {
        return opticalDistanceSensor1;
    }

    public OpticalDistanceSensor getOpticalDistanceSensor2() {
        return opticalDistanceSensor2;
    } */

    @Override
    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        this.leftWheel = hardwareMap.dcMotor.get("left wheel");
        this.rightWheel = hardwareMap.dcMotor.get("right wheel");
        // this.sweeper = hardwareMap.dcMotor.get("sweepers");
        this.lift = hardwareMap.dcMotor.get("lift");

        this.grabber = hardwareMap.servo.get("grabber");

        // this.opticalDistanceSensor1 = hardwareMap.opticalDistanceSensor.get("ods1");
        // this.opticalDistanceSensor2 = hardwareMap.opticalDistanceSensor.get("ods2");

        leftWheel.setPower(0.0);
        rightWheel.setPower(0.0);
        // sweeper.setPower(0.0);
        lift.setPower(0.0);

        // opticalDistanceSensor1.enableLed(false);
        // opticalDistanceSensor2.enableLed(false);

        leftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // sweeper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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

}
