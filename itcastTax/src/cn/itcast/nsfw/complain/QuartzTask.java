package cn.itcast.nsfw.complain;

import java.util.Timer;

public class QuartzTask {

    public void doSimpleTriggerTask(){
        Timer timer = new Timer();
        timer.schedule(new MyTimeTask(),10000,3000);
    }
}
