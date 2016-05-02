package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

	LifterManipulator shooter;
	DriveTrain mainDrive;
	
	Sensors sensors;
	int state;
	
	Timer timer;
	
	// Initial direction:
	float initialHeading;
	
	// Time points
	double barrierTimeSec;
	
	boolean shootingEnabled;
	
	boolean firstThrough;
	
	public void autonomousInit(LifterManipulator myShooter, DriveTrain myDriveTrain, Sensors mySensors){
		shooter = myShooter;
		mainDrive = myDriveTrain;
		sensors = mySensors;
		//if (ahrs == null)
		//ahrs = new AHRS(SPI.Port.kMXP); 
		
		//autoSelected = (String) chooser.getSelected();
		//autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		//System.out.println("Auto selected: " + autoSelected);
		
		timer = new Timer();
		state = 1;
		initialHeading = sensors.ahrs.getFusedHeading();
		shootingEnabled = false;
	}
	
	public void forward() {
		float curHeading = sensors.ahrs.getFusedHeading();
		mainDrive.moveForward(0.75, curHeading);
	}
	
	public boolean turnLeft(float desiredHeading){
		final float headingMargin = 6.0f; // TODO: decrease this after testing
		final double nominalRotSpeed = -1;
		boolean isAtDesiredHeading = false;
		float curHeading = sensors.ahrs.getFusedHeading();
		float neededHeadingChange = curHeading - desiredHeading;
		SmartDashboard.putNumber("Left Heading Change", neededHeadingChange);
		if (Math.abs(neededHeadingChange) >= 180) {
			if (neededHeadingChange < 0) {
				neededHeadingChange += 360.0f;
			}
		}
		SmartDashboard.putNumber("Left Adusted Change", neededHeadingChange);
		if (Math.abs(neededHeadingChange) < headingMargin)
		{
			SmartDashboard.putBoolean("Rotate Left", false);
			isAtDesiredHeading = true;
			mainDrive.moveLeft(0);
		}
		else
		{
			SmartDashboard.putBoolean("Rotate Left", true);

			mainDrive.moveLeft(nominalRotSpeed);
		}
		return isAtDesiredHeading;
	}
	
	public boolean turnRight(float desiredHeading){
		final float headingMargin = 6.0f; // TODO: decrease this after testing
		final double nominalRotSpeed = 1;
		boolean isAtDesiredHeading = false;
		float curHeading = sensors.ahrs.getFusedHeading();
		float neededHeadingChange = curHeading - desiredHeading;
		SmartDashboard.putNumber("Left Heading Change", neededHeadingChange);
		if (Math.abs(neededHeadingChange) >= 180) {
			if (neededHeadingChange < 0) {
				neededHeadingChange += 360.0f;
			}
		}
		SmartDashboard.putNumber("Left Adusted Change", neededHeadingChange);
		if (Math.abs(neededHeadingChange) < headingMargin)
		{
			SmartDashboard.putBoolean("Rotate Left", false);
			isAtDesiredHeading = true;
			mainDrive.moveLeft(0);
		}
		else
		{
			SmartDashboard.putBoolean("Rotate Left", true);

			mainDrive.moveLeft(nominalRotSpeed);
		}
		return isAtDesiredHeading;
	}
	
	public void lowbarPeriodic(){
		final double postBarrierDriveSec = 4; // Drive this amount of time after the barrier.
		final double barrierSideDetectInches = 20;

		// Robot drive speeds:
		final double barrierApproachSpeed = 0.75;
		final double barrierTraversalSpeed = 0.75;
		final double barrierExitSpeed = 0.75;
		final double slowDownSpeed = 0.35;
		final double stoppedSpeed = 0.0;
		
		// Known shooter angles:
		final double barrierShooterAngle = -24;
		final double nominalShootingAngle = -10.5;
		
		// local state machine steps
		final int initialState = 1;
		final int driveToBarrierState = initialState + 1;
		final int traversingBarrierState = driveToBarrierState + 1;
		final int exitBarrierState = traversingBarrierState + 1;
		final int approachingTargetState = exitBarrierState + 1;
		final int liftShooter = approachingTargetState +1;
		final int aimUpDown = liftShooter + 1;
		final int aimLeftRight = aimUpDown + 1;
		final int shoot = aimLeftRight +1;
		final int complete = shoot +1;

		boolean atDesiredAngle = false;
		boolean atDesiredHeading = false;
		boolean isLifterInitComplete = false;
		boolean isUpDownComplete = false;
		boolean isLeftRightComplete = false;
		
		SmartDashboard.putNumber("Auto State", state);
		
		switch (state) {
		
		case initialState:
			initialHeading = sensors.ahrs.getFusedHeading();
			SmartDashboard.putNumber("Auto Initial Heading", initialHeading);
			state++;
			break;

		case driveToBarrierState: //lower shooter and drive towards barrier
			if (sensors.sideSonar.getRangeInches() > barrierSideDetectInches){
				// We are not yet at the barrier
				atDesiredAngle = shooter.set_shooter_angle(barrierShooterAngle);
				//mainDrive.mainDrive.tankDrive(-barrierApproachSpeed, -barrierApproachSpeed);
				 mainDrive.moveForward(barrierApproachSpeed, initialHeading);
			} else { // we are at the barrier
				// We have reached the defense
				if (atDesiredAngle) {
					state++;
				}
				else
				{
					mainDrive.mainDrive.tankDrive(stoppedSpeed, stoppedSpeed);
					mainDrive.moveForward(stoppedSpeed, initialHeading);
					atDesiredAngle = shooter.set_shooter_angle(barrierShooterAngle);
					if (atDesiredAngle) {
						state++;
					}
				}
			}
			break;
			
		case traversingBarrierState: // traverse the barrier at a slow speed
			if (sensors.sideSonar.getRangeInches() <= barrierSideDetectInches){
				// We are still at the barrier
				mainDrive.mainDrive.tankDrive(-barrierTraversalSpeed, -barrierTraversalSpeed);
				mainDrive.moveForward(barrierTraversalSpeed, initialHeading);
			} else { // we have passed the barrier
				state++;
				timer.reset();
				timer.start();
			}
			break;
			
		case exitBarrierState:
			shooter.set_shooter_pos(nominalShootingAngle);
			if (timer.get() < (postBarrierDriveSec * .3)) { 
				mainDrive.moveForwardDriftRight(barrierExitSpeed);
			}
			else if (timer.get() < (postBarrierDriveSec * .7)) { 
					mainDrive.moveForwardDriftRight(barrierExitSpeed);
					shooter.set_shooter_pos(nominalShootingAngle);
			} else if (timer.get() < postBarrierDriveSec) { 
				mainDrive.moveForwardDriftRight(slowDownSpeed); // slow down before stopping
				shooter.set_shooter_pos(nominalShootingAngle);
			}
			else {
				mainDrive.moveForward(stoppedSpeed, initialHeading);
				state++;
			}
			break;
			
		case approachingTargetState:
			// Rotate left 110 degrees.
			float desiredHeading = initialHeading - 121;
			if (desiredHeading < 0) {
				desiredHeading += 360.0f;
			}
			atDesiredHeading = turnLeft(desiredHeading);
			shooter.set_shooter_pos(nominalShootingAngle);
			if (atDesiredHeading) {
				state++;
				timer.stop();
				timer.reset();
				timer.start();
			}
			break;
			
		case liftShooter:
			if (timer.get() < 3)
			{
				shooter.set_shooter_pos(nominalShootingAngle);
			}
			else
			{
				shooter.stop();
				timer.stop();
				timer.reset();
				timer.start();
				state++;
			}
			break;
			
		case aimUpDown:
			isUpDownComplete = shooter.auto_adjust();
			if (isUpDownComplete || (timer.get() > 1.5))
			{
				state++;
				timer.stop();
				timer.reset();
				timer.start();
			}
			break;
			
		case aimLeftRight:
			isLeftRightComplete = shooter.driveAdjust();
			if (isLeftRightComplete || (timer.get() > 2)) {
				state++;
			}
			break;
			
		case shoot:
			shooter.shootBall();
			shooter.stop();
			Timer.delay(1);
			state++;
			break;

		case complete:
			isLifterInitComplete = shooter.LifterManipulatorinit();
			if (isLifterInitComplete)
			{
				state++;
			}
			break;

		default:
			shooter.stop();
			break;
		}
	}
	public void noShootlowbarPeriodic(){
		final double postBarrierDriveSec = 3; // Drive this amount of time after the barrier.
		final double barrierSideDetectInches = 20;

		// Robot drive speeds:
		final double barrierApproachSpeed = 0.75;
		final double barrierTraversalSpeed = 0.75;
		final double barrierExitSpeed = 0.75;
		final double slowDownSpeed = 0.35;
		final double stoppedSpeed = 0.0;
		
		// Known shooter angles:
		final double barrierShooterAngle = -23.5;
		final double nominalShootingAngle = -12;
		
		// local state machine steps
		final int initialState = 1;
		final int driveToBarrierState = initialState + 1;
		final int traversingBarrierState = driveToBarrierState + 1;
		final int exitBarrierState = traversingBarrierState + 1;
		final int approachingTargetState = exitBarrierState + 1;
		final int complete = approachingTargetState +1;

		boolean atDesiredAngle = false;
		boolean atDesiredHeading = false;
		boolean isLifterInitComplete = false;
		boolean isUpDownComplete = false;
		boolean isLeftRightComplete = false;
		
		SmartDashboard.putNumber("Auto State", state);
		
		switch (state) {
		
		case initialState:
			initialHeading = sensors.ahrs.getFusedHeading();
			SmartDashboard.putNumber("Auto Initial Heading", initialHeading);
			state++;
			break;

		case driveToBarrierState: //lower shooter and drive towards barrier
			if (sensors.sideSonar.getRangeInches() > barrierSideDetectInches){
				// We are not yet at the barrier
				atDesiredAngle = shooter.set_shooter_angle(barrierShooterAngle);
				mainDrive.moveForward(barrierApproachSpeed, initialHeading);
			} else { // we are at the barrier
				// We have reached the defense
				if (atDesiredAngle) {
					state++;
				}
				else
				{
					mainDrive.moveForward(stoppedSpeed, initialHeading);
					atDesiredAngle = shooter.set_shooter_angle(barrierShooterAngle);
					if (atDesiredAngle) {
						state++;
					}
				}
			}
			break;
			
		case traversingBarrierState: // traverse the barrier at a slow speed
			if (sensors.sideSonar.getRangeInches() <= barrierSideDetectInches){
				// We are still at the barrier
				mainDrive.moveForward(barrierTraversalSpeed, initialHeading);
			} else { // we have passed the barrier
				state++;
				timer.reset();
				timer.start();
			}
			break;
			
		case exitBarrierState:
			shooter.set_shooter_pos(nominalShootingAngle);
			if (timer.get() < (postBarrierDriveSec * .3)) { 
				mainDrive.moveForwardDriftRight(barrierExitSpeed);
			}
			else if (timer.get() < (postBarrierDriveSec * .7)) { 
					mainDrive.moveForwardDriftRight(barrierExitSpeed);
					shooter.set_shooter_pos(nominalShootingAngle);
			} else if (timer.get() < postBarrierDriveSec) { 
				mainDrive.moveForwardDriftRight(slowDownSpeed); // slow down before stopping
				shooter.set_shooter_pos(nominalShootingAngle);
			}
			else {
				mainDrive.moveForward(stoppedSpeed, initialHeading);
				state++;
			}
			break;
			
		case approachingTargetState:
			// Rotate left 110 degrees.
			float desiredHeading = initialHeading - 121;
			if (desiredHeading < 0) {
				desiredHeading += 360.0f;
			}
			atDesiredHeading = turnLeft(desiredHeading);
			shooter.set_shooter_pos(nominalShootingAngle);
			if (atDesiredHeading) {
				state++;
			}
			break;

		case complete:
			isLifterInitComplete = shooter.LifterManipulatorinit();
			if (isLifterInitComplete)
			{
				state++;
			}
			break;

		default:
			shooter.stop();
			break;
		}
	}

	public void autoAPeriodic(){
		final double postBarrierDriveSec = 2; // Drive this amount of time after the barrier.
		final double barrierSideDetectInches = 20;

		// Robot drive speeds:
		final double barrierApproachSpeed = 1;
		final double barrierTraversalSpeed = 1;
		final double barrierExitSpeed = 0.75;
		final double slowDownSpeed = 0.35;
		final double stoppedSpeed = 0.0;
		
		// Known shooter angles:
		final double nominalShootingAngle = -13;
		
		// local state machine steps
		final int initialState = 1;
		final int driveToBarrierState = initialState + 1;
		final int traversingBarrierState = driveToBarrierState + 1;
		final int exitBarrierState = traversingBarrierState + 1;
		final int approachingTargetState = exitBarrierState + 1;
		final int liftShooter = approachingTargetState +1;
		final int aimUpDown = liftShooter + 1;
		final int aimLeftRight = aimUpDown + 1;
		final int shoot = aimLeftRight +1;
		final int complete = shoot +1;

		boolean atDesiredAngle = false;
		boolean atDesiredHeading = false;
		boolean isLifterInitComplete = false;
		boolean isUpDownComplete = false;
		boolean isLeftRightComplete = false;
		
		SmartDashboard.putNumber("Auto State", state);
		
		switch (state) {
		
		case initialState:
			initialHeading = sensors.ahrs.getFusedHeading();
			SmartDashboard.putNumber("Auto Initial Heading", initialHeading);
			state++;
			break;

		case driveToBarrierState: //lower shooter and drive towards barrier
			if (sensors.sideSonar.getRangeInches() > barrierSideDetectInches){
				// We are not yet at the barrier
				mainDrive.moveForward(barrierApproachSpeed, initialHeading);
			} else { // we are at the barrier
				// We have reached the defense
				state++;
			}
			break;
			
		case traversingBarrierState: // traverse the barrier at a slow speed
			if (sensors.sideSonar.getRangeInches() <= barrierSideDetectInches){
				// We are still at the barrier
				mainDrive.moveForward(barrierTraversalSpeed, initialHeading);
			} else { // we have passed the barrier
				state++;
				timer.reset();
				timer.start();

			}
			break;
			
		case exitBarrierState:
			 if (timer.get() < 0.4) { 
				mainDrive.moveForward(barrierExitSpeed, initialHeading);
				shooter.lifterTalon.set(1); // down
				}
			else if (timer.get() < (postBarrierDriveSec * .7)) { 
				mainDrive.moveForward(barrierExitSpeed, initialHeading);
				shooter.set_shooter_pos(nominalShootingAngle);
			} else if (timer.get() < postBarrierDriveSec) { 
				mainDrive.moveForward(slowDownSpeed, initialHeading);// slow down before stopping
				shooter.set_shooter_pos(nominalShootingAngle);
			}
			else {
				mainDrive.moveForward(stoppedSpeed, initialHeading);
				timer.stop();
				timer.reset();
				timer.start();
				state++;
			}
			break;
			
		case approachingTargetState:
			// Rotate left 180 degrees.
		
			float desiredHeading = initialHeading - 170;
			if (desiredHeading < 0) {
				desiredHeading += 360.0f;
			}
			atDesiredHeading = turnRight(desiredHeading);
			shooter.set_shooter_pos(-12.3);
			if (atDesiredHeading) {
				state++;
				timer.stop();
				timer.reset();
				timer.start();
			}
			break;
			
		case liftShooter:
			if (timer.get() < 3)
			{
				shooter.set_shooter_pos(nominalShootingAngle);
			}
			else
			{
				shooter.stop();
				timer.stop();
				timer.reset();
				timer.start();
				state++;
			}
			break;
			
		case aimUpDown:
			isUpDownComplete = shooter.auto_adjust();
			if (isUpDownComplete || (timer.get() > 2))
			{
				state++;
				timer.stop();
				timer.reset();
				timer.start();
			}
			break;
			
		case aimLeftRight:
			isLeftRightComplete = shooter.driveAdjust();
			if (isLeftRightComplete || (timer.get() > 3)) { 
				state++;
			}
			break;
			
		case shoot:
			shooter.shootBall();
			shooter.stop();
			Timer.delay(1);
			state++;
			break;

		case complete:
			isLifterInitComplete = shooter.LifterManipulatorinit();
			if (isLifterInitComplete)
			{
				state++;
			}
			break;

		default:
			shooter.stop();
			break;
		}
	}
	
	public void autoBPeriodic(){
		final double postBarrierDriveSec = 2; // Drive this amount of time after the barrier.
		final double barrierSideDetectInches = 20;

		// Robot drive speeds:
		final double barrierApproachSpeed = 1;
		final double barrierTraversalSpeed = 1;
		final double barrierExitSpeed = 0.75;
		final double slowDownSpeed = 0.35;
		final double stoppedSpeed = 0.0;
		
		// Known shooter angles:
		final double nominalShootingAngle = -12.5;
		
		// local state machine steps
		final int initialState = 1;
		final int driveToBarrierState = initialState + 1;
		final int traversingBarrierState = driveToBarrierState + 1;
		final int exitBarrierState = traversingBarrierState + 1;
		final int approachingTargetState = exitBarrierState + 1;
		final int liftShooter = approachingTargetState + 1;
		final int aimUpDown = liftShooter + 1;
		final int aimLeftRight = aimUpDown + 1;
		final int shoot = aimLeftRight +1;
		final int complete = shoot +1;

		boolean atDesiredAngle = false;
		boolean atDesiredHeading = false;
		boolean isLifterInitComplete = false;
		boolean isUpDownComplete = false;
		boolean isLeftRightComplete = false;
		
		SmartDashboard.putNumber("Auto State", state);
		
		switch (state) {
		
		case initialState:
			initialHeading = sensors.ahrs.getFusedHeading();
			SmartDashboard.putNumber("Auto Initial Heading", initialHeading);
			state++;
			break;

		case driveToBarrierState: //lower shooter and drive towards barrier
			if (sensors.sideSonar.getRangeInches() > barrierSideDetectInches){
				// We are not yet at the barrier
				mainDrive.moveForward(barrierApproachSpeed, initialHeading);
			} else { // we are at the barrier
				// We have reached the defense
				state++;
			}
			break;
			
		case traversingBarrierState: // traverse the barrier at a slow speed
			if (sensors.sideSonar.getRangeInches() <= barrierSideDetectInches){
				// We are still at the barrier
				mainDrive.moveForward(barrierTraversalSpeed, initialHeading);
			} else { // we have passed the barrier
				state++;
				timer.reset();
				timer.start();

			}
			break;
			
		case exitBarrierState:
			 if (timer.get() < 0.4) { 
				mainDrive.moveForward(barrierExitSpeed, initialHeading);
				shooter.lifterTalon.set(1); // down
				}
			else if (timer.get() < (postBarrierDriveSec * .7)) { 
				mainDrive.moveForward(barrierExitSpeed, initialHeading);
				shooter.set_shooter_pos(nominalShootingAngle);
			} else if (timer.get() < postBarrierDriveSec) { 
				mainDrive.moveForward(slowDownSpeed, initialHeading);// slow down before stopping
				shooter.set_shooter_pos(nominalShootingAngle);
			}
			else {
				mainDrive.moveForward(stoppedSpeed, initialHeading);
				state++;
			}
			break;
			
		case approachingTargetState:
			// Rotate left 180 degrees.
			float desiredHeading = initialHeading - 180;
			if (desiredHeading < 0) {
				desiredHeading += 360.0f;
			}
			atDesiredHeading = turnLeft(desiredHeading);
			shooter.set_shooter_pos(-12.3);
			if (atDesiredHeading) {
				state++;
				timer.stop();
				timer.reset();
				timer.start();
			}
			break;
			
		case liftShooter:
			if (timer.get() < 3)
			{
				shooter.set_shooter_pos(nominalShootingAngle);
			}
			else
			{
				shooter.stop();
				timer.stop();
				timer.reset();
				timer.start();
				state++;
			}
			break;
			
		case aimUpDown:
			isUpDownComplete = shooter.auto_adjust();
			if (isUpDownComplete || (timer.get() > 2))
			{
				state++;
				timer.stop();
				timer.reset();
				timer.start();
			}
			break;
			
		case aimLeftRight:
			isLeftRightComplete = shooter.driveAdjust();
			if (isLeftRightComplete || (timer.get() > 1)) {
				state++;
			}
			break;
			
		case shoot:
			shooter.shootBall();
			shooter.stop();
			Timer.delay(1);
			state++;
			break;

		case complete:
			isLifterInitComplete = shooter.LifterManipulatorinit();
			if (isLifterInitComplete)
			{
				state++;
			}
			break;

		default:
			shooter.stop();
			break;
		}
	}
	
	public void autoCPeriodic(){
		final double postBarrierDriveSec = 2; // Drive this amount of time after the barrier.
		final double barrierSideDetectInches = 20;

		// Robot drive speeds:
		final double barrierApproachSpeed = 1;
		final double barrierTraversalSpeed = 1;
		final double barrierExitSpeed = 0.75;
		final double slowDownSpeed = 0.35;
		final double stoppedSpeed = 0.0;
		
		// Known shooter angles:
		final double nominalShootingAngle = -12.5;
		
		// local state machine steps
		final int initialState = 1;
		final int driveToBarrierState = initialState + 1;
		final int traversingBarrierState = driveToBarrierState + 1;
		final int exitBarrierState = traversingBarrierState + 1;
		final int approachingTargetState = exitBarrierState + 1;
		final int liftShooter = approachingTargetState +1;
		final int aimUpDown = liftShooter + 1;
		final int aimLeftRight = aimUpDown + 1;
		final int shoot = aimLeftRight +1;
		final int complete = shoot +1;

		boolean atDesiredAngle = false;
		boolean atDesiredHeading = false;
		boolean isLifterInitComplete = false;
		boolean isUpDownComplete = false;
		boolean isLeftRightComplete = false;
		
		SmartDashboard.putNumber("Auto State", state);
		
		switch (state) {
		
		case initialState:
			initialHeading = sensors.ahrs.getFusedHeading();
			SmartDashboard.putNumber("Auto Initial Heading", initialHeading);
			state++;
			break;

		case driveToBarrierState: //lower shooter and drive towards barrier
			if (sensors.sideSonar.getRangeInches() > barrierSideDetectInches){
				// We are not yet at the barrier
				mainDrive.moveForward(barrierApproachSpeed, initialHeading);
			} else { // we are at the barrier
				// We have reached the defense
				state++;
			}
			break;
			
		case traversingBarrierState: // traverse the barrier at a slow speed
			if (sensors.sideSonar.getRangeInches() <= barrierSideDetectInches){
				// We are still at the barrier
				mainDrive.moveForward(barrierTraversalSpeed, initialHeading);
			} else { // we have passed the barrier
				state++;
				timer.reset();
				timer.start();

			}
			break;
			
		case exitBarrierState:
			 if (timer.get() < 0.4) { 
				mainDrive.moveForward(barrierExitSpeed, initialHeading);
				shooter.lifterTalon.set(1); // down
				}
			else if (timer.get() < (postBarrierDriveSec * .7)) { 
				mainDrive.moveForward(barrierExitSpeed, initialHeading);
				shooter.set_shooter_pos(nominalShootingAngle);
			} else if (timer.get() < postBarrierDriveSec) { 
				mainDrive.moveForward(slowDownSpeed, initialHeading);// slow down before stopping
				shooter.set_shooter_pos(nominalShootingAngle);
			}
			else {
				mainDrive.moveForward(stoppedSpeed, initialHeading);
				state++;
			}
			break;
			
		case approachingTargetState:
			// Rotate left 180 degrees.
			float desiredHeading = initialHeading - 200;
			if (desiredHeading < 0) {
				desiredHeading += 360.0f;
			}
			atDesiredHeading = turnLeft(desiredHeading);
			shooter.set_shooter_pos(-12.3);
			if (atDesiredHeading) {
				state++;
				timer.stop();
				timer.reset();
				timer.start();
			}
			break;
			
		case liftShooter:
			if (timer.get() < 3)
			{
				shooter.set_shooter_pos(nominalShootingAngle);
			}
			else
			{
				shooter.stop();
				timer.stop();
				timer.reset();
				timer.start();
				state++;
			}
			break;
			
		case aimUpDown:
			isUpDownComplete = shooter.auto_adjust();
			if (isUpDownComplete || (timer.get() > 2))
			{
				state++;
				timer.stop();
				timer.reset();
				timer.start();
			}
			break;
			
		case aimLeftRight:
			isLeftRightComplete = shooter.driveAdjust();
			if (isLeftRightComplete || (timer.get() > 1)) {
				state++;
			}
			break;
			
		case shoot:
			shooter.shootBall();
			shooter.stop();
			Timer.delay(1);
			state++;
			break;

		case complete:
			isLifterInitComplete = shooter.LifterManipulatorinit();
			if (isLifterInitComplete)
			{
				state++;
			}
			break;

		default:
			shooter.stop();
			break;
		}
	}

	public void autoDPeriodic(){
		final double postBarrierDriveSec = 3; // Drive this amount of time after the barrier.
		final double barrierSideDetectInches = 20;

		// Robot drive speeds:
		final double barrierApproachSpeed = 1;
		final double barrierTraversalSpeed = 1;
		final double barrierExitSpeed = 0.75;
		final double slowDownSpeed = 0.35;
		final double stoppedSpeed = 0.0;
		
		// Known shooter angles:
		final double nominalShootingAngle = -12.5;
		
		// local state machine steps
		final int initialState = 1;
		final int driveToBarrierState = initialState + 1;
		final int traversingBarrierState = driveToBarrierState + 1;
		final int exitBarrierState = traversingBarrierState + 1;
		final int approachingTargetState = exitBarrierState + 1;
		final int liftShooter = approachingTargetState +1;
		final int aimUpDown = liftShooter + 1;
		final int aimLeftRight = aimUpDown + 1;
		final int shoot = aimLeftRight +1;
		final int complete = shoot +1;

		boolean atDesiredAngle = false;
		boolean atDesiredHeading = false;
		boolean isLifterInitComplete = false;
		boolean isUpDownComplete = false;
		boolean isLeftRightComplete = false;
		
		SmartDashboard.putNumber("Auto State", state);
		
		switch (state) {
		
		case initialState:
			initialHeading = sensors.ahrs.getFusedHeading();
			SmartDashboard.putNumber("Auto Initial Heading", initialHeading);
			state++;
			break;

		case driveToBarrierState: //lower shooter and drive towards barrier
			if (sensors.sideSonar.getRangeInches() > barrierSideDetectInches){
				// We are not yet at the barrier
				mainDrive.moveForward(barrierApproachSpeed, initialHeading);
			} else { // we are at the barrier
				// We have reached the defense
				state++;
			}
			break;
			
		case traversingBarrierState: // traverse the barrier at a slow speed
			if (sensors.sideSonar.getRangeInches() <= barrierSideDetectInches){
				// We are still at the barrier
				mainDrive.moveForward(barrierTraversalSpeed, initialHeading);
			} else { // we have passed the barrier
				state++;
				timer.reset();
				timer.start();

			}
			break;
			
		case exitBarrierState:
			 if (timer.get() < 0.4) { 
				mainDrive.moveForward(barrierExitSpeed, initialHeading);
				shooter.lifterTalon.set(1); // down
				}
			else if (timer.get() < (postBarrierDriveSec * .7)) { 
				mainDrive.moveForward(barrierExitSpeed, initialHeading);
				shooter.set_shooter_pos(nominalShootingAngle);
			} else if (timer.get() < postBarrierDriveSec) { 
				mainDrive.moveForward(slowDownSpeed, initialHeading);// slow down before stopping
				shooter.set_shooter_pos(nominalShootingAngle);
			}
			else {
				mainDrive.moveForward(stoppedSpeed, initialHeading);
				state++;
			}
			break;
			
		case approachingTargetState:
			// Rotate left 180 degrees.
			float desiredHeading = initialHeading - 230;
			if (desiredHeading < 0) {
				desiredHeading += 360.0f;
			}
			atDesiredHeading = turnRight(desiredHeading);
			shooter.set_shooter_pos(-12.3);
			if (atDesiredHeading) {
				state++;
				timer.stop();
				timer.reset();
				timer.start();
			}
			break;
			
		case liftShooter:
			if (timer.get() < 3)
			{
				shooter.set_shooter_pos(nominalShootingAngle);
			}
			else
			{
				shooter.stop();
				timer.stop();
				timer.reset();
				timer.start();
				state++;
			}
			break;
			
		case aimUpDown:
			isUpDownComplete = shooter.auto_adjust();
			if (isUpDownComplete || (timer.get() > 3))
			{
				state++;
				timer.stop();
				timer.reset();
				timer.start();
			}
			break;
			
		case aimLeftRight:
			isLeftRightComplete = shooter.driveAdjust();
			if (isLeftRightComplete || (timer.get() > 1)) {
				state++;
			}
			break;
			
		case shoot:
			shooter.shootBall();
			shooter.stop();
			Timer.delay(1);
			state++;
			break;

		case complete:
			isLifterInitComplete = shooter.LifterManipulatorinit();
			if (isLifterInitComplete)
			{
				state++;
			}
			break;

		default:
			shooter.stop();
			break;
		}
	}

	public void noShootAutoPeriodic() {
		final double postBarrierDriveSec = 2; // Drive this amount of time after the barrier.
		final double barrierSideDetectInches = 20;

		// Robot drive speeds:
		final double barrierApproachSpeed = 1;
		final double barrierTraversalSpeed = 1;
		final double barrierExitSpeed = 0.75;
		final double slowDownSpeed = 0.35;
		final double stoppedSpeed = 0.0;

		// Known shooter angles:
		final double nominalShootingAngle = -12.5;
		
		// local state machine steps
		final int initialState = 1;
		final int driveToBarrierState = initialState + 1;
		final int traversingBarrierState = driveToBarrierState + 1;
		final int exitBarrierState = traversingBarrierState + 1;
		final int approachingTargetState = exitBarrierState + 1;
		final int complete = approachingTargetState +1;

		boolean atDesiredAngle = false;
		boolean atDesiredHeading = false;
		boolean isLifterInitComplete = false;
		boolean isUpDownComplete = false;
		boolean isLeftRightComplete = false;
		
		SmartDashboard.putNumber("Auto State", state);
		
		switch (state) {
		
		case initialState:
			initialHeading = sensors.ahrs.getFusedHeading();
			SmartDashboard.putNumber("Auto Initial Heading", initialHeading);
			state++;
			break;

		case driveToBarrierState: // drive towards barrier
			if (sensors.sideSonar.getRangeInches() > barrierSideDetectInches){
				// We are not yet at the barrier
				mainDrive.moveForward(barrierApproachSpeed, initialHeading);
			} else { // we are at the barrier
				// We have reached the defense
				state++;
			}
			break;
			
		case traversingBarrierState: // traverse the barrier at a slow speed
			if (sensors.sideSonar.getRangeInches() <= barrierSideDetectInches){
				// We are still at the barrier
				mainDrive.moveForward(barrierTraversalSpeed, initialHeading);
			} else { // we have passed the barrier
				state++;
				timer.reset();
				timer.start();

			}
			break;
			
		case exitBarrierState:
			 if (timer.get() < 0.4) { 
				mainDrive.moveForward(barrierExitSpeed, initialHeading);
				shooter.lifterTalon.set(1); // move shooter down for 0.4 second while driving
				}
			else if (timer.get() < (postBarrierDriveSec * .7)) { 
				mainDrive.moveForward(barrierExitSpeed, initialHeading);
				shooter.set_shooter_pos(nominalShootingAngle);
			} else if (timer.get() < postBarrierDriveSec) { 
				mainDrive.moveForward(slowDownSpeed, initialHeading);// slow down before stopping
				shooter.set_shooter_pos(nominalShootingAngle);
			}
			else {
				mainDrive.moveForward(stoppedSpeed, initialHeading);
				timer.stop();
				timer.reset();
				state++;
			}
			break;

		case approachingTargetState:
			// Rotate left 180 degrees.
			float desiredHeading = initialHeading - 180;
			if (desiredHeading < 0) {
				desiredHeading += 360.0f;
			}
			atDesiredHeading = turnLeft(desiredHeading);
			shooter.set_shooter_pos(nominalShootingAngle);
			if (atDesiredHeading) {
				mainDrive.moveForward(stoppedSpeed, initialHeading);
				state++;
			}
			break;

		case complete: // Do nothing, leave shooter ready to shoot.
			mainDrive.moveForward(stoppedSpeed, initialHeading);
			shooter.stop();
			state++;
			break;

		default:
			mainDrive.moveForward(stoppedSpeed, initialHeading);
			shooter.stop();
			break;
		}
	}

	public void superBreachAutoPeriodic() {
		final double barrierSideDetectInches = 20;

		// Robot drive speeds:
		final double barrierApproachSpeed = 1;
		final double barrierTraversalSpeed = 1;
		final double barrierExitSpeed = 0.75;
		final double slowDownSpeed = 0.35;
		final double stoppedSpeed = 0.0;

		// Known shooter angles:
		final double nominalShootingAngle = -12.5;
		
		// local state machine steps
		final int initialState = 1;
		final int driveToBarrierState = initialState + 1;
		final int traversingBarrierState = driveToBarrierState + 1;
		final int exitBarrierState = traversingBarrierState + 1;
		final int approachingTargetState = exitBarrierState + 1;
		final int complete = approachingTargetState + 1;

		final double postBarrierDriveSec = 1.0; // Drive this amount of time after the barrier.

		boolean atDesiredAngle = false;
		boolean atDesiredHeading = false;
		boolean isLifterInitComplete = false;
		boolean isUpDownComplete = false;
		boolean isLeftRightComplete = false;

		
		SmartDashboard.putNumber("Auto State", state);
		
		switch (state) {
		
		case initialState:
			initialHeading = sensors.ahrs.getFusedHeading();
			SmartDashboard.putNumber("Auto Initial Heading", initialHeading);
			firstThrough = true;
			state++;
			break;

		case driveToBarrierState: // drive towards barrier
			if (sensors.sideSonar.getRangeInches() > barrierSideDetectInches){
				// We are not yet at the barrier
				mainDrive.moveForward(barrierApproachSpeed, initialHeading);
			} else { // we are at the barrier
				// We have reached the defense
				state++;
			}
			break;
			
		case traversingBarrierState: // traverse the barrier at a slow speed
			if (sensors.sideSonar.getRangeInches() <= barrierSideDetectInches){
				// We are still at the barrier
				mainDrive.moveForward(barrierTraversalSpeed, initialHeading);
			} else { // we have passed the barrier
				state++;
				timer.reset();
				timer.start();
			}
			break;
			
		case exitBarrierState:
			if (timer.get() < postBarrierDriveSec) { 
				mainDrive.moveForward(slowDownSpeed, initialHeading);// slow down before stopping
			}
			else {
				mainDrive.moveForward(stoppedSpeed, initialHeading);
				timer.stop();
				timer.reset();
				state++;
			}
			break;

		case approachingTargetState:
			// Rotate left 180 degrees.
			float desiredHeading = initialHeading - 180;
			if (desiredHeading < 0) {
				desiredHeading += 360.0f;
			}
			atDesiredHeading = turnLeft(desiredHeading);
			if (atDesiredHeading) {
				mainDrive.moveForward(stoppedSpeed, initialHeading);
				if (firstThrough)
				{
					state = driveToBarrierState;
					initialHeading = initialHeading - 180;
					if (initialHeading < 0) {
						initialHeading += 360.0f;
					}
					firstThrough = false;
				}
				else
				{
					state++;
				}
			}
			break;

		case complete: // Do nothing, leave shooter ready to shoot.
			mainDrive.moveForward(stoppedSpeed, initialHeading);
			shooter.stop();
			state++;
			break;

		default:
			mainDrive.moveForward(stoppedSpeed, initialHeading);
			shooter.stop();
			break;
		}
	}
}


