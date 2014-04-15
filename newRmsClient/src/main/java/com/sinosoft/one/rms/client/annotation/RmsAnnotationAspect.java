package com.sinosoft.one.rms.client.annotation;


import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sinosoft.one.rms.client.EnvContext;

/**
 * rms client annotation aspect
 * User: ChengQi
 * Date: 3/22/12
 * Time: 11:08 AM
 */
@Aspect 
@Component("rmsAnnotationAspect")
public class RmsAnnotationAspect {

	private static CacheService arch4MethodNameCacheManager = CacheManager.getInstance("arch4Method");
	
//  @Around("execution(* ins..*service..*Service*Impl.*(..))") 
	@Around("execution(* com.sinosoft.one.ams.service..*.*(..))") 
    public Object register(ProceedingJoinPoint pjp) throws Throwable {
    	if(pjp.getThis() == null || pjp.getTarget() == null) {
    		return pjp.proceed();
    	}
    	String key = arch4MethodNameCacheManager.generateCacheKey("arch4Method", "arch4Method");
		Object result = arch4MethodNameCacheManager.getCache(key);
		Set<String> arch4MethodNames ;
		if (result != null) {
			arch4MethodNames=(HashSet<String>)result;
		}else{
			arch4MethodNames=new HashSet<String>(); 
			arch4MethodNames.add("findUnionBySql");
			arch4MethodNames.add("findUnionByHqls");
			arch4MethodNames.add("findByHqlNoLimit");
			arch4MethodNames.add("getAll");
			arch4MethodNames.add("findUnique");
			arch4MethodNames.add("isOptimizeFind");
			arch4MethodNames.add("findTopByHql");
			arch4MethodNames.add("findByHql");
			arch4MethodNames.add("findBySql");
			arch4MethodNames.add("find");
			arch4MethodNameCacheManager.putCache(key, arch4MethodNames);
		}
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method m = signature.getMethod();
        if(Proxy.isProxyClass(pjp.getThis().getClass())) {
            m = pjp.getTarget().getClass().getMethod(m.getName(), m.getParameterTypes());
        }
        DataAuthority dataAuthority = m.getAnnotation(DataAuthority.class);
        Object object=null;
        String tempDataAuthorityTaskId=null;
        if(dataAuthority != null){
        	System.out.println("RmsAnnotationAspect");
        	EnvContext.setClassName(pjp.getTarget().getClass().getName());
        	EnvContext.setDataAuthorityTaskId(dataAuthority.value());
        	object=pjp.proceed();
        	EnvContext.removeDataAuthorityTaskId();
        	EnvContext.removeClassName();
        }else {
        	if(EnvContext.getDataAuthorityTaskId()!=null){
        		if(pjp.getTarget().getClass().getName().toString().equals(EnvContext.getClassName().toString())){
        			for (String methodName : arch4MethodNames) {
						if(methodName.equals(m.getName())){
							return pjp.proceed();
						}
					}
    				return envContextHandle(pjp, object, tempDataAuthorityTaskId);
        		}else{
        			EnvContext.setClassName(pjp.getTarget().getClass().getName());
        			object=envContextHandle(pjp, object, tempDataAuthorityTaskId);
    				EnvContext.removeClassName();
    				return object;
        		}
        	}else {
        		return pjp.proceed();
			}
		}
        return object;
    }
    
    Object envContextHandle(ProceedingJoinPoint pjp,Object object, String tempDataAuthorityTaskId) throws Throwable{
    	tempDataAuthorityTaskId = EnvContext.getDataAuthorityTaskId();
		EnvContext.removeDataAuthorityTaskId();
		EnvContext.setDataAuthorityTaskId(null);
		object = pjp.proceed();
		EnvContext.removeDataAuthorityTaskId();
		EnvContext.setDataAuthorityTaskId(tempDataAuthorityTaskId);
    	return object;
    }
}
