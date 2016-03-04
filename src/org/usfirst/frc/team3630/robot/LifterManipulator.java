package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Encoder;

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
	
	Encoder shooterrot;
	
	public LifterManipulator(){
		shootLeft= new Joystick(1);
		shootRight= new Joystick(2);
		shooterrot = new Encoder(13,14);
		
		//spinLeft.setInverted(spinLeft.equals(true));
		//spinRight.setInverted(spinRight.equals(true));
		//spinLeft.setInverted(true);
		//spinRight.setInverted(false);
	}

	public void publishtodash(){
		 
	}
	
	public void degree_shoot(){
		boolean upperbinaryValue= limitswitch.get();
		double rot = 330;
		double rotdeviation= 5;
		
		if(shooterrot.getRaw() <= rot-rotdeviation){
			Lifter.set(.3);
		}
		
		else if(upperbinaryValue == true && shooterrot.getRaw()>=rot+rotdeviation)
			Lifter.set(-0.15);
		
		else if (upperbinaryValue == false)
			stop();
		}


	public void degree_pickup(){
		
		boolean upperbinaryValue= limitswitch.get();
		double rot = 115;
		double rotdeviation = 5;
		
		if(shooterrot.getRaw() <= rot-rotdeviation){
			Lifter.set(.3);
		}
		
		else if(upperbinaryValue == true && shooterrot.getRaw()>=rot+rotdeviation)
			Lifter.set(-0.15);
		
		else if (upperbinaryValue == false)
			stop();
		}


	public void degree_drive(){
		
		boolean upperbinaryValue= limitswitch.get();
		double rot = 103;
		double rotdeviation = 5;
		
		if(shooterrot.getRaw() <= rot-rotdeviation){
			Lifter.set(.3);
		}
		
		else if(upperbinaryValue == true && shooterrot.getRaw()>=rot+rotdeviation)
			Lifter.set(-0.15);
		
		else if (upperbinaryValue == false)
			stop();
		}

	public void Lifterdown(){
		 Lifter.set(.3);
	}

	public void LifterUp(){

		Lifter.set(-.5);
		}
	
	public void loadBall(){
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
	
	public void armIn(){
		if (limitswitch.get() == true){
			LifterUp();
		}
		else if ((limitswitch.get() == false)){
			stop();
		}
	}
	
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
			degree_shoot();
			break;

		case Consts.SHOOTER_JOYSTICK_CODE_DEGREE_DRIVE: // This is for driving. WE are going to a 103 deg pick up angle.
			degree_drive();
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_ARM_RESET: 
		default: // i.e. Consts.SHOOTER_JOYSTICK_CODE_DEFAULT:
			stop();
			break;
	}

	SmartDashboard.putBoolean("Kick completed",kickComplete.get() );
	SmartDashboard.putBoolean("Kick Ready",kickReady.get() );
	SmartDashboard.putBoolean("Shooter Home",limitswitch.get() );
	SmartDashboard.putNumber("Time", Timer.getMatchTime());
	SmartDashboard.putNumber("Straight Arm Raw", shooterrot.getRaw());
	
	}
}
	
