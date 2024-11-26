package org.example.Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor {
    private Connection connection;

    public Doctor (Connection connection){
        this.connection=connection;
    }


    public void viewDoctor(){
        String query ="select * from doctor";
        try{
            PreparedStatement pstm=connection.prepareStatement(query);
            ResultSet resultSet=pstm.executeQuery();
            while (resultSet.next()){
                int id= resultSet.getInt("id");
                String name=resultSet.getString("name");
                String specialization=resultSet.getString("specialization");
                System.out.println("id : "+ id+" name : "+name+" specialization : "+specialization);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    boolean getDoctorById(int id){
        String query ="select * from doctor where id= ?";
        try{
            PreparedStatement ptst=connection.prepareStatement(query);
            ptst.setInt(1,id);
            ResultSet resultSet=ptst.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
