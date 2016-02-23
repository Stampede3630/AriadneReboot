package org.usfirst.frc.team3630.robot;

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

	Joystick shootLeft;// 2 for shooting and driving 
	Joystick shootRight;
	
	
	Potdegrees shaftRotation = new Potdegrees(2);
	
	public LifterManipulator(){
		shootLeft= new Joystick(1);
		shootRight= new Joystick(2);
		
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
			Lifter.set(0.3);
			//Lifterdown();
		}
		else if (upperbinaryValue == true && shaftRotation.fetchDegrees()>=rot+rotdevation){
			Lifter.set(-0.15);
			//LifterUp();
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
			Lifter.set(0.3);
			//	Lifterdown();
		}
		else if (upperbinaryValue == true && shaftRotation.fetchDegrees()>=rot+rotdevation){
			 Lifter.set(-0.15);
			//LifterUp();
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
			Lifter.set(0.3);
		}
		else if (upperbinaryValue == true && shaftRotation.fetchDegrees()>=rot+rotdevation){
			Lifter.set(-0.15);
		}
		else if (upperbinaryValue == false ){
			 Lifter.set(0);
		 }
	}

	public void Lifterdown(){
		// double rot = lifterrot.degreesRot();
	//	double rot = shaftRotation.fetchDegrees();
	//if (rot <= 100){
		 Lifter.set(.3);
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
			shaftRotation.reset();
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
		Ballkicker.set(0);
		resetKickBall();
	}

		
	public void kick_ball(){

		final int maxTimeDelaySec = 5;
		final int loopsPerSec = 10;
		int kickLoops = 0;
 		while (kickComplete.get() && (kickLoops < (maxTimeDelaySec * loopsPerSec)) && !shootLeft.getRawButton(Consts.SHOOTER_LEFT_BTN_STOP)) {
			Ballkicker.set(-1);
			Timer.delay(1.0 / loopsPerSec);
			kickLoops++;
		}
		Ballkicker.set(0);
	}
		
	public void resetKickBall() {
	
		while (kickReady.get()) {
			Ballkicker.set(0.25);	
		}
		Ballkicker.set(0);
	}


	public int getJoyStickValue(){
		if (shootLeft.getRawButton(Consts.SHOOTER_LEFT_BTN_STOP)){
			return Consts.SHOOTER_JOYSTICK_CODE_STOP;
		}
		else if (shootLeft.getRawButton(Consts.SHOOTER_LEFT_BTN_LIFTERDOWN)){
			return Consts.SHOOTER_JOYSTICK_CODE_LIFTERDOWN;
		}
		else if (shootLeft.getRawButton(Consts.SHOOTER_LEFT_BTN_LIFTERUP)){
			return Consts.SHOOTER_JOYSTICK_CODE_LIFTERUP;
		}
		else if (shootRight.getRawButton(Consts.SHOOTER_RIGHT_BTN_LOADBALL)){
			return  Consts.SHOOTER_JOYSTICK_CODE_LOADBALL ; 
		}
		else if (shootRight.getRawButton(Consts.SHOOTER_RIGHT_BTN_SHOOTBALL)){
			return Consts.SHOOTER_JOYSTICK_CODE_SHOOTBALL;
		}
		else if (shootRight.getRawButton(Consts.SHOOTER_RIGHT_BTN_DEGREE_PICKUP)){
			return Consts.SHOOTER_JOYSTICK_CODE_DEGREE_PICKUP;
		}
		else if (shootRight.getRawButton(Consts.SHOOTER_RIGHT_BTN_DEGREE_CORRECTION)){
			return  Consts.SHOOTER_JOYSTICK_CODE_DEGREE_CORRECTION; 
		}
		else if (shootRight.getRawButton(Consts.SHOOTER_RIGHT_BTN_DEGREE_DRIVE)){
			return  Consts.SHOOTER_JOYSTICK_CODE_DEGREE_DRIVE; 
		}
		else if (shootRight.getRawButton(Consts.SHOOTER_RIGHT_BTN_ARM_RESET)){
			return  Consts.SHOOTER_JOYSTICK_CODE_ARM_RESET; 
		}
		else{
			return Consts.SHOOTER_JOYSTICK_CODE_DEFAULT;
		}
	}
		
	
	
	
	public void manipulatorPeriodic(){

	switch(getJoyStickValue()) {
		
		case Consts.SHOOTER_JOYSTICK_CODE_STOP:
			stop();
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_LIFTERDOWN: 
			Lifterdown();
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_LIFTERUP: 
			LifterUp();
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_LOADBALL: 
			loadBall();
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_SHOOTBALL:
			shootBall();
			break;

		case Consts.SHOOTER_JOYSTICK_CODE_DEGREE_PICKUP: // This is for picking up. WE are going to a 115 deg pick up angle.
			degree_pickup();
			break;

		case Consts.SHOOTER_JOYSTICK_CODE_DEGREE_CORRECTION: // This is for shooting. We are going to a 45 deg shooting angle.
			degree_corection();
			break;

		case Consts.SHOOTER_JOYSTICK_CODE_DEGREE_DRIVE: // This is for driving. WE are going to a 103 deg pick up angle.
			degree_drive();
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_ARM_RESET: 
			armReset();
			break;

		default: // i.e. Consts.SHOOTER_JOYSTICK_CODE_DEFAULT:
			stop();
			break;
	}

	SmartDashboard.putNumber("Shooter Pot Degrees",shaftRotation.fetchDegrees());
	SmartDashboard.putBoolean("Kick completed",kickComplete.get() );
	SmartDashboard.putBoolean("Kick Ready",kickReady.get() );
	SmartDashboard.putBoolean("Shooter Home",limitswitch.get() );
	SmartDashboard.putNumber("Time", Timer.getMatchTime());
	
	
	
	
	}
}
	
