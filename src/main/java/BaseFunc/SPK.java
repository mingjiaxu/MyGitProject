package BaseFunc;

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
        return T.parallelMultiply(invPart).mod(m);
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
    public static ArrayList<BigInteger> caculateSList(ArrayList<BigInteger> rList,ArrayList<BigInteger> secParamList,BigInteger c){
//        if(rList==null){
//            throw new Exception("rList为空");
//        }
//        else if(secParamList ==null){
//            throw new Exception("secParamList为空");
//        } else if (rList.size()!=secParamList.size()) {
//            throw new Exception("rList与secParamList长度不相等");
//        }
        ArrayList<BigInteger> sList  = new ArrayList<BigInteger>();
        for (int i = 0; i < rList.size() ; i++) {
            sList.add(rList.get(i).subtract(c.parallelMultiply(secParamList.get(i))));
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

}
