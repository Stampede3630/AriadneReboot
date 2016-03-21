package org.usfirst.frc.team3630.robot;

//import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

	LifterManipulator shooter;
	DriveTrain mainDrive;
	
	Sensors sensors;
	int state;
	
	public void autonomousInit(LifterManipulator myShooter, DriveTrain myDriveTrain, Sensors mySensors){
		shooter = myShooter;
		mainDrive = myDriveTrain;
		sensors = mySensors;
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
		mainDrive.moveRight(.25);
	}
	
	public void lowbarPeriodic(){
		boolean atDesiredAngle = false;
		switch(state){
		case 1: //lower shooter and drive towards low bar
			if (sensors.sideSonar.getRangeInches() > 25){
				atDesiredAngle = shooter.set_shooter_angle(-23);
				mainDrive.moveForward(0.75);
			}else{
				// We have reached the defense
				if (atDesiredAngle) {
					state++;
				}
				else
				{
					mainDrive.moveForward(0);
					atDesiredAngle = shooter.set_shooter_angle(-23);
					if (atDesiredAngle) {
						state++;
					}
				}
			}
			break;
			
		case 2:
			mainDrive.moveForward(0.75);
			break;
			
		default:
			break;
		}
	}
}