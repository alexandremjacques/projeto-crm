package br.com.ultimaschool.projeto.crm.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


@Repository
public class ClienteRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ClienteRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Cliente leClientePorId(Integer id) {
        Connection connection;
        Cliente cliente = null;
        String sql = "select id, nome, endereco, telefone, salario, nr_filhos, data_cadastro from cliente where id = ?";

        try {
            connection = jdbcTemplate.getDataSource().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                cliente = new Cliente();
                cliente.setNome(resultSet.getString("nome"));
                cliente.setEndereco(resultSet.getString("endereco"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setSalario(resultSet.getDouble("salario"));
                cliente.setNrFilhos(resultSet.getInt("nr_filhos"));
                cliente.setDataCadastro(resultSet.getDate("data_cadastro"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    public int apagaClientePorId(int id) {
        Connection connection;

        int nrClientesApagados = 0;

        String sql = "delete from cliente where id = ?";

        try {
            connection = jdbcTemplate.getDataSource().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            nrClientesApagados = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nrClientesApagados;
    }

    public int alteraClientePorId(int id, String nome, String endereco, String telefone, double salario, int nrFilhos,
                                  java.util.Date dataCadastro) {
        int nrClientesAlterados = 0;

        String sql = "update cliente set nome = :nome, endereco = :endereco, telefone = :telefonem, salario = :salario, nr_filhos = :nrFilhos, data_cadastro = :dataCadastro where id = :id";

        try {
            MapSqlParameterSource parametros = new MapSqlParameterSource();
            parametros.addValue("id", id);
            parametros.addValue("nome", nome);
            parametros.addValue("endereco", endereco);
            parametros.addValue("telefone", telefone);
            parametros.addValue("salario", salario);
            parametros.addValue("nrFilhos", nrFilhos);
            parametros.addValue("dataCadastro", dataCadastro);

            nrClientesAlterados = namedParameterJdbcTemplate.update(sql, parametros);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return nrClientesAlterados;
    }


    public void escreveCliente(String nome, String endereco, String telefone, double salario, int nrFilhos,
                              Date dataCadastro) {
        String sql = "insert into cliente (nome, endereco, telefone, salario, nr_filhos, data_cadastro) values (:nome, :endereco, :telefone, :salario, :nrFilhos, :dataCadastro)";

        try {
            MapSqlParameterSource parametros = new MapSqlParameterSource();
            parametros.addValue("nome", nome);
            parametros.addValue("endereco", endereco);
            parametros.addValue("telefone", telefone);
            parametros.addValue("salario", salario);
            parametros.addValue("nrFilhos", nrFilhos);
            parametros.addValue("dataCadastro", dataCadastro);

            namedParameterJdbcTemplate.update(sql, parametros);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
