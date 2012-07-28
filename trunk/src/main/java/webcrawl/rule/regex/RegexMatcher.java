package webcrawl.rule.regex;

import org.apache.oro.text.regex.Pattern;

import webcrawl.rule.Matcher;
import webcrawl.run.CrawlException;


public class RegexMatcher implements Matcher {
	private String patternSource;
	private Pattern pattern;

	public RegexMatcher(String patternSource) throws CrawlException {
		this.patternSource = patternSource;
		this.pattern = NameMatcherFactory.create(this.patternSource);
	}

	public boolean matches(String input) {
		if(input==null){
			return false;
		}
		return NameMatcherFactory.matches(pattern, input);
	}

	public String getMatchRule() {
		return this.patternSource;
	}

}
