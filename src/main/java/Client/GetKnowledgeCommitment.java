package Client;

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

    public static ArrayList<BigInteger> generate(){
        ArrayList<BigInteger> list = new ArrayList<BigInteger>();


        return list;
    }

}
