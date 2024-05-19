package JavaBean;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @projectName: MyProject
 * @package: JavaBean
 * @className: SPK2
 * @author: xjm
 * @description: TODO
 * @date: 2024/5/16 16:23
 * @version: 1.0
 */
public class SPK2Bean {
    private BigInteger c;
    private ArrayList<ArrayList<BigInteger>> Slists;

    public SPK2Bean(){
    }

    public BigInteger getC() {
        return c;
    }

    public void setC(BigInteger c) {
        this.c = c;
    }

    public ArrayList<ArrayList<BigInteger>> getLists() {
        return Slists;
    }

    public void setLists(ArrayList<ArrayList<BigInteger>> lists) {
        this.Slists = lists;
    }
}
