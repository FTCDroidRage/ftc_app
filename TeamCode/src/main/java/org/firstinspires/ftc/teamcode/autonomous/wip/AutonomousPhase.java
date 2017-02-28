package org.firstinspires.ftc.teamcode.autonomous.wip;

public enum AutonomousPhase {

    // Global
    WAITING("Waiting..."),

    // Beacon driving
    DRIVING_FROM_START("Driving to the first beacon..."),
    TURNING_TO_BEACON1("Turning to the first beacon..."),
    DRIVING_TO_BEACON1("Slowly driving up to the first beacon..."),
    BACKING_UP1("Backing up from the first beacon..."),
    TURNING_FROM_BEACON1("Turning away from the first beacon..."),
    DRIVING_FROM_BEACON1("Driving to the second beacon..."),
    TURNING_TO_BEACON2("Turning to the second beacon..."),
    DRIVING_TO_BEACON2("Slowly driving up to the second beacon..."),
    BACKING_UP2("Backing up from the second beacon..."),
    TURNING_TO_CENTER("Turning towards the center vortex..."),
    DRIVING_TO_CENTER("Driving to the center vortex..."),
    ADJUSTING_TO_SHOOT("Adjusting robot to shoot particles..."),
    SHOOTING_PARTICLES("Launching particles..."),
    TURNING_TO_CORNER("Turning towards the corner vortex..."),
    DRIVING_TO_CORNER("Driving to the corner vortex..."),
    GRABBING_CORNER("Grabbing on to the corner vortex..."),

    // Simple driving
    DRIVING_FORWARD("Driving forward..."),

    // Global
    COMPLETED("Completed.");

    private String displayMsg;

    AutonomousPhase(String displayMsg) {
        this.displayMsg = displayMsg;
    }

    public String getDisplayMsg() {
        return displayMsg;
    }

}