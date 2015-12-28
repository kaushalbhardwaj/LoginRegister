

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private Connection con;
	private PreparedStatement ps=null;
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
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
		//	System.out.println("in init1");
		}
		catch(Exception e)
		{
			System.out.println("error  "+e);
			
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try{
			String uname=request.getParameter("uname");
			String pass=request.getParameter("pass");
			//System.out.println("in 1");
			if(uname==null||pass.equals("")||pass==null||uname.equals(""))
			{
				out.println("<html><body><center><li><i>"
						+ "User Name and Password cannot be empty</i></li><br>"
						+ "<li><i>We cannot log you into your account at this time."
						+ "Please try again later</i></center></body></html>");
				return;
			}
			//System.out.println("in 2"+uname+pass);
			ps.setString(1, uname);
			ps.setString(2, pass);
			//System.out.println("in 3");
			ResultSet rs=ps.executeQuery();
			//ResultSet rs=null;
			if(rs.next())
			{
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
