package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface Hardware {

    void init(HardwareMap hardwareMap);

    void waitForTick(long milliseconds) throws InterruptedException;

    HardwareMap getHardwareMap();

}