package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Joystick;

public class CameraServo {
	Servo tilt = new Servo(8);
	Servo pan = new Servo(9); 
	
	Joystick shootLeft;
	
	public  CameraServo(){
		shootLeft = new Joystick(1);
	}
	public void tiltUp(){
		tilt.set(1);
		}
	public void tiltDown(){
		tilt.set(-1);
	}
	public void panLeft(){
		pan.set(1);
	}
	public void panRight(){
		pan.set(-1);
	}
	public int buttonReturn(){
		if (shootLeft.getRawButton(Consts.SHOOTER_LEFT_JOYSTICK_PANLEFT)) { // Note: this button is serviced in DriveTrain.driveBreach() - not here.
			return Consts.SHOOTER_LEFT_JOYSTICK_PANLEFT_CONSTANT;
		}
		else if (shootLeft.getRawButton(Consts.SHOOTER_LEFT_JOYSTICK_PANRIGHT)){
			return Consts.SHOOTER_LEFT_JOYSTICK_PANRIGHT_CONSTANT;
		}
		else if (shootLeft.getRawButton(Consts.SHOOTER_LEFT_JOYSTICK_lIFTUP)){
				return Consts.SHOOTER_LEFT_JOYSTICK_lIFTUP_CONSTANT;
		}
		else if (shootLeft.getRawButton(Consts.SHOOTER_LEFT_JOYSTICK_LIFTDOWN)){
			return Consts.SHOOTER_LEFT_JOYSTICK_LIFTDOWN_CONSTANT;
		}
		else{
			return Consts.SHOOTER_LEFT_JOYSTICK_CAMERA_DEFAULT;
		}
	}
	
	public void cameraPeriodic(){
		switch (buttonReturn()) {
		
		case Consts.SHOOTER_LEFT_JOYSTICK_PANLEFT_CONSTANT:
			panLeft();
			break;
		
		case Consts.SHOOTER_LEFT_JOYSTICK_PANRIGHT_CONSTANT: 
			panRight();
			break;
		
		case Consts.SHOOTER_LEFT_JOYSTICK_lIFTUP_CONSTANT:
			tiltUp();
			break;
		
		case Consts.SHOOTER_LEFT_JOYSTICK_LIFTDOWN_CONSTANT:
			tiltDown();
			break;
		}
	}
}
