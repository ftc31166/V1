package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MeepMeepTesting {
    public static void main(String[] args) {
        // blue1: heading = 0, x=-50, y=-49
        // red1: heading = 0, x=-50, y=49
        // red2: heading = 3.14159, x=60, y=10
        // blue2: heading = 3.14159, x=60, y=-10
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(60, -10, 3.14159))
                        .splineToLinearHeading(new Pose2d(35, -29, Math.toRadians(270)), Math.toRadians(-180))
                        .waitSeconds(1)
                        .forward(5)
                        .waitSeconds(1)
                        .forward(5)
                        .waitSeconds(1)
                        .back(25)
                        .splineToLinearHeading(new Pose2d(0, 0, Math.toRadians(225)), Math.toRadians(-180))
                        .forward(65)
                        .waitSeconds(2)
                        .back(65)
                        .splineToLinearHeading(new Pose2d(-24, 54, Math.toRadians(0)), Math.toRadians(50))
                        .waitSeconds(1)
                        .strafeRight(5)
                        .waitSeconds(1)
                        .strafeRight(5)
                        .waitSeconds(1)
                        .splineToLinearHeading(new Pose2d(-42, -50, Math.toRadians(225)), Math.toRadians(-50))
                        .splineToLinearHeading(new Pose2d(50, -35, Math.toRadians(225)), Math.toRadians(200))
                        .build());

        Image img = null;
        try { img = ImageIO.read(new File("C:\\Users\\User\\Downloads\\decode.png")); }
        catch(IOException e) {}

        meepMeep.setBackground(img)
                .addEntity(myBot)
                .start();
//        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
//                .setDarkMode(true)
//                .setBackgroundAlpha(0.95f)
//                .addEntity(myBot)
//                .start();
    }
}