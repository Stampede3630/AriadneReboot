package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;


public class DriveTrain {
    Joystick shootLeftJoy;// 2 for shooting and driving 
    Joystick shootRightJoy; 
    Joystick breachRightJoy;
    Joystick breachLeftJoy;
 //  AnalogInput ai0;
 //   int Left = 0;
 //   int Right = 1;
    boolean driveAutoCorrect;
    double driveStrength;
    
    //Made new gyro class
    //AnalogGyro gyro;
    //NAVX
    Sensors sensors;
    
    //input ports on roborio are represented by integers left and right
   // final int Left = 0;
    //final int Right = 1; // who final them

    // initialize drives 
    RobotDrive mainDrive;
    
    public DriveTrain(Sensors mySensors){
    	shootLeftJoy = new Joystick(Consts.SHOOTER_LEFT_JOYSTICK_CHAN); // joy-stick initializations 
    	shootRightJoy = new Joystick(Consts.SHOOTER_RIGHT_JOYSTICK_CHAN);
    	breachLeftJoy = new Joystick(Consts.BREACH_LEFT_JOYSTICK_CHAN);
        breachRightJoy = new Joystick(Consts.BREACH_RIGHT_JOYSTICK_CHAN);
        driveAutoCorrect = false;
        driveStrength = 1;
        
        sensors = mySensors;
    }

    public void driveTrainInit(){
    	mainDrive = new RobotDrive(0,1);
        //ai0 = new AnalogInput(1);
        //gyro = new AnalogGyro(ai0);
//        updateSmartDB();
    }

    // Will turn left if amount is positive (else will turn right).
	public void turnLeft(double amount){
		mainDrive.tankDrive(-amount, amount);
	}
	
	public void moveLeft(double speed){
		mainDrive.tankDrive(speed, -speed);
	}
		
	public void moveRight(double speed){
		mainDrive.tankDrive(-speed, speed);
	}
	
	public void moveForward(double speed){
		mainDrive.tankDrive(speed, speed);
	}
	
	public void stopDrive(){
		mainDrive.tankDrive(0,0);
	}
	
	// note that this is for autonomous, where we always drive backwards,
	// therefore the negative drive values. More negative means faster.
	// also, motor-wise the left motor is right-most when driving backwards.
	public void moveForwardDriftRight(double speed){
		double leftChange = 0.1;
		double rightChange = 0;
		mainDrive.tankDrive(-(speed + rightChange), -(speed + leftChange));
	}
	
	// note that this is for autonomous, where we always drive backwards,
	// therefore the negative drive values. More negative means faster.
	// also, motor-wise the left motor is right-most when driving backwards.
	public void moveForward(double speed, float heading){
		// TODO: make use of heading to tweak direction of travel.
		float curHeading = sensors.ahrs.getFusedHeading();
		float headingChange = heading - curHeading;
		// Make the direction change in the direction of heading change.
		final float headingMargin = 1.0f; // Acceptable inaccuracy in degrees.
		final float maxHeadingChange = 5.0f; // Don't try to correct too quickly.
		double adjMagnitude = 0.0;
		double leftChange = 0.0;
		double rightChange = 0.0;
		boolean enabled = true;
		float magChange = Math.abs(headingChange);
		if (enabled && (magChange > headingMargin)) {
			if (magChange > maxHeadingChange) {
				magChange = maxHeadingChange;
			}
			// Translate 1 degree to 0.05, 2 degrees to 0.1, 5 degrees to 0.25
			adjMagnitude = magChange / 20;
			// Change the direction a bit.
			if (headingChange > 0) {
				// Want to turn right a little bit.
				rightChange = -adjMagnitude; // Slow down the right motor to turn right.
				leftChange = adjMagnitude; // Speed up the left motor to turn right.
			} else {
				// Want to turn left a little bit.
				leftChange = -adjMagnitude; // Slow down the left motor to turn left.
				rightChange = adjMagnitude; // Speed up the right motor to turn left.
			}
		}
		mainDrive.tankDrive(-(speed + rightChange), -(speed + leftChange));
	}
    
    public void updateSmartDB() {
    	sensors.updateSmartDB();
    }

