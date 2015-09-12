/**
 * 
 */
package com.gdantas.data.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.gdantas.data.domain.Endereco;

/**
 * @author Glauber M. Dantas glauber.md@gmail.com
 *
 */
public class EnderecoDao extends MasterDao {
	
	public List<Endereco> listAll() {
		List<Endereco> enderecos = this.jdbcTemplate.query(
				"SELECT a.id, a.cep, a.rua, a.numero, a.complemento, a.bairro, a.cidade, a.estado FROM Endereco AS a", 
				new EnderecoMapper());
		return enderecos;
	}
	
	public Endereco get(int id) {
		List<Endereco> enderecos = this.jdbcTemplate.query(
				MessageFormat.format("SELECT a.id, a.cep, a.rua, a.numero, a.complemento, a.bairro, a.cidade, a.estado FROM Endereco AS a WHERE a.id = {0,number,#}", id), 
				new EnderecoMapper());
		return (enderecos != null && !enderecos.isEmpty()) ? enderecos.get(0) : null;
	}
	
	public Endereco getByCep(String cep) {
		List<Endereco> enderecos = this.jdbcTemplate.query(
				MessageFormat.format("SELECT a.id, a.cep, a.rua, a.numero, a.complemento, a.bairro, a.cidade, a.estado FROM Endereco AS a WHERE a.cep = ''{0}''", cep), 
				new EnderecoMapper());
		return (enderecos != null && !enderecos.isEmpty()) ? enderecos.get(0) : null;
	}
	
	public int add(Endereco end) {
		Number genKey = 0;
		
		SimpleJdbcInsert jdbcInsert = null;
		Map<String, Object> parameters = null;
		// Insere registro e obtém chave gerada
		jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
		jdbcInsert.withTableName("Endereco").usingGeneratedKeyColumns("ID");
		parameters = new HashMap<>();
		parameters.put("cep", end.getCep());
		parameters.put("rua", end.getRua());
		parameters.put("numero", end.getNumero());
		parameters.put("complemento", end.getComplemento());
		parameters.put("bairro", end.getBairro());
		parameters.put("cidade", end.getCidade());
		parameters.put("estado", end.getEstado());
		try {
			genKey = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		} catch(Exception e) {
			/*
			 *  Ignora neste momento pois o retorno controla a ação a tomar;
			 *  numa aplicação de produção, isto deve ser tratado.
			 */
		}
		
		return genKey.intValue();
	}
	
	public int update(Endereco end) {
		return this.jdbcTemplate.update(
				"UPDATE Endereco SET cep = ?,rua = ?,numero = ?,complemento = ?,bairro = ?,cidade = ?,estado = ? WHERE id = ?", 
				end.getCep(), end.getRua(), end.getNumero(), end.getComplemento(), end.getBairro(), end.getCidade(), end.getEstado(), end.getId()
		);
	}
	
	public int delete(int id) {
		return this.jdbcTemplate.update(
				MessageFormat.format("DELETE FROM Endereco WHERE id = {0,number,#}", id)
		);
	}
	
	private class EnderecoMapper implements RowMapper<Endereco> {
		
		@Override
		public Endereco mapRow(ResultSet rs, int row) throws SQLException {
			Endereco o = new Endereco();
			o.setId(rs.getInt("id"));
			o.setCep(rs.getString("cep"));
			o.setRua(rs.getString("rua"));
			o.setNumero(rs.getString("numero"));
			o.setComplemento(rs.getString("complemento"));
			o.setBairro(rs.getString("bairro"));
			o.setCidade(rs.getString("cidade"));
			o.setEstado(rs.getString("estado"));
			return o;
		}
		
	}
}
