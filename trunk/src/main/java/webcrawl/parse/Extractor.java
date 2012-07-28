package webcrawl.parse;

/**
 * extract target from the page content
 * @author jingjing.zhijj
 *
 */
public interface Extractor<T> {
	
	T extract(String pageContect);

}
