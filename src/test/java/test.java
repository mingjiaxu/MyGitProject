import BaseFunc.GetHash;
import BaseFunc.GetRndBigintger;
import BaseFunc.Params2List;
import BaseFunc.SPK;
import Client.*;
import JavaBean.*;
import Sever.GetSignatureS2CParams;
import Sever.SignatureVerify;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @projectName: MyProject
 * @package: PACKAGE_NAME
 * @className: test
 * @author: xjm
 * @description: TODO
 * @date: 5/12/2024 9:09 PM
 * @version: 1.0
 */
public class test {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        SecretParams secretParams = GetSecretParams.generate();
        CommitmentAndSignature cas = GetCommitmentAndSignature.generate(secretParams);
        //服务器
        //用户传递Pku j1 j2 SPK1 给服务器验证签名
        System.out.println(SignatureVerify.verify(secretParams.getPku(), cas));
        //服务器对用户秘密参数签名，返回参数给用户
        SignatureS2CParams signatureS2CParams = GetSignatureS2CParams.generate(cas);
        //客户
        //用户验证签名
        //更新t1
        secretParams.setT1(secretParams.getT1().add(signatureS2CParams.getT2()));
        System.out.println(CLCheck.check(secretParams, signatureS2CParams));
        //生成匿名证书
        AnonymousCertificate ac = GetAnonymousCertificate.generate(secretParams, signatureS2CParams);


        BigInteger w1 = GetRndBigintger.generate(100);
        BigInteger r = GetRndBigintger.generate(100);

        //生成知识承诺
        KnowledgeCommitment kc = GetKnowledgeCommitment.generatePart1(ac, w1, r);
        BigInteger alpha = kc.getIc().add(ac.getS()).multiply((kc.getIc().add(ac.getT1())).modInverse(PublicParams.q1)).mod(PublicParams.q1);
        BigInteger beta = (kc.getIc().add(ac.getT1())).multiply((kc.getIc().add(ac.getS())).modInverse(PublicParams.q1)).mod(PublicParams.q1);
        BigInteger m = kc.getIc().multiply(ac.getE()).subtract(ac.getI().multiply(ac.getE()).subtract(BigInteger.ONE)).divide(PublicParams.k);

        //挑战一测试

