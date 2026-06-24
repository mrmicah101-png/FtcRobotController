package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Flywheel {

    DcMotorEx rightMotor;
    DcMotorEx leftMotor;
    public void init (HardwareMap hardwaremap) {
        rightMotor = hardwaremap.get(DcMotorEx.class, "RightFlywheelMotor");
        leftMotor = hardwaremap.get(DcMotorEx.class, "LeftFlywheelMotor");
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public enum State{
        ON,
        OFF,

    }

    public State getState(){
        return currentState;
    }
    public void setState(State newState){
        currentState = newState;
    }

    State currentState =State.OFF;
    double targetTPM = 1250;
    double kF = 0.00041;
    double KF = 0;
    public void update(){
        double targetPower = targetTPM * kF;
        double error = targetTPM - rightMotor.getVelocity();
        switch (currentState){
            case ON:
                rightMotor.setPower(targetPower);
                leftMotor.setPower(targetPower);
                break;
            case OFF:
                rightMotor.setPower(0);
                leftMotor.setPower(0);
                break;
        }
    }
    public void status (Telemetry telemetry) {
        telemetry.addData("Power", rightMotor.getPower());
        telemetry.addData("TPM", rightMotor.getVelocity());
    }
}
