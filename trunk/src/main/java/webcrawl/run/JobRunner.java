package webcrawl.run;

public interface JobRunner {
	void startJob() throws CrawlException;
	
	void pauseJob();
	
	void shutdownJob();
	
	void instantiate() throws CrawlException;
	
	void resumeJob();
}
