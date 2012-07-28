package webcrawl.store;

import java.io.File;

/**
 * given a base directory and a long type file name, this logic should produce a
 * two level path starting from the given base directory. the root level name
 * will be the result of dividing by 1000. the second level will be the result
 * of mod by 1000. 
 * example: 
 * 1) base directory=/home/admin/output/crawl, filename=109, output=/home/admin/output/crawl/0/109/109
 * 2) base directory=/home/admin/output/crawl, filename=100109, output=/home/admin/output/crawl/100/109/100109
 * 3) base directory=/home/admin/output/crawl, filename=78900109, output=/home/admin/output/crawl/78900/109/78900109
 * 
 * @author jingjing.zhijj
 * 
 */
public class TwoLevelDirectoryLogic implements DirectoryLogic {

	@Override
	public String getAbsolutePath(String baseDirectory, long filename) {

		if (filename < 0 || baseDirectory == null || "".equals(baseDirectory)
				|| !baseDirectory.startsWith("/")) {
			return null;
		}

		long root = filename / 1000;
		long leave = filename % 1000;
		String pathSep = File.separator;

		StringBuilder sb = new StringBuilder(baseDirectory);
		if (!(sb.lastIndexOf(pathSep) == (sb.length() - 1))) {
			sb.append(pathSep);
		}
		sb.append(root).append(pathSep).append(leave).append(pathSep).append(filename);

		return sb.toString();
	}

}
