package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

public class Network_table_com {
NetworkTable table;
	public void Network_table_init(){
		table = NetworkTable.getTable("SmartDashboard");
	}
	public void Test(){
		try{
			SmartDashboard.putNumber("numberOfImages",(table.getNumber("IMAGE_COUNT", 0.0)));;
		}
		catch (TableKeyNotDefinedException ex)
		{
		}

	}
	
}
