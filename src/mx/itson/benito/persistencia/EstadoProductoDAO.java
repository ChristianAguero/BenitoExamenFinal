/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.benito.persistencia;

import java.util.Date;
import mx.itson.benito.entidades.*;
import mx.itson.benito.enumeradores.Estado;
import mx.itson.benito.utilerias.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Christian
 */
public class EstadoProductoDAO {
    
    public boolean guardar(Estado estado, Date fecha, String comentario, OrdenCompra orden){
        
        boolean resultado = false;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            EstadoProducto e = new EstadoProducto();
            e.setEstado(estado);
            e.setFecha(fecha);
            e.setComentario(comentario);
            e.setOrden(orden);
            
            session.save(e);
            
            session.getTransaction().commit();
            
            resultado = e.getId() != 0;
            
        }catch(Exception ex){
            
                System.err.println("Ocurrio un error" + ex.getMessage());
            
        }
        
        return resultado;
        
    }
    
    public EstadoProducto obtenerPorId(int id){
        
        EstadoProducto estado = new EstadoProducto();
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            estado = session.get(EstadoProducto.class,  id);
            
        }catch(HibernateException ex){
            
            System.err.println("Ocurrio un error: " + ex);
            
        }
        
        return estado;
        
    }
    
    
}
