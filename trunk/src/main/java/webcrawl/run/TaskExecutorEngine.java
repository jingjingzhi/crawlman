package webcrawl.run;

/**
 * 
 * @author jingjing.zhijj
 * 
 */
public interface TaskExecutorEngine {
	/**
	 * shrink the size of the worker pool
	 */
	public void shrink();

	/**
	 * expand the size of the worker pool
	 */
	public void expand();

	/**
	 * execute a task
	 * 
	 * @param r
	 */
	void execute(Runnable r);
}
