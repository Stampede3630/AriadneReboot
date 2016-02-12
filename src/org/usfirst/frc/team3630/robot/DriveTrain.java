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


    
    //input ports on roborio are represented by integers left and right
   // final int Left = 0;
    //final int Right = 1; // who final them

    // intialsie drives 
    RobotDrive mainDrive;
    
    public DriveTrain(){
    	shooter1 = new Joystick(1); // joysticks inisalise 
    	shooter2 = new Joystick(2);

    

	//defence1 = new Joystick(1);
//	defence2 = new Joystick(2);
    
    	
    
    	
    }
    public void driveTrainInit(){
    	mainDrive = new RobotDrive(0,1);

    
    }
    public void driveTrainPeriodic(){
    	mainDrive.arcadeDrive((shooter1.getY()*-1), (shooter2.getX()*-1));
    }
    public double driveTrainAngle(){
    	double X = shooter1.getX();
    	double Y = shooter2.getY();
    	
    	//checks for first quadrant angles and returns angle in degrees
    	if (Math.signum(X) == X && Math.signum(Y) == Y) {
    		double angle = 180/(Math.PI) * (Math.PI/2 - Math.tan(Y/X));
    		return angle;
    	}
    	
    	//checks for second quadrant angles and returns angle in degrees
    	else if (Math.signum(X) == -X && Math.signum(Y) == Y) {
    		double angle = 180/(Math.PI) * (Math.PI + Math.tan(Y/X));
    		return angle;
    	}
    	
    	//checks for third quadrant angles
    	else if (Math.signum(X) == -X && Math.signum(Y) == -Y){
    		double angle = 180/(Math.PI) * (Math.tan(Y/X) - Math.PI);
    		return angle;
    	}
    	
    	//checks for fourth quadrant angles and returns angle in degrees
    	else if (Math.signum(X) == -X && Math.signum(Y) == Y) {
    		double angle = 180/(Math.PI) * (Math.PI/2 + Math.tan(Y/X));
    		return angle;
    	}
    	
    	//returns angles for which X = zero (otherwise forward)
    	else if ((Math.signum(X) == 0 && Math.signum(Y) == Y) || 
    			(Math.signum(X) == 0 ** Math.signum(Y) )){
    		double angle = 0;  
    		return angle;
    	}
    	else
    }
    
}
