package webcrawl.run.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webcrawl.run.CrawlJobRunner;


/**
 * 
 * @author jingjing.zhijj
 * 
 */
public class EventBus extends AbstractCrawlJobEventListener {

	private static Log log = LogFactory.getLog(CrawlJobRunner.class);

	private Queue<Event> queue = new LinkedBlockingDeque<Event>();

	private List<EventListener> listeners = new ArrayList<EventListener>();

	private boolean stop;

	private EventBusRunnerThread eventBusRunnerThread;

	public void publishEvent(Event e) {
		this.queue.add(e);
	}

	private void pollEvent() {
		while (!stop) {
			Event event = this.queue.poll();
			if (event != null) {
				for (EventListener el : this.listeners) {
					try {
						el.onEvent(event);
					} catch (Exception e) {
					}
				}
			}
		}
		if(log.isInfoEnabled()){
			log.info("event bus now stop to service.");
		}
	}


	public void startRunnerThread() {
		this.stop = false;
		eventBusRunnerThread = new EventBusRunnerThread();
		eventBusRunnerThread.setDaemon(true);
		eventBusRunnerThread.start();
		if(log.isInfoEnabled()){
			log.info("event bus now start to service.");
		}
	}

	@Override
	protected void onJobInterrupted() {
		this.stop = true;
	}


	@Override
	protected void onJobFinished() {
		this.stop = true;
	}

	public void addListener(EventListener el) {
		if (el != null) {
			this.listeners.add(el);
		}
	}

	class EventBusRunnerThread extends Thread {
		public void run() {
			pollEvent();
		}
	}

}
