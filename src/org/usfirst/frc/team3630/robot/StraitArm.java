package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

public class StraitArm {
	
	Talon arm = new Talon(2);
	Joystick breachLeft;

	double deg; 

	DigitalInput limitswitch1 = new  DigitalInput(12);
	DigitalInput limitswitch2 = new  DigitalInput(15);
	
	public StraitArm() {
			breachLeft= new Joystick(0);
	}
	
	public void publishtodash(){
	
	}

	public void Armdown(){
		 arm.set(-.5);
	}
	
	public void ArmUp(){
		arm.set(.5);
		}

	public void stop(){
		arm.set(0);
	}

	public void armReset(){
		if (limitswitch1.get() == true){
			Armdown();
		}
		else if ((limitswitch1.get() == false)){
			stop();
		}
	}
	
	public void armOut(){
		if (limitswitch2.get() == true){
			ArmUp();
		}
		else if ((limitswitch2.get() == false)){
			stop();
		}
	}


	public int getJoyStickValue(){
		if (breachLeft.getRawButton(Consts.BREACH_LEFT_BTN_DRIVE_STRENGTH)) { // Note: this button is serviced in DriveTrain.driveBreach() - not here.
			return Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_NOP;
		}
		else if (breachLeft.getRawButton(Consts.BREACH_LEFT_BTN_ARMUP)){
			return Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_ARMUP;
		}
		else if (breachLeft.getRawButton(Consts.BREACH_LEFT_BTN_ARMDOWN)){  // armReset
			return Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_ARMDOWN;  // armReset
		}
		else if (breachLeft.getRawButton(Consts.BREACH_LEFT_BTN_4)){
			return  Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_4 ; 
		}
		else if (breachLeft.getRawButton(Consts.BREACH_LEFT_BTN_5)){
			return Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_5;
		}
		else if (breachLeft.getRawButton(Consts.BREACH_LEFT_BTN_AUTODOWN)){
			return Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_AUTODOWN;
		}
		// Actually implemented in Robot.java teleopPeriodic().
		else if (breachLeft.getRawButton(Consts.BREACH_LEFT_BTN_SWITCH_DRIVERS)){
			return  Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_SWITCH_DRIVERS ; 
		}
		else {
			return Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_DEFAULT;
		}
	}
		
	
	public void straightArmPeriodic(){

	switch (getJoyStickValue()) {
		
		case Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_NOP: // Purposely a NOP (Not an Operation) - do nothing here.
			break;
		
		case Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_ARMUP: 
			armOut();
			break;
		
		case Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_ARMDOWN: // armReset
			armReset();
			break;
		
		case Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_4: 
			break;

		case Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_5:
			break;

		case Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_AUTODOWN:
			break;

		// Actually implemented in Robot.java teleopPeriodic().
		case Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_SWITCH_DRIVERS:
			break;
		
		default: // i.e. Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_DEFAULT
			stop();
			break;
		}

	// SmartDashboard.putNumber("Straight Arm Degrees (Dist)",lifterrot.getDistance());
	}
}


