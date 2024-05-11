package Sever;

import JavaBean.PublicParams;

import java.math.BigInteger;

/**
 * @projectName: MyProject
 * @package: Sever
 * @className: CLAlgorithm
 * @author: xjm
 * @description: 服务器端对用户秘密参数进行签名
 * @date: 5/10/2024 3:41 PM
 * @version: 1.0
 */
public class CLAlgorithm {
    /**
     * @param j2: 用户知识承诺J2
     * @param t2: 100bit随机素数
     * @param y: 100bit随机素数
     * @return BigInteger
     * @author xjm
     * @description 使用CL签名算法对 用户秘密参数签名
     * @date 5/10/2024 10:01 PM
     */
    public static BigInteger signature(BigInteger j2,BigInteger t2,BigInteger y){
        return (j2.multiply(PublicParams.at.modPow(t2,PublicParams.n))).modPow(y.modInverse((PublicParams.p.subtract(BigInteger.ONE)).multiply(PublicParams.q.subtract(BigInteger.ONE))),PublicParams.n);
    }
}
