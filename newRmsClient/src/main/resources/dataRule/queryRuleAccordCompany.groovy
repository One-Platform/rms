import com.alibaba.fastjson.JSON;
import ins.framework.utils.StringUtils;
import com.sinosoft.one.newRms.client.DataRuleScript;
import java.util.ArrayList;
import java.util.List;

public class queryRuleAccordCompany implements DataRuleScript {
 	
 	private String TABLENAME="ge_rms_company";
 	
 	private String CLOUNMNAME="comCode";
 	
   	public String creatSQL(String rule,String tableNameAlias,String userCode,String comCode,String prama,String clounmName){
		String alias="";
		if(StringUtils.isNotBlank(tableNameAlias)){
			alias=tableNameAlias+".";
		}
		if(StringUtils.isNotBlank(prama)){
			Map<String,String> tempMap = (Map<String, String>)JSON.parse(prama);
    		if(StringUtils.isNotBlank(rule)){
    			rule=rule+" and "+alias+clounmName+"=(select "+CLOUNMNAME+" from "+TABLENAME+" where "+CLOUNMNAME+"='"+tempMap.get(clounmName)+"')"
    		}else{
    			rule=rule+""+alias+clounmName+"=(select "+CLOUNMNAME+" from "+TABLENAME+" where "+CLOUNMNAME+"='"+tempMap.get(clounmName)+"')"
    		}
			return rule 
		}else{
    		if(StringUtils.isNotBlank(rule)){
    			rule=rule+" and "+alias+clounmName+"=(select "+CLOUNMNAME+" from "+TABLENAME+" where "+CLOUNMNAME+"='"+ comCode+"')"
			}else{
    			rule=rule+""+alias+clounmName+"=(select "+CLOUNMNAME+" from "+TABLENAME+" where "+CLOUNMNAME+"='"+ comCode+"')"
			}
			return rule 
		}
  	}
  
  	
}
