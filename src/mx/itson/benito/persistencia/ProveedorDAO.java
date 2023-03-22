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
public class ProveedorDAO {
    
    /**
     * 
     * @return 
     */
    public List<Proveedor> ObtenerTodos(){
    
        List<Proveedor> proveedores = new ArrayList<>();

        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            CriteriaQuery<Proveedor> criteriaQuery = session.getCriteriaBuilder().createQuery(Proveedor.class);
            criteriaQuery.from(Proveedor.class);
            
            proveedores = session.createQuery(criteriaQuery).getResultList();
                    
        }catch(Exception ex){
            
            System.err.println("Ocurrio un error" + ex);
            
        }
        
        return proveedores;
    
    }
    
    public boolean guardar(String nombre, String clave, String telefono, String contacto){
        
        boolean resultado = false;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            Proveedor p = new Proveedor();
            p.setNombre(nombre);
            p.setClave(clave);
            p.setTelefono(telefono);
            p.setContacto(contacto);
            
            session.save(p);
            
            session.getTransaction().commit();
            
            resultado = p.getId() != 0;
            
        }catch(Exception ex){
            
                System.err.println("Ocurrio un error" + ex.getMessage());
            
        }
        
        return resultado;
        
    }
    
    public Proveedor obtenerPorId(int id){
        
        Proveedor proveedor = new Proveedor();
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            proveedor = session.get(Proveedor.class,  id);
            
        }catch(HibernateException ex){
            
            System.err.println("Ocurrio un error: " + ex);
            
        }
        
        return proveedor;
        
    }
    
    public boolean editar(int id, String nombre, String clave, String telefono, String contacto){
        
        boolean resultado = false;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            Proveedor proveedor= obtenerPorId(id);
            
            if(proveedor != null){
                
                proveedor.setNombre(nombre);
                proveedor.setClave(clave);
                proveedor.setTelefono(telefono);
                proveedor.setContacto(contacto);
                
                session.saveOrUpdate(proveedor);
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

               Proveedor proveedor = obtenerPorId(id);

               if(proveedor != null){

                   session.delete(proveedor);
                   session.getTransaction().commit();

                   resultado = true;

               }

            }catch(HibernateException ex){

               System.err.println("Ocurrio un error: " + ex);

           }
            
        }
         
         return resultado;
        
    }
    
}
