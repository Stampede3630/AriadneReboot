package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;

public class StraitArm {
	DigitalInput armUpLimit;
	DigitalInput armDownLimit;
	Joystick breachLeftJoy;
	Talon straitArmTalon;
	double deg; 

	
	public StraitArm() {
	breachLeftJoy = new Joystick(Consts.BREACH_LEFT_JOYSTICK_CHAN);
	armDownLimit = new  DigitalInput(Consts.STRAIT_ARM_DOWN_LIMIT_DIGITAL_INPUT_CHAN);
	armUpLimit = new  DigitalInput(Consts.STRAIT_ARM_UP_LIMIT_DIGITAL_INPUT_CHAN);
	straitArmTalon = new Talon(Consts.STRAIT_ARM_TALON_CHAN );
	}

	public void Armdown(){
		 straitArmTalon.set(-0.5);
	}
	
	public void ArmUp(){
		straitArmTalon.set(1);
	}

	public void stop(){
		straitArmTalon.set(0);
	}

	public void armReset(){
		if (armDownLimit.get() == true){
			Armdown();
		}
		else if ((armDownLimit.get() == false)){
			stop();
		}
	}
	
	public void armOut(){
		if (armUpLimit.get() == true){
			ArmUp();
		}
		else if ((armUpLimit.get() == false)){
			stop();
		}
	}


	public int getJoyStickValue(){
		if (breachLeftJoy.getRawButton(Consts.BREACH_LEFT_BTN_DRIVE_STRENGTH)) { // Note: this button is serviced in DriveTrain.driveBreach() - not here.
			return Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_NOP;
		}
		else if (breachLeftJoy.getRawButton(Consts.BREACH_LEFT_BTN_ARMUP)){
			return Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_ARMUP;
		}
		else if (breachLeftJoy.getRawButton(Consts.BREACH_LEFT_BTN_ARMDOWN)){  // armReset
			return Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_ARMDOWN;  // armReset
		}
		else if (breachLeftJoy.getRawButton(Consts.BREACH_LEFT_BTN_4)){
			return  Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_4 ; 
		}
		else if (breachLeftJoy.getRawButton(Consts.BREACH_LEFT_BTN_5)){
			return Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_5;
		}
		else if (breachLeftJoy.getRawButton(Consts.BREACH_LEFT_BTN_AUTODOWN)){
			return Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_AUTODOWN;
		}
		// Actually implemented in Robot.java teleopPeriodic().
		else if (breachLeftJoy.getRawButton(Consts.BREACH_LEFT_BTN_SWITCH_DRIVERS)){
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


