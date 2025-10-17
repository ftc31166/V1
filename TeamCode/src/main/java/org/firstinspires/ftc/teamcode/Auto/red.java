package org.firstinspires.ftc.teamcode.teleops;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.moreTuning.PinpointDrive;
import org.firstinspires.ftc.teamcode.Subsystems.AutonFuncs;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@Autonomous(name = "", group = "Autonomous")
public class red extends LinearOpMode {

    @Override
    public void runOpMode() {
        Pose2d startpos = new Pose2d(-50, 52, Math.toRadians(90));
        AutonFuncs robot = new AutonFuncs(hardwareMap);
        PinpointDrive drive = new PinpointDrive(hardwareMap,startpos);
        // vision here that outputs position


        TrajectoryActionBuilder driveAndDetect = drive.actionBuilder(startpos)
                .strafeTo(new Vector2d(-10,11))
                .stopAndAdd(robot.detectAprilTag())
                .stopAndAdd(robot.setServo(.25))
                .stopAndAdd(robot.shooterOn())
                .stopAndAdd(new SleepAction(2))
                .stopAndAdd(robot.shooterOff());




        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Vector2d(-10,11))
                .lineToY(37)
                .setTangent(Math.toRadians(0))
                .lineToX(18)
                .waitSeconds(3)
                .setTangent(Math.toRadians(0))
                .lineToXSplineHeading(46, Math.toRadians(180))
                .waitSeconds(3);
        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Vector2d(-10,11))
                .lineToYSplineHeading(33, Math.toRadians(180))
                .waitSeconds(2)
                .strafeTo(new Vector2d(46, 30))
                .waitSeconds(3);


        // actions that need to happen on init; for instance, a claw tightening.
        Actions.runBlocking(claw.closeClaw());




        int startPosition = visionOutputPosition;
        telemetry.addData("Starting Position", startPosition);
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        Action trajectoryActionChosen;
        if (startPosition == 1) {
            trajectoryActionChosen = tab1.build();
        } else if (startPosition == 2) {
            trajectoryActionChosen = tab2.build();
        } else {
            trajectoryActionChosen = tab3.build();
        }

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen,

                        lift.liftUp(),
                        claw.openClaw(),
                        lift.liftDown(),
                        trajectoryActionCloseOut
                )
        );
    }
}