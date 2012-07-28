package webcrawl.rule.regex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import webcrawl.run.CrawlException;


/**
 * 
 * @author jingjing.zhijj
 * 
 */
public class NameMatcherFactory {
	
	private static Log log = LogFactory.getLog(NameMatcherFactory.class);
	
	private static PatternCompiler pc = new Perl5Compiler();

	public static Pattern create(String pattern) throws CrawlException {
		try {
			Pattern p = pc
					.compile(pattern, Perl5Compiler.CASE_INSENSITIVE_MASK);
			return p;
		} catch (MalformedPatternException e) {
			throw new CrawlException("wrong pattern: ["+pattern+"]");
		}

	}

	public static boolean matches(Pattern pattern, String input) {
		PatternMatcher matcher = new Perl5Matcher();
		try {
			return matcher.matches(input, pattern);
		} catch (Exception e) {
			log.warn("matches exception, input:"+input + ", pattern: " + pattern, e);
		}
		
		return false;
	}

}
