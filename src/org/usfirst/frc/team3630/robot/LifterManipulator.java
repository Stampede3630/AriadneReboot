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
	Boulder_encoder leftMotor;
	Boulder_encoder rightMotor;
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
		shooterrotation = new Encoder(
				Consts.SHOOTER_ENCODER_DIGITAL_INPUT_CHAN,
				Consts.SHOOTER_ENCODER_DIGITAL_OUTPUT_CHAN, false);
		shooterrotation.setDistancePerPulse(1);
		leftMotor = new Boulder_encoder(
				Consts.LEFT_BOULDER_ENCODER_DIGITAL_INPUT_CHAN,
				Consts.LEFT_BOULDER_ENCODER_DIGITAL_OUTPUT_CHAN);
		rightMotor = new Boulder_encoder(
				Consts.RIGHT_BOULDER_ENCODER_DIGITAL_INPUT_CHAN,
				Consts.RIGHT_BOULDER_ENCODER_DIGITAL_OUTPUT_CHAN);
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
/*		StringBuilder sbRight = new StringBuilder(32);
		sbRight.append("Right motor RPM");
		StringBuilder sbLeft = new StringBuilder(32);
		sbLeft.append("Left motor RPM");
		// Loop for 1.5 sec - read the RPM each 0.1 sec
		for (int i = 0; i < 15; i++) {
			StringBuilder sbR = new StringBuilder(32);
			StringBuilder sbL = new StringBuilder(32);
			sbR.append(sbRight);
			sbR.append(Integer.toString(i));
			sbL.append(sbLeft);
			sbL.append(Integer.toString(i));
 			SmartDashboard.putNumber(sbR.toString(),rightMotor.RPM());
 			SmartDashboard.putNumber(sbL.toString(),leftMotor.RPM());
			Timer.delay(0.1);
		}
		*/
		Timer.delay(1.5); // Ramp up shooter motor for only 1 second (not 1.5 second).
		// SmartDashboard.putNumber("Right motor RPM",rightMotor.RPM());
		// SmartDashboard.putNumber("Left Motor RPM",leftMotor.RPM());
		// Todo: Add initial test code here to measure the left and right boulder encoder RPM values.
		kick_ball();
		}

	public void shootBallQuickly() {
		spinLeftTalon.set(1);
		spinRightTalon.set(-1);
		Timer.delay(0.5);
		kick_ball();
		}

	public void feedoutBall() {
		spinLeftTalon.set(0.4);
		spinRightTalon.set(-0.4);
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

		
	public void kick_ball() {
		// NOTE: The RPM related functionality is fully commented out because our initial
		// encoders on the shooter motors were of different types, and RPM values we
		// measured were inconsistent.
		
		// Add code to measure the left and right boulder encoder RPM slow-down amounts
		// during the ball shooting. To get a good slowdown, take multiple measurements of the
		// RPMs, getting the initial RPMs, then determining the minimum RPMs prior to RPM
		// increase afer the ball has shot. So give a little extra motor run-time to verify
		// the RPM increase again after the kicker kicks.
		
		final int maxTimeDelaySec = 2;
		int kickLoops = 0;
		final int loopsPerSec = 10;
		int maxLoops = maxTimeDelaySec * loopsPerSec;
		
		/* DEPRECATED RPM CODE:
		double initialRightRPM = rightMotor.RPM();
		double initialLeftRPM = leftMotor.RPM();
		double decreasedRightRPM = initialRightRPM;
		double decreasedLeftRPM = initialLeftRPM;
		SmartDashboard.putNumber("Prekick Right motor RPM", initialRightRPM);
		SmartDashboard.putNumber("Prekick Left Motor RPM", initialLeftRPM);*/
		
 		while (kickComplete.get() && (kickLoops < maxLoops) && !shootLeftJoy.getRawButton(Consts.SHOOTER_LEFT_BTN_STOP)) {
 			
 			/* DEPRECATED RPM MEASUREMENT CODE:
 			 *This is measurement code (removed when done)
 			double curRightRPM = rightMotor.RPM();
 			double curLeftRPM = leftMotor.RPM();
 			if (Math.abs(curRightRPM - initialRightRPM) > Math.abs(decreasedRightRPM - initialRightRPM)) {
 				decreasedRightRPM = curRightRPM; // Record the max change in RPM
 			}
 			if (Math.abs(curLeftRPM - initialLeftRPM) > Math.abs(decreasedLeftRPM - initialLeftRPM)) {
 				decreasedLeftRPM = curLeftRPM; // Record the max change in RPM
 			}
 			SmartDashboard.putNumber("Right motor RPM", curRightRPM);
 			SmartDashboard.putNumber("Left Motor RPM", curLeftRPM);
			*/
 			
 			SmartDashboard.putBoolean("Kick completed", kickComplete.get());
 			ballKickerTalon.set(-1);
			Timer.delay(0.1);
			kickLoops++;
 			}
		SmartDashboard.putBoolean("Kick completed", kickComplete.get());
		
 		ballKickerTalon.set(0);
		resetKickBall();
		
		/* DEPRECATED RPM MEASUREMENT CODE:
		for (int i=0; i < 20; i++) {
			double curRightRPM = rightMotor.RPM();
			double curLeftRPM = leftMotor.RPM();
			if (Math.abs(curRightRPM - initialRightRPM) > Math.abs(decreasedRightRPM - initialRightRPM)) {
				decreasedRightRPM = curRightRPM; // Record the max change in RPM
			}
			if (Math.abs(curLeftRPM - initialLeftRPM) > Math.abs(decreasedLeftRPM - initialLeftRPM)) {
				decreasedLeftRPM = curLeftRPM; // Record the max change in RPM
			}
 			SmartDashboard.putNumber("Right motor RPM", curRightRPM);
 			SmartDashboard.putNumber("Left Motor RPM", curLeftRPM);
			Timer.delay(0.1);
		}
		SmartDashboard.putNumber("Decreased Right motor RPM", decreasedRightRPM);
		SmartDashboard.putNumber("Decreased Left Motor RPM", decreasedLeftRPM);
		 */	
		}
	
	// This version of the set shooter angle/pos is called from autonomous low-bar periodic only
	// for lowering the shooter to -23.5 or -24 encoder setting so to traverse the low-bar barrier.
	public boolean set_shooter_angle(double newEncVal) {
		// Return true if complete: if the shooter encoder value is close enough to that requested.
		boolean isComplete = false;

		final double margin = 1; // big margin - just to get the shooter down quickly
		double curEncVal = shooterrotation.getDistance();
		
		double magnitudeDif = Math.abs(newEncVal - curEncVal);
		if (magnitudeDif > margin) {
			if (newEncVal > curEncVal) {
				lifterTalon.set(-0.4); // go up
				// isComplete = true; // this was why the shooter would stop going up in autonomous!
			} else {
				lifterTalon.set(0.5); // go down
			}
		} else {
			lifterTalon.set(0);
			isComplete = true;
		}
		return isComplete;
	}
	
	// This version of the set_shooter angle/pos is called for shooting, so should be semi-accurate.
	public boolean set_shooter_pos(double newEncVal) {
		// Return true if complete: if the shooter encoder value is close enough to that requested.
		boolean isComplete = false;

		final double margin = 0.3; // was 0.23; changed hoping for more accuracy
		final double offset = 0.0; // No offset - we expect to be told an accurate encoder value to go to.
		double curEncVal = shooterrotation.getDistance();
		
		// Adjust desire position - we want it a bit higher than requested.
		newEncVal = newEncVal + offset; // a positive offset adjustment will make it higher.
		
		SmartDashboard.putNumber("Goto Lifter Pos", newEncVal);
		SmartDashboard.putNumber("Cur Lifter Pos", curEncVal);

		double magnitudeDif = Math.abs(newEncVal - curEncVal);
		if ((magnitudeDif > margin) && evenPos){

			// Shooter position: further down has more negative (lesser number)
			if (newEncVal > curEncVal){ // desired position is less negative, lower angle
				lifterTalon.set(-0.6); // go up - must be large enough for the motor to react.
			}else{
				lifterTalon.set(0.2); // go down
			}
		} else {
			lifterTalon.set(0); // stop the motor
			if (magnitudeDif <= margin) {
				isComplete = true; // Only done once we are at or below the margin.
			}
		}
		evenPos = !evenPos;
		return isComplete;
		}
	
	public boolean setShooterAngleDeg(double shooterAngleDegrees) {
		// First map degrees to encoder values.
		double shooterEncoderValue = getShooterEncoderValue(shooterAngleDegrees);
		SmartDashboard.putNumber("AUTO_ADJ Encoder Goal", shooterEncoderValue);
		return set_shooter_pos(shooterEncoderValue);
	}
	
	public boolean auto_adjust() {
		// Automatically adjust the shooter up/down angle from the camera info.
		
		// The camera gives us the distance based on the width of the detected target.
		double distanceInches = ImageMath.get_targetDistanceInches();
		SmartDashboard.putNumber("AUTO_ADJ Distance (inches)", distanceInches);
		
		// The smarts of the algorithm are here. There are two implementation methods.
		// It can either determine the shooter angle based on lookup-tables or a formula.
		double shooterAngleDegrees = getShooterAngleDegrees(distanceInches);
		SmartDashboard.putNumber("AUTO_ADJ Angle (degrees)", shooterAngleDegrees);
		
		return setShooterAngleDeg(shooterAngleDegrees);
	}
	
	private double getShooterEncoderValue(double shooterAngleDegrees) {
/*		double shooterEncoderValue = -13.25;
		if (shooterAngleDegrees < 27) {
			shooterEncoderValue = -13.5;
		}
		else if (shooterAngleDegrees < 30) {
			shooterEncoderValue = -13.25;
		}
		else if (shooterAngleDegrees < 33) {
			shooterEncoderValue = -13.0;
		}
		else if (shooterAngleDegrees < 35) {
			shooterEncoderValue = -12.5;
		}
		else if (shooterAngleDegrees < 37.5) {
			shooterEncoderValue = -10.25;
		}
		else if (shooterAngleDegrees < 48) {
			shooterEncoderValue = -10.0;
		}
		else {
			shooterEncoderValue = -10.0;
		}
*/		
		// The following formula was empirically determined from initial target
		// calibration data table values. The error is greater for smaller values
		// of angle degrees, corresponding to shooting from a greater distance.
		//return (-289.0 / shooterAngleDegrees) - 1.0;
		// return (shooterAngleDegrees-100.8)/(-4.185); // new fromula from saturday 4/23/16 pre champs 
		
		// Formula from Encoder value vs. shooter degrees best curve fit over 13 to 70 degrees.
		return 0.2253 * shooterAngleDegrees - 23.38;
	}

	private double getShooterAngleDegrees(double distanceInches) {
		// Just from the target distance from imaging and knowing the nominal delta height, we can
		// calculate the angle 'phi' from horizontal the target bottom position is at.
		double targetAngleRadians = Math.asin(Consts.deltaTargetHeightInches / distanceInches);
		// double phiRad = targetAngleRadians;
		double targetAngleDegrees = targetAngleRadians * 180 / Math.PI; // display on dashboard
		double phiDeg = targetAngleDegrees;
		// The x-axis is the horizontal distance from the robot to the target.
		double xTargetDistInches = distanceInches * Math.cos(targetAngleRadians);
		// The y-axis is the vertical distance from the floor up to the target.
		// double yTargetDistInches = Consts.deltaTargetHeightInches + 12.0; // want to shoot 12" above the target bottom
		double xTargetDistMeters = xTargetDistInches / Consts.inchesPerMeter;
		double x = xTargetDistMeters;
		double yTargetDistMeters = (Consts.targetMiddleHeightInches - Consts.nominalCameraHeightInches) / Consts.inchesPerMeter;
		double y = yTargetDistMeters;
		double v = Consts.boulderSpeedMetersPerSec;
		double g = Consts.gravityAcceleration;
		double termInsideSqrt = v * v * v * v - g * (g * x * x + 2 * y * v * v);
		// theta is vertical angle pointing shooter to shoot boulder through middle of target opening.
		double shooterAngleDegrees = Consts.defaultShooterAngleDegrees;
		if (termInsideSqrt >= 0) {
			double shooterAngleRadians = Math.atan((v * v - Math.sqrt(termInsideSqrt)) / (g * x));
			// double thetaRad = shooterAngleRadians;
			shooterAngleDegrees = shooterAngleRadians * 180 / Math.PI; // display this on the dashboard
			double thetaDeg = shooterAngleDegrees;
			// phi is the vertical angle of the target bottom from the 0-angle horizon.
			// double deltaRad = thetaRad - phiRad;
			double deltaDeg = thetaDeg - phiDeg;
			double cameraDeltaDeg = deltaDeg - Consts.cameraTiltDegrees;
			// Camera delta y-pixels should correlate with the target y-position from the image when the shooter is at the
			// desired angle. This can be used as a cross-check visually to verify the shooter is at the desired angle.
			double cameraDeltaYPx = cameraDeltaDeg  * Consts.cameraPxPerDegree; // display this value in the dashboard
			// Todo: Determine relation between cameraDeltaYPx and cameraTargetBotYPx.
		}
		
		ImageMath.put_shooter_angle(shooterAngleDegrees);

		return shooterAngleDegrees;
	}

	// Rotate robot left or right so to aim at the target.
	public boolean driveAdjust(){
		boolean isComplete = false;
		double distance= ImageMath.get_targetDistanceInches();
			
		// Determine left/right robot orientation.
		double actual_right_of_center_px = ImageMath.get_target_right_of_center_px();
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
		double desired_rotation_deg = Consts.cameraFovWidthDegrees * desired_rotation_px / Consts.cameraFovWidthPx;
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
		else if (breachRightJoy.getRawButton(Consts.BREACH_RIGHT_BTN_AUTODOWN)){
			return Consts.SHOOTER_JOYSTICK_CODE_AUTOLEFTRIGHT;
		}
		else if (shootRightJoy.getRawButton(Consts.SHOOTER_RIGHT_BTN_AUTOUPDOWN)){
			return Consts.SHOOTER_JOYSTICK_CODE_AUTOUPDOWN;
		}
		else if (breachRightJoy.getRawButton(11)){
			return  Consts.SHOOTER_JOYSTICK_CODE_DEGREE_CORRECTION; 
		}
		else if (breachRightJoy.getRawButton(12)){
			return  Consts.SHOOTER_JOYSTICK_CODE_DEGREE_DRIVE; 
		}
		else if (shootRightJoy.getRawButton(Consts.SHOOTER_RIGHT_BTN_FEEDOUT)){
			return  Consts.SHOOTER_JOYSTICK_CODE_FEEDOUT; 
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

		case Consts.SHOOTER_JOYSTICK_CODE_AUTOLEFTRIGHT:
			driveAdjust();
			break;

		case Consts.SHOOTER_JOYSTICK_CODE_AUTOUPDOWN: // This is for picking up. WE are going to a 115 deg pick up angle.
			auto_adjust();
			break;

		case Consts.SHOOTER_JOYSTICK_CODE_DEGREE_CORRECTION: // This is for shooting. We are going to a 45 deg shooting angle.
			tankDriveTrain.moveRight(0.8);
			break;

		case Consts.SHOOTER_JOYSTICK_CODE_DEGREE_DRIVE: // This is for driving. WE are going to a 103 deg pick up angle.
			tankDriveTrain.moveLeft(0.8);
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_FEEDOUT:
			feedoutBall();
			break;
			
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
	SmartDashboard.putNumber("Right motor RPM",rightMotor.RPM());
	SmartDashboard.putNumber("Left Motor RPM",leftMotor.RPM());
	}
}
	
