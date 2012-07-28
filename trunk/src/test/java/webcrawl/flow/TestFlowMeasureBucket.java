package webcrawl.flow;

import java.util.Random;

import webcrawl.flow.FlowMeasureBucket;

public class TestFlowMeasureBucket {
	public void test() {
		FlowMeasureBucket flow = new FlowMeasureBucket(10);
		Random r = new Random();
		while (true) {
			try {
				long timeout = r.nextInt(20);
				timeout = Math.abs(timeout);
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flow.add();
			System.out.println("flow: " + flow.getFlow());
		}
	}
}
