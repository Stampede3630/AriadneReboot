package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

public class DriveTrain {
	//Joystick defence1; // 2 joystick for defence breaching 
    //Joystick defence2;
    Joystick shooter1;// 2 for shooting and driveing 
    Joystick shooter2; 
    int Left = 0;
    int Right = 1;
    
    RobotDrive mainDrive;
    
    public DriveTrain(){
   

    

	//defence1 = new Joystick(1);
//	defence2 = new Joystick(2);
	shooter1 = new Joystick(3);
	shooter2 = new Joystick(4);
    
    
    	
    }
    public void driveTrainInit(){
    	mainDrive = new RobotDrive( Right,Left);
    
    }
    public void driveTrainPeriodic (){
    	mainDrive.tankDrive(shooter1.getX(), shooter2.getY());
    }
    
    
}
