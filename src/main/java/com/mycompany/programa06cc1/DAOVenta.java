/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programa06cc1;


import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author braum
 */
public class DAOVenta implements IDAOGeneral<Venta, Long>{

    @Override
    public Venta create(Venta p) {
        Session session = HibernateUtil.getSession();
        Transaction t = session.beginTransaction();
        
        session.save(p);
        
        for(Iterator<DetalleVenta>  iterator = p.getDetalle().iterator(); iterator.hasNext();){
            DetalleVenta det = iterator.next();
            session.save(det);
        }
        
        t.commit();
        session.close();
        return p;
    }

    @Override
    public boolean delete(Long id) {
        Session session= HibernateUtil.getSession();
        Transaction t= session.beginTransaction();
        //Venta v=
        boolean res=false;
        Venta venta=findByID(id);
        if(venta==null){
            res= false;
        }
        else{
            Query q = session.createQuery("delete DetalleVenta where idVenta ="+id);
            q.executeUpdate();
            session.delete(venta);
            res= true;
        }
        t.commit();
        session.close();
        return res;
    }

    @Override
    public Venta update(Long id, Venta p) {
        Session session=HibernateUtil.getSession();
        Transaction t=session.beginTransaction();
        Venta vent=session.get(Venta.class, id);
        vent.setFecha(p.getFecha());
        vent.setMonto(p.getMonto());
        vent.setDetalle(p.getDetalle());
        session.update(vent);
        
        if (vent != null && p.getDetalle() != null) {;
            Query q = session.createQuery("delete DetalleVenta where idVenta ="+id);
            q.executeUpdate();
            for (DetalleVenta det: vent.getDetalle()) {
                //DetalleVenta det = iterator.next();
                det.setVenta(vent);
                session.save(det);
            }

        }
        t.commit();
        session.close();
        return vent;
    }

    @Override
    public List<Venta> findAll() {
        List<Venta> ventas=null;
        Session session = HibernateUtil.getSession();
        Transaction t = session.beginTransaction();
        ventas = session.createQuery("from Venta").list();
        for (Venta venta : ventas) {
            List<DetalleVenta> detalle = null;
            detalle = session.createQuery("from DetalleVenta where idVenta=" + venta.getIdVenta()).list();
            for (DetalleVenta detalleVenta : detalle) {
                venta.getDetalle().add(detalleVenta);
            }
        }
        t.commit();
        return ventas;
    }

    @Override
    public Venta findByID(Long id) {
        Session session=HibernateUtil.getSession();
        Transaction t=session.beginTransaction();
        List<DetalleVenta> detalle=null;
        detalle=session.createQuery("from DetalleVenta").list();
        Venta vent=session.get(Venta.class, id);
        
        if(vent!=null){
            for (DetalleVenta detalleVenta : detalle) {
                vent.getDetalle().add(detalleVenta);
            }
            t.commit();
            session.close();
            return vent;
        }
        else{
            return null;
        }
    }
    
    
}
