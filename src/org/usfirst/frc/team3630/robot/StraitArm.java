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
		if (breachLeft.getRawButton(1)){
			return 1;
		}
		else if (breachLeft.getRawButton(2)){
			return 2;
		}
		else if (breachLeft.getRawButton(3)){
			return 3;
		}
		else if (breachLeft.getRawButton(4)){
			return  4 ; 
		}
		else if (breachLeft.getRawButton(5)){
			return 5;
		}
		else if (breachLeft.getRawButton(6)){
			return 6;
		}
		else if (breachLeft.getRawButton(7)){
			return  7 ; 
		}
		else{
			return 0;
		}
	}
		
	
	public void straightArmPeriodic(){

	switch (getJoyStickValue()) {
		
		case 1:
			armReset();
			break;
		
		case 2: 
			ArmUp();
			break;
		
		case 3: 
			Armdown();
			break;
		
		case 4: 
			
			break;

		case 5:
			break;

		case 6:
			AutoDown();
			break;

		case 7:
			break;
		
		default:
			stop();
			break;
		}

	// SmartDashboard.putNumber("Straight Arm Degrees (Dist)",lifterrot.getDistance());
	SmartDashboard.putNumber("Straight Arm Raw", lifterrot.getRaw());
	}
}


