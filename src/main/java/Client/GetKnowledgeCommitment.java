package Client;

import BaseFunc.Params2List;
import JavaBean.AnonymousCertificate;
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
        for (int i = 0; i < baseNumList.size(); i++) {
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
    public static ArrayList<BigInteger> generate(AnonymousCertificate ac,BigInteger w1,BigInteger r){
        ArrayList<BigInteger> list = new ArrayList<BigInteger>();
        BigInteger A1 = ac.getA().parallelMultiply(PublicParams.h.modPow(w1,PublicParams.n));
        BigInteger T = PublicParams.b1.modPow(ac.getT1(),PublicParams.sigma).multiply(PublicParams.b2.modPow(r,PublicParams.sigma)).mod(PublicParams.sigma);
        BigInteger ic = ac.getI().add((ac.getE().add(ac.getD())).modInverse(PublicParams.k));
        BigInteger SN = (PublicParams.b1.modPow((ic.add(ac.getS())).modInverse(PublicParams.k),PublicParams.sigma)).multiply(PublicParams.b2.modPow((ic.add(ac.getT1())).modInverse(PublicParams.k),PublicParams.sigma)).mod(PublicParams.sigma);
        BigInteger w2 = ac.getW().add(ac.getY().multiply(w1));
        ArrayList<BigInteger> baseNumList = Params2List.convert(PublicParams.a,PublicParams.ax, PublicParams.as,PublicParams.at,PublicParams.ai,PublicParams.ae,PublicParams.ad,PublicParams.h);
        ArrayList<BigInteger> exponentList = Params2List.convert(ac.getX(),ac.getS(),ac.getT1(),ac.getI(),ac.getE(),ac.getD(),w2);
        BigInteger D = generate(baseNumList,exponentList,PublicParams.n);
        return list;
    }

}
