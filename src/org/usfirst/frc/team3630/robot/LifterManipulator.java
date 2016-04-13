package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Encoder;

public class LifterManipulator  {
	
	// initialize talons 
	Talon spinLeftTalon;
	Talon spinRightTalon;
	Talon lifterTalon;
	Talon ballKickerTalon;
	DigitalInput kickReady;
	DigitalInput kickComplete;
	DigitalInput lifterLimit;
	
	// 2 for shooting and driving 
	Joystick shootLeftJoy;
	Joystick shootRightJoy;
	Joystick breachRightJoy;
	
	Encoder shooterrotation;
	boolean evenPos = false;
	
	DriveTrain tankDriveTrain;
	
	public LifterManipulator(DriveTrain myTankDriveTrain){
		tankDriveTrain = myTankDriveTrain;
		spinLeftTalon = new Talon(Consts.SPIN_LEFT_TALON_CHAN);
		spinRightTalon = new Talon(Consts.SPIN_RIGHT_TALON_CHAN);
		lifterTalon = new Talon(Consts.LIFTER_TALON_CHAN);
		ballKickerTalon = new Talon(Consts.BALL_KICKER_TALON_CHAN);
		kickReady = new DigitalInput(Consts.KICK_READY_DIGITAL_INPUT_CHAN);
		kickComplete = new DigitalInput(Consts.KICK_COMPLETE_DIGITAL_INPUT_CHAN);
		lifterLimit = new  DigitalInput(Consts.LIFTER_LIMIT_DIGITAL_INPUT_CHAN);
		shootLeftJoy = new Joystick(Consts.SHOOTER_LEFT_JOYSTICK_CHAN);
		shootRightJoy = new Joystick(Consts.SHOOTER_RIGHT_JOYSTICK_CHAN);
		breachRightJoy = new Joystick(Consts.BREACH_RIGHT_JOYSTICK_CHAN);
		shooterrotation = new Encoder(13,14, false);
		shooterrotation.setDistancePerPulse(1);
	}
	
	public void degree_shoot(){
		boolean upperbinaryValue = lifterLimit.get();
		double rot = -40;
		double rotdeviation= 3;
		
		if(shooterrotation.getRaw() <= rot-rotdeviation){
			lifterTalon.set(-0.1);
		}
		
		else if(upperbinaryValue == true && shooterrotation.getRaw()>=rot+rotdeviation)
			lifterTalon.set(0.3);
		
		else if (upperbinaryValue == false)
			stop();
		}


	public void degree_pickup(){
		
		boolean upperbinaryValue = lifterLimit.get();
		double rot = -20;
		double rotdeviation = 5;
		
		if(shooterrotation.getRaw() <= rot-rotdeviation){
			lifterTalon.set(-0.1);
		}
		
		else if(upperbinaryValue == true && shooterrotation.getRaw()>=rot+rotdeviation)
			lifterTalon.set(0.3);
		
		else if (upperbinaryValue == false)
			stop();
		}


	public void degree_drive(){
		
		boolean upperbinaryValue= lifterLimit.get();
		double rot = 103;
		double rotdeviation = 5;
		
		if(shooterrotation.getRaw() <= rot-rotdeviation){
			lifterTalon.set(.3);
		}
		
		else if(upperbinaryValue == true && shooterrotation.getRaw()>=rot+rotdeviation)
			lifterTalon.set(-0.15);
		
		else if (upperbinaryValue == false)
			stop();
		}

	public void Lifterdown(){
		 lifterTalon.set(.4 );
		}

	public void LifterUp(){
		lifterTalon.set(-.7);
		}
	
	public void autoLifterUp(){
		lifterTalon.set(-1);
	}
	
	public void loadBall(){
		spinLeftTalon.set(-.5);
		spinRightTalon.set(.5);
		}
	
	public void shootBall(){
		
		spinLeftTalon.set(1);
		spinRightTalon.set(-1);
		Timer.delay(1.5);
		kick_ball();
		}

	public void shootBallQuickly(){
		
		spinLeftTalon.set(1);
		spinRightTalon.set(-1);
		Timer.delay(0.5);
		kick_ball();
		}

	public boolean LifterManipulatorinit(){
		boolean isComplete = false;
		if (lifterLimit.get() == true){
			LifterUp();
			}
		else if ((lifterLimit.get() == false)){
			stop();
			shooterrotation.reset();
			isComplete = true;
			}
		return isComplete;
		}
	
