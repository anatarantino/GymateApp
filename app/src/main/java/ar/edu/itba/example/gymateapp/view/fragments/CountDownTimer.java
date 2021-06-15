package ar.edu.itba.example.gymateapp.view.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CountDownTimer {

    private long remaining = 0;
    private long interval = 0;
    private android.os.CountDownTimer countDownTimer;
    private final MutableLiveData<CountDownTimer.Status> countDownStatus = new MutableLiveData<>();

    public static class Status {
        private boolean isFinished;
        private long remainingTime;

        public Status(long remainingTime, boolean isFinished) {
            this.isFinished = isFinished;
            this.remainingTime = remainingTime;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public long getRemainingTime() {
            return remainingTime;
        }
    }
    public void start(long time, long interval) {
        this.interval = interval;
        start(time);
    }

    private void start(long time) {
        countDownTimer = new android.os.CountDownTimer(time, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                CountDownTimer.this.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                CountDownTimer.this.onFinish();
            }
        }.start();
    }

    private void onTick(long millisUntilFinished) {
        remaining = millisUntilFinished;
        countDownStatus.setValue(new Status(remaining / 1000, false));
    }

    private void onFinish(){
        countDownStatus.setValue(new Status(0, true));
    }

    public LiveData<CountDownTimer.Status> getStatus() {
        return countDownStatus;
    }

    public void pause() {
        cancel();
    }

    public void resume() {
        start(remaining);
    }

    public void stop() {
        interval = 0;
        cancel();
    }

    private void cancel() {
        countDownTimer.cancel();
    }

}
