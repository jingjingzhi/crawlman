package webcrawl.rule;

public interface Matcher{
	
	boolean matches(String input);
	
	String getMatchRule();

}
