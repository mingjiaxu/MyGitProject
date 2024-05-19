package JavaBean;

import java.math.BigInteger;

/**
 * @projectName: MyProject
 * @package: JavaBean
 * @className: SecretParams
 * @author: xjm
 * @description: 用户秘密参数的 javabean
 * @date: 5/9/2024 10:24 PM
 * @version: 1.0
 */
public class SecretParams {
    private BigInteger u;    //私钥
    private BigInteger x;
    private BigInteger s;
    private BigInteger t1;
    private BigInteger i;
    private BigInteger e;
    private BigInteger d;
    private BigInteger w;
    private BigInteger pku;
    private BigInteger r;
    private BigInteger w1;      //w*
    private BigInteger alpha;
    private BigInteger beta;
    private BigInteger m;
    private BigInteger ec;


    public SecretParams() {
    }

    public BigInteger getU() {
        return u;
    }

    public void setU(BigInteger u) {
        this.u = u;
    }

    public BigInteger getX() {
        return x;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public BigInteger getS() {
        return s;
    }

    public void setS(BigInteger s) {
        this.s = s;
    }

    public BigInteger getT1() {
        return t1;
    }

    public void setT1(BigInteger t1) {
        this.t1 = t1;
    }

    public BigInteger getI() {
        return i;
    }

    public void setI(BigInteger i) {
        this.i = i;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public BigInteger getW() {
        return w;
    }

    public void setW(BigInteger w) {
        this.w = w;
    }

    public BigInteger getPku() {
        return pku;
    }

    public void setPku(BigInteger pku) {
        this.pku = pku;
    }

    public BigInteger getR() {
        return r;
    }

    public void setR(BigInteger r) {
        this.r = r;
    }

    public BigInteger getW1() {
        return w1;
    }

    public void setW1(BigInteger w1) {
        this.w1 = w1;
    }

    public BigInteger getAlpha() {
        return alpha;
    }

    public void setAlpha(BigInteger alpha) {
        this.alpha = alpha;
    }

    public BigInteger getBeta() {
        return beta;
    }

    public void setBeta(BigInteger beta) {
        this.beta = beta;
    }

    public BigInteger getM() {
        return m;
    }

    public void setM(BigInteger m) {
        this.m = m;
    }

    public BigInteger getEc() {
        return ec;
    }

    public void setEc(BigInteger ec) {
        this.ec = ec;
    }
}
