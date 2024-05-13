package Client;

import JavaBean.AnonymousCertificate;
import JavaBean.SecretParams;
import JavaBean.SignatureS2CParams;

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
}