	public void armIn(){
		if (lifterLimit.get() == true){
			LifterUp();
			}
		else if ((lifterLimit.get() == false)){
			stop();
			}
		}
	
	public void stop(){
		spinLeftTalon.set(0);
		spinRightTalon.set(0);
		lifterTalon.set(0);
		ballKickerTalon.set(0);
		resetKickBall();
		}

		
	public void kick_ball(){

		final int maxTimeDelaySec = 2;
		final int loopsPerSec = 10;
		int kickLoops = 0;
 		while (kickComplete.get() && (kickLoops < (maxTimeDelaySec * loopsPerSec)) && !shootLeftJoy.getRawButton(Consts.SHOOTER_LEFT_BTN_STOP)) {
 			SmartDashboard.putBoolean("Kick completed",kickComplete.get());
 			ballKickerTalon.set(-1);
			Timer.delay(1.5 / loopsPerSec);
			kickLoops++;
 			}
		ballKickerTalon.set(0);
		resetKickBall();
		}
	
	public boolean set_shooter_angle(double angle){
		final double margin = 1;
		double curPos = shooterrotation.getDistance();
		boolean atDesiredAngle = false;
		
		double magnitudeDif = Math.abs(angle - curPos);
		if (magnitudeDif > margin){
			if (angle > curPos){ // desired position is less negative, lower angle
				lifterTalon.set(-0.4); // go up
				// lifterTalon.set(0);
				atDesiredAngle = true;
			}else{
				lifterTalon.set(0.5); // go down
			}
		} else {
			lifterTalon.set(0);
			atDesiredAngle = true;
		}
		return atDesiredAngle;
	}
	
	public boolean set_shooter_pos(double pos){
		final double margin = 0.23; 
		final double offset = 0.0;
		double curPos = shooterrotation.getDistance();
		boolean isComplete = false;
		
		// adjust desire position - we want it a bit higher than requested.
		pos = pos + offset; // a positive offset adjustment will make it higher.
		
		SmartDashboard.putNumber("Goto Lifter Pos", pos);
		SmartDashboard.putNumber("Cur Lifter Pos", curPos);

		double magnitudeDif = Math.abs(pos - curPos);
		if ((magnitudeDif > margin) && evenPos){

			// Shooter position: further down has more negative (lesser number)
			if (pos > curPos){ // desired position is less negative, lower angle
				lifterTalon.set(-0.6); // go up -0.35
			}else{
				lifterTalon.set(0.2); // go down
			}
		}else{
			lifterTalon.set(0);
			isComplete = true;
		}
		evenPos = !evenPos;
		return isComplete;
		}
	
	public boolean auto_adjust() {
		boolean isComplete = false;
		ImageMath	math = new ImageMath();
		double distance= math.get_dist_from_image();
		SmartDashboard.putNumber("Distance Away", distance);
		
		if (180 <= distance){
			lifterTalon.set(0);
			isComplete = true;
		}
		else if (115 <= distance){
			isComplete = set_shooter_pos(-13.5);
		}

		else if(105 <= distance){
			isComplete = set_shooter_pos(-13.25);
		}
		else if(97 <= distance){
			isComplete = set_shooter_pos(-13.25);
		}
		else if( 89 <= distance){
			isComplete = set_shooter_pos(-13);
		}

		else if( 81 <= distance){
			isComplete = set_shooter_pos(-12.5);
		}
		else if( 77  <= distance){
			isComplete = set_shooter_pos(-10.25);
		}
		else if( 50  <= distance){
			isComplete = set_shooter_pos(-10); //test
		}
		else {
			lifterTalon.set(0);
			isComplete = true;
		}
		return isComplete;
	}
	
	public boolean driveAdjust(){
		boolean isComplete = false;
		ImageMath	math = new ImageMath();
		double distance= math.get_dist_from_image();
			
		// Determine left/right robot orientation.
		double actual_right_of_center_px = math.get_target_right_of_center_px();
		double desired_right_of_center_px = actual_right_of_center_px; // Will do nothing if not in range.
		if (100 <= distance) {
			desired_right_of_center_px = 27;
		} 
		else if (91 <= distance) {
			desired_right_of_center_px = 28;
		}
		else if (81 <= distance) {
			desired_right_of_center_px = 33; 
		}
		else if (77 <= distance) {
			desired_right_of_center_px = 41; 
		}
		else {
			desired_right_of_center_px = 44; 
		}
		
		
		// Desired rotation will be positive if we need to rotate left.
		double desired_rotation_px = desired_right_of_center_px - actual_right_of_center_px;
		double desired_rotation_deg = Consts.imageWidthDeg * desired_rotation_px / Consts.imageWidthPx;
		final double rot_margin_deg = 0;
		final double max_rot_angle_deg = 3;
		if (Math.abs(desired_rotation_deg) > rot_margin_deg) {
			if (Math.abs(desired_rotation_deg) > max_rot_angle_deg) {
				if (desired_rotation_deg >= 0) {
					desired_rotation_deg = max_rot_angle_deg;
					tankDriveTrain.turnLeft(0.6);
				}
				else
				{
					desired_rotation_deg = -max_rot_angle_deg;
					tankDriveTrain.turnLeft(-0.6);
				}
			}
			//tankDriveTrain.turnLeft(0.13 * desired_rotation_deg); // Need to figure out the constant.
		}
		else {
			isComplete = true;
		}
			
		return isComplete;
	}

