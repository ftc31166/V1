package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.AutonFuncs;
import org.firstinspires.ftc.teamcode.Subsystems.Constants;
import org.firstinspires.ftc.teamcode.moreTuning.PinpointDrive;

@Config
@Autonomous(name = "", group = "Autonomous")
public class blue extends LinearOpMode {

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




        TrajectoryActionBuilder GPP_PPG = drive.actionBuilder(new Pose2d(-10,-11,Math.toRadians(90)))
                .stopAndAdd(robot.intakeOn())
                .setTangent(Math.toRadians(-10))
                .splineToConstantHeading(new Vector2d(35,50),Math.toRadians(90))
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(robot.intakeOff())
                .strafeTo(new Vector2d(-10,-11))
                .stopAndAdd(robot.shooterOn())
                .stopAndAdd(new SleepAction(2))
                .stopAndAdd(robot.shooterOff())
                .stopAndAdd(robot.intakeOn())
                .strafeTo(new Vector2d(-10,-50))
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(robot.intakeOff())
                .strafeTo(new Vector2d(-10,-11))
                .stopAndAdd(robot.shooterOn())
                .stopAndAdd(new SleepAction(2))
                .stopAndAdd(robot.shooterOff())
                .setTangent(Math.toRadians(45))
                .splineToConstantHeading(new Vector2d(0,-50),Math.toRadians(90));
        TrajectoryActionBuilder PGP= drive.actionBuilder(new Pose2d(-10,-11,Math.toRadians(90)))
                .stopAndAdd(robot.intakeOn())
                .strafeTo(new Vector2d(-10,-50))
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(robot.intakeOff())
                .strafeTo(new Vector2d(-10,-11))
                .stopAndAdd(robot.shooterOn())
                .stopAndAdd(new SleepAction(2))
                .stopAndAdd(robot.shooterOff())
                .setTangent(0)
                .stopAndAdd(robot.intakeOn())
                .splineToConstantHeading(new Vector2d(10,-50),Math.toRadians(-90))
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(robot.intakeOff())
                .strafeTo(new Vector2d(-10,-11))
                .stopAndAdd(robot.shooterOn())
                .stopAndAdd(new SleepAction(2))
                .stopAndAdd(robot.shooterOff())
                .setTangent(Math.toRadians(45))
                .splineToConstantHeading(new Vector2d(0,-50),Math.toRadians(-90));

        // actions that need to happen on init; for instance, a claw tightening.

        waitForStart();

        if (isStopRequested()) return;



        Actions.runBlocking(
                new SequentialAction(
                        driveAndDetect.build()
                )
        );
        Action presetObelisk;
        if (Constants.tagID == 22) {
            presetObelisk = PGP.build();
        } else {
            presetObelisk = GPP_PPG.build();
        }
        Actions.runBlocking(presetObelisk);
    }
}