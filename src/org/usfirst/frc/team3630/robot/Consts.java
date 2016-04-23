package org.usfirst.frc.team3630.robot;

public final class Consts {
	
	// Joystick channel numbers
	public static final int BREACH_LEFT_JOYSTICK_CHAN = 0;
	public static final int SHOOTER_LEFT_JOYSTICK_CHAN = 1;
	public static final int SHOOTER_RIGHT_JOYSTICK_CHAN = 2;
	public static final int BREACH_RIGHT_JOYSTICK_CHAN = 4;
	
	// Talon channel numbers
	public static final int UARM_TALON_CHAN = 2;
	public static final int STRAIT_ARM_TALON_CHAN = 3;
	public static final int LIFTER_TALON_CHAN = 4;
	public static final int BALL_KICKER_TALON_CHAN = 5;
	public static final int SPIN_LEFT_TALON_CHAN = 6;
	public static final int SPIN_RIGHT_TALON_CHAN = 7;
	
	// Digital input channel numbers
	public static final int RIGHT_BOULDER_ENCODER_DIGITAL_INPUT_CHAN = 0;
	public static final int RIGHT_BOULDER_ENCODER_DIGITAL_OUTPUT_CHAN = 1;
	public static final int LEFT_BOULDER_ENCODER_DIGITAL_INPUT_CHAN = 2;
	public static final int LEFT_BOULDER_ENCODER_DIGITAL_OUTPUT_CHAN = 3;

	public static final int LIFTER_LIMIT_DIGITAL_INPUT_CHAN = 4;
	public static final int KICK_COMPLETE_DIGITAL_INPUT_CHAN = 5;
	public static final int KICK_READY_DIGITAL_INPUT_CHAN = 6;
	public static final int UARM_LIMIT_DIGITAL_INPUT_CHAN = 9;
	public static final int STRAIT_ARM_DOWN_LIMIT_DIGITAL_INPUT_CHAN = 12;

	public static final int SHOOTER_ENCODER_DIGITAL_INPUT_CHAN = 13;
	public static final int SHOOTER_ENCODER_DIGITAL_OUTPUT_CHAN = 14;

	public static final int STRAIT_ARM_UP_LIMIT_DIGITAL_INPUT_CHAN = 15;

	// Put all the Shooter Left Joystick BUTTON definitions here:
	public static final int SHOOTER_LEFT_BTN_LIFTERDOWN = 2;
	public static final int SHOOTER_LEFT_BTN_LIFTERUP = 3;
	public static final int SHOOTER_LEFT_BTN_STOP = 7;
	// camera buttons 
	public static final int SHOOTER_LEFT_BTN_PANLEFT = 4;
	public static final int SHOOTER_LEFT_BTN_PANRIGHT = 5;
	public static final int SHOOTER_LEFT_BTN_LIFTDOWN = 7;
	public static final int SHOOTER_LEFT_BTN_LIFTUP = 8;
	

	// Put all the Shooter Right Joystick BUTTON definitions here:
	public static final int SHOOTER_RIGHT_BTN_SHOOTBALL = 1;
	public static final int SHOOTER_RIGHT_BTN_LOADBALL = 2;
	public static final int SHOOTER_RIGHT_BTN_DEGREE_PICKUP = 3;
	public static final int SHOOTER_RIGHT_BTN_DEGREE_DRIVE = 4;
	//public static final int SHOOTER_RIGHT_BTN_DEGREE_CORRECTION = 5;
	public static final int SHOOTER_RIGHT_BTN_SHOOTQUICK = 5;
	public static final int SHOOTER_RIGHT_BTN_ARM_RESET = 6;
//	public static final int SHOOTER_RIGHT_BTN_KICK_TEST = 7;
	
	// Put all COMBINED (left and right) Shooter Joystick codes here:
	public static final int SHOOTER_JOYSTICK_CODE_DEFAULT = 0;
	public static final int SHOOTER_JOYSTICK_CODE_STOP = 1;
	public static final int SHOOTER_JOYSTICK_CODE_LIFTERDOWN = 2;
	public static final int SHOOTER_JOYSTICK_CODE_LIFTERUP = 3;
	public static final int SHOOTER_JOYSTICK_CODE_LOADBALL = 4;
	public static final int SHOOTER_JOYSTICK_CODE_SHOOTBALL = 5;
	public static final int SHOOTER_JOYSTICK_CODE_DEGREE_PICKUP = 6;
	public static final int SHOOTER_JOYSTICK_CODE_DEGREE_CORRECTION = 7;
//	public static final int SHOOTER_JOYSTICK_CODE_AUTODRIVEADJUST = 8;
	public static final int SHOOTER_JOYSTICK_CODE_DEGREE_DRIVE = 10;
	public static final int SHOOTER_JOYSTICK_CODE_ARM_RESET = 11;
	public static final int SHOOTER_JOYSTICK_CODE_SHOOTQUICK = 12;
	// return camera values
	public static final int SHOOTER_JOYSTICK_CODE_CAMERA_DEFAULT = 20;
	public static final int SHOOTER_JOYSTICK_CODE_PANLEFT = 21;
	public static final int SHOOTER_JOYSTICK_CODE_PANRIGHT = 22;
	public static final int SHOOTER_JOYSTICK_CODE_LIFTUP = 23;
	public static final int SHOOTER_JOYSTICK_CODE_LIFTDOWN = 24;

