import com.sinosoft.one.newRms.client.DataRuleScript;
import com.alibaba.fastjson.JSON;
import ins.framework.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class queryRuleAccordInsurance implements DataRuleScript {
 	
  	private String TABLENAME="PD_LMRISK";
 	
 	private String CLOUNMNAME="RISKCODE";
 	
   	public String creatSQL(String rule,String tableNameAlias,String userCode,String comCode,String prama,String clounmName){
		String alias="";
		if(StringUtils.isNotBlank(tableNameAlias)){
			alias=tableNameAlias+".";
		}
		String prams="";
		if(StringUtils.isNotBlank(prama)){
			Map<String,String> tempMap = (Map<String, String>)JSON.parse(prama);
			prams=tempMap.get(clounmName);
			println prams
			if(prams!=null && !prams.equals("")){
				List<String> strings=Arrays.asList(prams.split(","));
				if(strings.size()>1){
					prams="";
					for (String string : strings) {
						prams+="'"+string+"',";
					}
					prams=prams.substring(0, prams.length()-1);
				}else{
					prams="'"+tempMap.get(clounmName)+"'";;
				}
			}else{
				prams="' '";
			}	
    			if(StringUtils.isNotBlank(rule)){
    				rule=rule+" and "+clounmName+"=(select "+CLOUNMNAME+" from "+TABLENAME+" where "+CLOUNMNAME+" in ("+prams+"))"
    			}else{
    				rule=rule+""+clounmName+"=(select "+CLOUNMNAME+" from "+TABLENAME+" where "+CLOUNMNAME+" in ("+prams+"))"
    			}
    		
			return rule 
		}else{
    		if(StringUtils.isNotBlank(rule)){
    			rule=rule+" and "+alias+clounmName+"=(select "+CLOUNMNAME+" from "+TABLENAME+" where "+CLOUNMNAME+" in (' '))"
			}else{
    			rule=rule+""+alias+clounmName+"=(select "+CLOUNMNAME+" from "+TABLENAME+" where "+CLOUNMNAME+" in (' '))"
			}
			return rule 
		}
  	}
}
