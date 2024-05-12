import BaseFunc.GetRndBigintger;
import BaseFunc.PublicParams2List;
import BaseFunc.SecretParamsBean2List;
import Client.CLCheck;
import Client.GetCommitAndSignature;
import Client.GetSecretParams;
import JavaBean.CommitAndSignature;
import JavaBean.PublicParams;
import JavaBean.SecretParams;
import JavaBean.SignatureS2CParams;
import Sever.CLAlgorithm;
import Sever.GetSignatureS2CParams;
import Sever.SignatureVerify;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
        SecretParams secretParams = GetSecretParams.generate();
        CommitAndSignature cas = GetCommitAndSignature.generate(secretParams);
        //服务器
        //用户传递Pku j1 j2 SPK1 给服务器验证签名
        System.out.println(SignatureVerify.verify(secretParams.getPku(),cas));
        //服务器对用户秘密参数签名，返回参数给用户
        SignatureS2CParams signatureS2CParams = GetSignatureS2CParams.generate(cas);
        //客户
        //用户验证签名
            //更新t1
        secretParams.setT1(secretParams.getT1().add(signatureS2CParams.getT2()));
        System.out.println(CLCheck.check(secretParams,signatureS2CParams));
        //
        Random rnd = new Random();
        BigInteger w1 = new BigInteger(100,rnd);
        BigInteger r = new BigInteger(100,rnd);
        BigInteger K = new BigInteger("1021");
        BigInteger A1 = signatureS2CParams.getA().multiply(PublicParams.h.modPow(w1,PublicParams.n)).mod(PublicParams.n);
        BigInteger T = PublicParams.b1.modPow(secretParams.getT1(),PublicParams.sigma).multiply(PublicParams.b2.modPow(r,PublicParams.sigma)).mod(PublicParams.sigma);
        BigInteger ic = secretParams.getI().add(secretParams.getE().add(secretParams.getD()).modInverse(K)).mod(K);
        BigInteger SN = PublicParams.b1.modPow(ic.add(secretParams.getS().modInverse(K)),PublicParams.sigma).multiply(PublicParams.b2.modPow(ic.add(secretParams.getT1()).modInverse(K),PublicParams.sigma)).mod(PublicParams.sigma);
        BigInteger D = PublicParams.a;
        ArrayList<BigInteger>  ppbl= PublicParams2List.convert();
        ArrayList<BigInteger> spbl = SecretParamsBean2List.convert(secretParams);
        for (int i = 2; i < 8; i++) {
            D = D.multiply(ppbl.get(i).modPow(spbl.get(i-1),PublicParams.n));
        }
        D.multiply(PublicParams.h.modPow(secretParams.getW().add(signatureS2CParams.getY().multiply(w1)),PublicParams.n)).mod(PublicParams.n);

        BigInteger alpha = (ic.add(secretParams.getT1()).modInverse(K)).multiply(ic.add(secretParams.getS()));
        BigInteger beta = (ic.add(secretParams.getS()).modInverse(K)).multiply(ic.add(secretParams.getT1()));
        BigInteger  m = ic.multiply(secretParams.getE()).subtract(secretParams.getI().multiply(secretParams.getE()));
        //生成同等长度随机参数，用于隐藏私有参数

        ArrayList<BigInteger> arrayLists = new ArrayList<BigInteger>();
        ArrayList<BigInteger> arrayListr = new ArrayList<BigInteger>();
        ArrayList<BigInteger> arrayListT = new ArrayList<BigInteger>();
        for (int i = 0; i < 6; i++) {
            arrayListr.add(GetRndBigintger.generate(100));
        }
        for (int i = 0; i < 3; i++) {
            arrayListr.add(i+4,GetRndBigintger.generate(10));
        }
        BigInteger T1  = A1.modPow(arrayListr.get(0),PublicParams.n);
        BigInteger temp = ppbl.get(2).modPow(arrayListr.get(1),PublicParams.n);
        for (int i = 3; i < 10; i++) {
            temp = temp.multiply(ppbl.get(i).modPow(arrayListr.get(i-1),PublicParams.n));
        }
        temp = temp.mod(PublicParams.n);
        temp = temp.modInverse(PublicParams.n);
        T1 = T1.multiply(temp);
        arrayListT.add(T1);

        //计算s

        arrayListr.clear();
        arrayListr.add(GetRndBigintger.generate(100));
        arrayListr.add(GetRndBigintger.generate(100));
        BigInteger T2 = PublicParams.b1.modPow(arrayListr.get(0),PublicParams.sigma);
        T2 = T2.multiply(PublicParams.b2.modPow(arrayListr.get(1),PublicParams.sigma)).mod(PublicParams.sigma);
        arrayListT.add(T2);
        //计算s

        arrayListr.clear();
        arrayListr.add(GetRndBigintger.generate(100));
        arrayListr.add(GetRndBigintger.generate(alpha.bitLength()));
        BigInteger T3 = SN.modPow(arrayListr.get(0),PublicParams.sigma);
        T3 = T3.multiply(PublicParams.b2.modPow(arrayListr.get(1),PublicParams.sigma).modInverse(PublicParams.sigma));
        arrayListT.add(T3);
        //计算s


        arrayListr.clear();
        arrayListr.add(GetRndBigintger.generate(100));
        arrayListr.add(GetRndBigintger.generate(beta.bitLength()));
        BigInteger T4 = SN.modPow(arrayListr.get(0),PublicParams.sigma);
        T4 = T4.multiply(PublicParams.b1.modPow(arrayListr.get(1),PublicParams.sigma).modInverse(PublicParams.sigma));
        arrayListT.add(T4);
        //计算s
        arrayListr.clear();
        arrayListr.add(GetRndBigintger.generate(10));
        arrayListr.add(GetRndBigintger.generate(10));
        arrayListr.add(GetRndBigintger.generate(m.bitLength()));

        BigInteger T5 = PublicParams.a.modPow(ic,PublicParams.n).modPow(arrayListr.get(0),PublicParams.n);
        BigInteger temp1 = PublicParams.a.modPow(arrayListr.get(1),PublicParams.n).modPow(arrayListr.get(1),PublicParams.n);
        temp1 = temp1.parallelMultiply(PublicParams.a.modPow(K,PublicParams.n).modPow(arrayListr.get(2),PublicParams.n)).modInverse(PublicParams.n);
        T5  = T5.parallelMultiply(temp1);
        arrayListT.add(T5);
        //计算s

        arrayListr.clear();
        arrayListr.add(GetRndBigintger.generate(secretParams.getE().bitLength()));
        secretParams.setE(secretParams.getE().add(secretParams.getD()));
        arrayListr.add(GetRndBigintger.generate(secretParams.getE().bitLength()));
        arrayListr.add(GetRndBigintger.generate(10));

        BigInteger T6 = PublicParams.b.modPow(arrayListr.get(1),PublicParams.sigma);
        BigInteger temp2 = PublicParams.b.modPow(arrayListr.get(0),PublicParams.sigma);
        temp2 = temp2.parallelMultiply(PublicParams.b.modPow(arrayListr.get(2),PublicParams.sigma));
        temp2 = temp2.modInverse(PublicParams.sigma);
        T6 = T6.parallelMultiply(temp2);
        arrayListT.add(T6);
        //计算s
        arrayListr.clear();
        for (int i = 0; i < 5; i++) {
            arrayListr.add(GetRndBigintger.generate(100));
        }
        for (int i = 0; i < 3; i++) {
            arrayListr.add(i+3,GetRndBigintger.generate(10));
        }
        BigInteger T7  = ppbl.get(2).modPow(arrayListr.get(0),PublicParams.n);
        for (int i = 3; i < 10; i++) {
            T7 =  T7.multiply(ppbl.get(i).modPow(arrayListr.get(i-2),PublicParams.n));
        }
        T7 = T7.mod(PublicParams.n);
        arrayListT.add(T7);



    }


}
