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



    }


}
