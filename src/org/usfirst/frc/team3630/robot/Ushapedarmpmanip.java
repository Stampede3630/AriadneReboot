package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Ushapedarmpmanip {
	// inisaliose talons 
			Talon mainControl = new Talon(3);// control U shaped AEM 

			Joystick breachLeft;// 2 for shooting and driving 

			//ShooterEncoder armrot= new ShooterEncoder(1,2); // encoder to fetch degres of lifter shaft
			//double rot =armrot.degreesRot();
			public Ushapedarmpmanip  (){
			 breachLeft= new Joystick(3);
				
			}
		/*	public void degree_corection(){
				DigitalInput limitswitch= new  DigitalInput(4);
				boolean upperbinaryValue= limitswitch.get();
				double rot = shaftRotation.fetchDegrees();
				double rotdevation= rot +3;
				
				if (upperbinaryValue == false && rot<=rotdevation-3 ){
					Lifterdown();
				}
				else if (upperbinaryValue == false && rot>=rotdevation+3){
					LifterUp();
				}
			}
	*/
			
			
			


			public void Lowerarm(){
				// double rot = lifterrot.degreesRot();
				//double rot = shaftRotation.fetchDegrees();
				
			//if (rot <= 100){
				 mainControl.set(.25);
			}
			//}
			public void Raisearm(){
				//double rot2 = shaftRotation.fetchDegrees();
				//if (rot >= 0){
				mainControl.set(-.5);
				}
			//}
			
		
			public void stop(){
				mainControl.set(0);
				
			}
			public int getJoyStickValue(){
				if( breachLeft.getRawButton(1)){
					return 1 ;
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
				
			
			
			
			public void manipulatorPeriodicbreach(){
			
			switch(getJoyStickValue()) {
				
				case 1: 
				break;
				
				case 2: ;
					
				break;
				
				case 3: Lowerarm();
					
				break;
				
				case 4: Raisearm();
					
					break;
				case 5:
					
					break;
				case 6:
					
					
					break;
				case 7://degree_corection();
					break;
					default:
						stop();
						break;
				
			}
			//SmartDashboard.putNumber("strait arm endoder degrees degrees", rot);
		}
		}

