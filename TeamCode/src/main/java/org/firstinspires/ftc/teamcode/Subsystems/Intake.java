package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    public DcMotor intake;
    public DcMotor transfer;
    public Intake(HardwareMap hardwareMap){
        transfer = hardwareMap.get(DcMotor.class,"transfer");
        intake = hardwareMap.get(DcMotor.class,"intake");
    }

}
