package Sever;

import JavaBean.CommitAndSignature;
import JavaBean.SignatureS2CParams;

import java.math.BigInteger;
import java.util.Random;

/**
 * @projectName: MyProject
 * @package: Sever
 * @className: GetSignatureS2CParams
 * @author: xjm
 * @description: TODO
 * @date: 5/10/2024 9:58 PM
 * @version: 1.0
 */
public class GetSignatureS2CParams {
    /**
     * @param cas: 知识承诺及签名
     * @return SignatureS2CParams
     * @author xjm
     * @description 对用户秘密参数签名，返回签名及用于签名的参数
     * @date 5/10/2024 10:05 PM
     */
    public static SignatureS2CParams generate(CommitAndSignature cas){
        SignatureS2CParams signatureS2CParams = new SignatureS2CParams();
        Random rnd = new Random();
        BigInteger y = BigInteger.probablePrime(100,rnd);
        BigInteger t2 = BigInteger.probablePrime(100,rnd);
        BigInteger A = CLAlgorithm.signature(cas.getJ2(),t2,y);
        signatureS2CParams.setA(A);
        signatureS2CParams.setY(y);
        signatureS2CParams.setT2(t2);
        return signatureS2CParams;
    }
}
