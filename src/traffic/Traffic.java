/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//package Traffic;

/**
 *
 * @author ASUS
 */
public class Traffic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        Connection cn=null;
	ResultSet rs=null;
	Statement stmt=null;
	String query;
	try
	{
            Class.forName("com.mysql.jdbc.Driver");
            cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/TrafficOffences","root","maarlesh");
            System.out.println("Connected With Database");
	}
	catch(ClassNotFoundException cnfe)
	{	
            System.out.println(cnfe);
	}
	catch(SQLException sqle)
	{
            System.out.println(sqle);
	}
        try
        {   int flag = 0;
            System.out.println("----------------------------------TRAFFIC POLICE DEPARTMENT LOGIN PORTAL----------------------------------");
            do{
            System.out.println("           ENTER YOUR NAME:");
            String na = in.nextLine();
            System.out.println("       ENTER THE SECRET ID:");
            int id = in.nextInt();
            stmt=cn.createStatement();
            query="select * from police where name ='"+na+"'";
            rs=stmt.executeQuery(query);
            rs.next();
            if(rs.getInt("id") == id)
            {
                System.out.println("===============success===============");
                System.out.println("===YOU ARE LOGGED IN TO THE SYSTEM===");
                flag = 1;
            }
            else
            {
                System.out.println("/////////////user name and password mismatch/////////////");
                in.nextLine();
            }
            }while(flag == 0);
            int choice;
            do
            {
            System.out.println("Enter option:");
            System.out.println("1.Impose fine to a user:");
            System.out.println("2.View fine History of a user:");
            System.out.println("3.View User details:");
            System.out.println("4.Logout:");
            choice = in.nextInt();
            switch(choice)
            {
               case 1:
                        System.out.println("Enter the license No of the offender to enter the fine:");
                        int li = in.nextInt();
                        in.nextLine();
                        System.out.println("Enter the offence name:");
                        String offna = in.nextLine();
                        System.out.println("Enter the fine amount:");
                        int am = in.nextInt();
                        query="insert into offense values('"+offna+"',"+am+","+li+")";
                        System.out.println("*****User notified******");
                        stmt.executeUpdate(query);
                        break;
               case 2:
                        System.out.println("Enter the license NO of the offender:");
                        li = in.nextInt();
                        int total = 0;
                        query="select * from offense where offenderId ="+li;
                        System.out.println("*******FINE HISTORY*******");
                        System.out.println("LICENSE NO:"+li);
                        rs=stmt.executeQuery(query);
                        while(rs.next())
                        {
                            System.out.println("-----------------------------------------------------");
                            System.out.println("OFFENCE NAME:"+rs.getString("offenseName"));
                            System.out.println(" FINE AMOUNT:"+rs.getInt("fineAmount"));
                            total = total + rs.getInt("fineAmount");
                            System.out.println("-----------------------------------------------------");
                        }
                        System.out.println("-----------------------------------------------------");
                        System.out.println("\tTOTAL FINE AMOUNT ="+total);
                        System.out.println("-----------------------------------------------------");
                        break;
               case 3:
                        System.out.println("Enter the License NO :");
                        li = in.nextInt();
                        System.out.println("=======Displaying user details=======");
                        query="select * from offender where licenseNo = "+li;
                        rs = stmt.executeQuery(query);
                        rs.next();
                        System.out.println("-----------------------------------------------------");
                        System.out.println("LICENSE NO:"+rs.getInt("licenseNo"));
                        System.out.println("    REG NO:"+rs.getInt("regNo"));
                        System.out.println("      NAME:"+rs.getString("name"));
                        System.out.println("  PHONE NO:"+rs.getString("phNo"));
                        System.out.println("-----------------------------------------------------");
                        break;
               case 4:
                        break;
            }
            }while(choice != 4);
        }
        catch(SQLException e)
        {
            System.out.println("Security alert wrong user name contact administrator");
            e.printStackTrace();
        }
     }

    }
