package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.hardware.Hardware10863;
import org.firstinspires.ftc.teamcode.hardware.Hardware12285;

public class EncoderDrive {

    private Team team;
    private Hardware hardware;
    private double countsPerMotorRev;
    private double driveGearReduction;
    private double wheelDiameterInches;
    private double countsPerInch;
    private double driveSpeed;
    private double turnSpeed;

    // Default counts per motor rev is 1440, gear reduction is 2.0, drive speed is 0.6, and turn speed is 0.5
    public EncoderDrive(Team team, double countsPerMotorRev, double driveGearReduction, double driveSpeed, double turnSpeed) {
        this.team = team;
        this.countsPerMotorRev = countsPerMotorRev;
        this.driveGearReduction = driveGearReduction;
        this.driveSpeed = driveSpeed;
        this.turnSpeed = turnSpeed;

        switch (team) {
            case TEAM_12285:
                this.wheelDiameterInches = 2.5625;
                this.hardware = new Hardware12285();
                break;
            case TEAM_10863:
                this.wheelDiameterInches = 4.0;
                this.hardware = new Hardware10863();
                break;
        }

        this.countsPerInch = (countsPerMotorRev * driveGearReduction) / (wheelDiameterInches * 3.1415);
    }

    public double getCountsPerMotorRev() {
        return countsPerMotorRev;
    }

    public void setCountsPerMotorRev(double countsPerMotorRev) {
        this.countsPerMotorRev = countsPerMotorRev;
    }

    public double getDriveGearReduction() {
        return driveGearReduction;
    }

    public void setDriveGearReduction(double driveGearReduction) {
        this.driveGearReduction = driveGearReduction;
    }

    public double getDriveSpeed() {
        return driveSpeed;
    }

    public void setDriveSpeed(double driveSpeed) {
        this.driveSpeed = driveSpeed;
    }

    public double getTurnSpeed() {
        return turnSpeed;
    }

    public void setTurnSpeed(double turnSpeed) {
        this.turnSpeed = turnSpeed;
    }

    public Team getTeam() {
        return team;
    }

    public double getWheelDiameterInches() {
        return wheelDiameterInches;
    }

    public double getCountsPerInch() {
        return countsPerInch;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void drive(double speed, double leftInches, double rightInches, double timeoutS, LinearOpMode opMode) throws InterruptedException {
        int newLeftTarget;
        int newRightTarget;

        DcMotor left = hardware.getHardwareMap().dcMotor.get("left wheel");
        DcMotor right = hardware.getHardwareMap().dcMotor.get("right wheel");

        // Determine new target position, and pass to motor controller
        newLeftTarget = left.getCurrentPosition() + (int)(leftInches * countsPerInch);
        newRightTarget = right.getCurrentPosition() + (int)(rightInches * countsPerInch);
        left.setTargetPosition(newLeftTarget);
        right.setTargetPosition(newRightTarget);

        // Turn On RUN_TO_POSITION
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        left.setPower(Math.abs(speed));
        right.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        while (left.isBusy() && right.isBusy()) {

            // Display it for the driver.
            opMode.telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
            opMode.telemetry.addData("Path2",  "Running at %7d :%7d", left.getCurrentPosition(),
                    right.getCurrentPosition());
            opMode.telemetry.update();

            // Allow time for other processes to run.
            opMode.idle();
        }

        // Stop all motion;
        left.setPower(0);
        right.setPower(0);

        // Turn off RUN_TO_POSITION
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}