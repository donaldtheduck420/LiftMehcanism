/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.OI;
import edu.wpi.first.wpilibj.PWMTalonSRX;

public class DriveTrain extends Subsystem {

  private PWMTalonSRX left = new PWMTalonSRX(RobotMap.leftDPort);
  private PWMTalonSRX right = new PWMTalonSRX(RobotMap.rightDPort);
  private PWMTalonSRX lift = new PWMTalonSRX(RobotMap.liftPort);
  private PWMTalonSRX arm = new PWMTalonSRX(RobotMap.armPort);
  
  private static DriveTrain drive;

  private double leftPower;
  private double rightPower;
  private boolean downArm; 
  private boolean upArm;
  private boolean downLift;
  private boolean upLift;
  

  public DriveTrain() {
    left.setInverted(false);
    right.setInverted(true);
    arm.setInverted(false);
  }

  public static DriveTrain getInstance() {
    if(drive == null) {
      drive = new DriveTrain();
    }
    return drive;
  }

  public void tankDrive(double leftPow, double rightPow) {
    left.set(leftPow);
    right.set(rightPow);
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void periodic() {

    downArm = OI.getJoy().getRawButton(3);
    upArm = OI.getJoy().getRawButton(4); 

    downLift = OI.getJoy().getRawButton(5);
    upLift = OI.getJoy().getRawButton(6);
    

    if (downArm == true) {
      arm.set(-0.2);     //if downArm is true, sets the pwm motor speed -0.2 so the arm goes down, and vice versa if upArm is true
    } 
    else if (upArm == true) {
      arm.set(0.2);
    } 
    else {
      arm.set(0);
    } 

    if(downLift == true) {       //applies the same logic as the if statment above
      lift.set(-0.2);
    } 
    else if (upLift == true) {
      lift.set(0.2);
    } 
    else {
      arm.set(0);
    }


      leftPower = OI.getJoy().getY();   //get joystick positions
      rightPower = OI.getJoy().getY();  
    if (OI.getJoy().getX() < -0.2) {    //use positions to set speeds
      leftPower += -0.5;
      rightPower += 0.5;
    } else if (OI.getJoy().getX() > 0.2) {
      leftPower += 0.5;
      rightPower += -0.5;
    }

    tankDrive(leftPower * 0.3, rightPower * 0.3);   


  }
}