    public void setDriveMode() {
    	if (driveAutoCorrect) {
    		driveAutoCorrect = false;
    	} else {
    		driveAutoCorrect = true;
    	}
    }
    
// This routine was written by Carlos, and had to do with trying to drive straight - never tested.
//    public double driveTrainAngle(){
//    	double X = shootLeft.getX();
//    	double Y = shootRight.getY();
//    	
//    	//checks for first quadrant angles and returns angle in degrees
//    	if (Math.signum(X) == X && Math.signum(Y) == Y) {
//    		double angle = 180/(Math.PI) * (Math.PI/2 - Math.tan(Y/X));
//    		return angle;
//    	}
//    	
//    	//checks for second quadrant angles and returns angle in degrees
//    	else if (Math.signum(X) == -X && Math.signum(Y) == Y) {
//    		double angle = 180/(Math.PI) * (Math.PI + Math.tan(Y/X));
//    		return angle;
//    	}
//    	
//    	//checks for third quadrant angles
//    	else if (Math.signum(X) == -X && Math.signum(Y) == -Y){
//    		double angle = 180/(Math.PI) * (Math.tan(Y/X) - Math.PI);
//    		return angle;
//    	}
//    	
//    	//checks for fourth quadrant angles and returns angle in degrees
//    	else if (Math.signum(X) == -X && Math.signum(Y) == Y) {
//    		double angle = 180/(Math.PI) * (Math.PI/2 + Math.tan(Y/X));
//    		return angle;
//    	}
//    	
//    	//returns angles for which X = zero (otherwise forward)
//    	else if ((Math.signum(X) == 0 && Math.signum(Y) == Y) || 
//    			(Math.signum(X) == 0 && Math.signum(Y) == -Y)){
//    		double angle = 0;  
//    		return angle;
//    	}
//    	
//    	//returns angles for which nothing is moving
//    	else {
//    		return 0;
//    	}
//    	
//    }
    
// This routine was written by Carlos, and had to do with trying to drive straight - never tested.
//    public void driveTrainCorrect(double angle_intended){
//    	double Y = shootLeft.getY()*-1;
//    	//gets angles from gyro
//    	//double angleCor = gyro.getAngle();
//    	//sets tolerance
//    	double tolerance = 3;
//    	//Sets correction factor (I would set this up from 80-90 percent) Adjust as needed
//    	double Kcor = 0.97;
//    	
//    	//if (Math.abs(angle_intended - angleCor) < tolerance){
//    	//	mainDrive.drive(Y, angle_intended);
//    	//}
//    	
//    	//else{
//    	//	mainDrive.drive(Y, angle_intended - (angleCor - angle_intended)*Kcor);
//    	//}
//    }
    	
    public void driveSlower(double speed){
    	mainDrive.tankDrive(-speed, -speed);
    }
    
    public void driveShooter(){
    	mainDrive.setSensitivity(0.1);
    	mainDrive.arcadeDrive((shootLeftJoy.getY()*-1), (shootRightJoy.getX()*-1));
    }
    
    public void driveBreach() {
    	if (breachLeftJoy.getRawButton(Consts.BREACH_LEFT_BTN_DRIVE_STRENGTH)) { // Button serviced here instead of in StraitArm.
    		driveStrength = 0.25;
    	}
    	else
    	{
        	driveStrength = 1;
    	}
    		
    	mainDrive.arcadeDrive((-breachLeftJoy.getY()*-1 * driveStrength), (breachRightJoy.getX()*-1 * driveStrength));
    }

    
//    public void driveTrainPeriodic(){
//        if (!driveAutoCorrect) {
//    	//mainDrive.arcadeDrive((shootLeft.getY()*-1), (shootRight.getX()*-1));
//    	    if (shootLeft.getRawButton(1)){ // switch driver controls make o
//    	    	mainDrive.arcadeDrive((-breachLeft.getY()*-1), (breachRight.getX()*-1));// invert joyticks for front
//        	
//    	    }
//    	    else {
//    	  //  	shootLeft.getRawButton(1);
//    	    	mainDrive.arcadeDrive((shootLeft.getY()*-1), (shootRight.getX()*-1));
//    	    }
//    	} else {
//    		driveTrainCorrect(driveTrainAngle());
//    	}
//        updateSmartDB();
//    }
    	
    
    
}
