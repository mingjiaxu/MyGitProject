package Sever;

import BaseFunc.GetStrongPrime;
import JavaBean.PublicParams;

import java.math.BigInteger;
import java.util.Random;

/**
 * @projectName: MyProject
 * @package: Sever
 * @className: GetPublicParams
 * @author: xjm
 * @description: TODO
 * @date: 5/9/2024 10:43 PM
 * @version: 1.0
 */
public class GetPublicParams {
    /**
     * @param :void
     * @return PublicParams
     * @author xjm
     * @description 生成系统公共参数
     * @date 5/9/2024 11:03 PM
     */
    /**
    public static PublicParams generate(){
        PublicParams publicParams = new PublicParams();
        Random rnd = new Random();
        publicParams.setN(GetStrongPrime.generate(512).multiply(GetStrongPrime.generate(512)));
        publicParams.setA(new BigInteger(30,rnd));
        publicParams.setAx(new BigInteger(30,rnd));
        publicParams.setAs(new BigInteger(30,rnd));
        publicParams.setAt(new BigInteger(30,rnd));
        publicParams.setAi(new BigInteger(30,rnd));
        publicParams.setAe(new BigInteger(30,rnd));
        publicParams.setAd(new BigInteger(30,rnd));
        publicParams.setH(new BigInteger(30,rnd));

        publicParams.setSigma(GetStrongPrime.generate(1024));
        publicParams.setB((new BigInteger(30,rnd).modPow(BigInteger.TWO,publicParams.getSigma())));
        publicParams.setB1((new BigInteger(30,rnd).modPow(BigInteger.TWO,publicParams.getSigma())));
        publicParams.setB2((new BigInteger(30,rnd).modPow(BigInteger.TWO,publicParams.getSigma())));
        return  publicParams;
    }
    **/
}
