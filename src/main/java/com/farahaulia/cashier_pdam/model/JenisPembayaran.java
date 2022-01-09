/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.farahaulia.cashier_pdam.model;

import com.farahaulia.cashier_pdam.db.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class JenisPembayaran {
    private int id;
    private String namajenispembayaran;
    
    private Database database;
    private Connection connection;
    
    @Override
    public String toString(){
        return namajenispembayaran;
    }
    
    public boolean create(){
        
        String insertSQL = "INSERT INTO jenispembayaran VALUES (NULL, ?)";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, this.namajenispembayaran);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JenisPembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean update(){
        String updateSQL = "UPDATE jenispembayaran SET namajenispembayaran = ? WHERE id = ?";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, this.namajenispembayaran);
            preparedStatement.setInt(2, this.id);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JenisPembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean delete(){
        String deleteSQL = "DELETE FROM jenispembayaran WHERE id = ?";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, this.id);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JenisPembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<JenisPembayaran> read(){
        
        ArrayList<JenisPembayaran> list = new ArrayList<>();
        
        String selectSQL = "SELECT * FROM jenispembayaran" ;
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                JenisPembayaran jb = new JenisPembayaran();
                jb.setId(rs.getInt("id"));
                jb.setNamajenispembayaran(rs.getString("namajenispembayaran"));
                
                list.add(jb);
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(JenisPembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public boolean find(){
        String findSQL = "SELECT * FROM jenispembayaran WHERE id = ?";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(findSQL);
            preparedStatement.setInt(1, this.id);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                this.setNamajenispembayaran(rs.getString("namajenispembayaran"));
                return true;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(JenisPembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<JenisPembayaran> search(String keyword){
        ArrayList<JenisPembayaran> list = new ArrayList<>();
        
        String searchSQL = "SELECT * FROM jenispembayaran WHERE namajenispembayaran like ?" ;
        
        keyword = "%" + keyword + "%";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(searchSQL);
            preparedStatement.setString(1, keyword);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                JenisPembayaran jb = new JenisPembayaran();
                jb.setId(rs.getInt("id"));
                jb.setNamajenispembayaran(rs.getString("namajenispembayaran"));
                
                list.add(jb);
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(JenisPembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamajenispembayaran() {
        return namajenispembayaran;
    }

    public void setNamajenispembayaran(String namajenispembayaran) {
        this.namajenispembayaran = namajenispembayaran;
    }
}
