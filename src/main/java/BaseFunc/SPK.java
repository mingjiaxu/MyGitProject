package BaseFunc;

import JavaBean.AnonymousCertificate;
import JavaBean.KnowledgeCommitment;
import JavaBean.PublicParams;
import JavaBean.SPK2Bean;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @projectName: MyProject
 * @package: BaseFunc
 * @className: SPK
 * @author: xjm
 * @description: 提供一系列计算SPK相关内容方法,
 * @date: 5/12/2024 8:45 PM
 * @version: 1.0
 */
public class SPK {
    /**
     * @param baseNumList: 底数列表
     * @param exponentList: 指数列表
     * @param m: 模数
     * @return BigInteger
     * @author xjm
     * @description 挑战计算模式一    型如 ax^rx *as ^rs....的挑战
     * @date 5/12/2024 8:56 PM
     */
    public static BigInteger caculateT_P1(ArrayList<BigInteger> baseNumList,ArrayList<BigInteger> exponentList,BigInteger m){
//        if (baseNumList == null)
//            throw new Exception("baseNums为空");
//        else if(exponentList == null)
//            throw new Exception("baseNums为空");
//        else if(baseNumList.size()!= exponentList.size())
//            throw new Exception("底数列表与指数列表长度不相等");
        BigInteger T = BigInteger.ONE;
        for (int i = 0; i < baseNumList.size(); i++) {
            T = T.multiply(baseNumList.get(i).modPow(exponentList.get(i),m));
        }
        return T.mod(m);
    }
    /**
     * @param baseNumList: 底数列表
     * @param exponentList: 指数列表
     * @param m: 模数
     * @return BigInteger
     * @author xjm
     * @description 挑战计算模式二 型如  A^ry/(ax^x*as^s*....)的挑战
     * @date 5/12/2024 9:22 PM
     */
    public static BigInteger caculateT_P2(ArrayList<BigInteger> baseNumList,ArrayList<BigInteger> exponentList,BigInteger m){
//        if (baseNumList == null)
//            throw new Exception("baseNumList为空");
//        else if(exponentList == null)
//            throw new Exception("exponentList为空");
//        else if(baseNumList.size()!= exponentList.size())
//            throw new Exception("baseNumList与exponentList长度不相等");
        BigInteger T = baseNumList.get(0).modPow(exponentList.get(0),m);
        ArrayList<BigInteger> baseNumSubList = new ArrayList<BigInteger>();
        ArrayList<BigInteger> exponentSubList = new ArrayList<BigInteger>();
        baseNumSubList.addAll(baseNumList.subList(1,baseNumList.size()));
        exponentSubList.addAll(exponentList.subList(1,exponentList.size()));
        BigInteger invPart = caculateT_P1(baseNumSubList,exponentSubList,m).modInverse(m);
        return T.multiply(invPart).mod(m);
    }
    /**
     * @param TList: 挑战的集合列表
     * @return BigInteger
     * @author xjm
     * @description 对挑战的集合计算摘要;
     * @date 5/12/2024 9:48 PM
     */
    public static BigInteger caculateHashC(ArrayList<BigInteger> TList) throws NoSuchAlgorithmException {
        BigInteger T = BigInteger.ZERO;
        for (int i = 0; i < TList.size(); i++) {
            T = T.add(TList.get(i));
        }
        BigInteger c = GetHash.hash(T);
        return c;
    }
    /**
     * @param rList: 生成的随机数列表
     * @param secParamList: 挑战中需要隐藏的秘密参数的列表
     * @param c: 摘要
     * @return ArrayList<BigInteger>
     * @author xjm
     * @description 计算S的集合
     * @date 5/12/2024 9:59 PM
     */
//    public static ArrayList<BigInteger> caculateSList(ArrayList<BigInteger> rList,ArrayList<BigInteger> secParamList,BigInteger c){
////        if(rList==null){
////            throw new Exception("rList为空");
////        }
////        else if(secParamList ==null){
////            throw new Exception("secParamList为空");
////        } else if (rList.size()!=secParamList.size()) {
////            throw new Exception("rList与secParamList长度不相等");
////        }
//        ArrayList<BigInteger> sList  = new ArrayList<BigInteger>();
//        for (int i = 0; i < rList.size() ; i++) {
//            sList.add(rList.get(i).subtract(c.parallelMultiply(secParamList.get(i))));
//        }
//        return sList;
//    }
    /**
     * @param rList: 随机参数的二维泪飙
     * @param secParamList: 秘密参数的二维列表
     * @param c: 摘要
     * @return ArrayList<ArrayList<BigInteger>>
     * @author xjm
     * @description TODO
     * @date 5/13/2024 1:17 PM
     */
    public static ArrayList<ArrayList<BigInteger>> caculateSList(ArrayList<ArrayList<BigInteger>> rList,ArrayList<ArrayList<BigInteger>> secParamList,BigInteger c)
    {
        ArrayList<ArrayList<BigInteger>> sList  = new ArrayList<ArrayList<BigInteger>>();

        for (int i = 0; i < rList.size(); i++) {
            ArrayList<BigInteger> temp = new ArrayList<>();
            for (int j = 0; j < rList.get(i).size(); j++) {
                temp.add(rList.get(i).get(j).subtract(c.multiply(secParamList.get(i).get(j))));
            }
            sList.add(temp);
        }
        return sList;
    }
/**
 * @param secParamList: 秘密参数列表
 * @return ArrayList<BigInteger>
 * @author xjm
 * @description 为所有的秘密参数生成等长度的随机参数
 * @date 5/12/2024 10:04 PM
 */
    public static ArrayList<BigInteger> generateRList(ArrayList<BigInteger> secParamList){
//        if(secParamList==null)
//            throw new Exception("secParamList为空");

        ArrayList<BigInteger> rList = new ArrayList<BigInteger>();
        for (int i = 0; i < secParamList.size() ; i++) {
            rList.add(GetRndBigintger.generate(secParamList.get(i).bitLength()));
        }
        return rList;
    }

