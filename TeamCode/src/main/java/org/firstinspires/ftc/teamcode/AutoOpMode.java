package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "AutoOp", group = "")
public class AutoOpMode extends OpMode
{
    //Debug Reason
    public static final boolean using_wheel = false;
    public static final boolean using_linear = true;
    public static final boolean using_pincer = true;
    public static final boolean using_IMU = true;

    private Pincer pincer_part = new Pincer();
    private Linear linear_part = new Linear();
    private Wheel wheel_part = new Wheel();
    private int step = 0;
    private Gyro imu = new Gyro();

    @Override
    public void init()
    {
        imu.init(hardwareMap, telemetry, "imu");
        pincer_part.init(hardwareMap, telemetry);
        linear_part.init(hardwareMap, telemetry);
        wheel_part.init(hardwareMap, telemetry, this.imu);
        this.step = 0;
    }

    @Override
    public void start()
    {
        if(using_pincer) pincer_part.start();
        if(using_linear) linear_part.start();
        if(using_wheel) wheel_part.start();
        if(using_IMU) this.procedure_run();
    }

    @Override
    public void loop()
    {
        //Update Mechanism Parts
        if(using_pincer) pincer_part.update();
        if(using_linear) linear_part.update();
        if(using_wheel) wheel_part.update();
        if(using_IMU) imu.update();

        //Perform the Procedure
        if(this.procedure_finish()){
            this.procedure_run();
        }
    }

    private void procedure_run(){
        switch (this.step){
            /*
            case 0:
                this.telemetry.addData("Procedure", "0");
                this.wheel_part.start_step("detect signal");
                break;
            case 1:
                this.telemetry.addData("Procedure", "1");
                this.wheel_part.start_step("go parking place");
                break;
                */
            case 0:
                this.wheel_part.start_step("rotate");
                break;
        }
        this.step++;
    }

    private boolean procedure_finish(){
        return this.pincer_part.finish() && this.linear_part.finish() && this.wheel_part.finish();
    }
}