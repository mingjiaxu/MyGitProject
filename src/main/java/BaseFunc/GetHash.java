package BaseFunc;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @projectName: MyProject
 * @package: BaseFunc
 * @className: GetHash
 * @author: xjm
 * @description: TODO
 * @date: 5/9/2024 11:28 PM
 * @version: 1.0
 */
public class GetHash {
    /**
     * @param T:挑战
     * @return BigInteger
     * @author xjm
     * @description 对一个BigInteger 进行hash运算，最后返回Biginteger
     * @date 5/10/2024 8:17 AM
     */
    public static BigInteger hash(BigInteger T) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] message = T.toByteArray();
        md.update(message);
        byte[] digest = md.digest();
        return new BigInteger(digest);
    }

}
