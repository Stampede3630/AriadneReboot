package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveTrain {
	
	Joystick leftStick;
	Joystick rightStick;
	
	int leftChannel;
	int rightChannel;
	
	double strafe;
	double forward;
	double rotate;
	
	RobotDrive mainDrive;
	
	public DriveTrain(int lChannel, int rChannel, Joystick rStick, Joystick lStick) {
		leftChannel = lChannel;
		rightChannel = rChannel;
		leftStick = lStick;
		rightStick = rStick;
	}
	public void driveTraininit(){
		mainDrive= new RobotDrive(leftChannel,rightChannel);
				
			
		
		}
	public void driveTrainPeriodic(){
		mainDrive.tankDrive(leftStick, rightStick);
	}
	
}

