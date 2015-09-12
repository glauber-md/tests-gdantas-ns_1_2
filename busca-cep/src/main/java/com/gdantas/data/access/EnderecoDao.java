/**
 * 
 */
package com.gdantas.data.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.gdantas.data.domain.Endereco;

/**
 * @author Glauber M. Dantas glauber.md@gmail.com
 *
 */
public class EnderecoDao extends MasterDao {
	
	public List<Endereco> listAll() {
		List<Endereco> enderecos = this.jdbcTemplate.query(
				"SELECT a.id, a.cep, a.logradouro, a.numero, a.complemento, a.bairro, b.nome as cidade, c.sigla as estado FROM Endereco AS a INNER JOIN Cidade AS b ON b.id = a.cidadeId INNER JOIN Estado AS c ON c.id = b.estadoId", 
				new EnderecoMapper());
		return enderecos;
	}
	
	public Endereco get(int id) {
		List<Endereco> enderecos = this.jdbcTemplate.query(
				MessageFormat.format("SELECT a.id, a.cep, a.logradouro, a.numero, a.complemento, a.bairro, b.nome as cidade, c.sigla as estado FROM Endereco AS a INNER JOIN Cidade AS b ON b.id = a.cidadeId INNER JOIN Estado AS c ON c.id = b.estadoId WHERE a.id = {0,number,#}", id), 
				new EnderecoMapper());
		return (enderecos != null && !enderecos.isEmpty()) ? enderecos.get(0) : null;
	}
	
	public Endereco getByCep(String cep) {
		List<Endereco> enderecos = this.jdbcTemplate.query(
				MessageFormat.format("SELECT a.id, a.cep, a.logradouro, a.numero, a.complemento, a.bairro, b.nome as cidade, c.sigla as estado FROM Endereco AS a INNER JOIN Cidade AS b ON b.id = a.cidadeId INNER JOIN Estado AS c ON c.id = b.estadoId WHERE a.cep = ''{0}''", cep), 
				new EnderecoMapper());
		return (enderecos != null && !enderecos.isEmpty()) ? enderecos.get(0) : null;
	}
	
	public int add(Endereco end) {
		return this.jdbcTemplate.update(
				"INSERT INTO Endereco(cep,logradouro,numero,complemento,bairro,cidadeId) VALUES(?,?,?,?,?,?)", 
				end.getCep(), end.getRua(), end.getNumero(), end.getComplemento(), end.getBairro(), end.getCidade()
		);
	}
	
	public int update(Endereco end) {
		return this.jdbcTemplate.update(
				"UPDATE Endereco SET cep = ?,logradouro = ?,numero = ?,complemento = ?,bairro = ?,cidadeId = ? WHERE id = ?", 
				end.getCep(), end.getRua(), end.getNumero(), end.getComplemento(), end.getBairro(), end.getCidade()
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
			o.setRua(rs.getString("logradouro"));
			o.setNumero(rs.getString("numero"));
			o.setComplemento(rs.getString("complemento"));
			o.setBairro(rs.getString("bairro"));
			o.setCidade(rs.getString("cidade"));
			o.setEstado(rs.getString("estado"));
			return o;
		}
		
	}
}
