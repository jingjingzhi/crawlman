package webcrawl.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class DigestUtil {

	public static final String DEFAULT_CHARSET = "UTF-8";

	// 用来将字节转换成 16 进制表示的字符
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static String DEFAULT_HASH = "SHA-256";

	public static String digest(byte[] source) {
		try {
			MessageDigest messageDigest = MessageDigest
					.getInstance(DEFAULT_HASH);
			messageDigest.update(source);
			byte[] digest = messageDigest.digest();
			int j = digest.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = digest[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String digest(String str) {
		return digest(str, DEFAULT_CHARSET);
	}

	public static String digest(String str, String charset) {
		try {
			return digest(str.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

}
