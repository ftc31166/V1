package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class AutonFuncs extends LinearOpMode {

    Intake intake;
    Outtake outtake;

    public AutonFuncs(HardwareMap hardwareMap) {
        intake = new Intake(hardwareMap);
        outtake = new Outtake(hardwareMap);
    }

    public Action setServo(double pos){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                outtake.xturret.setPosition(pos);
                return false;
            }
        };
    }

    public Action detectAprilTag(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                outtake.detectAprilTag();
                return false;
            }
        };
    }
    public Action intakeOn(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                intake.gate.setPosition(0);
                intake.intake.setPower(-0.9);
                return false;
            }
        };
    }
    public Action intakeOff(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                intake.intake.setPower(0);
                return false;
            }
        };
    }
    public Action shooterOff(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                outtake.flywheel1.setPower(0);
                intake.intake.setPower(0);
                return false;
            }
        };
    }
    public Action shooterOn(){
        return new Action() {
            ElapsedTime timer = new ElapsedTime();
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                outtake.flywheel1.setPower(-.7);
                intake.gate.setPosition(1);
                while (timer.milliseconds() < 500){

                }
                intake.intake.setPower(-0.9);
                return false;
            }
        };
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
