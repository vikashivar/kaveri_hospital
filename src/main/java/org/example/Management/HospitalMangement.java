package org.example.Management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class HospitalMangement {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String user="root";
    private static final String password="Vicky@123";

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DriverManager.getConnection(url,user,password);
            Patient patient=new Patient(connection,scanner);
            Doctor doctor=new Doctor(connection);

            while(true){
                System.out.println("WELCOME KAVERI HOSPITAL...");
                System.out.println("Enter 1 Add To Patient");
                System.out.println("Enter 2 View to patients");
                System.out.println("Enter 3 Views Doctors");
                System.out.println("Enter 4 Book Appointment");
                System.out.println("Enter 5 Exit...");
                System.out.println("Enter Your Choice : ");
                int option=scanner.nextInt();

                switch (option){
                    case 1:
                        patient.addPatient();
                        break;
                    case 2:
                        patient.viewPatient();
                        break;
                    case 3:
                        doctor.viewDoctor();
                        break;
                    case 4:
                        bookAppointMent(connection,scanner,patient,doctor);
                        break;
                    case 5:
                        System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                        return;
                    default:
                        System.out.println("Enter Valid Option...!");

                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void bookAppointMent(Connection connection,Scanner scanner ,Patient patient,Doctor doctor){
        System.out.println("Add Patient Id : ");
        int PatentId=scanner.nextInt();
        System.out.println("Add Doctor Id : ");
        int DoctorId=scanner.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MMM-DDD) : " );
        String appointmentDate=scanner.next();

        if(patient.getPatientById(PatentId) && doctor.getDoctorById(DoctorId)){
            if(CheckDoctorAvailability(DoctorId,appointmentDate,connection)){
                try {
                    String appintmentQuery = "Insert into appointments (patient_id,doctor_id,appointment_date) values (?,?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(appintmentQuery);
                    preparedStatement.setInt(1,PatentId);
                    preparedStatement.setInt(2,DoctorId);
                    preparedStatement.setString(3,appointmentDate);
                    int rowAffected=preparedStatement.executeUpdate();
                    if(rowAffected>0){
                        System.out.println("Appointment booked");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                }

        }else {
            System.out.println("Failed to book appoinment ");
        }


    }

    public static boolean CheckDoctorAvailability(  int doctorId,String appointmentDate,Connection connection){
      try{
          String query ="select count(*) from appointments where doctor_id= ? and appointment_date = ?";
          PreparedStatement preparedStatement=connection.prepareStatement(query);
          preparedStatement.setInt(1,doctorId);
          preparedStatement.setString(2,appointmentDate);
          ResultSet resultSet=preparedStatement.executeQuery();
          if(resultSet.next()){
              int count=resultSet.getInt(1);
              if(count==0){
                  return true;
              }else{
                  return false;
              }
          }
      }catch (Exception e){
          e.printStackTrace();
      }
        return false;
    }


}
