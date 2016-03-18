package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Ultrasonic;

public class DriveTrain {
    Joystick shootLeft;// 2 for shooting and driving 
    Joystick shootRight; 
    Joystick breachRight;
    Joystick breachLeft;
 //  AnalogInput ai0;
 //   int Left = 0;
 //   int Right = 1;
    boolean driveAutoCorrect;
    double driveStrength;
    
    //Made new gyro class
    //AnalogGyro gyro;
    //NAVX
     AHRS ahrs;


    //Sonars
    int sonarChannelIn; 
	int sonarChannelOut; 
    
	Ultrasonic Sonar; 
    
    //input ports on roborio are represented by integers left and right
   // final int Left = 0;
    //final int Right = 1; // who final them

    // intialsie drives 
    RobotDrive mainDrive;
    
    public DriveTrain(int sonarChIn, int sonarChOut){
    	
    	shootLeft = new Joystick(1); // joysticks inisalise 
    	shootRight = new Joystick(2);
    	breachLeft = new Joystick(0);
        breachRight= new Joystick(4);
        driveAutoCorrect = false;
        driveStrength = 1;
        //  ahrs = new AHRS(SPI.Port.kMXP);  // Not using SPI - it was for the original Nav Mxp that doesn't work.
        ahrs = new AHRS(I2C.Port.kOnboard); // Have to use the I2C bus, not USB.
        //ahrs = new AHRS(SerialPort.Port.kUSB); // Note that USB is not supported by the FRC Roborio board.
        sonarChannelIn = sonarChIn;
        sonarChannelOut = sonarChOut;
        Sonar = new Ultrasonic(16,17);
    }

    // Will turn left if amount is positive (else will turn right).
	public void turnLeft(double amount){
		mainDrive.tankDrive(-amount, amount);
	}
	
    
    public void updateSmartDB() {
       SmartDashboard.putNumber("DriveTrainAngle",driveTrainAngle());
       //SmartDashboard.putNumber("GyroAngle",gyro.getAngle());
       SmartDashboard.putBoolean("DriveCorrectionEnabled?", driveAutoCorrect);
       SmartDashboard.putNumber("Sonar Range", Sonar.getRangeInches());
        //navx commands
       SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
       SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
       SmartDashboard.putNumber(   "IMU_CompassHeading",   ahrs.getCompassHeading());
        
//        Display 9-axis Heading (requires magnetometer calibration to be useful) 
        SmartDashboard.putNumber(   "IMU_FusedHeading",     ahrs.getFusedHeading());

        /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
        /* path for upgrading from the Kit-of-Parts gyro to the navx-MXP             */
        
        SmartDashboard.putNumber("IMU_TotalYaw", ahrs.getAngle());
        
    }
    public void driveTrainInit(){
    	mainDrive = new RobotDrive(0,1);
        //ai0 = new AnalogInput(1);
        //gyro = new AnalogGyro(ai0);
//        updateSmartDB();
     
    	Sonar.setEnabled(true);
		Sonar.setAutomaticMode(true);
    }

    public void setDriveMode() {
    	if (driveAutoCorrect) {
    		driveAutoCorrect = false;
    	} else {
    		driveAutoCorrect = true;
    	}
    }
    
    public double driveTrainAngle(){
    	double X = shootLeft.getX();
    	double Y = shootRight.getY();
    	
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
    
    public void driveTrainCorrect(double angle_intended){
    	double Y = shootLeft.getY()*-1;
    	//gets angles from gyro
    	//double angleCor = gyro.getAngle();
    	//sets tolerance
    	double tolerance = 3;
    	//Sets correction factor (I would set this up from 80-90 percent) Adjust as needed
    	double Kcor = 0.97;
    	
    	//if (Math.abs(angle_intended - angleCor) < tolerance){
    	//	mainDrive.drive(Y, angle_intended);
    	//}
    	
    	//else{
    	//	mainDrive.drive(Y, angle_intended - (angleCor - angle_intended)*Kcor);
    	//}
    }
    	
    public void driveShooter(){
    	mainDrive.arcadeDrive((shootLeft.getY()*-1), (shootRight.getX()*-1));
    }
    
    public void driveBreach() {
    	if (breachLeft.getRawButton(Consts.BREACH_LEFT_BTN_DRIVE_STRENGTH)) { // Button serviced here instead of in StraitArm.
    		driveStrength = 0.25;
    	}
    	else
    	{
        	driveStrength = 1;
    	}
    		
    	mainDrive.arcadeDrive((-breachLeft.getY()*-1 * driveStrength), (breachRight.getX()*-1 * driveStrength));
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
