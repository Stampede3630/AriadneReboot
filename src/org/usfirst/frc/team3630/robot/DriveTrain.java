package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

public class DriveTrain {
	//Joystick defence1; // 2 joystick for defence breaching 
    //Joystick defence2;
    Joystick shooter1;// 2 for shooting and driving 
    Joystick shooter2; 
    
    int Left = 0;
    int Right = 1;
    
    RobotDrive mainDrive;
    
    public DriveTrain(){
    	shooter1 = new Joystick(1);
    	shooter2 = new Joystick(2);

    

	//defence1 = new Joystick(1);
//	defence2 = new Joystick(2);
    
    
    	
    }
    public void driveTrainInit(){
    	mainDrive = new RobotDrive(0,1);

    
    }
    public void driveTrainPeriodic(){
    	mainDrive.arcadeDrive(shooter1.getY(),shooter2.getX());
    }
    
    
}
