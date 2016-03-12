package org.usfirst.frc.team3630.robot;



	import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


	public class ImageMath {
		double BOTTOM_RIGHT_X;
		double BOTTOM_LEFT_X;
		double Width_Degrees; 
		 double targetDistance;
		double targetWidthDeg ;
	

	public  double  math_periodic(){
		BOTTOM_RIGHT_X= SmartDashboard.getNumber("BOTTOM_RIGHT_X");
		 BOTTOM_LEFT_X= SmartDashboard.getNumber("BOTTOM_LEFT_X");
		 Width_Degrees = Math.abs(BOTTOM_RIGHT_X-BOTTOM_LEFT_X);
		 targetWidthDeg = (47 ) * (Width_Degrees) / (320 );
		targetDistance = (20 ) / Math.tan(targetWidthDeg * Math.PI / (180 ));
		return targetDistance;
		
	}
			
	}


