/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import java.lang.*;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class PostgreSQLcmd extends javax.swing.JFrame{
     //String url = "jdbc:postgresql://localhost:5432/testdb";
      static String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
      static String user = "postgres";
      static String password = "66177520";
      
    public static void SQLversioncheck() {
        //String url = "jdbc:postgresql://localhost:5432/testdb";
        //String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        //String user = "postgres";
        //String password = "66177520";

        try (Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT VERSION()")) {

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
        
            Logger lgr = Logger.getLogger(PostgreSQLcmd.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public static void datacheck() {

        //String url = "jdbc:postgresql://localhost:5432/testdb";
        //String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        //String user = "postgres";
        //String password = "66177520";

        try (Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM record");
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
            
                System.out.print(rs.getInt(1));
                System.out.print(": ");
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(PostgreSQLcmd.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public static void insertdata(String name,String mobilestr,String address) 
    {

        //String url = "jdbc:postgresql://localhost:5432/testdb";
        //String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        //String user = "postgres";
        //String password = "66177520";
        
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Enter ID:");
        String idinput = myScanner.nextLine();
        int id = Integer.parseInt(idinput);
        int mobile = Integer.parseInt(mobilestr);
        //int id = 6;
        //String author = "Trygve Gulbranssen";
        String query = "INSERT INTO record(id, name, mobile, address) VALUES(?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setInt(3, mobile);
            pst.setString(4, address);
            pst.executeUpdate();
            


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(PostgreSQLcmd.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public static void update_table(javax.swing.JTable Table1) {

        //String url = "jdbc:postgresql://localhost:5432/testdb";
        //String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        //String user = "postgres";
        //String password = "66177520";

        try 
        {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pst = con.prepareStatement("SELECT * FROM record ORDER BY id ASC");
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData Rss = rs.getMetaData();
            int col_num = Rss.getColumnCount();
            DefaultTableModel Df = (DefaultTableModel)Table1.getModel();
            Df.setRowCount(0);
            while (rs.next()) 
            {
                Vector v2 = new Vector();
                for(int a=1; a<= col_num; a++)
                {
                    v2.add(rs.getInt("id"));
                    v2.add(rs.getString("name"));
                    v2.add(rs.getInt("mobile"));
                    v2.add(rs.getString("address"));
                }
                Df.addRow(v2);
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(PostgreSQLcmd.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public static void editdata(javax.swing.JTable Table1, 
                                javax.swing.JTextField namefield,
                                javax.swing.JTextField mobilefield,
                                javax.swing.JTextField addressfield) 
    {

        //String url = "jdbc:postgresql://localhost:5432/testdb";
        //String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        //String user = "postgres";
        //String password = "66177520";
        DefaultTableModel Df = (DefaultTableModel)Table1.getModel();
        int selectedIndex = Table1.getSelectedRow();
        
        int id = Integer.parseInt(Df.getValueAt(selectedIndex, 0).toString());
        String name = namefield.getText();
        String mobilestr = mobilefield.getText();
        int mobile = Integer.parseInt(mobilestr);
        String address = addressfield.getText();
        
        String query = "UPDATE record SET name=?, mobile=?, address=? where id=?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            
            
            pst.setString(1, name);
            pst.setInt(2, mobile);
            pst.setString(3, address);
            pst.setInt(4, id);
            pst.executeUpdate();
            


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(PostgreSQLcmd.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public static void deletedata(javax.swing.JTable Table1, 
                                javax.swing.JTextField namefield,
                                javax.swing.JTextField mobilefield,
                                javax.swing.JTextField addressfield) 
    {

        //String url = "jdbc:postgresql://localhost:5432/testdb";
        //String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        //String user = "postgres";
        //String password = "66177520";
        
        DefaultTableModel Df = (DefaultTableModel)Table1.getModel();
        int selectedIndex = Table1.getSelectedRow();
        int id = Integer.parseInt(Df.getValueAt(selectedIndex, 0).toString());
        
        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete this row?","Warning",JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION);
        {
            String query = "DELETE FROM record where id=?";

            try {Connection con = DriverManager.getConnection(url, user, password);
                 PreparedStatement pst = con.prepareStatement(query);
                 pst.setInt(1, id);
                 pst.executeUpdate();
                } 

            catch (SQLException ex) {

                Logger lgr = Logger.getLogger(PostgreSQLcmd.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        
        
    }
}
