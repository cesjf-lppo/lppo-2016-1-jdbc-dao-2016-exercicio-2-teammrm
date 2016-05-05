package br.cesjf.lppo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger; 

public class AtividadeDAO {

    List<Atividade> listaTodos() throws Exception {
        List<Atividade> todos = new ArrayList<>();
        try {
            Connection conexao = ConexaoJDBC.getInstance();
            Statement operacao = conexao.createStatement();
            ResultSet resultado = operacao.executeQuery("SELECT * FROM atividade");
            while (resultado.next()) {
                Atividade ativ = new Atividade();
                ativ.setId(resultado.getLong("id"));
                ativ.setDescricao(resultado.getString("Descricao"));
                ativ.setFuncionario(resultado.getString("Funcionario"));
                ativ.setHoras(resultado.getInt("Horas"));
                ativ.setTipo(resultado.getString("Tipo"));                
                todos.add(ativ);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }

        return todos;
    }

    void criar(Atividade novaativ) throws Exception {
        try {
            Connection conexao = ConexaoJDBC.getInstance();
            Statement operacao = conexao.createStatement();
            operacao.executeUpdate(String.format("INSERT INTO atividade(Descricao, Funcionario, Horas, Tipo) VALUES('%s','%s',%i,'%s')", novaativ.getDescricao(), novaativ.getFuncionario(),novaativ.getHoras(),novaativ.getTipo()));

        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }
}
