public class MyTimer {
    private long startTime = 0;
    private long endTime = 0;

    public MyTimer() {
        this.start();
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {
        this.endTime = System.currentTimeMillis();
    }

    public long getMillisecond() {
        return this.endTime - this.startTime;
    }

    public long getSecond() {
        return (this.endTime - this.startTime)/1000;
    }

}

