package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    public DcMotor intake;
    public Intake(HardwareMap hardwareMap){
        intake = hardwareMap.get(DcMotor.class,"intake");
    }
}
