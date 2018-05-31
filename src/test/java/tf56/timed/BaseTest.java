package tf56.timed;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/***
 * @ClassName BaseTest
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/23 19:54
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {

    @Test
    public void test() {
        int a = 1;
        int b = 2;
        a =  a ^ b;
        b =  a ^ b;
        a =  a ^ b;
        System.out.println(a);
        System.out.println(b);


        System.out.println(2*8 == 2<<3);
        System.out.println(2<<3);
        System.out.println(64>>3);
        System.out.println(64>>>3);
    }

}
