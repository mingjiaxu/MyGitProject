package Sever;

import BaseFunc.GetStrongPrime;

import java.math.BigInteger;
import java.util.Random;

/**
 * @projectName: MyProject
 * @package: Sever
 * @className: main
 * @author: xjm
 * @description: TODO
 * @date: 5/9/2024 10:21 PM
 * @version: 1.0
 */
public class main {
    public static void main(String[] args) {
        Random rnd = new Random();
        BigInteger y = BigInteger.probablePrime(100,rnd);
        BigInteger t2 = BigInteger.probablePrime(100,rnd);
        BigInteger j2 = new BigInteger("78088242468732882706867384819116965081736416175334476409244354624415705630807873668898097286900220144111192508703133291385943942179661227353644298496976920454697907152348141455724418242103005921130629384015181176769637146057253773756618350316090604434296941466712238467186284342257913134844535668249196928670");
        BigInteger A = CLAlgorithm.signature(j2,t2,y);
        System.out.println("t2:"+t2);
        System.out.println("A:"+A);
    }
}
