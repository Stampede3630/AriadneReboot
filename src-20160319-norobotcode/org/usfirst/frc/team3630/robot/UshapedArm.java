package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;

public class UshapedArm {
	
	Talon uShapedArmTalon;
 	Joystick breachRightJoy;
	DigitalInput uarmLimit;
	Encoder Urot; // encoder to fetch degrees of lifter shaft
			
 	public UshapedArm () {
	//	Potdegrees lifterrot= new Potdegrees(2); 
		breachRightJoy = new Joystick(Consts.SHOOTER_RIGHT_JOYSTICK_CHAN);
		Urot = new Encoder(10, 11);
		uarmLimit = new  DigitalInput(Consts.UARM_LIMIT_DIGITAL_INPUT_CHAN);
		uShapedArmTalon = new Talon(Consts.UARM_TALON_CHAN);
 	}

	//spinLeft.setInverted(spinLeft.equals(true));
	//spinRight.setInverted(spinRight.equals(true));
	//spinLeft.setInverted(true);
	//spinRight.setInverted(false);
		
	public void Armdown(){
			
	//	if (deg <= 100){
			 uShapedArmTalon.set(1);
	//	}
	}

	public void ArmUp(){
			
		//if (deg>= 0){
			uShapedArmTalon.set(-1);
	//	}
	}

	public void stop(){
		uShapedArmTalon.set(0);
	}
		
	public void armReset(){
		if (uarmLimit.get() == true){
			uShapedArmTalon.set(-1);
		}
		else if ((uarmLimit.get() == false)){
			stop();
			Urot.reset();
		}
	}
	public void autoDown(){
		if(Urot.getRaw() < 715){
			Armdown();
		}
		else{
		stop();
		}
	}

	public int getJoyStickValue(){
		if (breachRightJoy.getRawButton(Consts.BREACH_RIGHT_BTN_ARMDOWN)){
			return Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMDOWN;
		}
		else if (breachRightJoy.getRawButton(Consts.BREACH_RIGHT_BTN_ARMUP)){
			return Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMUP;
		}
		else if (breachRightJoy.getRawButton(Consts.BREACH_RIGHT_BTN_ARMRESET)){
			return Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMRESET;
		}
		else if (breachRightJoy.getRawButton(Consts.BREACH_RIGHT_BTN_AUTODOWN)){
			return  Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_AUTODOWN ; 
		}
		else if (breachRightJoy.getRawButton(Consts.BREACH_RIGHT_BTN_5)){
			return Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_5;
		}
		else if (breachRightJoy.getRawButton(Consts.BREACH_RIGHT_BTN_6)){
			return Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_6;
		}
		else if (breachRightJoy.getRawButton(Consts.BREACH_RIGHT_BTN_7)){
			return  Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_7 ; 
		}
		else{
			return Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_DEFAULT;
		}
	}
			
		

	public void UArmPeriodic(){

		switch(getJoyStickValue()) {
			
			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMDOWN:
				Armdown();
				break;
			
			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMUP: 
				armReset();
				break;
			
			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMRESET: 
				
				break;
			
			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_AUTODOWN: 
				
				break;
				
			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_5:
				break;
				
			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_6:
				break;

			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_7:
				break;
			
			default: // i.e. Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_DEFAULT
				stop();
				break;
		}
	SmartDashboard.putNumber("U Shaped Arm Raw", Urot.getRaw());
	}

}


