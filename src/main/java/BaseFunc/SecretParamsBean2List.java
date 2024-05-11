package BaseFunc;

import JavaBean.SecretParams;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @projectName: MyProject
 * @package: BaseFunc
 * @className: SecretParamsBean2List
 * @author: xjm
 * @description: TODO
 * @date: 5/10/2024 9:13 AM
 * @version: 1.0
 */
public class SecretParamsBean2List {
    public static ArrayList<BigInteger> convert(SecretParams sp){
        ArrayList<BigInteger> arrayList = new ArrayList<BigInteger>();
        arrayList.add(sp.getU());
        arrayList.add(sp.getX());
        arrayList.add(sp.getS());
        arrayList.add(sp.getT1());
        arrayList.add(sp.getI());
        arrayList.add(sp.getE());
        arrayList.add(sp.getD());
        arrayList.add(sp.getW());
        return arrayList;
    }
}
