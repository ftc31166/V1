package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MeepMeepTestingMore {
    public static void main(String[] args) {
        // blue1: heading = 0, x=-50, y=-49
        // red1: heading = 0, x=-50, y=49
        // red2: heading = 3.14159, x=60, y=10
        // blue2: heading = 3.14159, x=60, y=-10
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-45, -45, Math.toRadians(-90)))
                        .back(10)
                        .splineToConstantHeading(new Vector2d(-12, -29), Math.toRadians(-10))
                        .waitSeconds(1)
                        .forward(5)
                        .waitSeconds(1)
                        .forward(5)
                        .waitSeconds(1)
                        .back(10)
                        .splineToConstantHeading(new Vector2d(-45, -45), Math.toRadians(180))
                        .waitSeconds(2)
                        .splineToLinearHeading(new Pose2d(0, -17, Math.toRadians(180)), Math.toRadians(270))
                        .lineToSplineHeading(new Pose2d(22, -17, Math.toRadians(0)))
                        .lineTo(new Vector2d(23, -53))
                        .waitSeconds(1)
                        .strafeLeft(6)
                        .waitSeconds(1)
                        .strafeLeft(6)
                        .waitSeconds(1)
                        .strafeLeft(15)
                        .splineToConstantHeading(new Vector2d(0, -20), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-42, -42), Math.toRadians(90))
                        .waitSeconds(2)
                        .strafeLeft(15)
                        .splineToLinearHeading(new Pose2d(24, -29, Math.toRadians(180)), Math.toRadians(-27))
                        .strafeLeft(10)
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