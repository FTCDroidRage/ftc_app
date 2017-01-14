package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Hardware;

public class TestHardware implements Hardware {

    private ElapsedTime elapsedTime;
    private HardwareMap robot;

    private DcMotor leftWheel;
    private DcMotor rightWheel;

    private DcMotor leftLaunch;
    private DcMotor rightLaunch;

    @Override
    public void init(HardwareMap hardwareMap) {
        this.elapsedTime = new ElapsedTime();
        this.robot = hardwareMap;

        this.leftWheel = robot.dcMotor.get("left wheel");
        this.rightWheel = robot.dcMotor.get("right wheel");

        this.leftLaunch = robot.dcMotor.get("left launch");
        this.rightLaunch = robot.dcMotor.get("right launch");

        leftLaunch.setPower(0.0);
        rightLaunch.setPower(0.0);

        leftWheel.setPower(0.0);
        rightWheel.setPower(0.0);

        leftLaunch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightLaunch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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
        return robot;
    }

    public DcMotor getLeftWheel() {
        return leftWheel;
    }

    public DcMotor getRightWheel() {
        return rightWheel;
    }

    public DcMotor getLeftLaunch() {
        return leftLaunch;
    }

    public DcMotor getRightLaunch() {
        return rightLaunch;
    }

}