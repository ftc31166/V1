package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.openftc.apriltag.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;

import java.util.List;

public class Outtake {
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    public DcMotor flywheel1;

    public Servo xturret;
    public Servo yturret;
    public double kP;
    public Outtake(HardwareMap hardwareMap){
        flywheel1 = hardwareMap.get(DcMotor.class,"fly1");
        xturret = hardwareMap.get(Servo.class, "xtur");
        yturret = hardwareMap.get(Servo.class, "ytur");
    }
    public void trackAprilTag(AprilTagDetection tag) {
        if (tag == null) return; // nothing to track

        // Image center (assuming 640x480)
        double centerX = 320;
        double centerY = 240;

        // Calculate error from center
        double errorX = tag.center.x - centerX;
        double errorY = tag.center.y - centerY;

        // Update servo positions (0.0 to 1.0)
        double newPan = xturret.getPosition() - errorX * kP;
        double newTilt = yturret.getPosition() + errorY * kP;

        // Clamp positions to 0-1 range
        newPan = Math.max(0, Math.min(1, newPan));
        newTilt = Math.max(0, Math.min(1, newTilt));

        xturret.setPosition(newPan);
        yturret.setPosition(newTilt);
    }
    public AprilTagDetection detectAprilTag(AprilTagDetectionPipeline pipeline) {
        List<AprilTagDetection> detections = pipeline.getDetectionsUpdate();
        if (detections != null && !detections.isEmpty()) {
            return detections.get(0); // return first tag
        }
        return null; // no tag detected
    }
}
