package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Potdegrees {
	Potentiometer pot;
	public Potdegrees(){
		
		pot = new AnalogPotentiometer(0, 360, 30);
		AnalogInput ai = new AnalogInput(1);
		pot = new AnalogPotentiometer(ai, 360, 30);
		
	
		
	}
	public double fetchDegrees(){
		
		double degrees = pot.get();
		return degrees;
		
	}
	
	
	}

