

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
//@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private Connection con;
	private PreparedStatement ps=null;
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
@Override
public void init() throws ServletException {
	// TODO Auto-generated method stub
	
	System.out.println("in init");
	try{
		ServletContext ctxt=getServletContext();
		String driverClassName=ctxt.getInitParameter("driverClassName");
		Class.forName(driverClassName);
		String url=ctxt.getInitParameter("url");
		String dbuser=getInitParameter("dbuser");
		String dbpass=getInitParameter("dbpass");
		String sqlst=getInitParameter("sqlstatement");

		con=DriverManager.getConnection(url,dbuser,dbpass);
		ps=con.prepareStatement(sqlst);
	}
	catch(Exception e)
	{
		
		
	}
			
			
}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in doget");
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try{
			String uname=request.getParameter("uname");
			String pass=request.getParameter("pass");
			String repass=request.getParameter("repass");
			if(uname==null||pass.equals("")||!pass.equals(repass))
			{
				out.println("<html><body><center>");
				out.println("<li><i>");
				out.println("Given details are not valid to register</i></li><br/>");
				out.println("<li><i>Please try again later</i>");
				out.println("</center></body></html>");
				return;
				
			}
			String addr=request.getParameter("addr");
			String phno=request.getParameter("phno");
			String email=request.getParameter("email");
			
			ps.setString(1, uname);
			ps.setString(2, pass);
			ps.setString(3, addr);
			ps.setString(4, phno);
			ps.setString(5, email);
			System.out.println("in doget2");
			int count=ps.executeUpdate();
			
			if(count==1||count==Statement.SUCCESS_NO_INFO){
				out.println("</head><body>"
						+ "<center><h1>Khome Company ltd.</h1></center>"
						+"<table border='1' width='100%' height='100%'><tr>"
			+"<td width='15%' valign='top' align='center'>"
				+"<br/><a href='Login.htm'>Login</a><br>"
				+"<br><a href='Register.html'>Register</a><br></td>");
				
				out.println("<td valign=\"top\" align=\"center\"><br />");
				out.println("<h3>Welcome, "+uname+"</h3><br/>");
				out.println("<h2>Enjoy Browsing the site</h2>");
				out.println("</td></tr></table></body></html>");
				
			}
			else{
				
				out.println("<html><body><center>Given details are incorrect<br><li><i>");
				out.println("Please try again later</i></center></body></html>");
			}
		}
		catch(Exception e)
		{
			out.println("<html><body><center><h2>Unable to process the request try after sometime"
					+ "</h2></center></body>"+e);
			
		}
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
@Override
public void destroy() {
	// TODO Auto-generated method stub
	
	try{
		
		con.close();
	}
	catch(Exception e)
	{
		
		
	}
}
}
