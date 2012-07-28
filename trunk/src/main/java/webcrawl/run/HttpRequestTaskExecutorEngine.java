package webcrawl.run;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webcrawl.run.event.AbstractCrawlJobEventListener;


/**
 * 
 * @author jingjing.zhijj
 * 
 */
public class HttpRequestTaskExecutorEngine extends
		AbstractCrawlJobEventListener implements TaskExecutorEngine {

	private static Log log = LogFactory
			.getLog(HttpRequestTaskExecutorEngine.class);

	private int minPoolSize = 8;
	private int corePoolSize = 10;
	private int maxPoolSize = 50;
	private int ajustStep = 2;
	private long keepAliveTime = 10000l;
	private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(
			5000);

	private ThreadPoolExecutor executorService = new ThreadPoolExecutor(
			this.corePoolSize, this.maxPoolSize, this.keepAliveTime,
			TimeUnit.MILLISECONDS, queue,
			new ThreadPoolExecutor.CallerRunsPolicy());

	public void shrink() {
		if (this.corePoolSize - ajustStep > this.minPoolSize) {
			this.corePoolSize -= ajustStep;
			setCorePoolSize();
		}
	}

	private void setCorePoolSize() {
		if (log.isInfoEnabled()) {
			log.info("corepool size resize to " + this.corePoolSize
					+ ", minPoolSize: " + this.minPoolSize + ", maxPoolSize:"
					+ this.maxPoolSize);
		}
		this.executorService.setCorePoolSize(this.corePoolSize);
	}

	public void expand() {
		if (this.corePoolSize + ajustStep < this.maxPoolSize) {
			this.corePoolSize += ajustStep;
			this.setCorePoolSize();
		}
	}

	public void execute(Runnable r) {
		executorService.execute(r);
	}

	@Override
	protected void onJobResumed() {
		executorService = new ThreadPoolExecutor(this.corePoolSize,
				this.maxPoolSize, this.keepAliveTime, TimeUnit.MILLISECONDS,
				queue, new ThreadPoolExecutor.CallerRunsPolicy());
	}

	@Override
	protected void onJobInterrupted() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onJobFinished() {
		this.executorService.shutdown();
		if (log.isInfoEnabled()) {
			log.info("executor engine was shut down ");
		}
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

}
