package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Hardware;

public class Hardware12285 implements Hardware {

    private HardwareMap hardwareMap;
    private ElapsedTime elapsedTime = new ElapsedTime();

    private DcMotor lift;
    private DcMotor paddle;

    private DcMotor wheelLeft;
    private DcMotor wheelRight;
    private DcMotor leftWheel;
    private DcMotor rightWheel;

    @Override
    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        this.lift = hardwareMap.dcMotor.get("lift");
        this.paddle = hardwareMap.dcMotor.get("paddle");

        this.wheelLeft = hardwareMap.dcMotor.get("wheel left");
        this.wheelRight = hardwareMap.dcMotor.get("wheel right");
        this.leftWheel = hardwareMap.dcMotor.get("left wheel");
        this.rightWheel = hardwareMap.dcMotor.get("right wheel");

        lift.setPower(0.0);
        paddle.setPower(0.0);

        wheelLeft.setPower(0.0);
        wheelRight.setPower(0.0);

        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        paddle.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        wheelLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
        return null;
    }

    public ElapsedTime getElapsedTime() {
        return elapsedTime;
    }

    public DcMotor getLift() {
        return lift;
    }

    public DcMotor getPaddle() {
        return paddle;
    }

    public DcMotor getWheelLeft() {
        return wheelLeft;
    }

    public DcMotor getWheelRight() {
        return wheelRight;
    }

    public DcMotor getLeftWheel() {
        return leftWheel;
    }

    public DcMotor getRightWheel() {
        return rightWheel;
    }


}