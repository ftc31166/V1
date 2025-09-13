package org.firstinspires.ftc.teamcode.VisionStuff;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.firstinspires.ftc.teamcode.openftc.apriltag.AprilTagDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.List;

public class camera {
    public void trackAprilTag(AprilTagDetection tag,Servo panServo, Servo tiltServo, double kP) {
        if (tag == null) return; // nothing to track

        // Image center (assuming 640x480)
        double centerX = 320;
        double centerY = 240;

        // Calculate error from center
        double errorX = tag.center.x - centerX;
        double errorY = tag.center.y - centerY;

        // Update servo positions (0.0 to 1.0)
        double newPan = panServo.getPosition() - errorX * kP;
        double newTilt = tiltServo.getPosition() + errorY * kP;

        // Clamp positions to 0-1 range
        newPan = Math.max(0, Math.min(1, newPan));
        newTilt = Math.max(0, Math.min(1, newTilt));

        panServo.setPosition(newPan);
        tiltServo.setPosition(newTilt);
    }
    public AprilTagDetection detectAprilTag(AprilTagDetectionPipeline pipeline) {
        List<AprilTagDetection> detections = pipeline.getDetectionsUpdate();
        if (detections != null && !detections.isEmpty()) {
            return detections.get(0); // return first tag
        }
        return null; // no tag detected
    }
}
