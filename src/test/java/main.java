import BaseFunc.*;
import Client.*;
import JavaBean.*;
import Sever.GetSignatureS2CParams;
import Sever.SignatureVerify;
import Sever.VerifyKnowledgeSignature;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.util.ArrayList;

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
        CommitmentAndSignature cas = GetCommitmentAndSignature.generate(secretParams);
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

        AnonymousCertificate ac = GetAnonymousCertificate.generate(secretParams,signatureS2CParams);
        BigInteger w1 = GetRndBigintger.generate(100);
        BigInteger r = GetRndBigintger.generate(100);

        KnowledgeCommitment kc  = GetKnowledgeCommitment.generatePart1(ac,w1,r);

        BigInteger alpha = kc.getIc().add(ac.getS()).multiply((kc.getIc().add(ac.getT1())).modInverse(PublicParams.k));
        BigInteger beta = kc.getIc().add(ac.getT1()).multiply((kc.getIc().add(ac.getS())).modInverse(PublicParams.k));
        BigInteger m = kc.getIc().multiply(ac.getE()).subtract(ac.getI().multiply(ac.getE()).add(BigInteger.ONE)).divide(PublicParams.k);

        ArrayList<BigInteger> TList = new ArrayList<>();

        //生成秘密参数列表，公共参数列表
        ArrayList<BigInteger> secParamList1 = Params2List.convert(ac.getY(),ac.getX(),ac.getS(),ac.getT1(),ac.getI(),ac.getE(),ac.getD(),ac.getW().add(ac.getY().multiply(w1)));
        ArrayList<BigInteger> pubParamList1 = Params2List.convert(kc.getA1(),PublicParams.ax,PublicParams.as,PublicParams.at,PublicParams.ai,PublicParams.ae,PublicParams.ad,PublicParams.h);
        //根据秘密参数列表生成随机数列表
        ArrayList<BigInteger> rList1 = SPK.generateRList(secParamList1);
        //计算对应挑战

        TList.add(SPK.caculateT_P2(pubParamList1,rList1,PublicParams.n));
        //重复计算7次，计算出所有挑战

        ArrayList<BigInteger> secParamList2 =Params2List.convert(ac.getT1(),r);
        ArrayList<BigInteger> pubParamList2 = Params2List.convert(PublicParams.b1,PublicParams.b2);
        ArrayList<BigInteger> rList2 = SPK.generateRList(secParamList2);
        TList.add(SPK.caculateT_P1(pubParamList2,rList2,PublicParams.sigma));

        ArrayList<BigInteger> secParamList3 =Params2List.convert(ac.getS(),alpha);
        ArrayList<BigInteger> pubParamList3 = Params2List.convert(kc.getSN(),PublicParams.b2);
        ArrayList<BigInteger> rList3 = SPK.generateRList(secParamList3);
        TList.add(SPK.caculateT_P2(pubParamList3,rList3,PublicParams.sigma));

        ArrayList<BigInteger> secParamList4 =Params2List.convert(ac.getT1(),beta);
        ArrayList<BigInteger> pubParamList4 = Params2List.convert(kc.getSN(),PublicParams.b1);
        ArrayList<BigInteger> rList4 = SPK.generateRList(secParamList4);
        TList.add(SPK.caculateT_P2(pubParamList4,rList4,PublicParams.sigma));

        ArrayList<BigInteger> secParamList5 =Params2List.convert(kc.getIc().multiply(ac.getE()),ac.getI().multiply(ac.getE()),PublicParams.k.multiply(m));
        ArrayList<BigInteger> pubParamList5 = Params2List.convert(PublicParams.a,PublicParams.a,PublicParams.a);
        ArrayList<BigInteger> rList5 = SPK.generateRList(secParamList5);
        TList.add(SPK.caculateT_P2(pubParamList5,rList5,PublicParams.n));

        BigInteger ec = secretParams.getE().add(secretParams.getD());
        ArrayList<BigInteger> secParamList6 = Params2List.convert(ec,secretParams.getE(),secretParams.getD());
        ArrayList<BigInteger> pubParamList6 = Params2List.convert(PublicParams.b,PublicParams.b,PublicParams.b);
        ArrayList<BigInteger> rList6 = SPK.generateRList(secParamList6);
        TList.add(SPK.caculateT_P2(pubParamList6,rList6,PublicParams.sigma));

        ArrayList<BigInteger> secParamList7 = Params2List.convert(ac.getX(),ac.getS(),ac.getT1(),ac.getI(),ac.getE(),ac.getD(),ac.getW().add(ac.getY().multiply(w1)));
        ArrayList<BigInteger> pubParamList7 = Params2List.convert(PublicParams.ax,PublicParams.as,PublicParams.at,PublicParams.ai,PublicParams.ae,PublicParams.ad,PublicParams.h);
        ArrayList<BigInteger> rList7 = SPK.generateRList(secParamList7);
        TList.add(SPK.caculateT_P1(pubParamList7,rList7,PublicParams.n));
        BigInteger c = SPK.caculateHashC(TList);
        kc = GetKnowledgeCommitment.generatePart2(kc,ac,c);//最终的知识承诺


//        ArrayList<BigInteger> rList = new ArrayList<>();
//        ArrayList<BigInteger> secParamList = new ArrayList<>();
//        rList.addAll(rList1);
//        rList.addAll(rList2);
//        rList.addAll(rList3);
//        rList.addAll(rList4);
//        rList.addAll(rList5);
//        rList.addAll(rList6);
//        rList.addAll(rList7);
//        secParamList.addAll(secParamList1);
//        secParamList.addAll(secParamList2);
//        secParamList.addAll(secParamList3);
//        secParamList.addAll(secParamList4);
//        secParamList.addAll(secParamList5);
//        secParamList.addAll(secParamList6);
//        secParamList.addAll(secParamList7);
        ArrayList<ArrayList<BigInteger>> rList = new ArrayList<>();
        ArrayList<ArrayList<BigInteger>> secParamList = new ArrayList<>();
        ArrayList<ArrayList<BigInteger>> pubParamList = new ArrayList<>();
        rList.add(rList1);
        rList.add(rList2);
        rList.add(rList3);
        rList.add(rList4);
        rList.add(rList5);
        rList.add(rList6);
        rList.add(rList7);
        secParamList.add(secParamList1);
        secParamList.add(secParamList2);
        secParamList.add(secParamList3);
        secParamList.add(secParamList4);
        secParamList.add(secParamList5);
        secParamList.add(secParamList6);
        secParamList.add(secParamList7);
        pubParamList.add(pubParamList1);
        pubParamList.add(pubParamList2);
        pubParamList.add(pubParamList3);
        pubParamList.add(pubParamList4);
        pubParamList.add(pubParamList5);
        pubParamList.add(pubParamList6);
        pubParamList.add(pubParamList7);
        ArrayList<ArrayList<BigInteger>> sList = SPK.caculateSList(rList,secParamList,c);

        //验证SPK2
        VerifyKnowledgeSignature.verify(c,kc,pubParamList,sList);

    }


}
