package webcrawl.run;

import java.util.List;

import webcrawl.request.HttpRequest;
import webcrawl.rule.Rule;


public class TargetMatcherWorker implements RequestMatcher {

	private List<Rule> targetMatchRules;

	private TaskExecutorEngine taskExecutorEngine;

	public TargetMatcherWorker(List<Rule> targetRules,
			TaskExecutorEngine taskExecutorEngine) {
		this.targetMatchRules = targetRules;
		this.taskExecutorEngine = taskExecutorEngine;
	}

	public boolean match(HttpRequest request) {
		for (Rule rule : this.targetMatchRules) {
			if (rule.match(request)) {
				this.taskExecutorEngine.execute(new TargetTask(request));
				return true;
			}
		}

		return false;
	}

}
