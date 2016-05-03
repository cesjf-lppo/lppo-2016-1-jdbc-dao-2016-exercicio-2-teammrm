
package br.cesjf.lppo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoJDBC {
    private static Connection instancia = null;
    
    private ConexaoJDBC(){};
    
    public static Connection getInstance() throws SQLException{
        if(instancia == null){
            instancia = DriverManager.getConnection("jdbc:derby://localhost:1527/lppo-2016-1", "usuario", "1234");
        }
        return instancia;
    } 
    
}
