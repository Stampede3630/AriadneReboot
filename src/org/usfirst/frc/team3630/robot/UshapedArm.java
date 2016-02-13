package org.usfirst.frc.team3630.robot;

public class UshapedArm {

	import edu.wpi.first.wpilibj.Joystick;
	import edu.wpi.first.wpilibj.Talon;
	import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
	Talon UShapedArm = new Talon(3);
 	Joystick breachLeft;
 	double deg
			
 	public UshapedArm () {
		Potdegrees lifterrot= new Potdegrees(2); 
		breachLeft= new Joystick(0);
		 deg = lifterrot.fetchDegrees();
 	}

			//spinLeft.setInverted(spinLeft.equals(true));
			//spinRight.setInverted(spinRight.equals(true));
			//spinLeft.setInverted(true);
			//spinRight.setInverted(false);
		
		

		
		
		


		public void Armdown(){
			 
			
			
		if (deg <= 100){
			 UShapedArm.set(.25);
		}
		}
		public void ArmUp(){
			
			if (deg>= 0){
			UShapedArm.set(-.5);
			}
		}
		
		/*public void loadBall(){
//		spinLeft.setInvertedMotor(spinLeft.MotorType.kspinLeft,true);// need to fix v
			spinLeft.set(.25);
			spinRight.set(-.25);
			
		}
		
		public void shootBall(){
			
			spinLeft.set(-1);
			spinRight.set(1);
			
		}
		
		public void kick_ball(){
			Ballkicker.set(.1);
			Ballkicker.set(-.1);
		}
			public void stop(){
			spinLeft.set(0);
			spinRight.set(0);
			Lifter.set(0);
			Ballkicker.set(0);
		}
		*/
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
			
		
		
		
		public void UArmPeriodic(){

		switch(getJoyStickValue()) {
			
			case 1:
				
			break;
			
			case 2: 
				
			break;
			
			case 3: 
				
			break;
	
		
			case 4: 
				
				Armdown();
				
				
			break;
			case 5:
				ArmUp();
				break;
			case 6:
				
				
				break;
			case 7:
				break;
				default:
					
					break;
			
		}
		SmartDashboard.putNumber("deegree of defence manip u shaped arm", deg);
	}
	}




