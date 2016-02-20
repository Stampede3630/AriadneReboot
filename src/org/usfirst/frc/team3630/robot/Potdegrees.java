package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;

public class Potdegrees {
	Potentiometer pot;
	double initDegrees ;

	public Potdegrees(int input){
		
		AnalogInput ai = new AnalogInput(input);
		pot = new AnalogPotentiometer(ai, 360, 0);
		
		initDegrees = pot.get();
	}

	public double fetchDegrees(){
		
		double degrees = pot.get()-initDegrees;
	//	DigitalInput limitswitch= new  DigitalInput(4);
		
		return degrees;
	}
	
	
}

