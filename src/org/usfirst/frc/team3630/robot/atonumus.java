package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class atonumus {
Talon left = new Talon(0);
Talon right = new Talon(1);
Timer auto = new Timer();
RobotDrive drive = new RobotDrive(0,1);// new robot drive
public void forward(){
	left.set(1);
	right.set(1);
}

public void turnLeft(){
	left.set(-0.25);
	right.set(.25);
}
public void turnRight(){
	left.set(.25);
	right.set(-.25);
}


public void autonmusPeridoic(){
	double auto = Timer.getMatchTime();
while (auto<3){	// while elapsed time less than 3 move forward 
	//drive.drive(0.25, 0.25); // sets motor speeds to .25 
	forward();
}
}
}
