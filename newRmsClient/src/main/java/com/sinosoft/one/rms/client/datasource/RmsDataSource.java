package com.sinosoft.one.rms.client.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.one.rms.client.DataRuleFactoryPostProcessor;
import com.sinosoft.one.rms.client.sqlparser.RmsSQLParser;


public class RmsDataSource implements DataSource {
	
	@Autowired
	private DataSource realDataSource;
	
	@Autowired
	private RmsSQLParser rmsSQLParser;
	
	@Autowired
	private DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor;
	
	public RmsDataSource(DataSource dataSource) {
		this.realDataSource = dataSource;
	}
	
	public RmsDataSource() {
		
	}

	public Connection getConnection() throws SQLException {
		return new RmsConnection(realDataSource.getConnection(),rmsSQLParser,dataRuleFactoryPostProcessor);
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return realDataSource.getConnection(username, password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return realDataSource.getLogWriter();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		realDataSource.setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		realDataSource.setLoginTimeout(seconds);
	}

	public int getLoginTimeout() throws SQLException {
		return realDataSource.getLoginTimeout();
	}
	
	
	

	public void setRmsSQLParser(RmsSQLParser rmsSQLParser) {
		this.rmsSQLParser = rmsSQLParser;
	}

	public void setDataRuleFactoryPostProcessor(
			DataRuleFactoryPostProcessor dataRuleFactoryPostProcessor) {
		this.dataRuleFactoryPostProcessor = dataRuleFactoryPostProcessor;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
}
