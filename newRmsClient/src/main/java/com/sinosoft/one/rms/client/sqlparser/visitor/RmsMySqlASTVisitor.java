package com.sinosoft.one.rms.client.sqlparser.visitor;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.sinosoft.one.rms.client.DataRuleFactoryPostProcessor;


/**
 * Created with IntelliJ IDEA.
 * User: carvin
 * Date: 12-11-8
 * Time: 下午6:10
 * To change this template use File | Settings | File Templates.
 */
public class RmsMySqlASTVisitor extends MySqlASTVisitorAdapter {
	private DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor;
	
	public RmsMySqlASTVisitor(DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor){
		this.dataRuleFactoryPostProcessor=dataRuleFactoryPostProcessor;
	}
    public boolean visit(MySqlSelectQueryBlock x) {
        return super.visit(x);
    }
}
