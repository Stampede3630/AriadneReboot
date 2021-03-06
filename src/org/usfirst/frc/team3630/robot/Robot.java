
package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**   
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    
    Joystick breachLeft; // 2 joystick for defence breaching 
    Joystick breachRight;
    Joystick shootLeft;// 2 for shooting and driving 
    Joystick shootRight; 
    
    int LeftFrontChanel;
    int LeftRearChanel;
    int RightRearChanel;
    int RightFrontChannel;
    DriveTrain tankDriveTrain;
    LifterManipulator shooter;
    UshapedArm hook;
    StraitArm arm2;
 
   
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     * @param DriveTrain 
     */
    public void robotInit() {
       tankDriveTrain = new DriveTrain();
       shooter = new LifterManipulator();
       tankDriveTrain.driveTrainInit(); 
       hook = new  UshapedArm();
       arm2= new StraitArm();

   		
       chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        
        shootLeft= new Joystick(1);
		shootRight= new Joystick(2);
		breachLeft= new Joystick(0);
		breachRight= new Joystick(4);
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	//switch(autoSelected) {
    	//case customAuto:
        //Put custom auto code here   
           // break;
    //	case defaultAuto:
    	//default:
    	//Put default auto code here
            //break;
    	}
    //}

    /**s
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    	if (breachLeft.getRawButton(Consts.BREACH_LEFT_BTN_SWITCH_DRIVERS)){ // switch driver controls.	
    	// The shooter person controls driving and also the manipulator and never controls the UArm and Straight arm..
    		tankDriveTrain.driveShooter(); 
    	 }
 	    else {
 	     //The breach person controls driving and never controls the shooter(manipulator) but always controls the UArm and Straight arm.
 	    	tankDriveTrain.driveBreach(); 
 	    }
    	
    	shooter.manipulatorPeriodic(); // Obviously only controlled by the shooter person.
    	hook.UArmPeriodic(); // Only controlled by the breach person.
    	arm2.straightArmPeriodic(); // Only controlled by the breach person.
    	
    	tankDriveTrain.updateSmartDB();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
