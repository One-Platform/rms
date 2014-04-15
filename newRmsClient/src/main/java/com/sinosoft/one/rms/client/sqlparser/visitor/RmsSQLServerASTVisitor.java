package com.sinosoft.one.rms.client.sqlparser.visitor;

import com.alibaba.druid.sql.dialect.sqlserver.ast.SQLServerSelectQueryBlock;
import com.alibaba.druid.sql.dialect.sqlserver.visitor.SQLServerASTVisitorAdapter;
import com.sinosoft.one.rms.client.DataRuleFactoryPostProcessor;

/**
 * Created with IntelliJ IDEA.
 * User: carvin
 * Date: 12-11-8
 * Time: 下午6:10
 * To change this template use File | Settings | File Templates.
 */
public class RmsSQLServerASTVisitor extends SQLServerASTVisitorAdapter {
	
	private DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor;
	
	public RmsSQLServerASTVisitor(DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor){
		this.dataRuleFactoryPostProcessor=dataRuleFactoryPostProcessor;
	}
    public boolean visit(SQLServerSelectQueryBlock x) {
        return super.visit(x);
    }
}
