/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itson.benito.persistencia;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import javax.swing.JOptionPane;
import mx.itson.benito.entidades.*;
import mx.itson.benito.utilerias.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author lahg2
 */
public class ArticuloDAO {
    
    /**
     * Sirve parra obtener los datos de la tabla articulo
     * @return Una lista de tipo Articulo con todos los articulos contenidos en ella
     */
    public List<Articulo> ObtenerTodos(){
    
        List<Articulo> articulos = new ArrayList<>();

        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            CriteriaQuery<Articulo> criteriaQuery = session.getCriteriaBuilder().createQuery(Articulo.class);
            criteriaQuery.from(Articulo.class);
            
            articulos = session.createQuery(criteriaQuery).getResultList();
                    
        }catch(Exception ex){
            
            System.err.println("Ocurrio un error" + ex);
            
        }
        
        return articulos;
    
    }
    
    public boolean guardar(String nombre, float precio, Proveedor proveedor){
        
        boolean resultado = false;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            Articulo a = new Articulo();
            a.setNombre(nombre);
            a.setPrecio(precio);
            a.setProveedor(proveedor);
            
            session.save(a);
            
            session.getTransaction().commit();
            
            resultado = a.getId() != 0;
            
        }catch(Exception ex){
            
                System.err.println("Ocurrio un error" + ex.getMessage());
            
        }
        
        return resultado;
        
    }
    
    public Articulo obtenerPorId(int id){
        
        Articulo articulo = new Articulo();
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            articulo = session.get(Articulo.class,  id);
            
        }catch(HibernateException ex){
            
            System.err.println("Ocurrio un error: " + ex);
            
        }
        
        return articulo;
        
    }
    
    public boolean editar(int id, String nombre, float precio, Proveedor proveedor){
        
        boolean resultado = false;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            Articulo articulo  = obtenerPorId(id);
            
            if(articulo != null){
                
                articulo.setNombre(nombre);
                articulo.setPrecio(precio);
                articulo.setProveedor(proveedor);
                
                session.saveOrUpdate(articulo);
                session.getTransaction().commit();
                
                resultado = true;
                
            }
            
        }catch(HibernateException ex){
            
            System.err.println("Ocurrio un error: " + ex);
            
        }
        
        return resultado;
        
    }
    
    public boolean eliminar(int id){
        
        boolean resultado = false;
        
        String[] options = {"Si", "No"};
        int x = JOptionPane.showOptionDialog(null, "El registro sera eliminado ¿Esta seguro de esto?", "Atencion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        
        if(x == 0){
        
            try{

               Session session = HibernateUtil.getSessionFactory().openSession();
               session.beginTransaction();

               Articulo articulo = obtenerPorId(id);

               if(articulo != null){

                   session.delete(articulo);
                   session.getTransaction().commit();

                   resultado = true;

               }

            }catch(HibernateException ex){

               System.err.println("Ocurrio un error: " + ex);

           }
            
        }
         
         return resultado;
        
    }
    
    public Articulo devolverArticulo(Articulo a){
        
        Articulo articulo = new Articulo();
        
        try{
            
            articulo = a;
            
        }catch(Exception ex){
            
            System.err.println("Ocurrio un error: " + ex.getMessage());
            
        }
        
        return articulo;
        
    }
    
}
