/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.programa06cc1;
import java.time.Instant;
import java.util.Date;

/**
 *
 * @author braum
 */
public class Programa06CC1 {

    public static void main(String[] args) {
        
        DAOVenta dao = new DAOVenta();
        Venta venta = new Venta();
        
        DetalleVenta d1 = new DetalleVenta();
        DetalleVenta d2 = new DetalleVenta();
        
        Date fecha = Date.from(Instant.now());
        venta.setFecha(new java.sql.Date(fecha.getTime()));
        
        venta.setMonto(1000.00);
        
        d1.setDescripcion("llsaldsadas");
        d1.setCantidad(3);
        d1.setPrecio(34);
        d1.setSubtotal(342);
        d1.setVenta(venta);
        venta.getDetalle().add(d1);
        
        d2.setDescripcion("llssdadascxss");
        d2.setCantidad(4);
        d2.setPrecio(32);
        d2.setSubtotal(3421);
        d2.setVenta(venta);
        venta.getDetalle().add(d2);
        
        
        
        dao.create(venta);
        System.out.println("Hello World!");
    }
}
