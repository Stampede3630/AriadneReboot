package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StraitArm {
	Talon arm = new Talon(2);

	Joystick breachLeft;

	
	
		
		ShooterEncoder lifterrot;
		double rot;
		public StraitArm() {
			//ShooterEncoder lifterrot= new ShooterEncoder(7,8); 
			breachLeft= new Joystick(0);
		}
		//spinLeft.setInverted(spinLeft.equals(true));
		//spinRight.setInverted(spinRight.equals(true));
		//spinLeft.setInverted(true);
		//spinRight.setInverted(false);
	
	

	public void publishtodash(){
	
	}
	
	
	


	public void Armdown(){
		 
		//double rot = shaftRotation.fetchDegrees();
		// rot = lifterrot.degreesRot();
	//if (rot <= 100){
		 arm.set(-.25);
	}
	//}
/*
	public void kick_ball(){
		Ballkicker.set(.1);
		Ballkicker.set(-.1);
		//when you kick ball, check limit switch to see if kickReady is true. If it's true, ballkicker.set(1); 
		int kickReady = 1;
		int kickCompleted = 1;
		if(kickReady.get) {
			while(!kickCompleted.get()) {
				ballkick.set(.5);
			
			}
			ballkick.stop();
		}
		}
		public void resetKickBall {
		
			while(!kickReady.get()) {
				ballkick.set(-.2);	
			}
			ballkick.stop();
			
		}

	}
	*/
	public void ArmUp(){
		//double rot2 = shaftRotation.fetchDegrees();
		//if (rot>= 0){
		arm.set(.5);
		}
	//}
	public void stop(){
		arm.set(0);
	}
	

	public int getJoyStickValue(){
		if(breachLeft.getRawButton(1)){
			return 1;
		}
		else if(breachLeft.getRawButton(2)){
			return 2;
		}
		else if(breachLeft.getRawButton(3)){
			return 3;
		}
		else if(breachLeft.getRawButton(4)){
			return  4 ; 
		}
		else if(breachLeft.getRawButton(5)){
			return 5;
		}
		else if(breachLeft.getRawButton(6)){
			return 6;
		}
		else if(breachLeft.getRawButton(7)){
			return  7 ; 
		}
		else{
			return 0;
	}

	}
		
	
	
	
	public void straightArmPeriodic(){

	switch(getJoyStickValue()) {
		
		case 1:
			
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
			
			
			break;
		case 7:
			break;
			default:
				stop();
				break;
		
	}
	SmartDashboard.putNumber("straight arm degrees",rot);
}
}


