package com.sinosoft.one.rms.client.sqlparser.visitor;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitorAdapter;
import com.sinosoft.one.rms.client.DataRuleFactoryPostProcessor;

/**
 * Created with IntelliJ IDEA.
 * User: carvin
 * Date: 12-11-8
 * Time: 下午6:10
 * To change this template use File | Settings | File Templates.
 */
public class RmsOracleASTVisitor extends OracleASTVisitorAdapter {
	private DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor;
	
	public RmsOracleASTVisitor(DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor){
		this.dataRuleFactoryPostProcessor=dataRuleFactoryPostProcessor;
	}
    public boolean visit(OracleSelectQueryBlock x) {
//    	OracleSelectHierachicalQueryClause queryClause = x.getHierachicalQueryClause();
//		if (queryClause != null) {
//			String rule = RmsSQLASTVisitorSupport.getRule(x,dataRuleFactoryPostProcessor);
//			if(StringUtils.isNotBlank(rule)){
//				SQLExpr connectByExpr = queryClause.getConnectBy();
//				SQLBinaryOpExpr sqlBinaryOpExpr = (SQLBinaryOpExpr) connectByExpr;
//				SQLBinaryOpExpr mySqlBinaryOpExpr = new SQLBinaryOpExpr();
//				mySqlBinaryOpExpr.setRight(sqlBinaryOpExpr);
//				SQLExprParser exprParser = new SQLExprParser(rule);
//				mySqlBinaryOpExpr.setLeft(exprParser.expr());
//				mySqlBinaryOpExpr.setOperator(SQLBinaryOperator.BooleanAnd);
//				queryClause.setConnectBy(mySqlBinaryOpExpr);
//			}
//			return super.visit(x);
//		}
		return RmsSQLASTVisitorSupport.appendWhereClause(x, dataRuleFactoryPostProcessor);
//		return RmsSQLASTVisitorSupport.visit(x, dataRuleFactoryPostProcessor);
    }
}
