package Client;

import JavaBean.PublicParams;
import JavaBean.SecretParams;

import java.math.BigInteger;
import java.util.Random;

/**
 * @projectName: MyProject
 * @package: Agent
 * @className: GetSecretParams
 * @author: xjm
 * @description:
 * @date: 5/9/2024 10:24 PM
 * @version: 1.0
 */
public class GetSecretParams {
    /**
     * @param numBits:数字bit长度，方案中取100bits
     * @param rnd: 随机源
     * @return SecretParams
     * @author xjm
     * @description 生成用户初始秘密参数
     * @date 5/9/2024 10:35 PM
     */
    public static SecretParams generate(int numBits,Random rnd){
        SecretParams secretParams = new SecretParams();
        secretParams.setU(new BigInteger(numBits,rnd));
        secretParams.setX(new BigInteger(numBits,rnd));
        secretParams.setS(new BigInteger(numBits,rnd));
        secretParams.setT1(new BigInteger(numBits,rnd));
        secretParams.setI(new BigInteger(numBits,rnd));
        secretParams.setE(new BigInteger(numBits,rnd));
        secretParams.setD(new BigInteger(numBits,rnd));
        secretParams.setW(new BigInteger(numBits,rnd));
        secretParams.setPku(PublicParams.b.modPow(secretParams.getU(),PublicParams.sigma)); //计算公钥PKu =b^u mod sigma
        return  secretParams;
    }
    /**
     * @param :无
     * @return SecretParams
     * @author xjm
     * @description 生成用户初始秘密参数
     * @date 5/10/2024 7:09 PM
     */
    public static SecretParams generate(){
        SecretParams secretParams = new SecretParams();
        Random rnd = new Random();
        secretParams.setU(new BigInteger(100,rnd));
        secretParams.setX(new BigInteger(100,rnd));
        secretParams.setS(new BigInteger(100,rnd));
        secretParams.setT1(new BigInteger(100,rnd));
        secretParams.setI(new BigInteger(10,rnd));
        secretParams.setE(new BigInteger(10,rnd));
        secretParams.setD(new BigInteger(10,rnd));
        secretParams.setW(new BigInteger(100,rnd));
        secretParams.setPku(PublicParams.b.modPow(secretParams.getU(),PublicParams.sigma)); //计算公钥PKu =b^u mod sigma
        return  secretParams;
    }

}
