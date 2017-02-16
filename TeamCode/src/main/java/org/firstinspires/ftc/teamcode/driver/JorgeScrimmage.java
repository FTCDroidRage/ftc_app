package org.firstinspires.ftc.teamcode.driver;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */


@TeleOp(name="hii", group="Linear Opmode")
public class JorgeScrimmage extends OpMode {

   /*
    * Note: the configuration of the servos is such that
    * as the arm servo approaches 0, the arm position moves up (away from the floor).
    * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
    */
    // TETRIX VALUES.

    DcMotor     wheelRight;
    DcMotor     wheelLeft;
    DcMotor     slapperRight;
    DcMotor     slapperLeft;

    /**
     * Constructor
     */
    public JorgeScrimmage() {

    }

    /*
        * Code to run when the op mode is first enabled goes here
        *
        * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
        */
    @Override
    public void init() {
      /*
       * Use the hardwareMap to get the dc motors and servos by name. Note
       * that the names of the devices must match the names used when you
       * configured your robot and created the configuration file.
       */

      /*
       * For the demo Tetrix K9 bot we assume the following,
       *   There are two motors "motor_1" and "motor_2"
       *   "motor_1" is on the right side of the bot.
       *   "motor_2" is on the left side of the bot.
       *
       * We also assume that there are two servos "servo_1" and "servo_6"
       *    "servo_1" controls the arm joint of the manipulator.
       *    "servo_6" controls the claw joint of the manipulator.
       */
        wheelRight = hardwareMap.dcMotor.get("right_wheel");
        wheelLeft = hardwareMap.dcMotor.get("left_wheel");

        slapperLeft = hardwareMap.dcMotor.get("left_plate");
        slapperRight = hardwareMap.dcMotor.get("right_plate");

        wheelLeft.setDirection(DcMotor.Direction.REVERSE);
        slapperLeft.setDirection(DcMotor.Direction.REVERSE);


        // assign the starting position of the wrist and claw


    }

    /*
        * This method will be called repeatedly in a loop
        *
        * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
        */
    @Override
    public void loop() {

      /*
       * Gamepad 1
       *
       * Gamepad 1 controls the motors via the left stick, and it controls the
       * wrist/claw via the a,b, x, y buttons
       */

        // tank drive
        // note that if y equal -1 then joystick is pushed all of the way forward.//
        double left = -gamepad1.left_stick_y;
        double right = -gamepad1.right_stick_y;
        double slapLeft = gamepad2.left_stick_y;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right,-1, 1);
        left = Range.clip(left, -1, 1);
        slapLeft = Range.clip(slapLeft, -.5, .5);




        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.


        // write the values to the motors
        wheelRight.setPower(right);
        wheelLeft.setPower(left);
        slapperLeft.setPower(slapLeft);
        slapperRight.setPower(slapLeft);
    }
}