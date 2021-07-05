package com.issuebook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class EditBookServlet
 */
@WebServlet("/IssueBookServlet")
public class IssueBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IssueBookServlet() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jdbcurl = "jdbc:mysql://localhost:3306/librarydb";
        String Username = "root";
        String driverclassname = "com.mysql.cj.jdbc.Driver";
        String sql = "";
        PrintWriter out = response.getWriter();
        try {
        	Class.forName(driverclassname);
			Connection con = DriverManager.getConnection(jdbcurl,Username,"");
			PreparedStatement st = con.prepareStatement("insert into issue_book values(?,?,?,?,?,?)");
			String isbn = request.getParameter("iisbn");
        	String name = request.getParameter("isname");
            String sid = request.getParameter("isid");
            String mobile = request.getParameter("ismob");

            
            PreparedStatement st1 = con.prepareStatement("select * from books where BIsbn = ?");
            st1.setString(1, isbn);
            ResultSet res = st1.executeQuery();
            if(res.next() != false) {
	            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	            LocalDateTime now = LocalDateTime.now();
	            String time = dtf.format(now);
	            
	            st.setNull(1, Types.NULL);
	            st.setString(2, isbn);
	            st.setString(3, name);
	            st.setString(4, sid);
	            st.setString(5, mobile);
	            st.setString(6, time);
	            
	            int i = st.executeUpdate();
	            Statement st11 = con.createStatement();
	            sql = "select * from books where BIsbn = '" + isbn + "'";
	            String issueCnt = "";
	            ResultSet result = st11.executeQuery(sql);
	            result.next();
	            issueCnt = result.getString("BIssued");
	            issueCnt = String.valueOf(1 + Integer.parseInt(issueCnt));
	
	            sql = "update books set BIssued = '" + issueCnt + "' where BIsbn = '" + isbn + "'"; 
	            
	            st1.executeUpdate(sql);
	        	
	        	out.println("<script>alert('You are successfully updated record')</script>");
	        	out.println("<script>\r\n"
	        			+ "    window.location = 'ViewBook.jsp'"
	        			+ "</script>"); 
            }
            else {
            	out.println("<script>alert('Book Not Exists Please Enter Again')</script>");
            	out.println("<script>\r\n"
	        			+ "    window.location = 'IssueBook.html'"
	        			+ "</script>"); 
            }
        	
        }catch(Exception ex) {System.out.println(sql);}
	}

}
