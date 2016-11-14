package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hardware12285 {

    private DcMotor leftWheel;
    private DcMotor rightWheel;
    private DcMotor sweeper;
    private DcMotor lift;

    private HardwareMap hardwareMap;
    private ElapsedTime elapsedTime = new ElapsedTime();

    public Hardware12285() {

    }

    public DcMotor getLeftWheel() {
        return leftWheel;
    }

    public DcMotor getRightWheel() {
        return rightWheel;
    }

    public DcMotor getSweeper() {
        return sweeper;
    }

    public DcMotor getLift() {
        return lift;
    }

    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        this.leftWheel = hardwareMap.dcMotor.get("left wheel");
        this.rightWheel = hardwareMap.dcMotor.get("right wheel");
        this.sweeper = hardwareMap.dcMotor.get("sweepers");
        this.lift = hardwareMap.dcMotor.get("lift");

        leftWheel.setPower(0.0);
        rightWheel.setPower(0.0);
        sweeper.setPower(0.0);
        lift.setPower(0.0);

        leftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        sweeper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void waitForTick(long milliseconds) throws InterruptedException {

        long  remaining = milliseconds - (long) elapsedTime.milliseconds();

        if (remaining > 0)
            Thread.sleep(remaining);

        elapsedTime.reset();
    }

}
