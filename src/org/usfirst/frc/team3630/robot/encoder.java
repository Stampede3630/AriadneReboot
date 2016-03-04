package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class encoder {
Encoder primenc;
	
	//requires the 
	public encoder(int input, int output) {
	//Set up of shooter encoder based on standard settings from FRC
		primenc = new Encoder(input, output, false, EncodingType.k4X);
		primenc.setMaxPeriod(.1);
		primenc.setMinRate(10);
		primenc.setDistancePerPulse(235);
		primenc.setReverseDirection(true); //this can be changed depending on encoder orientation
		primenc.setSamplesToAverage(7);
	}
	
	//this method returns to 
	public double degreesRot(){
		double rpm_motor = primenc.getDistance();
		double degrees = rpm_motor*1.5319;
		return degrees;
	}
}
