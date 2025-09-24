package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@TeleOp
public class MotorTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor motor = hardwareMap.get(DcMotor.class,"motor");
        ElapsedTime timer = new ElapsedTime();
        if (isStopRequested()) return;
        double power = 0;
        waitForStart();
        while (opModeIsActive()){
            if(gamepad1.a){
                motor.setPower(power);
            }if(gamepad1.b){
                motor.setPower(0);
            }
            if(gamepad1.dpad_right){
               power += .1;
                if(timer.milliseconds()<300){

                }
            }
            if(gamepad1.dpad_left){
                power -= .1;
                if(timer.milliseconds()<300){

                }
            }


            telemetry.addData("Motor Power", power);
            telemetry.update();
        }
    }
}
