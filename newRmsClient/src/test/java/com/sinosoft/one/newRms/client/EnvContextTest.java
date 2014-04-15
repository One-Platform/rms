package com.sinosoft.one.newRms.client;

import junit.framework.Assert;

import com.sinosoft.one.rms.client.EnvContext;

/**
 * Created by IntelliJ IDEA.
 * User: ChengQi
 * Date: 3/22/12
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnvContextTest {

    public void contextTest() throws Exception {
         TT  tt = new TT("aa");
         TT  t2 = new TT("bb");

         tt.start();
         t2.start();
    }

    class TT extends Thread{

        private String s ;
        
        TT(String s){
            this.s = s;
        }


        public void run(){
           EnvContext.setDataAuthorityTaskId(s);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Assert.assertEquals(s,EnvContext.getDataAuthorityTaskId());
        }
    }
}
