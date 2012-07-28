package webcrawl.run;

import java.util.concurrent.atomic.AtomicLong;

public class JobState {

	private AtomicLong submited = new AtomicLong(0);
	private AtomicLong finished = new AtomicLong(0);

	public void addSubmitted() {
		this.submited.incrementAndGet();
	}

	public void addFinished() {
		this.finished.incrementAndGet();
	}

	public long getSubmited() {
		return submited.longValue();
	}

	public long getFinished() {
		return finished.longValue();
	}

}
