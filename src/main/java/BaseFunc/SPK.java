package BaseFunc;

import java.math.BigInteger;
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
     * @param baseNumsList: 底数列表
     * @param exponentsList: 指数列表
     * @param m: 模数
     * @return BigInteger
     * @author xjm
     * @description 挑战计算模式一    型如 ax^rx *as ^rs....的挑战
     * @date 5/12/2024 8:56 PM
     */
    public static BigInteger caculateT_P1(ArrayList<BigInteger> baseNumsList,ArrayList<BigInteger> exponentsList,BigInteger m) throws Exception {
        if (baseNumsList == null)
            throw new Exception("baseNums为空");
        else if(exponentsList == null)
            throw new Exception("baseNums为空");
        else if(baseNumsList.size()!= exponentsList.size())
            throw new Exception("底数列表与指数列表长度不相等");
        BigInteger T = BigInteger.ONE;
        for (int i = 0; i < baseNumsList.size(); i++) {
            T = T.multiply(baseNumsList.get(i).modPow(exponentsList.get(i),m));
        }
        return T.mod(m);
    }
    /**
     * @param baseNumsList: 底数列表
     * @param exponentsList: 指数列表
     * @param m: 模数
     * @return BigInteger
     * @author xjm
     * @description 挑战计算模式二 型如  A^ry/(ax^x*as^s*....)的挑战
     * @date 5/12/2024 9:22 PM
     */
    public static BigInteger caculateT_P2(ArrayList<BigInteger> baseNumsList,ArrayList<BigInteger> exponentsList,BigInteger m) throws Exception {
        if (baseNumsList == null)
            throw new Exception("baseNums为空");
        else if(exponentsList == null)
            throw new Exception("baseNums为空");
        else if(baseNumsList.size()!= exponentsList.size())
            throw new Exception("底数列表与指数列表长度不相等");
        BigInteger T = baseNumsList.get(0).modPow(exponentsList.get(0),m);
        ArrayList<BigInteger> baseNumsSubList = new ArrayList<BigInteger>();
        ArrayList<BigInteger> exponentsSubList = new ArrayList<BigInteger>();
        baseNumsSubList.addAll(1,baseNumsList);
        exponentsSubList.addAll(1,exponentsList);
        BigInteger invPart = caculateT_P1(baseNumsSubList,exponentsSubList,m).modInverse(m);
        return T.parallelMultiply(invPart).mod(m);
    }
//    public static BigInteger caculateHashC(ArrayList<BigInteger> TList){
//        return BigInteger.ONE;
//    }
}
