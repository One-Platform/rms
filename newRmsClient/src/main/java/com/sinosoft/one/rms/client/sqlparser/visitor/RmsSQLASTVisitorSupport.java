package com.sinosoft.one.rms.client.sqlparser.visitor;

import ins.framework.utils.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.sinosoft.one.rms.DataPower;
import com.sinosoft.one.rms.User;
import com.sinosoft.one.rms.client.DataRuleFactoryPostProcessor;
import com.sinosoft.one.rms.client.EnvContext;
import com.sinosoft.one.rms.client.ShiroLoginUser;
import com.sinosoft.one.rms.model.BusDataInfo;

/**
 * Created with IntelliJ IDEA.
 * User: carvin
 * Date: 12-11-8
 * Time: 下午6:06
 * To change this template use File | Settings | File Templates.
 */
public class RmsSQLASTVisitorSupport {
    
	
	public static Map<String, String> getTableAlias(SQLSelectQueryBlock x) {
		Map<String, String> tableAlias = new HashMap<String, String>();
        
        if (x.getFrom() instanceof SQLExprTableSource) {
        	recordTableSource((SQLExprTableSource)x.getFrom(), tableAlias);
        } else if(x.getFrom() instanceof SQLJoinTableSource) {
        	SQLJoinTableSource tableSource = (SQLJoinTableSource)x.getFrom();
            doSQLJoinTableSource(tableSource, tableAlias);
        }

        List<SQLSelectItem> sqlSelectItems = x.getSelectList();
        if(sqlSelectItems.size() > 1 || !sqlSelectItems.get(0).equals("*")) {
            Set<String> tableAliasSet = new HashSet<String>();
            for(SQLSelectItem item : sqlSelectItems) {
                String itemExpr = item.getExpr().toString();
                String[] itemExprParts = itemExpr.split("\\.");
                if(itemExpr.length() == 2) {
                    tableAliasSet.add(itemExprParts[0]);
                }
            }

            if(!tableAliasSet.isEmpty()) {
                Set<String> tableAliasKeys = tableAlias.keySet();
                Iterator<String> tableAliasKeysIt = tableAliasKeys.iterator();
                while(tableAliasKeysIt.hasNext()) {
                    String aTableAlias = tableAliasKeysIt.next();
                    if(!tableAliasSet.contains(aTableAlias)) {
                        tableAliasKeysIt.remove();
                    }
                }
            }
        }
        return tableAlias;
	}
	
	public static String getRule(SQLSelectQueryBlock x ,DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor) {
		Map<String, String> tableAlias = getTableAlias(x);
		String rule="";
//      displayTableAlias(tableAlias);
		User user= ShiroLoginUser.getLoginUser();
//		User user= EnvContext.getLogin();
		for (DataPower dataPower : user.getDataPowers()) {
			if (dataPower.getTaskId().toString().equals(EnvContext.getDataAuthorityTaskId().toString())) {
				for (BusDataInfo busDataInfo : dataPower.getBusDataInfos()) {
					for(String key : tableAlias.keySet()) {
						if (tableAlias.get(key).equals(busDataInfo.getBusDataTable().toString().toLowerCase())){
							if(!key.toString().equals(tableAlias.get(key).toString())){
								System.out.println("1111111111111-----------"+dataPower.getComCode());
								rule = dataRuleFactoryPostProcessor.getScript(dataPower.getRuleId()).creatSQL(rule, key, dataPower.getUserCode(), dataPower.getComCode(), dataPower.getParam(),busDataInfo.getBusDataColumn());
								System.out.println(rule);
							}
							else {
								System.out.println("2222222222222-----------"+dataPower.getComCode());
								rule = dataRuleFactoryPostProcessor.getScript(dataPower.getRuleId()).creatSQL(rule, null, dataPower.getUserCode(), dataPower.getComCode(), dataPower.getParam(),busDataInfo.getBusDataColumn());
								System.out.println(rule);
							}
						}
					}
				}
			}
		}
		return rule;
	}
	
	public static boolean appendWhereClause(SQLSelectQueryBlock x, DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor) {
		SQLExpr whereExpr;
		whereExpr = x.getWhere();
		String rule = getRule(x, dataRuleFactoryPostProcessor);
		if(StringUtils.isNotBlank(rule)){
			if(x.getWhere()!=null){
			
				SQLBinaryOpExpr sqlBinaryOpExpr = (SQLBinaryOpExpr) whereExpr;
				SQLBinaryOpExpr mySqlBinaryOpExpr = new SQLBinaryOpExpr();
				mySqlBinaryOpExpr.setRight(sqlBinaryOpExpr);
				SQLExprParser exprParser = new SQLExprParser(rule);
				mySqlBinaryOpExpr.setLeft(exprParser.expr());
				mySqlBinaryOpExpr.setOperator(SQLBinaryOperator.BooleanAnd);
				x.setWhere(mySqlBinaryOpExpr);
				return true;
			}else{
				SQLExprParser exprParser = new SQLExprParser(rule);
				x.setWhere(exprParser.expr());
			}
		}
			
		return true;
	}
	
    
    private static void doSQLJoinTableSource(SQLJoinTableSource tableSource, Map<String, String> tableAlias) {
        if (tableSource.getLeft() instanceof SQLExprTableSource) {
            recordTableSource((SQLExprTableSource)tableSource.getLeft(), tableAlias);
        }
        if (tableSource.getRight() instanceof SQLExprTableSource) {
            recordTableSource((SQLExprTableSource)tableSource.getRight(), tableAlias);
        }
        if (tableSource.getRight() instanceof SQLJoinTableSource) {
            doSQLJoinTableSource((SQLJoinTableSource)tableSource.getRight(), tableAlias);
        }
    }

    private static void recordTableSource(SQLExprTableSource x, Map<String, String> tableAlias) {
        if (x.getExpr() instanceof SQLIdentifierExpr) {
            String tableName = ((SQLIdentifierExpr) x.getExpr()).getName().toLowerCase();
            String alias=null;
            if(StringUtils.isNotBlank(x.getAlias())){
            	alias  = x.getAlias().toLowerCase();;
            }
            if (alias != null) {
                tableAlias.put(alias, tableName);
            }else{
            	tableAlias.put(tableName, tableName);
            }
        }
    }

}
