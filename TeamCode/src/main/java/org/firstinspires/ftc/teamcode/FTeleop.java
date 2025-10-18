package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;

@TeleOp
public class FTeleop extends LinearOpMode {
    public enum shootState{
        FLYWHEEL_ON,
        FLYWHEEL_OFF,
        INTAKE_ON,
        KICKER_KICK,
        INTAKE_OFF,

    }
    shootState shoot = shootState.FLYWHEEL_OFF;
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("fl");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("bl");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("fr");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("br");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        Outtake outtake = new Outtake(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        ElapsedTime timer = new ElapsedTime();
        ElapsedTime flywheelTimer = new ElapsedTime();
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            if(gamepad1.left_bumper){
                y *=.3;
                x *=.3;
                rx *=.3;
            }
            else if(gamepad1.right_bumper){

            }
            else{
                x *= 0.8;
                y *= 0.8;
                rx *= 0.8;
            }


            if (gamepad2.right_trigger>0&&timer.milliseconds()>250){

                outtake.xturret.setPosition(outtake.xturret.getPosition()+.05);
                timer.reset();
            }
            else if (gamepad2.left_trigger>0&&timer.milliseconds()>250){

                outtake.xturret.setPosition(outtake.xturret.getPosition()-.05);
                timer.reset();
            }


            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.options) {
                imu.resetYaw();
            }
            switch (shoot){
                case FLYWHEEL_OFF:
                    intake.gate.setPosition(.9);
                    outtake.flywheel1.setPower(0);
                    if(gamepad2.right_bumper){
                        timer.reset();
                        shoot = shootState.FLYWHEEL_ON;
                    }
                case FLYWHEEL_ON:
                    outtake.flywheel1.setPower(-.7);
                    if(timer.milliseconds()>1000){
                        shoot = shootState.INTAKE_ON;
                    }

                case INTAKE_ON:
                    intake.intake.setPower(-.9);
                    if(timer.milliseconds()>500){
                        shoot = shootState.KICKER_KICK;
                    }
                case KICKER_KICK:
                    intake.gate.setPosition(.5);
                    intake.intake.setPower(0);
                    if(timer.milliseconds() > 200){
                        shoot = shootState.FLYWHEEL_OFF;
                    }
            }

            if (gamepad1.a){
                intake.intake.setPower(-0.9);
            }
            if (gamepad1.b){
                intake.intake.setPower(0);
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
        }
    }
}