package com.sinosoft.one.rms.client;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.sinosoft.one.rms.User;



/**
 * Created by IntelliJ IDEA.
 * User: ChengQi
 * Date: 3/22/12
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnvContext {


	private static ThreadLocal<Map<String,Object  >>  threadLocal  = new ThreadLocal<Map<String, Object>>();

    private static final String DATA = "DATA";
    
    private static final String CLASSNAME="MATHOD";
    
    private static final String USER="USER";
      
    public static void setDataAuthorityTaskId(String value) {
		Map<String,Object >  context = threadLocal.get();
        if(context == null){
        	 context = new HashMap<String,Object >();
        	 threadLocal.set(context);
        }
        if( context.get(DATA)==null){
        	context.put(DATA, new LinkedList ());
        }
        LinkedList linkedList= (LinkedList) context.get(DATA);
        linkedList.addFirst(value);
        
    }

    public static String getDataAuthorityTaskId() {
        if(threadLocal.get() == null)
            return null;
        LinkedList linkedList= (LinkedList) threadLocal.get().get(DATA);
        if(linkedList!=null){
        	return (String)linkedList.peek();
        }
        return null;
    }
    
    public static void removeDataAuthorityTaskId (){
		LinkedList linkedList= (LinkedList) threadLocal.get().get(DATA);
		linkedList.remove();
	}
    
    
	public static void setClassName(String calssName) {
		Map<String,Object >  context = threadLocal.get();
        if(context == null){
        	 context = new HashMap<String,Object >();
        	 threadLocal.set(context);
        }
        if( context.get(CLASSNAME)==null){
        	context.put(CLASSNAME, new LinkedList ());
        }
        LinkedList linkedList= (LinkedList) context.get(CLASSNAME);
        linkedList.addFirst(calssName);
	}
	
	public static String getClassName(){
    	if(threadLocal.get() == null)
    		 return null;
    	LinkedList linkedList= (LinkedList) threadLocal.get().get(CLASSNAME);
        return (String)linkedList.peek();
    }
	
	public static void removeClassName(){
		LinkedList linkedList= (LinkedList) threadLocal.get().get(CLASSNAME);
		linkedList.remove();
	}
	
	//-------------------------------------------------------------//
	public static void setLogin(User value) {
		Map<String, Object> context = threadLocal.get();
		if (context == null) {
			context = new HashMap<String, Object>();
			threadLocal.set(context);
		}
		context.put(USER, value);
	}
	
	public static User getLogin() {
		if(threadLocal.get() == null)
            return null;
		return (User)threadLocal.get().get(USER);
	}
}
