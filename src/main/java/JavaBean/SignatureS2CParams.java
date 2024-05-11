package JavaBean;

import java.math.BigInteger;

/**
 * @projectName: MyProject
 * @package: Sever
 * @className: SignatureS2CParams
 * @author: xjm
 * @description: TODO
 * @date: 5/10/2024 4:01 PM
 * @version: 1.0
 */
public class SignatureS2CParams {
    private BigInteger A;
    private BigInteger y;
    private BigInteger t2;

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

    public BigInteger getT2() {
        return t2;
    }

    public void setT2(BigInteger t2) {
        this.t2 = t2;
    }
}
