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

public class redGPP_PPG {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-50, 52, Math.toRadians(90)))
                        .strafeTo(new Vector2d(-10,11))
                        .waitSeconds(5)
                        .setTangent(Math.toRadians(-10))
                        .splineToConstantHeading(new Vector2d(35,50),Math.toRadians(90))
                        .strafeTo(new Vector2d(-10,11))
                        .waitSeconds(5)
                        .strafeTo(new Vector2d(-10,50))
                        .strafeTo(new Vector2d(-10,11))
                        .waitSeconds(5)
                        .setTangent(Math.toRadians(45))
                        .splineToConstantHeading(new Vector2d(0,50),Math.toRadians(90))
                        .build());

        Image img = null;
        try { img = ImageIO.read(new File("C:\\Users\\Dheem\\Documents\\image.png")); }
        catch(IOException e) {}

        meepMeep.setBackground(img)
                .addEntity(myBot)
                .start();

    }
}