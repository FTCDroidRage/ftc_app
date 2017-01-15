package org.firstinspires.ftc.teamcode.util;

import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;

public class VuforiaUtil {

    private VuforiaLocalizer vuforiaLocalizer;
    private VuforiaLocalizer.Parameters parameters;
    private VuforiaTrackables visionTargets;
    private VuforiaTrackable target;
    private VuforiaTrackableDefaultListener listener;

    private OpenGLMatrix lastKnownLocation;
    private OpenGLMatrix phoneLocation;

    private static final String VUFORIA_KEY = "Ac+mSSz/////AAAAGX7qWAluukJCne3f/WMl3lR041QLVsuBByABDY/OoLY4P/" +
            "aqR+qbKcEfjdWYffl1UKkPGmkNiYOLwRRAjFMowUftC92bbpf7Y0N+Tsz772/ETwHx3LO" +
            "YWpCM6okjazmdOGg7umVHWc9E4GiSri3dCFvV/4qm0uJVF4OE7eEbQ9g1qwipSQVMDQCkQ" +
            "vnpsrHOJ26ya9KUw/pyTZyBwqXdfZiRK4jB5EK4a2laRO0UIuNfW+wM7lRZILMRulNVyVF2x" +
            "c5Mxo8VAj2OjwBzmUXaCZxTajj8PgrPPU6yqZT+YxXJbcCem22E70urP6yrffwy2AFD6d3LGVYc3" +
            "hA9mTJEGp5G9UFWzHPcCh9k5+kjH6o8T";

    private float robotX = 0;
    private float robotY = 0;
    private float robotAngle = 0;

    public VuforiaUtil() {
        this.parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        parameters.useExtendedTracking = false;

        this.vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters);
        this.visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("FTC_2016-2017");
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        target = visionTargets.get(0);
        target.setName("Wheels Target");
        target.setLocation(createMatrix(0, 500, 0, 90, 0, 90));

        phoneLocation = createMatrix(0, 225, 0, 90, 0, 0);

        this.listener = (VuforiaTrackableDefaultListener) target.getListener();
        listener.setPhoneInformation(phoneLocation, parameters.cameraDirection);
    }

    public static OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w) {
        return OpenGLMatrix.translation(x, y, z).multiplied(Orientation.getRotationMatrix(
                AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, u, v, w));
    }

    public static String formatMatrix(OpenGLMatrix matrix) {
        return matrix.formatAsTransform();
    }

    public OpenGLMatrix getPhoneLocation() {
        return phoneLocation;
    }

    public OpenGLMatrix getLastKnownLocation() {
        return lastKnownLocation;
    }

    public VuforiaTrackableDefaultListener getListener() {
        return listener;
    }

    public VuforiaTrackable getTarget() {
        return target;
    }

    public VuforiaTrackables getVisionTargets() {
        return visionTargets;
    }

    public VuforiaLocalizer.Parameters getParameters() {
        return parameters;
    }

    public VuforiaLocalizer getVuforiaLocalizer() {
        return vuforiaLocalizer;
    }

    public float getRobotX() {
        return robotX;
    }

    public float getRobotY() {
        return robotY;
    }

    public float getRobotAngle() {
        return robotAngle;
    }

    public void setTarget(VuforiaTrackable target) {
        this.target = target;
    }

    public void setLastKnownLocation(OpenGLMatrix lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public void setPhoneLocation(OpenGLMatrix phoneLocation) {
        this.phoneLocation = phoneLocation;
    }

    public void setRobotX(float robotX) {
        this.robotX = robotX;
    }

    public void setRobotY(float robotY) {
        this.robotY = robotY;
    }

    public void setRobotAngle(float robotAngle) {
        this.robotAngle = robotAngle;
    }

    public static VectorF navOffWall(VectorF trans, double robotAngle, VectorF offWall) {
        return new VectorF((float) (trans.get(0) - offWall.get(0) * Math.sin(Math.toRadians(robotAngle)) - offWall.get(2)
                * Math.cos(Math.toRadians(robotAngle))), trans.get(1),
                (float) (trans.get(2) + offWall.get(0) * Math.cos(Math.toRadians(robotAngle))
                        - offWall.get(2) * Math.sin(Math.toRadians(robotAngle))));
    }

    public static VectorF anglesFromTarget(VuforiaTrackableDefaultListener image) {
        if (image.getRawPose() == null) return null;

        float[] data = image.getRawPose().getData();
        float[][] rotation = {{data[0], data[1]}, {data[4], data[5], data[6]}, {data[8], data[9], data[10]}};

        double thetaX = Math.atan2(rotation[2][1], rotation[2][2]);
        double thetaY = Math.atan2(-rotation[2][0], Math.sqrt(rotation[2][1] * rotation[2][1] + rotation[2][2] * rotation[2][2]));
        double thetaZ = Math.atan2(rotation[1][0], rotation[0][0]);

        return new VectorF((float)thetaX, (float)thetaY, (float)thetaZ);
    }

}