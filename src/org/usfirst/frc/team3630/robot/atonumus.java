package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;

public class atonumus {
	//Talon left = new Talon(0);
	//Talon right = new Talon(1);
	Timer auto = new Timer();
	RobotDrive mainDrive = new RobotDrive(0,1);// new robot drive
	
	public void forward(){
		mainDrive.tankDrive(.25, .25);
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
	
	public void autonmousPeriodic(){
		double auto = Timer.getMatchTime();
		while (auto<3){	// while elapsed time less than 3 seconds move forward 
			forward();
			
		}
	}

}
