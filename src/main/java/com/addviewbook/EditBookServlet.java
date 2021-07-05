package com.addviewbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EditBookServlet
 */
@WebServlet("/EditBookServlet")
public class EditBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditBookServlet() {
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
			Statement st = con.createStatement();
			String isbn = request.getParameter("bisbn");
        	String name = request.getParameter("bname");
        	String author = request.getParameter("bauthor");
        	String publisher = request.getParameter("bpublish");
        	String quantity = request.getParameter("bquant");
        	HttpSession sc = request.getSession();
        	String bookId = (String) sc.getAttribute("EditBookIdNo");
        	String sql = "update books set BIsbn = '"+isbn+"', BName = '"+name+"', BAuthor = '"+author+"',"
        			+ "BPublisher = '"+publisher+"', BQuantity = '"+quantity+"' where BookId = "+bookId+"";
        	st.executeUpdate(sql);
        	PrintWriter out = response.getWriter();
        	out.println("<script>alert('You are successfully updated record')</script>");
        	out.println("<script>\r\n"
        			+ "    window.location = 'ViewBook.jsp'"
        			+ "</script>"); 
        	
        }catch(Exception ex) {}
	}

}
