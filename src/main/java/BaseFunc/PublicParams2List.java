package BaseFunc;

import JavaBean.PublicParams;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @projectName: MyProject
 * @package: BaseFunc
 * @className: PublicParams2List
 * @author: xjm
 * @description: TODO
 * @date: 5/10/2024 9:32 AM
 * @version: 1.0
 */
public class PublicParams2List {
    public static ArrayList<BigInteger> convert(){
        ArrayList<BigInteger> arrayList = new ArrayList<BigInteger>();
        arrayList.add(PublicParams.n);
        arrayList.add(PublicParams.a);
        arrayList.add(PublicParams.ax);
        arrayList.add(PublicParams.as);
        arrayList.add(PublicParams.at);
        arrayList.add(PublicParams.ai);
        arrayList.add(PublicParams.ae);
        arrayList.add(PublicParams.ad);
        arrayList.add(PublicParams.h);
        arrayList.add(PublicParams.sigma);
        arrayList.add(PublicParams.b);
        arrayList.add(PublicParams.b1);
        arrayList.add(PublicParams.b2);
        return arrayList;
    }
}
