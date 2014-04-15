package com.sinosoft.one.rms.client.sqlparser.visitor;

import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectQueryBlock;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGASTVisitorAdapter;
import com.sinosoft.one.rms.client.DataRuleFactoryPostProcessor;

/**
 * Created with IntelliJ IDEA.
 * User: carvin
 * Date: 12-11-8
 * Time: 下午6:10
 * To change this template use File | Settings | File Templates.
 */
public class RmsPGASTVisitor extends PGASTVisitorAdapter {
	
	private DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor;
	
	public RmsPGASTVisitor(DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor){
		this.dataRuleFactoryPostProcessor=dataRuleFactoryPostProcessor;
	}
	public boolean visit(PGSelectQueryBlock x) {
        return super.visit(x);
    }
}
