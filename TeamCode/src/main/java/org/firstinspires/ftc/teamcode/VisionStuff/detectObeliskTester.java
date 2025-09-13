package org.firstinspires.ftc.teamcode.VisionStuff;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.apriltag.AprilTagDetection;
import org.firstinspires.ftc.teamcode.openftc.apriltag.AprilTagDetectionPipeline;

import java.util.ArrayList;

@TeleOp(name="AprilTag Detector", group="Vision")
public class detectObeliskTester extends LinearOpMode {
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    // Camera intrinsics (approximate for Logitech C270)
    double fx = 600;
    double fy = 600;
    double cx = 320;
    double cy = 240;

    double tagsize = 0.166; // meters (change to your tag size)

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        camera.setPipeline(aprilTagDetectionPipeline);

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
                    telemetry.addData("Detected ID", tag.id);
                    telemetry.addData("Center", "(%.2f, %.2f)", tag.center.x, tag.center.y);
                    telemetry.addData("Distance (m)", tag.pose.z);
                }
            } else {
                telemetry.addLine("No tags detected");
            }
            telemetry.update();
        }
    }
}