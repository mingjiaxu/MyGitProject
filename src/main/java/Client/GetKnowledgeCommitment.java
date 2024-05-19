package Client;

import BaseFunc.Params2List;
import JavaBean.AnonymousCertificate;
import JavaBean.KnowledgeCommitment;
import JavaBean.PublicParams;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @projectName: MyProject
 * @package: Client
 * @className: GetKnowledgeCommit
 * @author: xjm
 * @description: 用于生成知识承诺
 * @date: 5/12/2024 11:40 PM
 * @version: 1.0
 */
public class GetKnowledgeCommitment {
    /**
     * @param baseNum: 底数
     * @param exponent: 指数
     * @param m: 模数
     * @return BigInteger
     * @author xjm
     * @description 计算知识承诺J1
     * @date 5/12/2024 11:42 PM
     */
    public static BigInteger generate(BigInteger baseNum,BigInteger exponent,BigInteger m){
        return baseNum.modPow(exponent,m);
    }
    /**
     * @param baseNumList: 底数列表
     * @param exponentList: 指数列表
     * @param m: 模数
     * @return BigInteger
     * @author xjm
     * @description 计算知识承诺J2
     * @date 5/12/2024 11:47 PM
     */
    public static BigInteger generate(ArrayList<BigInteger> baseNumList, ArrayList<BigInteger> exponentList, BigInteger m){
        //异常处理部分------之后补

        //计算部分
        BigInteger J = baseNumList.getFirst();
        for (int i = 0; i < exponentList.size(); i++) {
            J = J.parallelMultiply(baseNumList.get(i+1).modPow(exponentList.get(i),m));
        }
        return  J.mod(m);
    }
/**
 * @param ac: 匿名证书
 * @param w1: 100bit随机数
 * @param r: 100bit随机数
 * @return ArrayList<BigInteger>
 * @author xjm
 * @description 计算知识承诺 (A',T,ic，SN,D)
 * @date 5/13/2024 9:07 AM
 */
    public static KnowledgeCommitment generatePart1(AnonymousCertificate ac,BigInteger w1,BigInteger r){
        KnowledgeCommitment kc = new KnowledgeCommitment();
        BigInteger A1 = ac.getA().parallelMultiply(PublicParams.h.modPow(w1,PublicParams.n));
        BigInteger T = PublicParams.b1.modPow(ac.getT1(),PublicParams.sigma).multiply(PublicParams.b2.modPow(r,PublicParams.sigma)).mod(PublicParams.sigma);
        BigInteger ic = ac.getI().add(ac.getE().modInverse(PublicParams.k)).mod(PublicParams.k);
        BigInteger SN = (PublicParams.b1.modPow((ic.add(ac.getS())).modInverse(PublicParams.q1),PublicParams.sigma)).multiply(PublicParams.b2.modPow((ic.add(ac.getT1())).modInverse(PublicParams.q1),PublicParams.sigma)).mod(PublicParams.sigma);
        BigInteger w2 = ac.getW().add(ac.getY().multiply(w1));
        ArrayList<BigInteger> baseNumList = Params2List.convert(PublicParams.a,PublicParams.ax, PublicParams.as,PublicParams.at,PublicParams.ai,PublicParams.ae,PublicParams.ad,PublicParams.h);
        ArrayList<BigInteger> exponentList = Params2List.convert(ac.getX(),ac.getS(),ac.getT1(),ic,ac.getE().add(ac.getD()),ac.getD(),w2);
        BigInteger D = generate(baseNumList,exponentList,PublicParams.n);
        kc.setA1(A1);
        kc.setT(T);
        kc.setIc(ic);
        kc.setSN(SN);
        kc.setD(D);
        return kc;
    }
    /**
     * @param kc: 知识承诺
     * @param ac: 匿名证书
     * @param c: 摘要
     * @return KnowledgeCommitment
     * @author xjm
     * @description 计算知识承诺 R1，R2
     * @date 5/13/2024 11:37 AM
     */
    public static void generatePart2(KnowledgeCommitment kc,AnonymousCertificate ac,BigInteger c){
        kc.setR1(((kc.getIc().add(ac.getS())).modInverse(PublicParams.q1)).subtract(c.multiply(ac.getT1())));
        kc.setR2(((kc.getIc().add(ac.getT1())).modInverse(PublicParams.q1)).subtract(c.multiply(ac.getR())));   //(ic)
    }

}
