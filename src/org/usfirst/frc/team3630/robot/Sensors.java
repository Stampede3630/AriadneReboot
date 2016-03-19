package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.AHRS;

public class Sensors {
	
	//Sonars
	Ultrasonic frontSonar;
	Ultrasonic sideSonar;
	int FrontSonarChannelIn = 16;
	int FrontSonarChannelOut = 17;
	int SideSonarChannelIn = 18;
	int SideSonarChannelOut = 19;
	
	//Gyro
	AHRS ahrs;
	
	public Sensors(){
		frontSonar = new Ultrasonic(FrontSonarChannelIn, FrontSonarChannelOut);
		sideSonar = new Ultrasonic(SideSonarChannelIn, SideSonarChannelIn);
		
		ahrs = new AHRS(I2C.Port.kOnboard);
	}
	
	public void sensorInit(){
		frontSonar.setEnabled(true);
		sideSonar.setEnabled(true);
		frontSonar.setAutomaticMode(true);
		sideSonar.setAutomaticMode(true);
	}
	
	public void updateSmartDB() {
	    // SmartDashboard.putNumber("DriveTrainAngle", driveTrainAngle()); // by Carlos, related to drive straight - not tested.
	    //SmartDashboard.putNumber("GyroAngle",gyro.getAngle());
		//SmartDashboard.putBoolean("DriveCorrectionEnabled?", driveAutoCorrect);
		
		//Print sonar values
		SmartDashboard.putNumber("Front Sonar Range", frontSonar.getRangeInches());
	    SmartDashboard.putNumber("Side Sonar Range", sideSonar.getRangeInches());
	       
	    //navx commands
	    SmartDashboard.putBoolean("IMU_Connected",ahrs.isConnected());
	    SmartDashboard.putBoolean("IMU_IsCalibrating",ahrs.isCalibrating());
	    SmartDashboard.putNumber("IMU_CompassHeading",ahrs.getCompassHeading());
	    SmartDashboard.putNumber("IMU_TotalYaw",ahrs.getAngle());
	       
	    //Display 9-axis Heading (requires magnetometer calibration to be useful) 
	    SmartDashboard.putNumber("IMU_FusedHeading",ahrs.getFusedHeading());

	        /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
	        /* path for upgrading from the Kit-of-Parts gyro to the navx-MXP             */
	        
	       
	    }
	
}
