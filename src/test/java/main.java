import BaseFunc.*;
import Client.*;
import JavaBean.*;
import Sever.GetSignatureS2CParams;
import Sever.SignatureVerify;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.util.Random;

/**
 * @projectName: MyProject
 * @package: PACKAGE_NAME
 * @className: main
 * @author: xjm
 * @description: TODO
 * @date: 5/10/2024 4:18 PM
 * @version: 1.0
 */
public class main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        //第一部分
        SecretParams secretParams = GetSecretParams.generate();
        CommitmentAndSignature cas = GetCommitmentAndSignature.generate(secretParams);
        //服务器
        //用户传递Pku j1 j2 SPK1 给服务器验证签名

        System.out.println("SPK1签名验证"+SignatureVerify.verify(secretParams.getPku(),cas));

        //第二部分
        //服务器对用户秘密参数签名，返回参数给用户
        SignatureS2CParams signatureS2CParams = GetSignatureS2CParams.generate(cas);
        //客户
        //用户验证签名
            //更新t1
        secretParams.setT1(secretParams.getT1().add(signatureS2CParams.getT2()));
        System.out.println("CL签名验证"+CLCheck.check(secretParams,signatureS2CParams));
        //生成匿名证书
        AnonymousCertificate ac = GetAnonymousCertificate.generate(secretParams, signatureS2CParams);


        //第三部分
        //生成100bit随机参数 r 和 w*
        GetAnonymousCertificate.generate(ac);
        //生成第一部分知识承诺
        KnowledgeCommitment kc = GetKnowledgeCommitment.generatePart1(ac, ac.getW1(), ac.getR());
        //计算 alpha beta m ec
        GetAnonymousCertificate.generate(kc,ac);
        //计算SPK2
        SPK2Bean spk2Bean = SPK.caculateSPK2(ac,kc);
        //验证SPK2
        System.out.println("SPK2验证"+SPK.verifySPK2(kc, spk2Bean));

        //第四部分 计算 R1 R2
        GetKnowledgeCommitment.generatePart2(kc,ac,spk2Bean.getC());
        BigInteger temp = kc.getT().modPow(spk2Bean.getC(),PublicParams.sigma).parallelMultiply(PublicParams.b1.modPow(kc.getR1(),PublicParams.sigma)).parallelMultiply(PublicParams.b2.modPow(kc.getR2(),PublicParams.sigma)).mod(PublicParams.sigma);

        System.out.println("SN验证:"+temp.equals(kc.getSN()));

        //生成 y'随机素数
        BigInteger y1 = BigInteger.probablePrime(100,new Random());
        BigInteger AD = kc.getD().modPow(y1.modInverse((PublicParams.p.subtract(BigInteger.ONE)).multiply(PublicParams.q.subtract(BigInteger.ONE))),PublicParams.n).mod(PublicParams.n);//
        System.out.println("最后的验证:"+AD.modPow(y1,PublicParams.n).equals(kc.getD()));
        //更新 (A,y) (AD,y')
        ac.setY(y1);
        ac.setA(AD);
        ac.setI(kc.getIc());
        ac.setE(ac.getEc());
        ac.setW(ac.getW1());
        //
        
    }
}
