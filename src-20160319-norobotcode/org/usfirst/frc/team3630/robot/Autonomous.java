package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.Timer;

public class Autonomous {
	//Talon left = new Talon(0);
	//Talon right = new Talon(1);

	RobotDrive mainDrive = new RobotDrive(0,1);// new robot drive
	
	Sensors sensors;
	int state;
	
	public void autonomousInit(Sensors mySensors){
		sensors = mySensors;
		//if (ahrs == null)
		//ahrs = new AHRS(SPI.Port.kMXP); 
		
		//autoSelected = (String) chooser.getSelected();
		//autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		//System.out.println("Auto selected: " + autoSelected);
		
		state = 1;
	}
	
	public void forward(double speed){
		mainDrive.tankDrive(speed, speed);
		//mainDrive.tankDrive(1, 1);
		//left.set(1);
		//right.set(1);
	}
	
	public void turnLeft(){
		mainDrive.tankDrive(-0.25, 0.25);
		//left.set(-0.25);
		//right.set(.25);
	}
	
	public void turnRight(){
		mainDrive.tankDrive(0.25, -0.25);
		//left.set(.25);
		//right.set(-.25);
	}
	
	public void lowbarPeriodic(){
		
		switch(state){
		case 1: //lower shooter and drive towards low bar
			if (sensors.sideSonar.getRangeInches() > 25){
				forward(0.75);
			}else{
				forward(0.25);
			}
			break;
			
		case 2:
			break;
			
		default:
			break;
		}
	}
}