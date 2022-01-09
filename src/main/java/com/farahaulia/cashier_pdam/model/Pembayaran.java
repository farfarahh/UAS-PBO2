/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.farahaulia.cashier_pdam.model;

import com.farahaulia.cashier_pdam.db.Database;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class Pembayaran {
    private int id;
    private String namaPelanggan;
    private double harga;
    private JenisPembayaran jenisPembayaran;
    
    private Database database;
    private Connection connection;
    
        public boolean create(){
        String insertSQL = "INSERT INTO pembayaran VALUES (NULL, ?, ?, ?)";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, this.jenisPembayaran.getId());
            preparedStatement.setString(2, this.namaPelanggan);
            preparedStatement.setDouble(3, this.harga);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JenisPembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean update(){
        String updateSQL = "UPDATE pembayaran SET idjenispembayaran=?, namapelanggan=?, harga=? WHERE id = ?";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, this.jenisPembayaran.getId());
            preparedStatement.setString(2, this.namaPelanggan);
            preparedStatement.setDouble(3, this.harga);
            preparedStatement.setInt(4, this.id);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JenisPembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean delete(){
        String deleteSQL = "DELETE FROM pembayaran WHERE id = ?";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, this.id);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Pembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void tampilLaporan(String laporanFile, String SQL) {
        try {
            File file = new File(laporanFile);
            JasperDesign jasDes = JRXmlLoader.load(file);
            
            JRDesignQuery sqlQuery = new JRDesignQuery();
            sqlQuery.setText(SQL);
            jasDes.setQuery(sqlQuery);

            JasperReport JR = JasperCompileManager.compileReport(jasDes);
            JasperPrint JP = JasperFillManager.fillReport(JR,null,getConnection());
            JasperViewer.viewReport(JP);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    
    public ArrayList<Pembayaran> read(){
        ArrayList<Pembayaran> list = new ArrayList<>();
        
        String selectSQL = "SELECT pembayaran.*, jenispembayaran.namajenispembayaran FROM pembayaran INNER JOIN jenispembayaran ON pembayaran.idjenispembayaran = jenispembayaran.id" ;
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Pembayaran b = new Pembayaran();
                
                b.setId(rs.getInt("id"));
                b.setNamaPelanggan(rs.getString("namapelanggan"));
                b.setHarga(rs.getDouble("harga"));
                
                JenisPembayaran jb = new JenisPembayaran();
                jb.setId(rs.getInt("idjenispembayaran"));
                jb.setNamajenispembayaran(rs.getString("namajenispembayaran"));
                
                b.setJenisPembayaran(jb);
                
                list.add(b);
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(JenisPembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public ArrayList<Pembayaran> search(String keywords){
        keywords = "%" + keywords + "%";
        ArrayList<Pembayaran> list = new ArrayList<>();
        
        String selectSQL = "SELECT pembayaran.*, jenispembayaran.namajenispembayaran FROM pembayaran \n" +
"INNER JOIN jenispembayaran ON pembayaran.idjenispembayaran = jenispembayaran.id "
                + "WHERE namapelanggan like ? OR namajenispembayaran like ?" ;
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, keywords);
            preparedStatement.setString(2, keywords);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Pembayaran b = new Pembayaran();
                
                b.setId(rs.getInt("id"));
                b.setNamaPelanggan(rs.getString("namapelanggan"));
                b.setHarga(rs.getDouble("harga"));
                
                JenisPembayaran jb = new JenisPembayaran();
                jb.setId(rs.getInt("idjenispembayaran"));
                jb.setNamajenispembayaran(rs.getString("namajenispembayaran"));
                
                b.setJenisPembayaran(jb);
                
                list.add(b);
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(JenisPembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public void find(){
        
        String selectSQL = "SELECT pembayaran.*, jenispembayaran.namajenispembayaran FROM pembayaran \n" +
"INNER JOIN jenispembayaran ON pembayaran.idjenispembayaran = jenispembayaran.id "
                + "WHERE pembayaran.id = ?" ;
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, this.id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                
                setId(rs.getInt("id"));
                setNamaPelanggan(rs.getString("namapelanggan"));
                setHarga(rs.getDouble("harga"));
                
                JenisPembayaran jb = new JenisPembayaran();
                jb.setId(rs.getInt("idjenispembayaran"));
                jb.setNamajenispembayaran(rs.getString("namajenispembayaran"));
                
                setJenisPembayaran(jb);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(JenisPembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public JenisPembayaran getJenisPembayaran() {
        return jenisPembayaran;
    }

    public void setJenisPembayaran(JenisPembayaran jenisPembayaran) {
        this.jenisPembayaran = jenisPembayaran;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
