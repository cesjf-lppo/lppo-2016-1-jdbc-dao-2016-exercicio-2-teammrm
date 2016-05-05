package br.cesjf.lppo;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AtividadeDAOPrep {
        private PreparedStatement operacaoListarTodos;
        private PreparedStatement operacaoCriar;
        private PreparedStatement operacaoExcluirPorId;

        
    public AtividadeDAOPrep() throws Exception {
            try {
                operacaoListarTodos = ConexaoJDBC.getInstance().prepareStatement("SELECT * FROM atividade");
                operacaoCriar = ConexaoJDBC.getInstance().prepareStatement("INSERT INTO atividade(descricao, funcionario, horas, tipo) VALUES(?,?,?,?)", new String[]{"id"});
                operacaoExcluirPorId = operacaoExcluirPorId = ConexaoJDBC.getInstance().prepareStatement("DELETE FROM atividade WHERE id=?");

            } catch (SQLException ex) {
                Logger.getLogger(AtividadeDAOPrep.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception(ex);
            }
    }
        
        
    public List<Atividade> listaTodos() throws Exception {
        List<Atividade> todos = new ArrayList<>();
        try {
            ResultSet resultado = operacaoListarTodos.executeQuery();
            while (resultado.next()) {
                Atividade ativ = new Atividade();
                ativ.setId(resultado.getLong("id"));
                ativ.setDescricao(resultado.getString("descricao"));
                ativ.setFuncionario(resultado.getString("funcionario"));
                ativ.setHoras(resultado.getInt("horas"));
                ativ.setTipo(resultado.getString("tipo"));                
                todos.add(ativ);
            }


        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAOPrep.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }

        return todos;
    }

    public void criar(Atividade novaativ) throws Exception {
        try {                  
            operacaoCriar.setString(1, novaativ.getFuncionario());
            operacaoCriar.setString(2, novaativ.getDescricao());            
            operacaoCriar.setInt(3, novaativ.getHoras());
            operacaoCriar.setString(4, novaativ.getTipo());
            
            operacaoCriar.executeUpdate();
            ResultSet keys = operacaoCriar.getGeneratedKeys();
            if (keys.next()) {
                novaativ.setId(keys.getLong(1));
            }           
        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAOPrep.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }

    public void excluirPorId(Long id) throws Exception {
        try {
            operacaoExcluirPorId.setLong(1, id);
            operacaoExcluirPorId.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAOPrep.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }

    }

    public void excluir(Atividade novaativ) throws Exception {
        excluirPorId(novaativ.getId());
    }

    public void salvar(Atividade novaativ) throws Exception {
        Connection conexao = ConexaoJDBC.getInstance();
        Statement operacao = conexao.createStatement();
        try {
           operacao.executeUpdate(String.format("INSERT INTO atividade(Descricao, Funcionario, Horas, Tipo) VALUES('%s','%s',%i,'%s')", novaativ.getDescricao(), novaativ.getFuncionario(),novaativ.getHoras(),novaativ.getTipo()));
 } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }
    
    public Atividade buscaPorId(Long id) throws Exception {
        Atividade estab = null;
        try {
            Connection conexao = ConexaoJDBC.getInstance();
            Statement operacao = conexao.createStatement();
            ResultSet resultado = operacao.executeQuery(String.format("SELECT * FROM atividade WHERE id=%d", id));
            if (resultado.next()) {
                 Atividade ativ = new Atividade();
                ativ.setId(resultado.getLong("id"));
                ativ.setDescricao(resultado.getString("descricao"));
                ativ.setFuncionario(resultado.getString("funcionario"));
                ativ.setHoras(resultado.getInt("horas"));
                ativ.setTipo(resultado.getString("tipo"));  
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAOPrep.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
        return estab;
    }
    
    
    
}
