package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;

public class StraitArm {
	
	Talon arm = new Talon(2);
	Joystick breachLeft;
		
	Encoder lifterrot;
	double deg; 

	DigitalInput limitswitch = new  DigitalInput(12);
	
	public StraitArm() {
			lifterrot= new Encoder(7,8); 
	//		deg =  lifterrot.degreesRot();
			breachLeft= new Joystick(0);
	}
	
		//spinLeft.setInverted(spinLeft.equals(true));
		//spinRight.setInverted(spinRight.equals(true));
		//spinLeft.setInverted(true);
		//spinRight.setInverted(false);

	public void publishtodash(){
	
	}

	public void Armdown(){

	//if (rot <= 100){
		 arm.set(-.5);

	}

	//}

	public void AutoDown(){
		if(lifterrot.getRaw() < 330){
			arm.set(.5
					);
		}
		else{
			stop();
		}
	}
	
	public void ArmUp(){
		//double rot2 = shaftRotation.fetchDegrees();
		//if (rot>= 0){
		arm.set(.5);
		}
	//}
	
	public void stop(){
		arm.set(0);
	}

	public void armReset(){
		if (limitswitch.get() == true){
			Armdown();
		}
		else if ((limitswitch.get() == false)){
			stop();
			lifterrot.reset();
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
		else if (breachLeft.getRawButton(Consts.BREACH_LEFT_BTN_7)){
			return  Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_7 ; 
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
			ArmUp();
			break;
		
		case Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_ARMDOWN: // armReset
			armReset();
			break;
		
		case Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_4: 
			break;

		case Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_5:
			break;

		case Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_AUTODOWN:
			AutoDown();
			break;

		case Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_7:
			break;
		
		default: // i.e. Consts.BREACH_LEFT_JOYSTICK_STRAITARM_CODE_DEFAULT
			stop();
			break;
		}

	// SmartDashboard.putNumber("Straight Arm Degrees (Dist)",lifterrot.getDistance());
	SmartDashboard.putNumber("Straight Arm Raw", lifterrot.getRaw());
	}
}


