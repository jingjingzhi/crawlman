package webcrawl.run.event;

/**
 * 
 * @author jingjing.zhijj
 * 
 */
public class CrawlJobEvent implements Event {

	public static int CRAWL_JOB_STARTED = 1;
	public static int CRAWL_JOB_INTERRUPTED = 2;
	public static int CRAWL_JOB_RESUMED = 4;
	public static int CRAWL_JOB_FINISHED = 8;
	public static int CRAWL_TASK_SUBMITTED = 16;
	public static int CRAWL_TASK_FINISHED = 32;

	public CrawlJobEvent(int type, Object source) {
		this.type = type;
		this.source = source;
	}

	public CrawlJobEvent() {
	}

	private int type = CRAWL_TASK_SUBMITTED;
	private Object source;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public static boolean interested(int expectedType, int actualType) {
		return (expectedType & actualType) == expectedType;
	}
	

}