	// Put all the Breach Left Joystick BUTTON definitions here:
	public static final int BREACH_LEFT_BTN_DRIVE_STRENGTH = 1;
	public static final int BREACH_LEFT_BTN_ARMUP = 2;
	public static final int BREACH_LEFT_BTN_ARMDOWN = 3;
	public static final int BREACH_LEFT_BTN_4 = 4;
	public static final int BREACH_LEFT_BTN_5 = 5;
	public static final int BREACH_LEFT_BTN_AUTODOWN = 6;
	public static final int BREACH_LEFT_BTN_SWITCH_DRIVERS = 1;

	// Put Breach Left StraitArm Joystick codes here:
	public static final int BREACH_LEFT_JOYSTICK_STRAITARM_CODE_DEFAULT = 0;
	public static final int BREACH_LEFT_JOYSTICK_STRAITARM_CODE_NOP = 1;
	public static final int BREACH_LEFT_JOYSTICK_STRAITARM_CODE_ARMUP = 2;
	public static final int BREACH_LEFT_JOYSTICK_STRAITARM_CODE_ARMDOWN = 3;
	public static final int BREACH_LEFT_JOYSTICK_STRAITARM_CODE_4 = 4;
	public static final int BREACH_LEFT_JOYSTICK_STRAITARM_CODE_5 = 5;
	public static final int BREACH_LEFT_JOYSTICK_STRAITARM_CODE_AUTODOWN = 6;
	public static final int BREACH_LEFT_JOYSTICK_STRAITARM_CODE_SWITCH_DRIVERS = 7;
	
	// Put all the Breach Right Joystick BUTTON definitions here:
	public static final int BREACH_RIGHT_BTN_ARMDOWN = 1;
	public static final int BREACH_RIGHT_BTN_ARMUP = 2;
	public static final int BREACH_RIGHT_BTN_AUTODOWN = 3;
	public static final int BREACH_RIGHT_BTN_4 = 4;
	public static final int BREACH_RIGHT_BTN_5 = 5;
	public static final int BREACH_RIGHT_BTN_6 = 6;
	public static final int BREACH_RIGHT_BTN_7 = 7;
	public static final int BREACH_RIGHT_BTN_ARMRESET = 11;
	
	// Put Breach Right UARM Joystick codes here:
	public static final int BREACH_RIGHT_JOYSTICK_UARM_CODE_DEFAULT = 0;
	public static final int BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMDOWN = 1;
	public static final int BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMUP = 2;
	public static final int BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMRESET = 3;
	public static final int BREACH_RIGHT_JOYSTICK_UARM_CODE_AUTODOWN = 4;
	public static final int BREACH_RIGHT_JOYSTICK_UARM_CODE_5 = 5;
	public static final int BREACH_RIGHT_JOYSTICK_UARM_CODE_6 = 6;
	public static final int BREACH_RIGHT_JOYSTICK_UARM_CODE_7 = 7;

	// For some calculations assume the degrees per Px is the same for both
	// horizontal and vertical. Todo: Determine if this is true.
	public static final double cameraFovWidthDegrees = 67.0;
	public static final int cameraFovWidthPx = 320;
	public static final int cameraFovHeightPx = 240;

	public static final double cameraPxPerDegree = cameraFovWidthPx / cameraFovWidthDegrees;
	public static final int cameraTiltDegrees = 18; // Camera points 18 degrees lower than the shooter angle.
	public static final double targetWidthInches = 20.0;
	public static final int nominalCameraHeightInches = 24;
	public static final int targetBottomHeightInches = 83;
	public static final int targetMiddleHeightInches = targetBottomHeightInches + 12;
	public static final double gravityAcceleration = 9.80665; // meters / (sec * sec)
	public static final double inchesPerMeter = 39.3701;
	public static final double boulderWheelDiameterInches = 6;
	public static final double boulderWheelCircumferenceInches = boulderWheelDiameterInches * 2 * Math.PI;
	public static final double boulderSpeedInchPerSec = 304.33;
	public static final double boulderSpeedMetersPerSec = boulderSpeedInchPerSec / inchesPerMeter;

	// Todo: Measure: ramp wheel to  max velocity, record rpm, shoot ball, check rpm every tick, record min rpm.
	// The max - min = the delta. Take measurements for multiple shots, put the average in a Java constant.
	public static final double boulderWheelDeltaRpm = 16; // Measure this during  practice shooting.
	public static final double boulderWheelRpm = boulderWheelDeltaRpm + 60 * boulderSpeedInchPerSec  / boulderWheelCircumferenceInches;
	
	// Todo: Correct the following value once we know what angle the shooter is at to improve accuracy.
	public static final double deltaTargetHeightInches = targetBottomHeightInches - nominalCameraHeightInches;
	
	// Average of angles that produce long level travel between 120 and 160 inches from target.
	public static final double defaultShooterAngleDegrees = 25.0; 

	/**
	   The caller references the constants using <tt>Consts.EMPTY_STRING</tt>, 
	   and so on. Thus, the caller should be prevented from constructing objects of 
	   this class, by declaring this private constructor. 
	  */
	  // private Consts(){
	    // This prevents anyone from calling this constructor:
	  //  throw new AssertionError(); // Let's not do any assertion errors.
	  //}

}
