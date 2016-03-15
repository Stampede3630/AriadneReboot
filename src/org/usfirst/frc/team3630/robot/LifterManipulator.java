package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Encoder;


public class LifterManipulator  {
	
	// initialize talons 
	Talon spinLeft;
	Talon spinRight;
	Talon Lifter;
	Talon Ballkicker;
	DigitalInput kickReady;
	DigitalInput kickComplete;
	DigitalInput limitswitch;

	Joystick shootLeft;// 2 for shooting and driving 
	Joystick shootRight;
	
	Encoder shooterrotation;
	boolean evenPos = false;
	
	public LifterManipulator(){
		spinLeft = new Talon(6);
		 spinRight = new Talon(7);
		 Lifter = new Talon(4);
		 Ballkicker = new Talon(5);
		 kickReady = new DigitalInput(6);
		 kickComplete = new DigitalInput(5);
		 limitswitch = new  DigitalInput(4);
		shootLeft= new Joystick(1);
		shootRight= new Joystick(2);
		shooterrotation = new Encoder(13,14, false);
		shooterrotation.setDistancePerPulse(1);
		//spinLeft.setInverted(spinLeft.equals(true));
		//spinRight.setInverted(spinRight.equals(true));
		//spinLeft.setInverted(true);
		//spinRight.setInverted(false);
	}

	public void publishtodash(){
		 
	}
	
	public void degree_shoot(){
		boolean upperbinaryValue= limitswitch.get();
		double rot = -40;
		double rotdeviation= 3;
		
		if(shooterrotation.getRaw() <= rot-rotdeviation){
			Lifter.set(-0.1);
		}
		
		else if(upperbinaryValue == true && shooterrotation.getRaw()>=rot+rotdeviation)
			Lifter.set(0.3);
		
		else if (upperbinaryValue == false)
			stop();
		}


	public void degree_pickup(){
		
		boolean upperbinaryValue= limitswitch.get();
		double rot = -20;
		double rotdeviation = 5;
		
		if(shooterrotation.getRaw() <= rot-rotdeviation){
			Lifter.set(-0.1);
		}
		
		else if(upperbinaryValue == true && shooterrotation.getRaw()>=rot+rotdeviation)
			Lifter.set(0.3);
		
		else if (upperbinaryValue == false)
			stop();
		}


	public void degree_drive(){
		
		boolean upperbinaryValue= limitswitch.get();
		double rot = 103;
		double rotdeviation = 5;
		
		if(shooterrotation.getRaw() <= rot-rotdeviation){
			Lifter.set(.3);
		}
		
		else if(upperbinaryValue == true && shooterrotation.getRaw()>=rot+rotdeviation)
			Lifter.set(-0.15);
		
		else if (upperbinaryValue == false)
			stop();
		}

	public void Lifterdown(){
		 Lifter.set(.4 );
	}

	public void LifterUp(){

		Lifter.set(-.75);
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
		
	//	resetKickBall();
	}
	
//	public void kickTest() {
//		Ballkicker.set(-0.1);
//	}

	public void LifterManipulatorinit(){
		if (limitswitch.get() == true){
			LifterUp();
		}
		else if ((limitswitch.get() == false)){
			stop();
			shooterrotation.reset();
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

		final int maxTimeDelaySec = 2;
		final int loopsPerSec = 10;
		int kickLoops = 0;
 		while (kickComplete.get() && (kickLoops < (maxTimeDelaySec * loopsPerSec)) && !shootLeft.getRawButton(Consts.SHOOTER_LEFT_BTN_STOP)) {
 			SmartDashboard.putBoolean("Kick completed",kickComplete.get());
 			Ballkicker.set(-1);
			Timer.delay(1.5 / loopsPerSec);
			kickLoops++;
		}
		Ballkicker.set(0);
		resetKickBall();
	}
	
	public void set_shooter_pos(double pos)
	{
		final double margin = 0.2;
		final double offset = 0.25;
		double curPos = shooterrotation.getDistance();
		
		// adjust desire position - we want it a bit higher than requested.
		pos = pos + offset; // a positive offset adjustment will make it higher.
		
		SmartDashboard.putNumber("Goto Lifter Pos", pos);
		SmartDashboard.putNumber("Cur Lifter Pos", curPos);

		double magnitudeDif = Math.abs(pos - curPos);
		if ((magnitudeDif > margin) && evenPos)
		{

			// Shooter position: further down has more negative (lesser number)
			if (pos > curPos) // desired position is less negative, lower angle
			{
				Lifter.set(-0.35); // go up
			}
			else
			{
				Lifter.set(0.1); // go down
			}
		}
		else
		{
			Lifter.set(0);
		}
		evenPos = !evenPos;
	}
	
	public void auto_adjust(){
	ImageMath	math = new ImageMath();
		double distance= math.math_periodic();
		SmartDashboard.putNumber("Distance Away", distance);
		
		if (180 <= distance  ){
			Lifter.set(0);
		}
		else if (115 <= distance  ){
			set_shooter_pos(-11.74);
		}

		else if(105 <= distance  ){
			set_shooter_pos(-11.25);
		}
		else if(97 <= distance){
			set_shooter_pos(-10.25);
		}
		else if( 89 <= distance ){
			set_shooter_pos(-9.25);
		}

		else if( 81 <= distance  ){
			set_shooter_pos(-8.5);
		}
		else if( 77  <= distance ){
			set_shooter_pos(-7);
		}
		
		else {
			Lifter.set(0);
		}

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
		else if (shootRight.getRawButton(6)){
			return 8;
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
			LifterManipulatorinit();
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_LOADBALL: 
			loadBall();
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_SHOOTBALL:
			shootBall();
			break;

		case 8:
			auto_adjust();
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

	SmartDashboard.putBoolean("Kick completed",kickComplete.get());
	SmartDashboard.putBoolean("Kick Ready",kickReady.get());
	SmartDashboard.putBoolean("Shooter Home",limitswitch.get());
	SmartDashboard.putNumber("Time", Timer.getMatchTime());
	SmartDashboard.putNumber("Shooter Angle",shooterrotation.getDistance());
	SmartDashboard.putNumber("Shooter Angle RAW",shooterrotation.getRaw());

	}
}
	
