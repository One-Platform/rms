package com.sinosoft.one.rms.client;



/**
 * 数据权限规则接口，所有数据规则的groovy文件必须实现此接口
 * User: ChengQi
 * Date: 8/13/12
 * Time: 3:57 PM
 */
public interface DataRuleScript {
	
	public String creatSQL(String rule,String tableNameAlias,String userCode,String comCode,String prama,String clounmName);
	
}
