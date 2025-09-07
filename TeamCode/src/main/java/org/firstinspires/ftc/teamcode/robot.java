package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class robot {
    public DcMotor intake;
    public DcMotor elevator;
    public DcMotor shooter;
    public double intakeSpeed = 1;
    public double elevatorSpeed = 1;
    public double shooterSpeed = 1;

    public robot(HardwareMap hardwareMap){
        intake = hardwareMap.get(DcMotor.class, "intake");
        elevator = hardwareMap.get(DcMotor.class, "elevator");
        shooter = hardwareMap.get(DcMotor.class, "shooter");
    }
    public void intakeOn(){
        intake.setPower(intakeSpeed);
    }
    public void elevatorOn(){
        elevator.setPower(elevatorSpeed);
    }
    public void shooterOn(){
        shooter.setPower(shooterSpeed);
    }
}
