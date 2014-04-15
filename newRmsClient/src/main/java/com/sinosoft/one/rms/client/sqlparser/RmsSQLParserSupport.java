package com.sinosoft.one.rms.client.sqlparser;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGOutputVisitor;
import com.alibaba.druid.sql.dialect.sqlserver.visitor.SQLServerOutputVisitor;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;
import com.alibaba.druid.util.JdbcUtils;
import com.sinosoft.one.rms.client.DataRuleFactoryPostProcessor;
import com.sinosoft.one.rms.client.sqlparser.visitor.RmsMySqlASTVisitor;
import com.sinosoft.one.rms.client.sqlparser.visitor.RmsOracleASTVisitor;
import com.sinosoft.one.rms.client.sqlparser.visitor.RmsPGASTVisitor;
import com.sinosoft.one.rms.client.sqlparser.visitor.RmsSQLServerASTVisitor;

/**
 * Created with IntelliJ IDEA.
 * User: carvin
 * Date: 12-11-8
 * Time: 下午5:58
 * To change this template use File | Settings | File Templates.
 */
@Component("rmsSQLParser")
public class RmsSQLParserSupport implements RmsSQLParser {
//    private RmsSQLParserUtils() {}
	@Autowired
	private DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor;
	
    public  String parser(Connection connection, String sql) throws Exception{
        String url = connection.getMetaData().getURL();
        String dbType = JdbcUtils.getDbType(url,  connection.getMetaData().getDriverName());
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, dbType);
        List<SQLStatement> stmtList = parser.parseStatementList();
        StringBuilder out = new StringBuilder();
        SQLASTVisitor visitor = createSQLParserVisitor(dbType);
        SQLASTVisitor outputVisitor = RmsSQLParserSupport.createOutputVisitor(out, dbType);
        for(SQLStatement statement : stmtList) {
            statement.accept(visitor);
            statement.accept(outputVisitor);
        }
        return out.toString();
    }

    public static SQLASTOutputVisitor createOutputVisitor(StringBuilder out, String dbType) {
        if (JdbcUtils.ORACLE.equals(dbType) || JdbcUtils.ALI_ORACLE.equals(dbType)) {
        	OracleOutputVisitor oracleOutputVisitor=  new OracleOutputVisitor(out);
        	oracleOutputVisitor.setPrettyFormat(false);
            return oracleOutputVisitor;
        }

        if (JdbcUtils.MYSQL.equals(dbType)) {
        	MySqlOutputVisitor mySqlOutputVisitor=new MySqlOutputVisitor(out);
        	mySqlOutputVisitor.setPrettyFormat(false);
            return mySqlOutputVisitor;
        }

        if (JdbcUtils.POSTGRESQL.equals(dbType)) {
        	PGOutputVisitor pgOutputVisitor=new PGOutputVisitor(out);
        	pgOutputVisitor.setPrettyFormat(false);
            return pgOutputVisitor;
        }

        if (JdbcUtils.SQL_SERVER.equals(dbType)) {
        	SQLServerOutputVisitor sqlServerOutputVisitor= new SQLServerOutputVisitor(out);
        	sqlServerOutputVisitor.setPrettyFormat(false);
            return sqlServerOutputVisitor;
        }

        return new SQLASTOutputVisitor(out);
    }

    public  SQLASTVisitor createSQLParserVisitor(String dbType) {
        if (JdbcUtils.ORACLE.equals(dbType) || JdbcUtils.ALI_ORACLE.equals(dbType)) {
            return new RmsOracleASTVisitor(dataRuleFactoryPostProcessor);
        }

        if (JdbcUtils.MYSQL.equals(dbType)) {
            return new RmsMySqlASTVisitor(dataRuleFactoryPostProcessor);
        }

        if (JdbcUtils.POSTGRESQL.equals(dbType)) {
            return new RmsPGASTVisitor(dataRuleFactoryPostProcessor);
        }

        if (JdbcUtils.SQL_SERVER.equals(dbType)) {
            return new RmsSQLServerASTVisitor(dataRuleFactoryPostProcessor);
        }

        if (JdbcUtils.H2.equals(dbType)) {
            return new RmsMySqlASTVisitor(dataRuleFactoryPostProcessor);
        }

        return new SQLASTVisitorAdapter();
    }

	
}