	public void resetKickBall() {
		while (kickReady.get()) {
			ballKickerTalon.set(0.4);	
		}
		ballKickerTalon.set(0);
	}

	public int getJoyStickValue(){
		if (shootLeftJoy.getRawButton(Consts.SHOOTER_LEFT_BTN_STOP)){
			return Consts.SHOOTER_JOYSTICK_CODE_STOP;
		}
		else if (shootLeftJoy.getRawButton(Consts.SHOOTER_LEFT_BTN_LIFTERDOWN)){
			return Consts.SHOOTER_JOYSTICK_CODE_LIFTERDOWN;
		}
		else if (shootLeftJoy.getRawButton(Consts.SHOOTER_LEFT_BTN_LIFTERUP)){
			return Consts.SHOOTER_JOYSTICK_CODE_LIFTERUP;
		}
		else if (shootRightJoy.getRawButton(Consts.SHOOTER_RIGHT_BTN_LOADBALL)){
			return  Consts.SHOOTER_JOYSTICK_CODE_LOADBALL ; 
		}
		else if (shootRightJoy.getRawButton(Consts.SHOOTER_RIGHT_BTN_SHOOTBALL)){
			return Consts.SHOOTER_JOYSTICK_CODE_SHOOTBALL;
		}
		else if (shootRightJoy.getRawButton(Consts.SHOOTER_RIGHT_BTN_SHOOTQUICK)){
			return Consts.SHOOTER_JOYSTICK_CODE_SHOOTQUICK;
		}
		else if (breachRightJoy.getRawButton(3)){
			return 8;
		}
		else if (shootRightJoy.getRawButton(4)){
			return Consts.SHOOTER_JOYSTICK_CODE_DEGREE_PICKUP;
		}
		else if (breachRightJoy.getRawButton(11)){
			return  Consts.SHOOTER_JOYSTICK_CODE_DEGREE_CORRECTION; 
		}
		else if (breachRightJoy.getRawButton(12)){
			return  Consts.SHOOTER_JOYSTICK_CODE_DEGREE_DRIVE; 
		}
		else if (shootRightJoy.getRawButton(Consts.SHOOTER_RIGHT_BTN_ARM_RESET)){
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

		case Consts.SHOOTER_JOYSTICK_CODE_SHOOTQUICK:
			shootBallQuickly();
			break;

		case 8:
			driveAdjust();
			break;

		case Consts.SHOOTER_JOYSTICK_CODE_DEGREE_PICKUP: // This is for picking up. WE are going to a 115 deg pick up angle.
			auto_adjust();
			break;

		case Consts.SHOOTER_JOYSTICK_CODE_DEGREE_CORRECTION: // This is for shooting. We are going to a 45 deg shooting angle.
			tankDriveTrain.moveRight(0.8);
			break;

		case Consts.SHOOTER_JOYSTICK_CODE_DEGREE_DRIVE: // This is for driving. WE are going to a 103 deg pick up angle.
			tankDriveTrain.moveLeft(0.8);
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_ARM_RESET: 
			default: // i.e. Consts.SHOOTER_JOYSTICK_CODE_DEFAULT:
			stop();
			break;
	}

	SmartDashboard.putBoolean("Kick completed",kickComplete.get());
	SmartDashboard.putBoolean("Kick Ready",kickReady.get());
	SmartDashboard.putBoolean("Shooter Home",lifterLimit.get());
	SmartDashboard.putNumber("Time", Timer.getMatchTime());
	SmartDashboard.putNumber("Shooter Angle",shooterrotation.getDistance());
	SmartDashboard.putNumber("Shooter Angle RAW",shooterrotation.getRaw());
	}
}
	
