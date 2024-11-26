package org.example.Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.scanner=scanner;
        this.connection=connection;
    }

    public void addPatient(){
        System.out.print("Enter Patient Name : ");
        String name=scanner.next();
        System.out.print("Enter Patient Age : ");
        int age=scanner.nextInt();
        System.out.print("Enter Patient Gender : ");
        String gender =scanner.next();


        try{
          String  query ="insert into patient (name,age,gender) values (?,?,?)";
            PreparedStatement pstm=connection.prepareStatement(query);
            pstm.setString(1,name);
            pstm.setInt(2,age);
            pstm.setString(3,gender);
            int affectRow=pstm.executeUpdate();
            if(affectRow>0){
                System.out.println("Patinet Data Entry Succecfully Add ! ");
            }
        }catch (Exception e){
            e.printStackTrace();
        };


    }
    public void viewPatient(){
        String query ="select * from patient";
        try{
            PreparedStatement pstm=connection.prepareStatement(query);
            ResultSet resultSet=pstm.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getInt("id")+" "+resultSet.getString("name")+" "+resultSet.getInt("age")+" "+resultSet.getString("gender"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    boolean getPatientById(int id){
        String query ="select * from patient where id= ?";
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
