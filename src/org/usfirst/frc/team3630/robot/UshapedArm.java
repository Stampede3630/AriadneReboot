package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;

public class UshapedArm {
	
	Talon UShapedArm;
 	Joystick breachRight;
	DigitalInput limitswitch;
	Encoder Urot; // encoder to fetch degrees of lifter shaft
 	
			
 	public UshapedArm () {
	//	Potdegrees lifterrot= new Potdegrees(2); 
		breachRight= new Joystick(4);
		Urot = new Encoder(10,11);
		limitswitch = new  DigitalInput(9);
		UShapedArm = new Talon(3);
 	}

	//spinLeft.setInverted(spinLeft.equals(true));
	//spinRight.setInverted(spinRight.equals(true));
	//spinLeft.setInverted(true);
	//spinRight.setInverted(false);
		
	public void Armdown(){
			
	//	if (deg <= 100){
			 UShapedArm.set(.5);
	//	}
	}

	public void ArmUp(){
			
		//if (deg>= 0){
			UShapedArm.set(-1);
	//	}
	}

	public void stop(){
		UShapedArm.set(0);
	}
		
	public void armReset(){
		if (limitswitch.get() == true){
			UShapedArm.set(-.25);
		}
		else if ((limitswitch.get() == false)){
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
		if (breachRight.getRawButton(Consts.BREACH_RIGHT_BTN_ARMDOWN)){
			return Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMDOWN;
		}
		else if (breachRight.getRawButton(Consts.BREACH_RIGHT_BTN_ARMUP)){
			return Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMUP;
		}
		else if (breachRight.getRawButton(Consts.BREACH_RIGHT_BTN_ARMRESET)){
			return Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMRESET;
		}
		else if (breachRight.getRawButton(Consts.BREACH_RIGHT_BTN_AUTODOWN)){
			return  Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_AUTODOWN ; 
		}
		else if (breachRight.getRawButton(Consts.BREACH_RIGHT_BTN_5)){
			return Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_5;
		}
		else if (breachRight.getRawButton(Consts.BREACH_RIGHT_BTN_6)){
			return Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_6;
		}
		else if (breachRight.getRawButton(Consts.BREACH_RIGHT_BTN_7)){
			return  Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_7 ; 
		}
		else{
			return Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_STOP;
		}
	}
			
		

	public void UArmPeriodic(){

		switch(getJoyStickValue()) {
			
			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMDOWN:
				Armdown();
				break;
			
			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMUP: 
				ArmUp();
				break;
			
			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_ARMRESET: 
				armReset();
				break;
			
			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_AUTODOWN: 
				autoDown();
				break;
				
			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_5:
				break;
				
			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_6:
				break;

			case Consts.BREACH_RIGHT_JOYSTICK_UARM_CODE_7:
				break;
			
			default: // i.e. Consts.BREACH_JOYSTICK_CODE_STOP
				stop();
				break;
		}
	SmartDashboard.putNumber("U Shaped Arm Raw", Urot.getRaw());
	}

}


