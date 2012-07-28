package webcrawl.store;

import junit.framework.Assert;

import org.junit.Test;

import webcrawl.store.TwoLevelDirectoryLogic;

public class TestTwoLevelDirectoryLogic {

	@Test
	public void test() {
		TwoLevelDirectoryLogic logic = new TwoLevelDirectoryLogic();

		String base = "/home/admin/output/crawl/";
		Assert.assertEquals("/home/admin/output/crawl/0/109/109",
				logic.getAbsolutePath(base, 109));
		Assert.assertEquals("/home/admin/output/crawl/100/109/100109",
				logic.getAbsolutePath(base, 100109));
		Assert.assertEquals("/home/admin/output/crawl/78900/109/78900109",
				logic.getAbsolutePath(base, 78900109));
	}

}
