package JavaBean;

import java.math.BigInteger;

/**
 * @projectName: MyProject
 * @package: JavaBean
 * @className: AnonymousCertificate
 * @author: xjm
 * @description: 存放匿名证书
 * @date: 5/13/2024 12:01 AM
 * @version: 1.0
 */
public class AnonymousCertificate extends SecretParams{
    private BigInteger A;
    private BigInteger y;

    public AnonymousCertificate() {
    }

    public BigInteger getA() {
        return A;
    }

    public void setA(BigInteger a) {
        A = a;
    }

    public BigInteger getY() {
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }
}
