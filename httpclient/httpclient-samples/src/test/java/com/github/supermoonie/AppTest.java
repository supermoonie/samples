package com.github.supermoonie;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Java8Tester :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    private void printInfo(String[] strArr, Consumer<String> con1, Consumer<String> con2){

        for (int i = 0; i < strArr.length; i++) {
            con1.andThen(con2).accept(strArr[i]);
        }

    }

    @Test
    public void consumerTest2() {
        String[] strArr = {"迪丽热巴,女","郑爽,女","杨紫,女"};
        printInfo(strArr,(message)-> System.out.print("姓名:" + message.split(",")[0] + "。  "),
                (message)-> System.out.println("性别:" + message.split(",")[1] + "。"));
    }

    @Test
    public void consumerTest() {
        Consumer<String> f = System.out::println;
        Consumer<String> f2 = n -> System.out.println(n + "-F2");

        //执行完F后再执行F2的Accept方法
        f.andThen(f2).accept("test");

        //连续执行F的Accept方法
        f.andThen(f).andThen(f).andThen(f).accept("test1");
    }

    @Test
    public void functionTest() {
        Function<Integer, Integer> f = s -> ++s;
        Function<Integer, Integer> g = s -> s * 2;

        System.out.println(f.compose(g).apply(1));

        System.out.println(f.andThen(g).apply(1));

        System.out.println(Function.identity().apply("a"));
    }
}
