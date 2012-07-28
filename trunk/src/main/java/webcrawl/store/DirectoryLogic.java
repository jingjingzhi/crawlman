package webcrawl.store;
/**
 * 
 * @author jingjing.zhijj
 *
 */
public interface DirectoryLogic {

	/**
	 * given a none null directory and a long file name, the logic should
	 * produce an absolute file path
	 * 
	 * @param baseDirectory
	 * @param filename
	 * @return
	 */
	String getAbsolutePath(String baseDirectory, long filename);

}
