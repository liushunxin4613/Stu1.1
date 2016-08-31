package com.fengyang.util;

public class FixedThread extends Thread {

	boolean isStop = false;

	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public void stopThread() {
		isStop = true;
	}

	public FixedThread() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FixedThread(Runnable runnable, String threadName) {
		super(runnable, threadName);
		// TODO Auto-generated constructor stub
	}

	public FixedThread(Runnable runnable) {
		super(runnable);
		// TODO Auto-generated constructor stub
	}

	public FixedThread(String threadName) {
		super(threadName);
		// TODO Auto-generated constructor stub
	}

	public FixedThread(ThreadGroup group, Runnable runnable, String threadName,
			long stackSize) {
		super(group, runnable, threadName, stackSize);
		// TODO Auto-generated constructor stub
	}

	public FixedThread(ThreadGroup group, Runnable runnable, String threadName) {
		super(group, runnable, threadName);
		// TODO Auto-generated constructor stub
	}

	public FixedThread(ThreadGroup group, Runnable runnable) {
		super(group, runnable);
		// TODO Auto-generated constructor stub
	}

	public FixedThread(ThreadGroup group, String threadName) {
		super(group, threadName);
		// TODO Auto-generated constructor stub
	}

}
