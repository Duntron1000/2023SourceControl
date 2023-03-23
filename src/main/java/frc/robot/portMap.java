package frc.robot;

public class portMap {

    //drive train
    public static final int CAN_left1 = 1;
    public static final int CAN_left2 = 2;
    public static final int CAN_right1 = 3;
    public static final int CAN_right2 = 4;

    //Arm
    public static final int CAN_susRotate = 5;//5
    public static final int CAN_tilt = 6; //6
    public static final int CAN_extending = 7; //7

    //Grabber
    public static final int CAN_leftIntake = 8; //8
    public static final int CAN_rightIntake = 9; //9

    //controllers
    public static final int joystick = 0;
    public static final int xbox = 1;

    //Tilt PID Values -- not yet accualy used
    public static final double TILT_P = 0.1;
    public static final double TILT_I = 0;
    public static final double TILT_D = 0;
    public static final double TILT_Iz = 0;
    public static final double TILT_FF = 0;

    //Extend PID Values -- not yet accualy used
    public static final double EXTEND_P = 0.1;
    public static final double EXTEND_I = 0;
    public static final double EXTEND_D = 0;
    public static final double EXTEND_Iz = 0;
    public static final double EXTEND_FF = 0;


    //Test motor
    public static final int CAN_TEST = 62;

}
