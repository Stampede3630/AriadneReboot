package org.usfirst.frc.team3630.robot;

//import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

	DriveTrain mainDrive;
	
	Sensors sensors;
	int state;
	
	public void autonomousInit(DriveTrain myDriveTrain, Sensors mySensors){
		mainDrive = myDriveTrain;
		//if (ahrs == null)
		//ahrs = new AHRS(SPI.Port.kMXP); 
		
		//autoSelected = (String) chooser.getSelected();
		//autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		//System.out.println("Auto selected: " + autoSelected);
		
		state = 1;
	}
	
	public void forward(){
		mainDrive.moveForward(0.75);
	}
	
	public void turnLeft(){
		mainDrive.moveLeft(0.25);
	}
	
	public void turnRight(){
		mainDrive.moreRight(.25);
	}
	
	public void lowbarPeriodic(){
		switch(state){
		case 1: //lower shooter and drive towards low bar
			if (sensors.sideSonar.getRangeInches() > 25){
				mainDrive.moveForward(0.75);
			}else{
				mainDrive.moveForward(0.25);
			}
			break;
			
		case 2:
			break;
			
		default:
			break;
		}
	}
}