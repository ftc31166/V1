package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public DcMotor intake;
    public Servo gate;
    public Intake(HardwareMap hardwareMap){

        intake = hardwareMap.get(DcMotor.class,"intake");
        gate = hardwareMap.get(Servo.class,"gate");
    }
}
