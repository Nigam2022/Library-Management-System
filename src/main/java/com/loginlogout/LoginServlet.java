package com.loginlogout;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jdbcurl = "jdbc:mysql://localhost:3306/librarydb";
        String Username = "root";
        String driverclassname = "com.mysql.cj.jdbc.Driver";
        String sql = "";
        try {
        	Class.forName(driverclassname);
			Connection con = DriverManager.getConnection(jdbcurl,Username,"");
			PreparedStatement st = con.prepareStatement("select * from admindata where EmailId = ? AND Password = ?");
			
        	String email = request.getParameter("email");
        	String password = request.getParameter("password");
        	
        	st.setString(1,email);
        	st.setString(2, password);
        	ResultSet rs = st.executeQuery();
        	HttpSession sc = request.getSession();
        	PrintWriter out = response.getWriter();
        	if(rs.next() == false) {
        		PreparedStatement st1 = con.prepareStatement("select * from librarian where LemailId = ? AND Lpassword = ?");            	
            	st1.setString(1,email);
            	st1.setString(2, password);
            	
            	ResultSet rs1 = st1.executeQuery();
            	System.out.println("HELLO WORLD");
            	if(rs1.next() == false) {
            		
            		out.println("<script>alert('Incorrect Email Or Password Please Enter Again')</script>");
            		out.println("<script>\r\n"
                			+ "    window.location = 'index.jsp'"
                			+ "</script>");
            	}
            	else {
            		sc.setAttribute("LoginOfLibrarian",  rs1.getString("Lname"));
            		out.println("<script>\r\n"
                			+ "    window.location = 'ViewBook.jsp'"
                			+ "</script>"); 
            	}
        	}
        	else {
        	
        	sc.setAttribute("LoginOfAdmin", rs.getString("Name"));
        	out.println("<script>\r\n"
        			+ "    window.location = 'adminWork.jsp'"
        			+ "</script>"); 
        	}
        	
        	
        }catch(Exception ex) {
        	System.out.println("HELLO");
        }
	}

}
