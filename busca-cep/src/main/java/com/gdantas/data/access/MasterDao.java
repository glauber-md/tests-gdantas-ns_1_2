/**
 * 
 */
package com.gdantas.data.access;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Glauber M. Dantas (glauber.dantas@tvftecnologia.com.br) TVF Tecnologia
 *
 */
public class MasterDao {

	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("dsNs")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
