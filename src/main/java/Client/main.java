package Client;

import BaseFunc.GetHash;
import BaseFunc.GetRndBigintger;
import BaseFunc.PublicParams2List;
import BaseFunc.SecretParamsBean2List;
import JavaBean.CommitAndSignature;
import JavaBean.PublicParams;
import JavaBean.SecretParams;
import Sever.SignatureVerify;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @projectName: MyProject
 * @package: Agent
 * @className: main
 * @author: xjm
 * @description: TODO
 * @date: 5/9/2024 10:20 PM
 * @version: 1.0
 */
public class main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        SecretParams secretParams = GetSecretParams.generate();
        CommitAndSignature commitAndSignature = GetCommitAndSignature.generate(secretParams);
        System.out.println(SignatureVerify.verify(secretParams.getPku(),commitAndSignature));

        /**
        //计算知识承诺 J1,J2
        BigInteger j1 = PublicParams.b.modPow(secretParams.getT1(),PublicParams.sigma);
        BigInteger j2 = PublicParams.a.mod(PublicParams.n);
        j2 = j2.multiply(PublicParams.ax.modPow(secretParams.getX(),PublicParams.n));
        j2 = j2.multiply(PublicParams.as.modPow(secretParams.getS(),PublicParams.n));
        j2 = j2.multiply(PublicParams.at.modPow(secretParams.getT1(),PublicParams.n));
        j2 = j2.multiply(PublicParams.ai.modPow(secretParams.getI(),PublicParams.n));
        j2 = j2.multiply(PublicParams.ae.modPow(secretParams.getE(),PublicParams.n));
        j2 = j2.multiply(PublicParams.ad.modPow(secretParams.getD(),PublicParams.n));
        j2 = j2.multiply(PublicParams.h.modPow(secretParams.getW(),PublicParams.n));
        j2 = j2.mod(PublicParams.n);
        System.out.println("j1:"+j1);
        System.out.println("j2:"+j2);
        //计算知识签名
        ArrayList<BigInteger> SPK1 = new ArrayList<BigInteger>();
        ArrayList<BigInteger> secretParamsList = SecretParamsBean2List.convert(secretParams);
        ArrayList<BigInteger> publicParamsList = PublicParams2List.convert();
        ArrayList<BigInteger> R = new ArrayList<BigInteger>();
        ArrayList<BigInteger> T = new ArrayList<BigInteger>();
        //生成SPK中的随机数
        for (int i = 0; i < 9; i++) {
            BigInteger r = GetRndBigintger.generate(100);
            R.add(r);
        }

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

        BigInteger temp1 = (secretParams.getPku().modPow(SPK1.get(0),PublicParams.sigma).multiply(PublicParams.b.modPow(SPK1.get(1),PublicParams.sigma))).mod(PublicParams.sigma);
        BigInteger temp2 = (j1.modPow(SPK1.get(0),PublicParams.sigma).multiply(PublicParams.b.modPow(SPK1.get(2),PublicParams.sigma))).mod(PublicParams.sigma);
        BigInteger temp3 = (j2.multiply(PublicParams.a.modInverse(PublicParams.n))).modPow(SPK1.get(0),PublicParams.n
        );
        for (int i = 2; i < 9; i++) {
            temp3 = temp3.multiply(publicParamsList.get(i).modPow(SPK1.get(i+1),PublicParams.n));
        }
        temp3 = temp3.mod(PublicParams.n);

        BigInteger temp4 = temp1.add(temp2).add(temp3);
        try {
            BigInteger tempC = GetHash.hash(temp4);
            System.out.println(tempC.equals(SPK1.get(0)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
         **/

        /**
        BigInteger ru = GetRndBigintger.generate(100);
        BigInteger Tu = PublicParams.b.modPow(ru,PublicParams.sigma);
        System.out.println("Tu："+Tu);
        try {
            BigInteger c = GetHash.hash(Tu);
            BigInteger s1 = ru.subtract(c.multiply(secretParams.getU()));
            BigInteger temp = GetHash.hash(secretParams.getPku().modPow(c,PublicParams.sigma).multiply(PublicParams.b.modPow(s1,PublicParams.sigma)).mod(PublicParams.sigma));
            System.out.println("c:"+c);
            System.out.println("temp:"+temp);
            System.out.println(temp.equals(c));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        **/
    }

}
