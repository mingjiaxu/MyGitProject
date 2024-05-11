package Sever;

import BaseFunc.GetHash;
import BaseFunc.PublicParams2List;
import JavaBean.CommitAndSignature;
import JavaBean.PublicParams;
import JavaBean.SecretParams;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @projectName: MyProject
 * @package: Sever
 * @className: SignatureVerify
 * @author: xjm
 * @description: TODO
 * @date: 5/10/2024 9:41 PM
 * @version: 1.0
 */
public class SignatureVerify {
    /**
     * @param Pku: 用户公钥
     * @param commitAndSignature: 知识承诺j1 j2 及 知识签名SPK1
     * @return boolean
     * @author xjm
     * @description 根据以上参数，验证用户签名的有效性
     * @date 5/10/2024 9:51 PM
     */
    public static boolean verify(BigInteger Pku,CommitAndSignature commitAndSignature) throws NoSuchAlgorithmException {
        BigInteger j1 = commitAndSignature.getJ1();
        BigInteger j2 = commitAndSignature.getJ2();
        ArrayList<BigInteger> SPK1 = commitAndSignature.getSPK1();
        ArrayList<BigInteger> publicParamsList = PublicParams2List.convert();
        BigInteger temp1 = (Pku.modPow(SPK1.get(0), PublicParams.sigma).multiply(PublicParams.b.modPow(SPK1.get(1),PublicParams.sigma))).mod(PublicParams.sigma);
        BigInteger temp2 = (j1.modPow(SPK1.get(0),PublicParams.sigma).multiply(PublicParams.b.modPow(SPK1.get(2),PublicParams.sigma))).mod(PublicParams.sigma);
        BigInteger temp3 = (j2.multiply(PublicParams.a.modInverse(PublicParams.n))).modPow(SPK1.get(0),PublicParams.n
        );
        for (int i = 2; i < 9; i++) {
            temp3 = temp3.multiply(publicParamsList.get(i).modPow(SPK1.get(i+1),PublicParams.n));
        }
        temp3 = temp3.mod(PublicParams.n);
        BigInteger temp4 = temp1.add(temp2).add(temp3);
        BigInteger tempC = GetHash.hash(temp4);
        return tempC.equals(SPK1.get(0))? true:false;
    }
}
