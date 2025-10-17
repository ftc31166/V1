package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.openftc.apriltag.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

import java.util.List;

public class Outtake {
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    public DcMotor flywheel1;
    double fx = 600;
    double fy = 600;
    double cx = 320;
    double cy = 240;

    double tagsize = 0.166;
    public Servo xturret;
    public Servo yturret;
    public double kP;
    public Outtake(HardwareMap hardwareMap){
        flywheel1 = hardwareMap.get(DcMotor.class,"fly1");
        xturret = hardwareMap.get(Servo.class, "xtur");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        camera.setPipeline(aprilTagDetectionPipeline);
    }

    public void detectAprilTag() {
        ElapsedTime timer = new ElapsedTime();
        List<AprilTagDetection> detections = aprilTagDetectionPipeline.getDetectionsUpdate();
        while (timer.milliseconds() < 1000){
            if (detections != null && !detections.isEmpty()) {
                Constants.tagID = detections.get(0).id;
                break;
            }
        }


    }
}
