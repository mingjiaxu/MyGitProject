package Sever;

import BaseFunc.GetHash;
import BaseFunc.Params2List;
import BaseFunc.SPK;
import JavaBean.KnowledgeCommitment;
import JavaBean.PublicParams;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @projectName: MyProject
 * @package: Sever
 * @className: VerifyKnowledgeSignature
 * @author: xjm
 * @description: TODO
 * @date: 5/13/2024 11:47 AM
 * @version: 1.0
 */
public class VerifyKnowledgeSignature {
    public static boolean verify(BigInteger c, KnowledgeCommitment kc,ArrayList<ArrayList<BigInteger>> pubParamList, ArrayList<ArrayList<BigInteger>> sList){
        BigInteger sum = BigInteger.ZERO;
        {
            BigInteger temp = PublicParams.a.modPow(c,PublicParams.n);
            ArrayList<BigInteger> baseNumList = pubParamList.get(0);
            ArrayList<BigInteger> exponentList = sList.get(0);
            temp.multiply(SPK.caculateT_P2(baseNumList,exponentList,PublicParams.n));
            sum=sum.add(temp);
        }
        {
            BigInteger temp = kc.getT().modPow(c, PublicParams.n);
            ArrayList<BigInteger> baseNumList = pubParamList.get(1);
            ArrayList<BigInteger> exponentList = sList.get(1);
            temp.multiply(SPK.caculateT_P1(baseNumList, exponentList, PublicParams.sigma));
            sum=sum.add(temp);
        }
        {
            BigInteger temp = (PublicParams.b1.multiply(kc.getSN().modPow(kc.getIc(),PublicParams.sigma).modInverse(PublicParams.sigma))).modPow(c, PublicParams.sigma);
            ArrayList<BigInteger> baseNumList = pubParamList.get(2);
            ArrayList<BigInteger> exponentList = sList.get(2);
            temp.multiply(SPK.caculateT_P2(baseNumList, exponentList, PublicParams.sigma));
            sum=sum.add(temp);
        }
        {
            BigInteger temp = (PublicParams.b2.multiply(kc.getSN().modPow(kc.getIc(),PublicParams.sigma).modInverse(PublicParams.sigma))).modPow(c, PublicParams.sigma);
            ArrayList<BigInteger> baseNumList = pubParamList.get(3);
            ArrayList<BigInteger> exponentList = sList.get(3);
            temp.multiply(SPK.caculateT_P2(baseNumList, exponentList, PublicParams.sigma));
            sum=sum.add(temp);
        }
        {
            BigInteger temp = PublicParams.a.modPow(c,PublicParams.n);
            ArrayList<BigInteger> baseNumList = pubParamList.get(4);
            ArrayList<BigInteger> exponentList = sList.get(4);
            temp.multiply(SPK.caculateT_P2(baseNumList,exponentList,PublicParams.n));
            sum=sum.add(temp);
        }
        {
            BigInteger temp = BigInteger.ONE.modPow(c,PublicParams.sigma);
            ArrayList<BigInteger> baseNumList = pubParamList.get(5);
            ArrayList<BigInteger> exponentList = sList.get(5);
            temp.multiply(SPK.caculateT_P2(baseNumList,exponentList,PublicParams.sigma));
            sum=sum.add(temp);
        }
        {
            BigInteger temp = kc.getD().multiply((PublicParams.a.multiply(PublicParams.ai.modPow(kc.getIc(),PublicParams.n))).modInverse(PublicParams.n)).modPow(c,PublicParams.n);
            ArrayList<BigInteger> baseNumList = pubParamList.get(6);
            ArrayList<BigInteger> exponentList = sList.get(6);
            temp.multiply(SPK.caculateT_P2(baseNumList,exponentList,PublicParams.n));
            sum=sum.add(temp);
        }
        try {
            BigInteger c1 = GetHash.hash(sum);
            if(c1.equals(c)){
                System.out.println("第二次签名验证通过");
            }
            else{
                System.out.println("第二次签名失败");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
