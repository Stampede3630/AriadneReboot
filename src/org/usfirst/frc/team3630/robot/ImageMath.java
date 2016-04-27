package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ImageMath {
	
	public static double  get_targetDistanceInches() {
		double BOTTOM_LEFT_X = SmartDashboard.getNumber("BOTTOM_LEFT_X");
		double BOTTOM_RIGHT_X = SmartDashboard.getNumber("BOTTOM_RIGHT_X");
		double targetWidthPx = Math.abs(BOTTOM_RIGHT_X - BOTTOM_LEFT_X);
		double targetWidthDeg = Consts.cameraFovWidthDegrees * targetWidthPx / Consts.cameraFovWidthPx;
		
		SmartDashboard.putNumber("AUTO_ADJ Width Pixels", targetWidthPx);
		SmartDashboard.putNumber("AUTO_ADJ Width Degrees", targetWidthDeg);

		// Perform distance calculation based on half the target width, since we assume we are straight
		// in front of the target. So the triangle for calculations has:
		//    adjacent side = distance to the target;
		//    opposite side = half the width of the target (10 inches)
		//    theta degrees is the angle between pointing at the target center and its right side.
		double thetaDegrees = targetWidthDeg / 2.0;
		double thetaRadians = thetaDegrees * Math.PI / 180.0;
		double targetDistanceInches = (Consts.targetWidthInches / 2.0) / Math.tan(thetaRadians);
		
		return targetDistanceInches;
		}
	
	// Todo: Check this calculation.
	public static double get_target_right_of_center_px() {
		double BOTTOM_RIGHT_X = SmartDashboard.getNumber("BOTTOM_RIGHT_X");
		double BOTTOM_LEFT_X = SmartDashboard.getNumber("BOTTOM_LEFT_X");
		double targetWidthPx = Math.abs(BOTTOM_RIGHT_X - BOTTOM_LEFT_X);
		double right_of_center = Math.min(BOTTOM_RIGHT_X, BOTTOM_LEFT_X) + (targetWidthPx / 2) - (Consts.cameraFovWidthPx / 2);
		return right_of_center;
	}
	
	public static void put_shooter_angle(double curShooterAngle) {
		SmartDashboard.putNumber("CUR_SHOOTER_ANGLE", curShooterAngle);
	}
}
