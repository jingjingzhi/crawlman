package webcrawl.flow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * measure flow in the latest one second
 * 
 * @author jingjing.zhijj
 * 
 */
public class FlowMeasureBucket {

	private static Log log = LogFactory.getLog(FlowMeasureBucket.class);

	private long bucketFlows[] = new long[100];
	/**
	 * 10 Milliseconds a span
	 */
	private int span = 10;
	private int buckets = 1000;

	private int curBucket = 0;
	
	public FlowMeasureBucket(){
		
	}

	/**
	 * 
	 * @param buckets
	 * @param span
	 */
	public FlowMeasureBucket(int span) {

		if (1000 % span == 0) {
			this.span = span;
			this.buckets = 1000 / this.span;
		}

		bucketFlows = new long[this.buckets];
		log.info("buckets:" + this.buckets);
	}

	/**
	 * time in milliseconds
	 * 
	 * @param time
	 */
	public void add() {

		int expectBucket = getExpectedBucket();

		if (log.isDebugEnabled()) {
			log.debug("curbucket:" + this.curBucket + ", expected bucket:"
					+ expectBucket);
		}

		if (this.curBucket == expectBucket) {
			this.bucketFlows[this.curBucket]++;
		} else {

			if (this.curBucket < expectBucket) {
				for (int cur = this.curBucket + 1; cur <= expectBucket; cur++) {
					this.bucketFlows[cur] = 0;
				}
			} else {
				for (int cur = this.curBucket + 1; cur < this.bucketFlows.length; cur++) {
					this.bucketFlows[cur] = 0;
				}

				for (int cur = 0; cur <= expectBucket; cur++) {
					this.bucketFlows[cur] = 0;
				}
			}
			this.bucketFlows[expectBucket]++;
			this.curBucket = expectBucket;
		}

	}

	private int getExpectedBucket() {
		return (int) (System.currentTimeMillis() % 1000 / span);
	}

	/**
	 * get flow in the latest one second
	 * 
	 * @return
	 */
	public long getFlow() {
		long sum = 0;
		for (long bucketFlow : this.bucketFlows) {
			sum += bucketFlow;
		}
		return sum;
	}

}
