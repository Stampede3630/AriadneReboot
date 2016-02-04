package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
public class LifterManipulator {
	CANTalon spinLeft = new CANTalon(2);
	CANTalon spinRight = new CANTalon(3);
	CANTalon Lifter = new CANTalon(4);
	Joystick shooter1;// 2 for shooting and driving 
	Joystick shooter2;
	
	public void spinLeftp(){
		
		spinLeft.set(1);
	}
	public void spinRight(){
	spinRight.set(1);
	}
	public void Lifterdown(){
		Lifter.set(-1);
		
	}
	public void LifterUp(){
		Lifter.set(1);
	}
	public void  loadBall(){
		spinLeft.setInvertedMotor(spinLeft.MotorType.kspinLeft,true);
		spinLeft.set(1);
		spinRight.set(1);
	}
	
	public void shootBall(){
		spinLeft.set(1);
		spinRight.set(2);
		
	}
	public void stop(){
		spinLeft.set(0);
		spinRight.set(0);
		Lifter.set(0);
	}
	public void manipulatorPeriodic(){
		if(shooter1.getRawButton(2)){
			Lifterdown();
		}
		if(shooter1.getRawButton(3)){
			LifterUp();
		}
		if(shooter1.getRawButton(4)){
			loadBall();
		}
		if(shooter1.getRawButton(5)){
			shootBall();
		}
		if(shooter2.getRawButton(6)){
			stop();
		}
	}
	
}

