
package holamundo;

import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class BD_Clientes {
    
    Connection conexion = null;
    Statement estado = null;
    
    public void metodoConexion()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:restorant.sqlite");
            
            DatabaseMetaData metaDatos = conexion.getMetaData();
            ResultSet rs = metaDatos.getTables("restorant", null, "clientes", null);
            
            boolean existe = false;
            while(rs.next()){
                existe = true;
            }
            
            if(existe == false){
                JOptionPane.showMessageDialog(null, "no existe la tabla");
                String sql = "create table clientes(rut text PRIMARY KEY, nombre text, apellido text);";
                      
                estado = conexion.createStatement();
                estado.executeUpdate(sql);
                estado.close();
            }
            rs.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error en: " + e);
        }
    }
    
    public int insertar(String elRut, String elNombre, String elApellido){
        try{
            metodoConexion();
            String sql = "insert into clientes(rut, nombre, apellido) values ('"+elRut+"', '"+elNombre+"', '"+elApellido+"')";
            
            estado = conexion.createStatement();
            estado.executeUpdate(sql);
            estado.close();
            conexion.close();
            JOptionPane.showMessageDialog(null, "Datos insertados con exito");
            return 1;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error en: " + e.getMessage());
            return 0;
        }
    }
    
    public int eliminar(String elRut){
        try{
            metodoConexion();
            String sql = "delete from clientes where rut='"+elRut+"' ";
            
            estado = conexion.createStatement();
            estado.executeUpdate(sql);
            estado.close();
            conexion.close();
            JOptionPane.showMessageDialog(null, "Datos eliminados con exito");
            return 1;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error en: " + e.getMessage());
            return 0;
        }
    }
    
    public int actualizar(String elRut, String elNombre, String elApellido){
        try{
            metodoConexion();
            String sql = "update clientes set nombre='"+elNombre+"', apellido='"+elApellido+"' where rut='"+elRut+"' ";
            
            estado = conexion.createStatement();
            estado.executeUpdate(sql);
            estado.close();
            conexion.close();
            JOptionPane.showMessageDialog(null, "Datos actualizados con exito");
            return 1;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error en: " + e.getMessage());
            return 0;
        }
    }
    
    public void mostrar(String elRut, JTextField tuRut, JTextField tuNombre, JTextField tuApellido){
        try{
            metodoConexion();
            String sql = "select * from clientes where rut='"+elRut+"' ";
            
            estado = conexion.createStatement();
            ResultSet resultado = estado.executeQuery(sql);
            
            boolean existe = false;
            while(resultado.next()){
                tuRut.setText(resultado.getString("rut"));
                tuNombre.setText(resultado.getString("nombre"));
                tuApellido.setText(resultado.getString("apellido"));
                existe = true;
            }
            if(existe != true){
                JOptionPane.showMessageDialog(null, "no existe usuario");
            }
            
            estado.close();
            conexion.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error en: " + e.getMessage());
        }
    }
}
