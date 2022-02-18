import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class StopWatch {

    static long min, sec, hr, totalSec = 0;

    private static String format(long value){
        if (value < 10){
            return 0 + "" + value;
        }
        return String.valueOf(value);
    }

    public static void convertTime(){
        min = TimeUnit.SECONDS.toMinutes(totalSec);
        sec = totalSec - (min*60);
        hr = TimeUnit.SECONDS.toHours(totalSec);
        min = min - (hr*60);

        System.out.println("" + format(hr) + ":" + format(min) + ":" + format(sec));
        totalSec -= 1;
    }
    public static void main(String[] args){
        totalSec = 300;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("After one second...");
                convertTime();
                if (totalSec < 0){
                    System.exit(0);
                }

            }
        };
        Timer timer =  new Timer();
        timer.schedule(timerTask, 0, 1000);
    }


}
