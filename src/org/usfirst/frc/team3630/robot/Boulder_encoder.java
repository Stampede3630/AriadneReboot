package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

// Encoder for CIM Motor
// 2 channel quadrature output
// 20 pulses per channel per revolution 

public class Boulder_encoder{

Encoder boulderEncoder;
	
	//requires the 
	public Boulder_encoder(int input, int output) {
	//Set up of shooter encoder based on standard settings from FRC
		 boulderEncoder = new Encoder(input, output, false, EncodingType.k1X);
		 boulderEncoder.setMaxPeriod(.1);  // The maximum period (in seconds) where the device is still considered moving.
		 boulderEncoder.setMinRate(0.2); // This is in terms of distance per second (0.2 / sec = 2 ticks or 1/10th rotation).
		 boulderEncoder.setDistancePerPulse(0.05); // At 20 pulses (ticks) per revolution this gives a distance of 1 for 1 revolution.
		 boulderEncoder.setReverseDirection(false); //this can be changed depending on encoder orientation
		 boulderEncoder.setSamplesToAverage(7);
	}
	
	// The encoder will begin counting as soon as it is created. 
	// To reset the encoder value to 0 call reset().
	public void reset() {
		 boulderEncoder.reset();
	}
	// Want distance divided by time
	public double RPM(){
		// Get the current rate of the encoder. 
		// Units are distance per second as scaled by the value from setDistancePerPulse().
		// A distance of 1 is one rotation.
		double rps =  boulderEncoder.getRate();
		double rpm = rps*60;
		return rpm;
	}
	
}
