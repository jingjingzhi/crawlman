package webcrawl.rule;

import java.util.ArrayList;
import java.util.List;

import webcrawl.request.HttpRequest;


public class RuleProcesser {
	
	private List<Rule> rules = new ArrayList<Rule>();
	
	public boolean accept(HttpRequest req){
		for(Rule r : rules){
			boolean result = r.match(req);
			if(!result){
				return false;
			}
		}
		return true;
	}

	public List<Rule> getRules() {
		return rules;
	}

}
