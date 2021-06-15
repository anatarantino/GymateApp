package ar.edu.itba.example.gymateapp.view.fragments;

import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Timer {

    private long remaining = 0;
    private long interval = 0;
    private CountDownTimer countDownTimer;
    private final MutableLiveData<Timer.Status> countDownStatus = new MutableLiveData<>();

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
        countDownTimer = new CountDownTimer(time, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                Timer.this.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Timer.this.onFinish();
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

    public LiveData<Timer.Status> getStatus() {
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
