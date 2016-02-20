package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Talon;
public class LifterManipulator  {

	// initialize talons 
	Talon spinLeft = new Talon(6);
	Talon spinRight = new Talon(7);
	Talon Lifter = new Talon(4);
	Talon Ballkicker = new Talon(5);
	DigitalInput kickReady = new DigitalInput(6);
	DigitalInput kickComplete = new DigitalInput(5);
	DigitalInput limitswitch = new  DigitalInput(4);

	Joystick shooter1;// 2 for shooting and driving 
	Joystick shooter2;
	
	
	Potdegrees shaftRotation = new Potdegrees(2);
	
	public LifterManipulator(){
		shooter1= new Joystick(1);
		shooter2= new Joystick(2);
		
		//spinLeft.setInverted(spinLeft.equals(true));
		//spinRight.setInverted(spinRight.equals(true));
		//spinLeft.setInverted(true);
		//spinRight.setInverted(false);
	}

	public void publishtodash(){
		 
	}

	public void degree_corection(){
		
		boolean upperbinaryValue= limitswitch.get();
		double rot = 45;
		double rotdevation= 5;
		
		if (shaftRotation.fetchDegrees() <= rot-rotdevation ){
			Lifterdown();
		}
		else if (upperbinaryValue == true && shaftRotation.fetchDegrees()>=rot+rotdevation){
			LifterUp();
		}
		else if (upperbinaryValue == false ){
			 Lifter.set(0);
		 }
	}

	public void degree_pickup(){
		
		boolean upperbinaryValue= limitswitch.get();
		double rot = 115;
		double rotdevation = 5;
		
		if (shaftRotation.fetchDegrees() <= rot-rotdevation ){
			Lifterdown();
		}
		else if (upperbinaryValue == true && shaftRotation.fetchDegrees()>=rot+rotdevation){
			LifterUp();
		}
		else if (upperbinaryValue == false ){
			 Lifter.set(0);
		 }
	}

public void degree_drive(){
		
		boolean upperbinaryValue= limitswitch.get();
		double rot = 103;
		double rotdevation = 5;
		
		if (shaftRotation.fetchDegrees() <= rot-rotdevation ){
			Lifterdown();
		}
		else if (upperbinaryValue == true && shaftRotation.fetchDegrees()>=rot+rotdevation){
			LifterUp();
		}
		else if (upperbinaryValue == false ){
			 Lifter.set(0);
		 }
	}

	public void Lifterdown(){
		// double rot = lifterrot.degreesRot();
	//	double rot = shaftRotation.fetchDegrees();
	//if (rot <= 100){
		 Lifter.set(.5);
	}
	//}

	public void LifterUp(){
		//double rot2 = shaftRotation.fetchDegrees();
		//
		//if (rot2 >= 0){
		Lifter.set(-.5);
		}
	//}
	
	public void loadBall(){
//	spinLeft.setInvertedMotor(spinLeft.MotorType.kspinLeft,true);// need to fix v
		spinLeft.set(-.5);
		spinRight.set(.5);
	}
	
	public void shootBall(){
		
		spinLeft.set(1);
		spinRight.set(-1);
		Timer.delay(1.5);
		kick_ball();
		resetKickBall();
	}

	public void LifterManipulatorinit(){
		if (limitswitch.get() == true){
			LifterUp();
		}
		else if ((limitswitch.get() == false)){
			stop();
		}
	}
	
	public void armReset(){
		if (limitswitch.get() == true){
			LifterUp();
		}
		else if ((limitswitch.get() == false)){
			stop();
		}
	}
	
	//public void kick_ball(){
		//Ballkicker.set(.1);
		//Ballkicker.set(-.1);
	//}
	
	public void stop(){
		spinLeft.set(0);
		spinRight.set(0);
		Lifter.set(0);
		resetKickBall();
	}


		
	public void kick_ball(){

		while (kickComplete.get()) {
			Ballkicker.set(-.75);
		
		}
		Ballkicker.set(0);
	}
		
	public void resetKickBall() {
	
		while (kickReady.get()) {
			Ballkicker.set(.1);	
		}
		Ballkicker.set(0);
	}


	public int getJoyStickValue(){
		if (shooter1.getRawButton(1)){
			return 1;
		}
		else if (shooter1.getRawButton(2)){
			return 2;
		}
		else if (shooter1.getRawButton(3)){
			return 3;
		}
		else if (shooter2.getRawButton(2)){
			return  4 ; 
		}
		else if (shooter2.getRawButton(1)){
			return 5;
		}
		else if (shooter2.getRawButton(3)){
			return 6;
		}
		else if (shooter2.getRawButton(5)){
			return  7; 
		}
		else if (shooter2.getRawButton(4)){
			return  10; 
		}
		else if (shooter2.getRawButton(6)){
			return  11; 
		}
		else{
			return 0;
		}
	}
		
	
	
	
	public void manipulatorPeriodic(){

	switch(getJoyStickValue()) {
		
		case 1:
			break;
		
		case 2: 
			Lifterdown();
			break;
		
		case 3: 
			LifterUp();
			break;
		
		case 4: 
			loadBall();
			break;
		
		case 5:
			shootBall();
			break;

		case 6:
			degree_pickup();
			break;

		case 7:
			degree_corection();
			break;

		case 10: 
			degree_drive();
			break;
		
		case 11: 
			armReset();
			break;

		default:
			stop();
			break;
	}

	SmartDashboard.putNumber("pot degrees",shaftRotation.fetchDegrees());
	SmartDashboard.putBoolean("Kick completed",kickComplete.get() );
	SmartDashboard.putBoolean("Kick Ready",kickReady.get() );
	SmartDashboard.putBoolean("shooter home",limitswitch.get() );
	SmartDashboard.putNumber("time", Timer.getMatchTime() );
}
}


