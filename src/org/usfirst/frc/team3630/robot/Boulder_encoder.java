package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Boulder_encoder{
Encoder boulderEncoder;
	
	//requires the 
	public Boulder_encoder(int input, int output) {
	//Set up of shooter encoder based on standard settings from FRC
		 boulderEncoder = new Encoder(input, output, false, EncodingType.k1X);
		 boulderEncoder.setMaxPeriod(.1);
		 boulderEncoder.setMinRate(10);
		 boulderEncoder.setDistancePerPulse(20);
		 boulderEncoder.setReverseDirection(true); //this can be changed depending on encoder orientation
		 boulderEncoder.setSamplesToAverage(7);
	}
	
	//this method returns to 
	public double RPM(){
		double rps =  boulderEncoder.getDistance();
		double rpm = rps*60;
		return rpm;
	}
	
}
