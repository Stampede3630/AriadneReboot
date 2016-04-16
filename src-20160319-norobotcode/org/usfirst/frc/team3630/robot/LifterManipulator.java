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

	Joystick shootLeftJoy;// 2 for shooting and driving 
	Joystick shootRightJoy;
	Joystick breachRightJoy;
	
	Encoder shooterrotation;
	boolean evenPos = false;
	
	DriveTrain tankDriveTrain;
	
	// Robot Drive for rotating shooter left/right for good shot.
	//RobotDrive mainDrive = new RobotDrive(0,1);// new robot drive
	
	// Will turn right if amount is positive (else will turn left).
	//public void turnRight(double amount){
		//mainDrive.tankDrive(amount, -amount);
	//}
	
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
		//spinLeftTalon.setInverted(spinLeft.equals(true));
		//spinRightTalon.setInverted(spinRight.equals(true));
		//spinLeftTalon.setInverted(true);
		//spinRightTalon.setInverted(false);
	}

	public void publishtodash(){
		 
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

		lifterTalon.set(-.75);
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
		
	//	resetKickBall();
	}
	
//	public void kickTest() {
//		Ballkicker.set(-0.1);
//	}

	public void LifterManipulatorinit(){
		if (lifterLimit.get() == true){
			LifterUp();
		}
		else if ((lifterLimit.get() == false)){
			stop();
			shooterrotation.reset();
		}
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
	
	public void set_shooter_pos(double pos)
	{
		final double margin = 0.25;
		final double offset = 0.2;
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
				lifterTalon.set(-0.35); // go up
			}
			else
			{
				lifterTalon.set(0.1); // go down
			}
		}
		else
		{
			lifterTalon.set(0);
		}
		evenPos = !evenPos;
	}
	
	public void auto_adjust() {
		ImageMath	math = new ImageMath();
		double distance= math.get_dist_from_image();
		SmartDashboard.putNumber("Distance Away", distance);
		
		if (180 <= distance  ){
			lifterTalon.set(0);
		}
		else if (115 <= distance  ){
			set_shooter_pos(-12.75);
		}

		else if(105 <= distance  ){
			set_shooter_pos(-12.75);
		}
		else if(97 <= distance){
			set_shooter_pos(-12);
		}
		else if( 89 <= distance ){
			set_shooter_pos(-11.5);
		}

		else if( 81 <= distance  ){
			set_shooter_pos(-10.75);
		}
		else if( 77  <= distance ){
			set_shooter_pos(-10);
		}
		
		else {
			lifterTalon.set(0);
		}
	}
		public void driveAdjust(){
			ImageMath	math = new ImageMath();
			double distance= math.get_dist_from_image();
			
		// Determine left/right robot orientation.
		double actual_right_of_center_px = math.get_target_right_of_center_px();
		double desired_right_of_center_px = actual_right_of_center_px; // Will do nothing if not in range.
		if (100 <= distance) {
			desired_right_of_center_px = 40;
		} 
		else if (91 <= distance) {
			desired_right_of_center_px = 44;
		}
		else if (81 <= distance) {
			desired_right_of_center_px = 51;
		}
		else if (77 <= distance) {
			desired_right_of_center_px = 55;
		}
		
		// Desired rotation will be positive if we need to rotate left.
		double desired_rotation_px = desired_right_of_center_px - actual_right_of_center_px;
		double desired_rotation_deg = Consts.imageWidthDeg * desired_rotation_px / Consts.imageWidthPx;
		final double rot_margin_deg = 1.0;
		if (Math.abs(desired_rotation_deg) > rot_margin_deg) {
			tankDriveTrain.turnLeft(0.2 * desired_rotation_deg); // Need to figure out the constant.
		}
	}
	

	public void resetKickBall() {
	
		while (kickReady.get()) {
			ballKickerTalon.set(0.25);	
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
		else if (breachRightJoy.getRawButton(3)){
			return 8;
		}
		else if (shootRightJoy.getRawButton(4)){
			return Consts.SHOOTER_JOYSTICK_CODE_DEGREE_PICKUP;
		}
		else if (shootRightJoy.getRawButton(Consts.SHOOTER_RIGHT_BTN_DEGREE_CORRECTION)){
			return  Consts.SHOOTER_JOYSTICK_CODE_DEGREE_CORRECTION; 
		}
		else if (shootRightJoy.getRawButton(Consts.SHOOTER_RIGHT_BTN_DEGREE_DRIVE)){
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

		case 8:
			driveAdjust();
			break;

		case Consts.SHOOTER_JOYSTICK_CODE_DEGREE_PICKUP: // This is for picking up. WE are going to a 115 deg pick up angle.
			auto_adjust();
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
	SmartDashboard.putBoolean("Shooter Home",lifterLimit.get());
	SmartDashboard.putNumber("Time", Timer.getMatchTime());
	SmartDashboard.putNumber("Shooter Angle",shooterrotation.getDistance());
	SmartDashboard.putNumber("Shooter Angle RAW",shooterrotation.getRaw());

	}
}
	