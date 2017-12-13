package org.goldengates.buildascript.api;

public class Time {

	private long end;
	private final long start;
	private final long period;

	public Time(final long period) {
		this.period = period;
		start = System.currentTimeMillis();
		end = start + period;
	}

	public boolean isRunning() {
		return System.currentTimeMillis() < end;
	}

	public static String format(final long time) {
		final StringBuilder t = new StringBuilder();
		final long total_secs = time / 1000;
		final long total_mins = total_secs / 60;
		final long total_hrs = total_mins / 60;
		final int secs = (int) total_secs % 60;
		final int mins = (int) total_mins % 60;
		final int hrs = (int) total_hrs % 24;
		if (hrs < 10) {
			t.append("0");
		}
		t.append(hrs);
		t.append(":");
		if (mins < 10) {
			t.append("0");
		}
		t.append(mins);
		t.append(":");
		if (secs < 10) {
			t.append("0");
		} else if (secs < 0) {
			t.append("0");
		} else {
			t.append(secs);
		}
		return t.toString();
	}

	public long getPeriod() {
		return period;
	}
}
