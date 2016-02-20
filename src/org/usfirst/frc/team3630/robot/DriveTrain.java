package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain {
	//Joystick defence1; // 2 joystick for defence breaching 
    //Joystick defence2;
    Joystick shooter1;// 2 for shooting and driving 
    Joystick shooter2; 
    Joystick breachLeft;
    Joystick breachRight;
    
    int Left = 0;
    int Right = 1;
    
    //Made new gyro class
    AnalogGyro gyro = new AnalogGyro(1);

    
    //input ports on roborio are represented by integers left and right
   // final int Left = 0;
    //final int Right = 1; // who final them

    // intialsie drives 
    RobotDrive mainDrive;
    
    public DriveTrain(){
    	shooter1 = new Joystick(1); // joysticks inisalise 
    	shooter2 = new Joystick(2);
    	breachLeft = new Joystick(0);
        breachRight= new Joystick(4);
    

	//defence1 = new Joystick(1);
//	defence2 = new Joystick(2);
    
    	
    
    	
    }
    public void driveTrainInit(){
    	mainDrive = new RobotDrive(0,1);

    
    }
<<<<<<< HEAD

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
    			(Math.signum(X) == 0 && Math.signum(Y) == -Y)){
    		double angle = 0;  
    		return angle;
    	}
    	
    	//returns angles for which nothing is moving
    	else {
    		return 0;
    	}
    	
    }
    
    public void driveTrainPeriodic(double angle_intended){
    	double Y = shooter1.getY()*-1;
    	//gets angles from gyro
    	double angleCor = gyro.getAngle();
    	//sets tolerance
    	double tolerance = 3;
    	//Sets correction factor (I would set this up from 80-90 percent) Adjust as needed
    	double Kcor = 0.97;
    	
    	if (Math.abs(angle_intended - angleCor) < tolerance){
    		mainDrive.drive(Y, angle_intended);
    	}
    	
    	else{
    		mainDrive.drive(Y, angle_intended - (angleCor - angle_intended)*Kcor);
    	}
    	
    }
=======
    public void driveTrainPeriodic(){
    	mainDrive.arcadeDrive((shooter1.getY()*-1), (shooter2.getX()*-1));
    if (shooter2.getRawButton(6)){ // switch driver controls make o
    	mainDrive.arcadeDrive((-breachLeft.getY()*-1), (breachRight.getX()*-1));// invert joyticks for front
    	
    }
    else{
    	shooter2.getRawButton(7);
    	mainDrive.arcadeDrive((shooter1.getY()*-1), (shooter2.getX()*-1));
    }
   
    }
   
>>>>>>> master
    
}
