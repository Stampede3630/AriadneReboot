package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Talon;
public class LifterManipulator  {
// inisaliose talons 
	Talon spinLeft = new Talon(6);
	Talon spinRight = new Talon(7);
	Talon Lifter = new Talon(4);
	Talon Ballkicker = new Talon(5);
	Joystick shooter1;// 2 for shooting and driving 
	Servo camera = new Servo(8);
	
	Potdegrees  shaftRotation = new Potdegrees(1);
	public LifterManipulator(){
		shooter1= new Joystick(1);
		//spinLeft.setInverted(spinLeft.equals(true));
		//spinRight.setInverted(spinRight.equals(true));
		//spinLeft.setInverted(true);
		//spinRight.setInverted(false);
	}
	public void degree_corection(){
		DigitalInput limitswitch= new  DigitalInput(4);
		boolean upperbinaryValue= limitswitch.get();
		double rot = shaftRotation.fetchDegrees();
		double rotdevation= rot +3;
		
		if (upperbinaryValue == false && rot<=rotdevation-3 ){
			Lifterdown();
		}
		else if (upperbinaryValue == false && rot>=rotdevation+3){
			LifterUp();
		}
	}

	public void publishtodash(){
	
	}
	
	
	


	public void Lifterdown(){
		// double rot = lifterrot.degreesRot();
		double rot = shaftRotation.fetchDegrees();
	if (rot <= 100){
		 Lifter.set(.25);
	}
	}
	public void LifterUp(){
		double rot2 = shaftRotation.fetchDegrees();
		if (rot2 >= 0){
		Lifter.set(-.5);
		}
	}
	
	public void loadBall(){
//	spinLeft.setInvertedMotor(spinLeft.MotorType.kspinLeft,true);// need to fix v
		spinLeft.set(.25);
		spinRight.set(-.25);
		
	}
	
	public void shootBall(){
		
		spinLeft.set(-1);
		spinRight.set(1);
		
	}
	public void raiseServo(){
		camera.set(.5);
	}
	public void lowerServo(){
		camera.set(-.5);
	}
	/*public void kick_ball(){
		Ballkicker.set(.1);
		Ballkicker.set(-.1);
	}
	*/
		public void stop(){
		spinLeft.set(0);
		spinRight.set(0);
		Lifter.set(0);
		Ballkicker.set(0);
		camera.set(0);
	}
	public int getJoyStickValue(){
		if(shooter1.getRawButton(1)){
			return 1;
		}
		else if(shooter1.getRawButton(2)){
			return 2;
		}
		else if(shooter1.getRawButton(3)){
			return 3;
		}
		else if(shooter1.getRawButton(4)){
			return  4 ; 
		}
		else if(shooter1.getRawButton(5)){
			return 5;
		}
		else if(shooter1.getRawButton(6)){
			return 6;
		}
		else if(shooter1.getRawButton(7)){
			return  7 ; 
		}
		else if(shooter1.getRawButton(8)){
			return  8; 
		}
		
		else{
			return 0;
	}

	}
		
	
	
	
	public void manipulatorPeriodic(){
		/*
		if(shooter1.getRawButton(2)){
			Lifterdown(); // moves lifter down
		}
	
		
		if(shooter1.getRawButton(3)){
			LifterUp(); // moves lifter up
		}
		
		
		
		if(shooter1.getRawButton(4)){
			loadBall(); // load ball intake one motors 
		}
		
		if(shooter1.getRawButton(5)){
			shootBall(); // load ball intake one motors 
		}
	
		
		if (shooter1.getRawButton(1)){
			stop(); // kicks ball to shooting mec
		}*/
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
			raiseServo();
			
			break;
		case 7:degree_corection();
			break;
		case 8 :
			lowerServo();
			default:
				stop();
				break;
		
	}
	SmartDashboard.putNumber("pot degrees",shaftRotation.fetchDegrees());
}
}


