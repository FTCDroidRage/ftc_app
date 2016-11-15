package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hardware10863 implements Hardware {

    private DcMotor leftWheel;
    private DcMotor rightWheel;
    private DcMotor leftIntake;
    private DcMotor rightIntake;

    private CRServo arm;

    private HardwareMap hardwareMap;
    private ElapsedTime elapsedTime = new ElapsedTime();

    public Hardware10863() {

    }

    public DcMotor getLeftWheel() {
        return leftWheel;
    }

    public DcMotor getRightWheel() {
        return rightWheel;
    }

    public DcMotor getLeftIntake() {
        return leftIntake;
    }

    public DcMotor getRightIntake() {
        return rightIntake;
    }

    public CRServo getArm() {
        return arm;
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        this.leftWheel = hardwareMap.dcMotor.get("left wheel");
        this.rightWheel = hardwareMap.dcMotor.get("right wheel");
        this.leftIntake = hardwareMap.dcMotor.get("left intake");
        this.rightIntake = hardwareMap.dcMotor.get("right intake");

        this.arm = hardwareMap.crservo.get("arm");

        leftWheel.setPower(0.0);
        rightWheel.setPower(0.0);
        leftIntake.setPower(0.0);
        rightIntake.setPower(0.0);

        leftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
