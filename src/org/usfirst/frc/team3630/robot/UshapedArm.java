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
		if (breachRight.getRawButton(1)){
			return 1;
		}
		else if (breachRight.getRawButton(2)){
			return 2;
		}
		else if (breachRight.getRawButton(11)){
			return 3;
		}
		else if (breachRight.getRawButton(3)){
			return  4 ; 
		}
		else if (breachRight.getRawButton(5)){
			return 5;
		}
		else if (breachRight.getRawButton(6)){
			return 6;
		}
		else if (breachRight.getRawButton(7)){
			return  7 ; 
		}
		else{
			return 0;
		}
	}
			
		

	public void UArmPeriodic(){

		switch(getJoyStickValue()) {
			
			case 1:
				Armdown();
				break;
			
			case 2: 
				ArmUp();
				break;
			
			case 3: 
				armReset();
				break;
			
			case 4: 
				autoDown();
				break;
				
			case 5:
				break;
				
			case 6:
				break;

			case 7:
				break;
			
			default:
				stop();
				break;
		}
	SmartDashboard.putNumber("U Shaped Arm Raw", Urot.getRaw());
	}

}


