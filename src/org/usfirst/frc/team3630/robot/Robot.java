
package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
	Joystick leftStick;
	Joystick rightStick;
	DriveTrain driveTrain;
	
	int rightMotor;
	int leftMotor;// chanle for tallons
	
	
     
    public void robotInit() {
    	leftStick= new Joystick(1);
    	rightStick= new Joystick(2);
    	rightMotor = 5;
    	leftMotor  = 6;
    	driveTrain = new DriveTrain( rightMotor, leftMotor, rightStick,leftStick);
    	driveTrain.driveTrainPeriodic();

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	driveTrain.driveTrainPeriodic();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
