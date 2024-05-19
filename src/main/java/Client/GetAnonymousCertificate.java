package Client;

import BaseFunc.GetRndBigintger;
import JavaBean.*;

import java.math.BigInteger;

/**
 * @projectName: MyProject
 * @package: Client
 * @className: GetAnonymousCertificate
 * @author: xjm
 * @description: TODO
 * @date: 5/13/2024 9:30 AM
 * @version: 1.0
 */
public class GetAnonymousCertificate {
    public static AnonymousCertificate generate(SecretParams sp1, SignatureS2CParams sp2){
        AnonymousCertificate ac = new AnonymousCertificate();
        ac.setA(sp2.getA());
        ac.setY(sp2.getY());
        ac.setU(sp1.getU());
        ac.setX(sp1.getX());
        ac.setS(sp1.getS());
        ac.setT1(sp1.getT1());
        ac.setI(sp1.getI());
        ac.setE(sp1.getE());
        ac.setD(sp1.getD());
        ac.setW(sp1.getW());
        return ac;
    }
    public static void generate(AnonymousCertificate ac){
        ac.setR(GetRndBigintger.generate(100));
        ac.setW1(GetRndBigintger.generate(100));
    }
    public static void generate(KnowledgeCommitment kc, AnonymousCertificate ac){
        BigInteger alpha = kc.getIc().add(ac.getS()).multiply((kc.getIc().add(ac.getT1())).modInverse(PublicParams.q1)).mod(PublicParams.q1);
        BigInteger beta = (kc.getIc().add(ac.getT1())).multiply((kc.getIc().add(ac.getS())).modInverse(PublicParams.q1)).mod(PublicParams.q1);
        BigInteger m = kc.getIc().multiply(ac.getE()).subtract(ac.getI().multiply(ac.getE()).subtract(BigInteger.ONE)).divide(PublicParams.k);
        BigInteger ec = ac.getE().add(ac.getD());
        ac.setBeta(beta);
        ac.setAlpha(alpha);
        ac.setM(m);
        ac.setEc(ec);
    }
}
