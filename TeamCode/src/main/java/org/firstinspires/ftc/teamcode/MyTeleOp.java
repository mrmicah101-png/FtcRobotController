package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Gate;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;

@TeleOp
public class MyTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {




        Drivetrain drivetrain = new Drivetrain();
        drivetrain.initiate(hardwareMap);
        Intake intake = new Intake();
        intake.initiate(hardwareMap);
        Flywheel flywheel = new Flywheel();
        flywheel.init(hardwareMap);
        Gate gate = new Gate();
        gate.initiate(hardwareMap);

        waitForStart();


        if (isStopRequested()) return;


        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;
            if(gamepad1.leftTriggerWasPressed()){
                switch(intake.getState()){
                    case RESTING:
                        intake.setState(Intake.State.INTAKING);
                        break;
                    default:
                        intake.setState(Intake.State.RESTING);
                        break;
                }
            }
            if(gamepad1.crossWasPressed()){
                switch(intake.getState()) {
                    case RESTING:
                        intake.setState(Intake.State.EJECTING);
                        break;
                    default:
                        intake.setState(Intake.State.RESTING);
                        break;
                }
            }


            if(gamepad1.rightTriggerWasPressed()){
                switch(flywheel.getState()) {
                    case OFF:
                        flywheel.setState(Flywheel.State.ON);
                        break;
                    default:
                        gate.shoot();
                        intake.setState(Intake.State.INTAKING);
                        break;
                }
            }


            if(gamepad1.leftBumperWasPressed()){
                intake.setState(Intake.State.RESTING);
                flywheel.setState(Flywheel.State.OFF);
            }

            drivetrain.run(x, y, rx);
            intake.update();
            flywheel.update();
            gate.update();

            flywheel.status(telemetry);
            telemetry.update();
        }
    }
}