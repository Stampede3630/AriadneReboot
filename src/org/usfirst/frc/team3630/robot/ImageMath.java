package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ImageMath {
	double BOTTOM_RIGHT_X;
	double BOTTOM_LEFT_X;
	double Width_Degrees; 
public void ImageMathinit(){
	 
	}
public int  math_periodic(){
	BOTTOM_RIGHT_X= SmartDashboard.getNumber("BOTTOM_RIGHT_X");
	 BOTTOM_LEFT_X= SmartDashboard.getNumber("BOTTOM_LEFT_X");
	 Width_Degrees = Math.abs(BOTTOM_RIGHT_X-BOTTOM_LEFT_X);
	double targetWidthDeg = (47 ) * (Width_Degrees) / (320 );
	double targetDistance = (20 ) / math.TAN(targetWidthDeg * Math.PI / (180 ));
}
		
}
