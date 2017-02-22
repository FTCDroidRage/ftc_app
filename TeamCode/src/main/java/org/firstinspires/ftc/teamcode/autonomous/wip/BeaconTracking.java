package org.firstinspires.ftc.teamcode.autonomous.wip;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.hardware.Hardware12285;
import org.firstinspires.ftc.teamcode.util.VuforiaUtil;

// @Autonomous(name="[WIP] Beacon Tracking", group="Linear Opmode")
public class BeaconTracking extends LinearOpMode {

    private Hardware12285 robot;

    private double countsPerMotorRev = 1440;
    private double driveGearReduction = 2.0;
    private double wheelDiameterInches = 4.0;
    private double countsPerInch = (countsPerMotorRev * driveGearReduction) / (wheelDiameterInches * 3.1415);

    @Override
    public void runOpMode() throws InterruptedException {
        this.robot = new Hardware12285();

        robot.init(hardwareMap);

        // Setup Vuforia
        VuforiaUtil vuforiaUtil = new VuforiaUtil();

        vuforiaUtil.setLastKnownLocation(VuforiaUtil.createMatrix(0, 0, 0, 0, 0, 0));

        waitForStart();

        while (opModeIsActive()) {
            OpenGLMatrix lastLocation = vuforiaUtil.getListener().getUpdatedRobotLocation();

            if (lastLocation != null) {
                vuforiaUtil.setLastKnownLocation(lastLocation);
            }

            float[] coordinates = vuforiaUtil.getLastKnownLocation().getData();

            vuforiaUtil.setRobotX(coordinates[0]);
            vuforiaUtil.setRobotY(coordinates[1]);
            vuforiaUtil.setRobotAngle(Orientation.getOrientation(vuforiaUtil.getLastKnownLocation(), AxesReference.EXTRINSIC,
                    AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle);

            telemetry.addData("Tracking " + vuforiaUtil.getTarget().getName(), vuforiaUtil.getListener().isVisible());
            telemetry.addData("Last Known Location", VuforiaUtil.formatMatrix(vuforiaUtil.getLastKnownLocation()));

            telemetry.update();

        }

        robot.getWheelLeft().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.getWheelRight().setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.getWheelLeft().setPower(0.5);
        robot.getWheelRight().setPower(0.5);

        while (opModeIsActive() && vuforiaUtil.getListener().getRawPose() == null) {
            idle();
        }

        robot.getWheelLeft().setPower(0.5);
        robot.getWheelRight().setPower(0.5);

        VectorF angles = VuforiaUtil.anglesFromTarget(vuforiaUtil.getListener());
        VectorF trans = null;

        if (vuforiaUtil.getListener().getPose() != null && angles != null) {
            trans = VuforiaUtil.navOffWall(vuforiaUtil.getListener().getPose().getTranslation(),
                    Math.toDegrees(angles.get(0)) - 90,
                    new VectorF(500, 0, 0));
        }

        if (trans != null) {
            if (trans.get(0) > 0) {
                robot.getWheelLeft().setPower(0.5);
                robot.getWheelRight().setPower(-0.5);
            } else {
                robot.getWheelLeft().setPower(-0.5);
                robot.getWheelRight().setPower(0.5);
            }

            do {
                if (vuforiaUtil.getListener().getPose() != null) {
                    trans = VuforiaUtil.navOffWall(vuforiaUtil.getListener().getPose().getTranslation(),
                            Math.toDegrees(angles.get(0)) - 90,
                            new VectorF(500, 0, 0));
                    idle();
                }
            } while ( opModeIsActive() && Math.abs(trans.get(0)) > 30);

            robot.getWheelLeft().setPower(0.0);
            robot.getWheelRight().setPower(0.0);

            robot.getWheelLeft().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.getWheelRight().setMode(DcMotor.RunMode.RUN_TO_POSITION);

            robot.getWheelLeft().setTargetPosition((int) (robot.getWheelLeft().getCurrentPosition() +
                    ((Math.hypot(trans.get(0), trans.get(2)) + 150) / countsPerInch)));
            robot.getWheelRight().setTargetPosition((int) (robot.getWheelRight().getCurrentPosition() +
                    ((Math.hypot(trans.get(0), trans.get(2)) + 150) / countsPerInch)));

            robot.getWheelLeft().setPower(0.5);
            robot.getWheelRight().setPower(0.5);

            while (opModeIsActive() && robot.getWheelLeft().isBusy() && robot.getWheelRight().isBusy()) {
                idle();
            }

            robot.getWheelLeft().setPower(0.0);
            robot.getWheelRight().setPower(0.0);
        }

        robot.getWheelLeft().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.getWheelRight().setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (opModeIsActive() && vuforiaUtil.getListener().getPose() == null
                || Math.abs(vuforiaUtil.getListener().getPose().getTranslation().get(0)) > 10) {
            if (vuforiaUtil.getListener() != null) {
                if (vuforiaUtil.getListener().getPose().getTranslation().get(0) > 0) {
                    robot.getWheelLeft().setPower(0.5);
                    robot.getWheelRight().setPower(-0.5);
                } else {
                    robot.getWheelLeft().setPower(-0.5);
                    robot.getWheelRight().setPower(0.5);
                }
            } else {
                robot.getWheelLeft().setPower(-0.5);
                robot.getWheelRight().setPower(0.5);
            }
        }

        robot.getWheelLeft().setPower(0.0);
        robot.getWheelRight().setPower(0.0);
    }

}