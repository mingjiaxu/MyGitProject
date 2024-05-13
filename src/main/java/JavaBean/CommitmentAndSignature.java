package JavaBean;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @projectName: MyProject
 * @package: JavaBean
 * @className: CommitAndSignature
 * @author: xjm
 * @description: TODO
 * @date: 5/10/2024 9:22 PM
 * @version: 1.0
 */
public class CommitmentAndSignature {
    private BigInteger j1;
    private BigInteger j2;
    private ArrayList<BigInteger> SPK1;
    public CommitmentAndSignature(){
    }
    public BigInteger getJ1() {
        return j1;
    }

    public void setJ1(BigInteger j1) {
        this.j1 = j1;
    }

    public BigInteger getJ2() {
        return j2;
    }

    public void setJ2(BigInteger j2) {
        this.j2 = j2;
    }

    public ArrayList<BigInteger> getSPK1() {
        return SPK1;
    }

    public void setSPK1(ArrayList<BigInteger> SPK1) {
        this.SPK1 = SPK1;
    }
}
