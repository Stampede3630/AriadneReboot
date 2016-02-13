package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;

public class Potdegrees {
	Potentiometer pot;
	double initDegrees ;
	public Potdegrees(int input){
		
		pot = new AnalogPotentiometer(0, 360, 30);
		AnalogInput ai = new AnalogInput(input);
		pot = new AnalogPotentiometer(ai, 360, 30);
		
		// double initDegrees= pot.get();
	
		
	}
	public double fetchDegrees(){
		
		double degrees = pot.get()-initDegrees;
		DigitalInput limitswitch= new  DigitalInput(4);
		boolean upperbinaryValue= limitswitch.get();
		 if (upperbinaryValue == true ){
			 degrees = 0;
		 }
		return degrees;
		 
		
	}
	
	
	}

