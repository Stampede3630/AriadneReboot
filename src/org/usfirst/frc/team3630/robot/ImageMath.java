package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ImageMath {
	
	public  double  get_dist_from_image(){
		double BOTTOM_LEFT_X = SmartDashboard.getNumber("BOTTOM_LEFT_X");
		double BOTTOM_RIGHT_X = SmartDashboard.getNumber("BOTTOM_RIGHT_X");
		double Width_Px = Math.abs(BOTTOM_RIGHT_X-BOTTOM_LEFT_X);
		double targetWidthDeg = Consts.imageWidthDeg * Width_Px / Consts.imageWidthPx;
		double targetDistance = (20) / Math.tan(targetWidthDeg * Math.PI / (Consts.imageWidthPx / 2));
		return targetDistance;
		}
	
	public double get_target_right_of_center_px() {
		double BOTTOM_RIGHT_X = SmartDashboard.getNumber("BOTTOM_RIGHT_X");
		double BOTTOM_LEFT_X = SmartDashboard.getNumber("BOTTOM_LEFT_X");
		double width = Math.abs(BOTTOM_RIGHT_X - BOTTOM_LEFT_X);
		double right_of_center = Math.min(BOTTOM_RIGHT_X, BOTTOM_LEFT_X) + (width / 2) - (Consts.imageWidthPx / 2);
		return right_of_center;
	}
}
