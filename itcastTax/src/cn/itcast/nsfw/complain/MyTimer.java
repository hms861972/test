package cn.itcast.nsfw.complain;

import java.util.Timer;

public class MyTimer {

    public static void main(String[] args){
        Timer timer = new Timer();
        timer.schedule(new MyTimeTask(),10000,3000);
        System.out.println("hjjgh");
    }
}
