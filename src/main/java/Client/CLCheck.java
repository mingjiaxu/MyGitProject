package Client;

import JavaBean.PublicParams;
import JavaBean.SecretParams;
import JavaBean.SignatureS2CParams;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @projectName: MyProject
 * @package: BaseFunc
 * @className: CLCheck
 * @author: xjm
 * @description: 验证CL签名算法有效性
 * @date: 5/10/2024 4:05 PM
 * @version: 1.0
 */
public class CLCheck {
    /**
     * @param secretParams: 用户秘密参数
     * @param signatureS2CParams:  服务发过来的签名参数
     * @return boolean
     * @author xjm
     * @description LC签名检查
     * @date 5/10/2024 10:31 PM
     */
    public static boolean check(SecretParams secretParams, SignatureS2CParams signatureS2CParams){
        BigInteger left = signatureS2CParams.getA().modPow(signatureS2CParams.getY(), PublicParams.n);
        BigInteger right = PublicParams.a.mod(PublicParams.n);
        right =right.multiply(PublicParams.ax.modPow(secretParams.getX(),PublicParams.n));
        right =right.multiply(PublicParams.as.modPow(secretParams.getS(),PublicParams.n));
        right =right.multiply(PublicParams.at.modPow(secretParams.getT1(),PublicParams.n));
        right =right.multiply(PublicParams.ai.modPow(secretParams.getI(),PublicParams.n));
        right =right.multiply(PublicParams.ae.modPow(secretParams.getE(),PublicParams.n));
        right =right.multiply(PublicParams.ad.modPow(secretParams.getD(),PublicParams.n));
        right =right.multiply(PublicParams.h.modPow(secretParams.getW(),PublicParams.n));
        right =right.mod(PublicParams.n);
        return left.equals(right) ? true:false;
    }
}
