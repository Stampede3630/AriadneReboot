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
		if (shootLeft.getRawButton(Consts.SHOOTER_LEFT_BTN_PANLEFT)) { // Note: this button is serviced in DriveTrain.driveBreach() - not here.
			return Consts.SHOOTER_JOYSTICK_CODE_PANLEFT;
		}
		else if (shootLeft.getRawButton(Consts.SHOOTER_LEFT_BTN_PANRIGHT)){
			return Consts.SHOOTER_JOYSTICK_CODE_PANRIGHT;
		}
		else if (shootLeft.getRawButton(Consts.SHOOTER_LEFT_BTN_LIFTUP)){
				return Consts.SHOOTER_JOYSTICK_CODE_LIFTUP;
		}
		else if (shootLeft.getRawButton(Consts.SHOOTER_LEFT_BTN_LIFTDOWN)){
			return Consts.SHOOTER_JOYSTICK_CODE_LIFTDOWN;
		}
		else{
			return Consts.SHOOTER_JOYSTICK_CODE_CAMERA_DEFAULT;
		}
	}
	
	public void cameraPeriodic(){
		switch (buttonReturn()) {
		
		case Consts.SHOOTER_JOYSTICK_CODE_PANLEFT:
			panLeft();
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_PANRIGHT: 
			panRight();
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_LIFTUP:
			tiltUp();
			break;
		
		case Consts.SHOOTER_JOYSTICK_CODE_LIFTDOWN:
			tiltDown();
			break;
		}
	}
}
