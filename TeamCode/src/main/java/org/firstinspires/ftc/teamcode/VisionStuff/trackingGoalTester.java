package org.firstinspires.ftc.teamcode.VisionStuff;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.apriltag.AprilTagDetection;
import org.firstinspires.ftc.teamcode.openftc.apriltag.AprilTagDetectionPipeline;

import java.util.ArrayList;

@TeleOp(name="AprilTag Turret Lock", group="Vision")
public class trackingGoalTester extends LinearOpMode {
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    Servo panServo, tiltServo;

    double panPos = 0.5;   // Start centered
    double tiltPos = 0.5;

    // Gain factor (adjust sensitivity)
    double kP = 0.001;

    // Camera intrinsics (approximate for Logitech C270)
    double fx = 600;
    double fy = 600;
    double cx = 320;
    double cy = 240;

    double tagsize = 0.166; // meters (change to your tag size)

    // The specific AprilTag ID we want to track
    int targetTagID = 24;

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        camera.setPipeline(aprilTagDetectionPipeline);

        panServo = hardwareMap.get(Servo.class, "pan");
        tiltServo = hardwareMap.get(Servo.class, "tilt");

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                telemetry.addData("Camera error", errorCode);
                telemetry.update();
            }
        });

        telemetry.setMsTransmissionInterval(50);

        waitForStart();

        while (opModeIsActive()) {
            ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getLatestDetections();

            if (detections.size() > 0) {
                for (AprilTagDetection tag : detections) {
                    if (tag.id == targetTagID) {
                        // Get tag center (in pixels)
                        double tagX = tag.center.x;
                        double tagY = tag.center.y;

                        // Screen center
                        double centerX = 640 / 2.0;
                        double centerY = 480 / 2.0;

                        // Error
                        double errorX = tagX - centerX;
                        double errorY = tagY - centerY;

                        // Adjust servo positions TODO: fix this based on servo turning
                        panPos -= errorX * kP;
                        tiltPos += errorY * kP;

                        // Constrain between 0 and 1
                        panPos = Math.max(0, Math.min(1, panPos));
                        tiltPos = Math.max(0, Math.min(1, tiltPos));

                        panServo.setPosition(panPos);
                        tiltServo.setPosition(tiltPos);

                        telemetry.addData("Tracking Tag ID", tag.id);
                        telemetry.addData("Pan Pos", panPos);
                        telemetry.addData("Tilt Pos", tiltPos);
                    }
                }
            } else {
                telemetry.addLine("No tags detected");
            }
            telemetry.update();
        }
    }
}