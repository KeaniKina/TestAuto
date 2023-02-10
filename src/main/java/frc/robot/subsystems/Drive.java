package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Drive extends SubsystemBase{
    private CANSparkMax leftFront;
    private CANSparkMax leftBack; 
    private CANSparkMax rightFront; 
    private CANSparkMax rightBack; 
    private MotorControllerGroup left; 
    private MotorControllerGroup right; 
    private DifferentialDrive diffDrive; 

    private AHRS navx; 

    private RelativeEncoder enc; 

    public Drive() {
        leftFront = new CANSparkMax(DriveConstants.leftFrontPort, MotorType.kBrushless); 
        leftBack = new CANSparkMax(DriveConstants.leftBackPort, MotorType.kBrushless); 
        rightFront = new CANSparkMax(DriveConstants.rightFrontPort, MotorType.kBrushless); 
        rightBack = new CANSparkMax(DriveConstants.rightBackPort, MotorType.kBrushless); 

        left = new MotorControllerGroup(leftFront, leftBack); 
        right = new MotorControllerGroup(rightFront, rightBack); 

        diffDrive = new DifferentialDrive(left, right); 

        enc = leftFront.getEncoder(); 

        navx = new AHRS(SPI.Port.kMXP); 

        enc.setPosition(0); 
        navx.zeroYaw();

        leftFront.setIdleMode(IdleMode.kBrake); 
        leftBack.setIdleMode(IdleMode.kBrake); 
        rightFront.setIdleMode(IdleMode.kBrake); 
        rightBack.setIdleMode(IdleMode.kBrake); 
    }

    public double getSpeed(){
        return leftBack.get();
    }

    public void resetEnc() {
        enc.setPosition(0); 
    }

    public void resetYaw() {
        navx.zeroYaw(); 
    }

    public double getEnc() {
        return enc.getPosition(); 
    }

    public double getYaw() {
        return navx.getAngle(); 
    }

    public double getRoll() {
        return navx.getRoll();
    }

    public void tank(double leftSpeed, double rightSpeed) {
        diffDrive.tankDrive(-leftSpeed, rightSpeed);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("getAngle", getYaw()); 
        SmartDashboard.putNumber("Roll", getRoll() );

    }
}