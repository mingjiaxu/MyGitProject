package BaseFunc;

import java.math.BigInteger;
import java.util.Random;

/**
 * @projectName: MyProject
 * @package: BaseFunc
 * @className: GetStrongPrime
 * @author: xjm
 * @description: 获取强素数
 * @date: 5/9/2024 10:46 PM
 * @version: 1.0
 */
public class GetStrongPrime {
    /**
     * @param bitLength:强素数的长度
     * @return BigInteger
     * @author xjm
     * @description 生成指定长度的强素数
     * @date 5/9/2024 10:49 PM
     */

    public static BigInteger generate(int bitLength){
        Random random = new Random();
        while(true) {
            BigInteger bigInteger = BigInteger.probablePrime(bitLength - 1, random).multiply(new BigInteger("2")).add(new BigInteger("1"));
            if (bigInteger.isProbablePrime(256)) {
                return bigInteger;
            }
        }
    }
}
