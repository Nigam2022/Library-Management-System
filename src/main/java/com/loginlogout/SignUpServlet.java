package com.loginlogout;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jdbcurl = "jdbc:mysql://localhost:3306/librarydb";
        String Username = "root";
        String driverclassname = "com.mysql.cj.jdbc.Driver";
        try {
        	Class.forName(driverclassname);
			Connection con = DriverManager.getConnection(jdbcurl,Username,"");
			PreparedStatement st1 = con.prepareStatement("select * from librarian where LemailId = ?");
			PreparedStatement st = con.prepareStatement("insert into librarian values(?,?,?,?)");
			PrintWriter out = response.getWriter();
			String name = request.getParameter("name");
        	String email = request.getParameter("email");
        	String password = request.getParameter("password");
        	st1.setString(1,email);
        	ResultSet rs = st1.executeQuery();
        	if(rs.next() == false) {
	        	st.setNull(1, Types.NULL);
	        	st.setString(2,name);
	        	st.setString(3,email);
	        	st.setString(4, password);
	        	int rs1 = st.executeUpdate();
	        	
	        	out.println("<script>\r\n"
	        			+ "    window.location = 'index.jsp'"
	        			+ "</script>"); 
	        	
	        }
        	else {
        		out.println("<script>\r\n"
	        			+ "alert('Email Id Exists Please Enter Again Unique')"
	        			+ "</script>"); 
        		out.println("<script>\r\n"
	        			+ "    window.location = 'SignUp.html'"
	        			+ "</script>"); 
        	}
        	
        }catch(Exception ex) {
        	System.out.println("HELLO");
        }
	}

}
