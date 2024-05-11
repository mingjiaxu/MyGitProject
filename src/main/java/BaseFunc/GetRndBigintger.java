package BaseFunc;

import java.math.BigInteger;
import java.util.Random;

/**
 * @projectName: MyProject
 * @package: BaseFunc
 * @className: GetRndBigintger
 * @author: xjm
 * @description: TODO
 * @date: 5/10/2024 8:21 AM
 * @version: 1.0
 */
public class GetRndBigintger {
    public static BigInteger generate(int numbits){
        Random rnd = new Random();
        return new BigInteger(numbits,rnd);
    }
}
