package cn.itcast.nsfw.complain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class MyTimeTask extends TimerTask {
    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()));
    }
}