        //生成秘密参数列表，公共参数列表
        {
            //输入参数
            ArrayList<BigInteger> secParamList1 = Params2List.convert(ac.getY(), ac.getX(), ac.getS(), ac.getT1(), ac.getI(), ac.getE(), ac.getD(), ac.getW().add(ac.getY().multiply(w1)));
            ArrayList<BigInteger> pubParamList1 = Params2List.convert(kc.getA1(), PublicParams.ax, PublicParams.as, PublicParams.at, PublicParams.ai, PublicParams.ae, PublicParams.ad, PublicParams.h);
            //根据秘密参数列表生成随机数列表
            ArrayList<BigInteger> rList1 = SPK.generateRList(secParamList1);
            //计算对应挑战
            ArrayList<ArrayList<BigInteger>> rList = new ArrayList<>(); //二维的BigInteger 数组
            ArrayList<ArrayList<BigInteger>> secParamList = new ArrayList<>(); //二维的秘密参数数组
            rList.add(rList1);
            secParamList.add(secParamList1);
            BigInteger T = SPK.caculateT_P2(pubParamList1, rList1, PublicParams.n);
            BigInteger c = GetHash.hash(T);
            ArrayList<ArrayList<BigInteger>> SList = SPK.caculateSList(rList, secParamList, c);
            //验证部分
            ArrayList<BigInteger> sList1 = SList.get(0);
            BigInteger T1 = PublicParams.a.modPow(c, PublicParams.n).multiply(SPK.caculateT_P2(pubParamList1, sList1, PublicParams.n));
            BigInteger c1 = GetHash.hash(T1.mod(PublicParams.n));
            System.out.println("1:"+c1.equals(c));
        }
        //挑战二测试
        {
            ArrayList<BigInteger> secParamList2 = Params2List.convert(ac.getT1(), r);
            ArrayList<BigInteger> pubParamList2 = Params2List.convert(PublicParams.b1, PublicParams.b2);

            //根据秘密参数列表生成随机数列表
            ArrayList<BigInteger> rList2 = SPK.generateRList(secParamList2);
            //计算对应挑战
            ArrayList<ArrayList<BigInteger>> rList = new ArrayList<>();
            ArrayList<ArrayList<BigInteger>> secParamList = new ArrayList<>();
            rList.add(rList2);
            secParamList.add(secParamList2);
            BigInteger T = SPK.caculateT_P1(pubParamList2, rList2, PublicParams.sigma);
            BigInteger c = GetHash.hash(T);
            ArrayList<ArrayList<BigInteger>> SList = SPK.caculateSList(rList, secParamList, c);
            ArrayList<BigInteger> sList1 = SList.get(0);
            BigInteger T1 = kc.getT().modPow(c, PublicParams.sigma).multiply(SPK.caculateT_P1(pubParamList2, sList1, PublicParams.sigma));
            BigInteger c1 = GetHash.hash(T1.mod(PublicParams.sigma));
            System.out.println("2:"+c1.equals(c));
        }
        //挑战三测试
        {
            ArrayList<BigInteger> secParamList3 = Params2List.convert(ac.getT1(), beta);
            ArrayList<BigInteger> pubParamList3 = Params2List.convert(kc.getSN(), PublicParams.b1);
            ArrayList<BigInteger> rList3 = SPK.generateRList(secParamList3);
            ArrayList<ArrayList<BigInteger>> rList = new ArrayList<>();
            ArrayList<ArrayList<BigInteger>> secParamList = new ArrayList<>();
            rList.add(rList3);
            secParamList.add(secParamList3);
            BigInteger T = SPK.caculateT_P2(pubParamList3, rList3, PublicParams.sigma);
            BigInteger c = GetHash.hash(T);
            ArrayList<ArrayList<BigInteger>> SList = SPK.caculateSList(rList, secParamList, c);
            ArrayList<BigInteger> sList1 = SList.get(0);
            BigInteger T1 = (PublicParams.b2.multiply((kc.getSN().modPow(kc.getIc(), PublicParams.sigma)).modInverse(PublicParams.sigma))).modPow(c, PublicParams.sigma).multiply(SPK.caculateT_P2(pubParamList3, sList1, PublicParams.sigma));
            BigInteger c1 = GetHash.hash(T1.mod(PublicParams.sigma));
            System.out.println("3:"+c1.equals(c));

        }
        //挑战四测试
        {
            ArrayList<BigInteger> secParamList5 = Params2List.convert(ac.getE(), ac.getE().multiply(ac.getI()), m);
            ArrayList<BigInteger> pubParamList5 = Params2List.convert(PublicParams.a.modPow(kc.getIc(),PublicParams.n), PublicParams.a, PublicParams.a.modPow(PublicParams.k,PublicParams.n));
            ArrayList<BigInteger> rList5 = SPK.generateRList(secParamList5);
            ArrayList<ArrayList<BigInteger>> rList = new ArrayList<>();
            ArrayList<ArrayList<BigInteger>> secParamList = new ArrayList<>();
            rList.add(rList5);
            secParamList.add(secParamList5);
            BigInteger T = SPK.caculateT_P2(pubParamList5, rList5, PublicParams.n);
            BigInteger c = GetHash.hash(T);
            ArrayList<ArrayList<BigInteger>> SList = SPK.caculateSList(rList, secParamList, c);
            ArrayList<BigInteger> sList1 = SList.get(0);
            BigInteger T1 = (PublicParams.a.modPow(c, PublicParams.n)).multiply(SPK.caculateT_P2(pubParamList5, sList1, PublicParams.n));
            BigInteger c1 = GetHash.hash(T1.mod(PublicParams.n));
            System.out.println("4:"+c1.equals(c));
        }
        {
            //挑战五测试
            BigInteger ec = secretParams.getE().add(secretParams.getD());
            ArrayList<BigInteger> secParamList6 = Params2List.convert(ec, secretParams.getE(), secretParams.getD());
            ArrayList<BigInteger> pubParamList6 = Params2List.convert(PublicParams.b, PublicParams.b, PublicParams.b);
            ArrayList<BigInteger> rList6 = SPK.generateRList(secParamList6);
            ArrayList<ArrayList<BigInteger>> rList = new ArrayList<>();
            ArrayList<ArrayList<BigInteger>> secParamList = new ArrayList<>();
            rList.add(rList6);
            secParamList.add(secParamList6);
            BigInteger T = SPK.caculateT_P2(pubParamList6, rList6, PublicParams.sigma);
            BigInteger c = GetHash.hash(T);
            ArrayList<ArrayList<BigInteger>> SList = SPK.caculateSList(rList, secParamList, c);
            ArrayList<BigInteger> sList1 = SList.get(0);
            BigInteger T1 = (BigInteger.ONE.modPow(c, PublicParams.sigma)).multiply(SPK.caculateT_P2(pubParamList6, sList1, PublicParams.sigma));
            BigInteger c1 = GetHash.hash(T1.mod(PublicParams.sigma));
            System.out.println("5:"+c1.equals(c));
        }
        {
            ArrayList<BigInteger> secParamList7 = Params2List.convert(ac.getX(), ac.getS(), ac.getT1(), ac.getE().add(ac.getD()), ac.getD(), ac.getW().add(ac.getY().multiply(w1)));
            ArrayList<BigInteger> pubParamList7 = Params2List.convert(PublicParams.ax, PublicParams.as, PublicParams.at, PublicParams.ae, PublicParams.ad, PublicParams.h);
            ArrayList<BigInteger> rList7 = SPK.generateRList(secParamList7);
            ArrayList<ArrayList<BigInteger>> rList = new ArrayList<>();
            ArrayList<ArrayList<BigInteger>> secParamList = new ArrayList<>();
            rList.add(rList7);
            secParamList.add(secParamList7);
            BigInteger T7 = SPK.caculateT_P1(pubParamList7, rList7, PublicParams.n);
            BigInteger c = GetHash.hash(T7);
            ArrayList<ArrayList<BigInteger>> SList = SPK.caculateSList(rList, secParamList, c);
            ArrayList<BigInteger> sList1 = SList.get(0);
            BigInteger T1 = (kc.getD().multiply((PublicParams.a.mod(PublicParams.n).multiply(PublicParams.ai.modPow(kc.getIc(), PublicParams.n))).modInverse(PublicParams.n)).modPow(c, PublicParams.n)).multiply(SPK.caculateT_P1(pubParamList7, sList1, PublicParams.n));
            BigInteger c1 = GetHash.hash(T1.mod(PublicParams.n));
            System.out.println("6:" + c1.equals(c));
        }
    }

}
