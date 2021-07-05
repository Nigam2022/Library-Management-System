package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EditLibrarian
 */
@WebServlet("/EditLibrarianServlet")
public class EditLibrarianServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditLibrarianServlet() {
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
			PreparedStatement st = con.prepareStatement("update librarian set Lname = ?, LemailId = ?, Lpassword = ? where LibId = ?");
			String name = request.getParameter("namee");
			String email = request.getParameter("emaill");
			String pass = request.getParameter("passs");
        	HttpSession sc = request.getSession();
        	String bookId = (String) sc.getAttribute("LibIdForEditAndDelete");
        	Integer LibId = Integer.parseInt(bookId);
        	st.setString(1, name);
        	st.setString(2, email);
        	st.setString(3, pass);
        	st.setInt(4, LibId);
        	System.out.println("Hello after 4");
        	int i = st.executeUpdate();
        	PrintWriter out = response.getWriter();
        	out.println("<script>alert('You are successfully updated record')</script>");
        	out.println("<script>\r\n"
        			+ "    window.location = 'adminWork.jsp'"
        			+ "</script>"); 
        	
        }catch(Exception ex) {System.out.println("Hello exception");}
	}

}
