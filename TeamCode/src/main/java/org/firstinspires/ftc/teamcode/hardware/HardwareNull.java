package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.Hardware;

public class HardwareNull implements Hardware {

    @Override
    public void init(HardwareMap hardwareMap) {

    }

    @Override
    public void waitForTick(long milliseconds) throws InterruptedException {

    }

    @Override
    public HardwareMap getHardwareMap() {
        return null;
    }

}