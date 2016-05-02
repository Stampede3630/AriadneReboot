
package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
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
    
    Joystick breachLeftJoy; // 2 joystick for defence breaching 
    Joystick breachRightJoy;
    Joystick shootLeftJoy;// 2 for shooting and driving 
    Joystick shootRightJoy; 
    
    int LeftFrontChanel;
    int LeftRearChanel;
    int RightRearChanel;
    int RightFrontChannel;
    DriveTrain tankDriveTrain;
    LifterManipulator shooter;
    UshapedArm uArm;
    StraitArm arm2;
    
    Sensors sensors;
    Autonomous autonomous;
    int autoMode;
    
    //public AHRS ahrs = null;
//    I2C bob;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     * @param DriveTrain 
     */
    public void robotInit() {
    	
    	sensors = new Sensors();
    	sensors.sensorInit();
        
    	tankDriveTrain = new DriveTrain(sensors);
		shooter = new LifterManipulator(tankDriveTrain);
		tankDriveTrain.driveTrainInit(); 
		uArm = new UshapedArm(tankDriveTrain);
		arm2= new StraitArm();
		
		autonomous = new Autonomous();
		autoMode = 1; // Low bar
		SmartDashboard.putNumber("AUTO_MODE", autoMode);
		SmartDashboard.putString("AUTO_NAME", "PREMATCH");

		
		//if (ahrs == null)
		//ahrs = new AHRS(SerialPort.Port.kUSB); 
		//ahrs = new AHRS(I2C.Port.kOnboard);
			
		chooser = new SendableChooser();
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		
		shootLeftJoy = new Joystick(Consts.SHOOTER_LEFT_JOYSTICK_CHAN);
		shootRightJoy = new Joystick(Consts.SHOOTER_RIGHT_JOYSTICK_CHAN);
		breachLeftJoy = new Joystick(Consts.BREACH_LEFT_JOYSTICK_CHAN);
		breachRightJoy = new Joystick(Consts.BREACH_RIGHT_JOYSTICK_CHAN);

    }
    
    public void disableInit(){
    	tankDriveTrain.stopDrive();
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
		autonomous.autonomousInit(shooter, tankDriveTrain, sensors);
		
		autoMode = (int)SmartDashboard.getNumber("AUTO_MODE");
		if ((autoMode < 0) || (autoMode > 7)) {
			autoMode = 1; // Low Bar
			SmartDashboard.putNumber("AUTO_MODE", autoMode);
		}
		
		// Start recording AVI video of the first practice match.
		// SmartDashboard.putString("RR_AVI_COMMAND", "Start");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic(){
    	switch (autoMode) {
    	case 0: // Low Bar No Shoot
    		//low bar without shooting
    		autonomous.noShootlowbarPeriodic();
    		SmartDashboard.putString("AUTO_NAME", "LBARNOSHOOT");
    		break;
    	case 1: // Low Bar
    	   	//low bar and shoot
    		autonomous.lowbarPeriodic();
    		SmartDashboard.putString("AUTO_NAME", "LOWBAR");
    		break;
    	case 2: // A
    		//driveTrain defense in location A
        	autonomous.autoAPeriodic();
    		SmartDashboard.putString("AUTO_NAME", "BREACH A");
    		break;
    	case 3: // B
    		//driveTrain defense in location B
    		autonomous.autoBPeriodic();
    		SmartDashboard.putString("AUTO_NAME", "BREACH B");
    		break;
    	case 4: // C
    		//driveTrain defense in location C
        	autonomous.autoCPeriodic();
    		SmartDashboard.putString("AUTO_NAME", "BREACH C");
    		break;
    	case 5: // D
    		//driveTrain defense in location D
        	autonomous.autoDPeriodic();
    		SmartDashboard.putString("AUTO_NAME", "BREACH C");
    		break;
    	case 6: // Any barrier breach, NO SHOOT
    		autonomous.noShootAutoPeriodic();
    		SmartDashboard.putString("AUTO_NAME", "NOSHOOT");
    		break;
    	case 7: // Super Breach: Breach once, return over barrier, rotate, ready to re-breach
    		autonomous.superBreachAutoPeriodic();
    		SmartDashboard.putString("AUTO_NAME", "SUPER");
    		break;
		default:
			break;
    	}
		
	//driveTrain defense without shooting
		//autonomous.noShootautoPeriodic();
		
    	sensors.updateSmartDB();
    }
    /**s
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    	if (breachLeftJoy.getRawButton(Consts.BREACH_LEFT_BTN_SWITCH_DRIVERS)){ // switch driver controls.	
    	// The shooter person controls driving and also the manipulator and never controls the UArm and Straight arm..
    		tankDriveTrain.driveShooter(); 
    	 }
 	    else {
 	     //The breach person controls driving and never controls the shooter(manipulator) but always controls the UArm and Straight arm.
 	    	tankDriveTrain.driveBreach(); 
 	    }
    	
    	shooter.manipulatorPeriodic(); // Obviously only controlled by the shooter person.
    	uArm.UArmPeriodic(); // Only controlled by the breach person.
    	arm2.straightArmPeriodic(); // Only controlled by the breach person.
    	
    	tankDriveTrain.updateSmartDB();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {

    }
    
}
