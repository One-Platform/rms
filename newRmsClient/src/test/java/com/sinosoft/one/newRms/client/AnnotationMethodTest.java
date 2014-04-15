package com.sinosoft.one.newRms.client;

import com.sinosoft.one.rms.client.annotation.DataAuthority;

/**
 * Created by IntelliJ IDEA.
 * User: ChengQi
 * Date: 3/21/12
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class AnnotationMethodTest extends BaseTest{

    @DataAuthority
    void test(){
         this.sound();
    }

    
    public static void main(String[] args){
          new AnnotationMethodTest().test();
    }
}
