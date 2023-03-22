/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itson.benito.persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import mx.itson.benito.entidades.*;
import mx.itson.benito.utilerias.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author lahg2
 */
public class OrdenCompraDAO {
    
    /**
     * 
     * @return 
     */
    public List<OrdenCompra> ObtenerTodos(){
    
        List<OrdenCompra> ordenes = new ArrayList<>();

        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            CriteriaQuery<OrdenCompra> criteriaQuery = session.getCriteriaBuilder().createQuery(OrdenCompra.class);
            criteriaQuery.from(OrdenCompra.class);
            
            ordenes = session.createQuery(criteriaQuery).getResultList();
                    
        }catch(Exception ex){
            
            System.err.println("Ocurrio un error: " + ex);
            
        }
        
        return ordenes;
    
    }
    
    public boolean guardar(String cliente, String direccion, String telefono, Proveedor proveedor, String folio, float subtotal, Date fecha, List<Articulo> articulos){
        
        boolean resultado = false;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            OrdenCompra o = new OrdenCompra();
            o.setCliente(cliente);
            o.setDireccion(direccion);
            o.setTelefono(telefono);
            o.setProveedor(proveedor);
            o.setFolio(folio);
            o.setSubtotal(subtotal);
            
            float iva = (float) (subtotal * 0.16);
            float total = subtotal + iva;
            
            o.setIva(iva);
            o.setTotal(total);
            o.setFecha(fecha);
            o.setArticulos(articulos);
            
            session.save(o);
            
            session.getTransaction().commit();
            
            resultado = o.getId() != 0;
            
        }catch(Exception ex){
            
                System.err.println("Ocurrio un error" + ex.getMessage());
            
        }
        
        return resultado;
        
    }
    
    public OrdenCompra obtenerPorId(int id){
        
        OrdenCompra orden = new OrdenCompra();
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            orden = session.get(OrdenCompra.class,  id);
            
        }catch(HibernateException ex){
            
            System.err.println("Ocurrio un error: " + ex);
            
        }
        
        return orden;
        
    }
    
    public boolean editar(int id, String cliente, String direccion, String telefono, Proveedor proveedor, String folio, float subtotal, Date fecha, List<Articulo> articulos){
        
        boolean resultado = false;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            OrdenCompra o = obtenerPorId(id);
            
            if(proveedor != null){
                
                o.setCliente(cliente);
                o.setDireccion(direccion);
                o.setTelefono(telefono);
                o.setProveedor(proveedor);
                o.setFolio(folio);
                o.setSubtotal(subtotal);

                float iva = (float) (subtotal * 0.16);
                float total = subtotal + iva;

                o.setIva(iva);
                o.setTotal(total);
                o.setFecha(fecha);
                o.setArticulos(articulos);
                
                session.saveOrUpdate(o);
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

               OrdenCompra orden= obtenerPorId(id);

               if(orden != null){

                   session.delete(orden);
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
