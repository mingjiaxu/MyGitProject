package JavaBean;

import java.lang.ref.PhantomReference;
import java.math.BigInteger;

/**
 * @projectName: MyProject
 * @package: JavaBean
 * @className: KnowledgeCommitment
 * @author: xjm
 * @description: 知识承诺(A',T,ic,SN,D)的实体类
 * @date: 5/13/2024 9:11 AM
 * @version: 1.0
 */
public class KnowledgeCommitment {
    private BigInteger A1;
    private BigInteger T;
    private BigInteger ic;
    private BigInteger SN;
    private BigInteger D;

    public KnowledgeCommitment(){
    }

    public BigInteger getA1() {
        return A1;
    }

    public void setA1(BigInteger a1) {
        A1 = a1;
    }

    public BigInteger getT() {
        return T;
    }

    public void setT(BigInteger t) {
        T = t;
    }

    public BigInteger getIc() {
        return ic;
    }

    public void setIc(BigInteger ic) {
        this.ic = ic;
    }

    public BigInteger getSN() {
        return SN;
    }

    public void setSN(BigInteger SN) {
        this.SN = SN;
    }

    public BigInteger getD() {
        return D;
    }

    public void setD(BigInteger d) {
        D = d;
    }
}