    public static SPK2Bean caculateSPK2(AnonymousCertificate ac, KnowledgeCommitment kc){
        SPK2Bean spk2Bean = new SPK2Bean();
        ArrayList<ArrayList<BigInteger>> rList = new ArrayList<>(); //二维的BigInteger 数组
        ArrayList<ArrayList<BigInteger>> secParamList = new ArrayList<>(); //二维的秘密参数数组
        //第一部分
        ArrayList<BigInteger> secParamList1 = Params2List.convert(ac.getY(), ac.getX(), ac.getS(), ac.getT1(), ac.getI(), ac.getE(), ac.getD(), ac.getW().add(ac.getY().multiply(ac.getW1())));

        ArrayList<BigInteger> pubParamList1 = Params2List.convert(kc.getA1(), PublicParams.ax, PublicParams.as, PublicParams.at, PublicParams.ai, PublicParams.ae, PublicParams.ad, PublicParams.h);
        ArrayList<BigInteger> rList1 = SPK.generateRList(secParamList1);

        rList.add(rList1);
        secParamList.add(secParamList1);
        BigInteger T1 = SPK.caculateT_P2(pubParamList1, rList1, PublicParams.n);
        //第二部分
        ArrayList<BigInteger> secParamList2 = Params2List.convert(ac.getT1(), ac.getR());
        ArrayList<BigInteger> pubParamList2 = Params2List.convert(PublicParams.b1, PublicParams.b2);
        ArrayList<BigInteger> rList2 = SPK.generateRList(secParamList2);
        rList.add(rList2);
        secParamList.add(secParamList2);
        BigInteger T2 = SPK.caculateT_P1(pubParamList2, rList2, PublicParams.sigma);
        //第三部分
        ArrayList<BigInteger> secParamList3 = Params2List.convert(ac.getS(), ac.getAlpha());
        ArrayList<BigInteger> pubParamList3 = Params2List.convert(kc.getSN(), PublicParams.b2);
        ArrayList<BigInteger> rList3 = SPK.generateRList(secParamList3);
        rList.add(rList3);
        secParamList.add(secParamList3);
        BigInteger T3 = SPK.caculateT_P2(pubParamList3, rList3, PublicParams.sigma);
        //第四部分
        ArrayList<BigInteger> secParamList4 = Params2List.convert(ac.getT1(), ac.getBeta());
        ArrayList<BigInteger> pubParamList4 = Params2List.convert(kc.getSN(), PublicParams.b1);
        ArrayList<BigInteger> rList4 = SPK.generateRList(secParamList4);
        rList.add(rList4);
        secParamList.add(secParamList4);
        BigInteger T4 = SPK.caculateT_P2(pubParamList4, rList4, PublicParams.sigma);
        //第五部分
        ArrayList<BigInteger> secParamList5 = Params2List.convert(ac.getE(), ac.getE().multiply(ac.getI()), ac.getM());
        ArrayList<BigInteger> pubParamList5 = Params2List.convert(PublicParams.a.modPow(kc.getIc(),PublicParams.n), PublicParams.a, PublicParams.a.modPow(PublicParams.k,PublicParams.n));
        ArrayList<BigInteger> rList5 = SPK.generateRList(secParamList5);
        rList.add(rList5);
        secParamList.add(secParamList5);
        BigInteger T5 = SPK.caculateT_P2(pubParamList5, rList5, PublicParams.n);
        //第六部分
        ArrayList<BigInteger> secParamList6 = Params2List.convert(ac.getEc(), ac.getE(), ac.getD());
        ArrayList<BigInteger> pubParamList6 = Params2List.convert(PublicParams.b, PublicParams.b, PublicParams.b);
        ArrayList<BigInteger> rList6 = SPK.generateRList(secParamList6);
        rList.add(rList6);
        secParamList.add(secParamList6);
        BigInteger T6 = SPK.caculateT_P2(pubParamList6, rList6, PublicParams.sigma);
        //第七部分
        ArrayList<BigInteger> secParamList7 = Params2List.convert(ac.getX(), ac.getS(), ac.getT1(), ac.getE().add(ac.getD()), ac.getD(), ac.getW().add(ac.getY().multiply(ac.getW1())));
        ArrayList<BigInteger> pubParamList7 = Params2List.convert(PublicParams.ax, PublicParams.as, PublicParams.at, PublicParams.ae, PublicParams.ad, PublicParams.h);
        ArrayList<BigInteger> rList7 = SPK.generateRList(secParamList7);
        rList.add(rList7);
        secParamList.add(secParamList7);
        BigInteger T7 = SPK.caculateT_P1(pubParamList7, rList7, PublicParams.n);

        BigInteger T = T7.add(T1).add(T2).add(T3).add(T4).add(T5).add(T6);
//        System.out.println("T1:"+T1);
//        System.out.println("T2:"+T2);
//        System.out.println("T3:"+T3);
//        System.out.println("T4:"+T4);
//        System.out.println("T5:"+T5);
//        System.out.println("T6:"+T6);
//        System.out.println("T7:"+T7);

        BigInteger c;
        try {
            c = GetHash.hash(T);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        ArrayList<ArrayList<BigInteger>> SList = SPK.caculateSList(rList, secParamList, c);
        spk2Bean.setC(c);
        spk2Bean.setLists(SList);
        return spk2Bean;
    }

    public static boolean verifySPK2(KnowledgeCommitment kc, SPK2Bean spk2Bean){
        ArrayList<BigInteger> pubParamList1 = Params2List.convert(kc.getA1(), PublicParams.ax, PublicParams.as, PublicParams.at, PublicParams.ai, PublicParams.ae, PublicParams.ad, PublicParams.h);
        ArrayList<BigInteger> pubParamList2 = Params2List.convert(PublicParams.b1, PublicParams.b2);
        ArrayList<BigInteger> pubParamList3 = Params2List.convert(kc.getSN(), PublicParams.b2);
        ArrayList<BigInteger> pubParamList4 = Params2List.convert(kc.getSN(), PublicParams.b1);
        ArrayList<BigInteger> pubParamList5 = Params2List.convert(PublicParams.a.modPow(kc.getIc(),PublicParams.n), PublicParams.a, PublicParams.a.modPow(PublicParams.k,PublicParams.n));
        ArrayList<BigInteger> pubParamList6 = Params2List.convert(PublicParams.b, PublicParams.b, PublicParams.b);
        ArrayList<BigInteger> pubParamList7 = Params2List.convert(PublicParams.ax, PublicParams.as, PublicParams.at, PublicParams.ae, PublicParams.ad, PublicParams.h);
        BigInteger T1 = PublicParams.a.modPow(spk2Bean.getC(), PublicParams.n).multiply(SPK.caculateT_P2(pubParamList1, spk2Bean.getLists().get(0), PublicParams.n)).mod(PublicParams.n);
        BigInteger T2 = kc.getT().modPow(spk2Bean.getC(), PublicParams.sigma).multiply(SPK.caculateT_P1(pubParamList2, spk2Bean.getLists().get(1), PublicParams.sigma)).mod(PublicParams.sigma);
        BigInteger T3 = (PublicParams.b1.multiply((kc.getSN().modPow(kc.getIc(), PublicParams.sigma)).modInverse(PublicParams.sigma))).modPow(spk2Bean.getC(), PublicParams.sigma).multiply(SPK.caculateT_P2(pubParamList3, spk2Bean.getLists().get(2), PublicParams.sigma)).mod(PublicParams.sigma);
        BigInteger T4 = (PublicParams.b2.multiply((kc.getSN().modPow(kc.getIc(), PublicParams.sigma)).modInverse(PublicParams.sigma))).modPow(spk2Bean.getC(), PublicParams.sigma).multiply(SPK.caculateT_P2(pubParamList4, spk2Bean.getLists().get(3), PublicParams.sigma)).mod(PublicParams.sigma);
        BigInteger T5 = (PublicParams.a.modPow(spk2Bean.getC(), PublicParams.n)).multiply(SPK.caculateT_P2(pubParamList5, spk2Bean.getLists().get(4), PublicParams.n)).mod(PublicParams.n);
        BigInteger T6 = (BigInteger.ONE.modPow(spk2Bean.getC(), PublicParams.sigma)).multiply(SPK.caculateT_P2(pubParamList6, spk2Bean.getLists().get(5), PublicParams.sigma));
        BigInteger T7 = (kc.getD().multiply((PublicParams.a.mod(PublicParams.n).multiply(PublicParams.ai.modPow(kc.getIc(), PublicParams.n))).modInverse(PublicParams.n)).modPow(spk2Bean.getC(), PublicParams.n)).multiply(SPK.caculateT_P1(pubParamList7, spk2Bean.getLists().get(6), PublicParams.n)).mod(PublicParams.n);
        BigInteger T = T7.add(T1).add(T2).add(T3).add(T4).add(T5).add(T6);

//        System.out.println("T1:"+T1);
//        System.out.println("T2:"+T2);
//        System.out.println("T3:"+T3);
//        System.out.println("T4:"+T4);
//        System.out.println("T5:"+T5);
//        System.out.println("T6:"+T6);
//        System.out.println("T7:"+T7);
        BigInteger c;
        try {
            c = GetHash.hash(T);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return c.equals(spk2Bean.getC())?true:false;
    }


}
