package com.forgetpass;

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


/**
 * Servlet implementation class SendMail
 */
@WebServlet("/SendMail")
public class SendMail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String jdbcurl = "jdbc:mysql://localhost:3306/librarydb";
        String Username = "root";
        String driverclassname = "com.mysql.cj.jdbc.Driver";
        String sql = "";
        String to=request.getParameter("to");
		String subject="Forgot Password";
		String msg = "You PassWord for ELibrary Management System is : ";
        try {
        	Class.forName(driverclassname);
			Connection con = DriverManager.getConnection(jdbcurl,Username,"");
			PreparedStatement st = con.prepareStatement("select * from librarian where LemailId = ?");
			st.setString(1, to);
			ResultSet rs = st.executeQuery();
			if(rs.next() == false) System.out.println("HELLO WORLD");
			msg += rs.getString("Lpassword");
			System.out.println(msg + " " + to + " "+ subject);
			Mailer.send(to, subject, msg);
				
        }catch(Exception ex) {}
		
		out.print("<script>alert('PassWord Sent In Your EmailId')</script>");
		out.close();
	}
}
