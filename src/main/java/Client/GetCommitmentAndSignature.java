package Client;

import BaseFunc.GetHash;
import BaseFunc.GetRndBigintger;
import BaseFunc.PublicParams2List;
import BaseFunc.SecretParamsBean2List;
import JavaBean.CommitmentAndSignature;
import JavaBean.PublicParams;
import JavaBean.SecretParams;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @projectName: MyProject
 * @package: Client
 * @className: GetCommitAndSignature
 * @author: xjm
 * @description: TODO
 * @date: 5/10/2024 9:25 PM
 * @version: 1.0
 */
public class GetCommitmentAndSignature {
    public static CommitmentAndSignature generate(SecretParams secretParams){
        CommitmentAndSignature cas = new CommitmentAndSignature();

        BigInteger j1 = PublicParams.b.modPow(secretParams.getT1(),PublicParams.sigma);
        cas.setJ1(j1);
        BigInteger j2 = PublicParams.a.mod(PublicParams.n);
        j2 = j2.multiply(PublicParams.ax.modPow(secretParams.getX(),PublicParams.n));
        j2 = j2.multiply(PublicParams.as.modPow(secretParams.getS(),PublicParams.n));
        j2 = j2.multiply(PublicParams.at.modPow(secretParams.getT1(),PublicParams.n));
        j2 = j2.multiply(PublicParams.ai.modPow(secretParams.getI(),PublicParams.n));
        j2 = j2.multiply(PublicParams.ae.modPow(secretParams.getE(),PublicParams.n));
        j2 = j2.multiply(PublicParams.ad.modPow(secretParams.getD(),PublicParams.n));
        j2 = j2.multiply(PublicParams.h.modPow(secretParams.getW(),PublicParams.n));
        j2 = j2.mod(PublicParams.n);
        cas.setJ2(j2);

        ArrayList<BigInteger> SPK1 = new ArrayList<BigInteger>();
        ArrayList<BigInteger> secretParamsList = SecretParamsBean2List.convert(secretParams);
        ArrayList<BigInteger> publicParamsList = PublicParams2List.convert();
        ArrayList<BigInteger> R = new ArrayList<BigInteger>();
        ArrayList<BigInteger> T = new ArrayList<BigInteger>();
        //生成SPK中的随机数
        for (int i = 0; i < 5; i++) {
            BigInteger r = GetRndBigintger.generate(100);
            R.add(r);
        }
        for (int i = 0; i < 3; i++) {
            BigInteger r = GetRndBigintger.generate(10);
            R.add(r);
        }
        R.add(GetRndBigintger.generate(100));


        T.add(PublicParams.b.modPow(R.get(0),PublicParams.sigma));  //b^r1 mod sigma
        T.add(PublicParams.b.modPow(R.get(1),PublicParams.sigma));  //b^r2 mod sigma
        //
        BigInteger tempT = BigInteger.ONE;
        for (int i = 2; i < 9; i++) {
            tempT = tempT.multiply(publicParamsList.get(i).modPow(R.get(i),PublicParams.n));
        }
        tempT = tempT.mod(PublicParams.n);
        T.add(tempT);

        //合并T
        BigInteger temp = BigInteger.ZERO;
        for (int i = 0; i < T.size() ; i++) {
            temp = temp.add(T.get(i));
        }
        //获取整体摘要
        try {
            SPK1.add(GetHash.hash(temp));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //往SPK1中添加S参数
        SPK1.add(R.get(0).subtract(SPK1.get(0).multiply(secretParams.getU())));
        SPK1.add(R.get(1).subtract(SPK1.get(0).multiply(secretParams.getT1())));
        for (int i = 2; i <9 ; i++) {
            SPK1.add(R.get(i).subtract(SPK1.get(0).multiply(secretParamsList.get(i-1))));
        }
        cas.setSPK1(SPK1);
        return cas;
    }
}
