package org.usfirst.frc.team3630.robot;

public final class Consts {

	// Put all the Shooter Left Joystick BUTTON definitions here:
	public static final int SHOOTER_LEFT_BTN_LIFTERDOWN = 2;
	public static final int SHOOTER_LEFT_BTN_LIFTERUP = 3;
	public static final int SHOOTER_LEFT_BTN_STOP = 7;

	// Put all the Shooter Right Joystick BUTTON definitions here:
	public static final int SHOOTER_RIGHT_BTN_SHOOTBALL = 1;
	public static final int SHOOTER_RIGHT_BTN_LOADBALL = 2;
	public static final int SHOOTER_RIGHT_BTN_DEGREE_PICKUP = 3;
	public static final int SHOOTER_RIGHT_BTN_DEGREE_DRIVE = 4;
	public static final int SHOOTER_RIGHT_BTN_DEGREE_CORRECTION = 5;
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
//	public static final int SHOOTER_JOYSTICK_CODE_KICK_TEST = 8;
	public static final int SHOOTER_JOYSTICK_CODE_DEGREE_DRIVE = 10;
	public static final int SHOOTER_JOYSTICK_CODE_ARM_RESET = 11;

	// Put all the Breach Left Joystick BUTTON definitions here:
	public static final int BREACH_LEFT_BTN_DRIVE_STRENGTH = 1;
	public static final int BREACH_LEFT_BTN_ARMUP = 2;
	public static final int BREACH_LEFT_BTN_ARMDOWN = 3;
	public static final int BREACH_LEFT_BTN_4 = 4;
	public static final int BREACH_LEFT_BTN_5 = 5;
	public static final int BREACH_LEFT_BTN_AUTODOWN = 6;
	public static final int BREACH_LEFT_BTN_SWITCH_DRIVERS = 7;

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

	// camera buttons 
	public static final int SHOOTER_LEFT_JOYSTICK_PANLEFT = 4;
	public static final int SHOOTER_LEFT_JOYSTICK_PANRIGHT = 5;
	public static final int SHOOTER_LEFT_JOYSTICK_lIFTUP = 8;
	public static final int SHOOTER_LEFT_JOYSTICK_LIFTDOWN = 7;
	
	// return camera values
	public static final int SHOOTER_LEFT_JOYSTICK_PANLEFT_CONSTANT = 1;
	public static final int SHOOTER_LEFT_JOYSTICK_PANRIGHT_CONSTANT = 2;
	public static final int SHOOTER_LEFT_JOYSTICK_lIFTUP_CONSTANT = 3;
	public static final int SHOOTER_LEFT_JOYSTICK_LIFTDOWN_CONSTANT = 4;
	public static final int SHOOTER_LEFT_JOYSTICK_CAMERA_DEFAULT = 0;
	/**
	   The caller references the constants using <tt>Consts.EMPTY_STRING</tt>, 
	   and so on. Thus, the caller should be prevented from constructing objects of 
	   this class, by declaring this private constructor. 
	  */
	  private Consts(){
	    // This prevents anyone from calling this constructor:
	    throw new AssertionError();
	  }

}
