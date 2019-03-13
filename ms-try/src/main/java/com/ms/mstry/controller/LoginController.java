package com.ms.mstry.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ms.mstry.controller.bean.UserInfo;

@RestController
public class LoginController {
	
	@RequestMapping(method = RequestMethod.GET, path="/hello-world")
	public String helloWorld() {
		return "Hello World!!";
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST, path="/loginAction")
	public UserInfo authenticate(@RequestBody(required=true) UserInfo userInfo) throws InstantiationException, IllegalAccessException, SQLException {
		
		  Connection connection = connectJDBCToAWSEC2();
		  
		   PreparedStatement preparedStatement = null;
		     ResultSet resultSet = null;
		     String dbUserId = null;
		     String dbUserPwd = null;
		  
		  if(connection!=null) {
			  
			  preparedStatement = connection.prepareStatement("select * from User where UserId = ?;");
			  preparedStatement.setString(1, userInfo.getUserId());
			  resultSet = preparedStatement.executeQuery();
			  
			  while (resultSet.next()) {
				   dbUserId = resultSet.getString("UserId");
		           dbUserPwd = resultSet.getString("Pwd");
			  }
		  }
		
		  
		  if (resultSet != null) {
              resultSet.close();
          }

          if (preparedStatement != null) {
        	  preparedStatement.close();
          }

          if (connection != null) {
        	  connection.close();
          }
		  
		if(userInfo.getUserId()!= null && userInfo.getUserId().equals(dbUserId) && userInfo.getPwd().equals(dbUserPwd) ) {
			
			System.out.println("Login success");
			return userInfo;
		}
		else {
			
			return new UserInfo();
		}
		
	}

	
	
	public static Connection connectJDBCToAWSEC2() throws InstantiationException, IllegalAccessException {

	    System.out.println("----MySQL JDBC Connection Testing -------");
	    
	    try {
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
	    } catch (ClassNotFoundException e) {
	        System.out.println("Where is your MySQL JDBC Driver?");
	        e.printStackTrace();
	        return null;
	    }

	    System.out.println("MySQL JDBC Driver Registered!");
	    Connection connection = null;

	    try {
	        connection = DriverManager.
	                getConnection("jdbc:mysql://myfirstdb.cgt3orlhz3vw.us-east-1.rds.amazonaws.com:3306/myfirstdb","myfirstdb","myfirstdb");
	    } catch (SQLException e) {
	        System.out.println("Connection Failed!:\n" + e.getMessage());
	    }

	    if (connection != null) {
	        System.out.println("SUCCESS!!!! You made it, take control     your database now!");
	        return connection;
	    } else {
	        System.out.println("FAILURE! Failed to make connection!");
	        return null;
	    }

	}
}
