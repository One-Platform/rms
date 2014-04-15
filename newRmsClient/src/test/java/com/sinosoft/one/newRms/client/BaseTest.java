package com.sinosoft.one.newRms.client;

/**
 * Created by IntelliJ IDEA.
 * User: ChengQi
 * Date: 3/21/12
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseTest {
    
    void sound(){
        long s1  =   System.nanoTime();
        System.out.println("s - "+ s1);
        StackTraceElement[] st= new Throwable().getStackTrace();
        long s2  =   System.nanoTime();
        System.out.println("e -" + s2);
        System.out.println("i - " + (s2-s1));

        StackTraceElement[] st2= new Throwable().getStackTrace();

        for (int i=0;i<st.length;i++){
            StackTraceElement traceElement = st[i];
            System.out.println(i+","+traceElement.toString());
        }
        //System.out.println("aaaaa");
    }
}
