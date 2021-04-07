package com.vm.framework;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public abstract class FutureImp<T> implements Future<T> {

    private volatile T result = null;
    private volatile boolean cancelled = false;
    private final CountDownLatch countDownLatch;

    public FutureImp() {
        countDownLatch = new CountDownLatch(1);
    }

    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        if (isDone()) {
            return false;
        } else {
            countDownLatch.countDown();
            cancelled = true;
            return !isDone();
        }
    }

    @Override
    public T get() throws InterruptedException {
        countDownLatch.await();
        return result;
    }

    @Override
    public T get(final long timeout, final TimeUnit unit)
            throws InterruptedException {
        countDownLatch.await(timeout, unit);
        return result;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public boolean isDone() {
        return countDownLatch.getCount() == 0;
    }

    public void setResult(final T result) {
        this.result = result;
        countDownLatch.countDown();
    }
}