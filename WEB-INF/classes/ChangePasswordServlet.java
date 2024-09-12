// desc portfolioproject1;
//  Name                                      Null?    Type
//  ----------------------------------------- -------- ----------------------------
//  USERNAME                                  NOT NULL VARCHAR2(30)
//  PASSWORD                                  NOT NULL VARCHAR2(15)
//  HINTQUESTION                              NOT NULL VARCHAR2(50)
//  HINTANSWER                                NOT NULL VARCHAR2(30)
 /* changepassword.html  to ChangePasswordServlet.class */
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ChangePasswordServlet extends HttpServlet
{
  public void service(HttpServletRequest rq,HttpServletResponse rs) throws IOException
   {
      PrintWriter pw=rs.getWriter();
      rs.setContentType("text/html");
      HttpSession hs=rq.getSession();
      String htmlPwd, htmlConfirm, sessionUname;
      try
	  {
		// ForgotPasswordServlet
		// hs.setAttribute("hsForgotUsername",uname);
		// MainPageServlet
		// hs.setAttribute("hsMainUsername",uname);
		sessionUname = hs.getAttribute("hsLoginUsername") != null 
                      ? hs.getAttribute("hsLoginUsername").toString() 
                      : hs.getAttribute("hsForgotUsername") != null 
                      ? hs.getAttribute("hsForgotUsername").toString()
                      : null;
		// sessionpwd=hs.getAttribute("hsPassWordMain").toString();
		// forgotserv1uname=hs.getAttribute("hsUserNameMain").toString();
		// forgotserv1pwd=hs.getAttribute("hsPassWordMain").toString();
        
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection cn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","manager");
        Statement st=cn.createStatement();
        htmlPwd=rq.getParameter("newpwd");
        htmlConfirm=rq.getParameter("confnewpwd");
        
		PreparedStatement ps=cn.prepareStatement("select *from portfolioproject1 where USERNAME=?");
		ps.setString(1, sessionUname);
		ResultSet res=ps.executeQuery();
		pw.println("<head>");
		pw.println("<script>");
		pw.println("function redirectSuccess(){");
		pw.println(" setTimeout(function() { window.location.href ='loginpage.html'; }, 3000);");
		pw.println("}");
		pw.println("function redirectfailPassword() {");
		pw.println(" setTimeout(function() { window.location.href ='changepassword.html';}, 3000);");
		pw.println("}");
		pw.println("function redirectfailSomething() {");
		pw.println(" setTimeout(function() { window.location.href ='forgotform.html';}, 3000);");
		pw.println("}");
		pw.println("</script>");

		// dbpwd=res.getString(2);
		if(res.next())
		{
			if(htmlPwd.equals(htmlConfirm)){
				if(!(htmlConfirm.equals(res.getString(2)))){
					st.executeUpdate("update portfolioproject1 set Password='"+htmlConfirm+"' where Username='"+sessionUname+"'");
					pw.println("<body onload='redirectSuccess()'>"); // body for main one
        			pw.println("<h1 style='color: green; font-size: 26px; font-weight: bold; text-align: center;'> Password Successfully Updated... </h1>");
        			pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'> please Wait Few Seconds...Automatically Redirect.... </h4>");
				}else{
					pw.println("<body onload='redirectfailPassword()'>"); // body for password same
        			pw.println("<h1 style='color: red; font-size: 26px; font-weight: bold; text-align: center;'> Old Password and New Password Same Change it... </h1>");
        			pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'> please Wait Few Seconds...Automatically Redirect.... </h4>");
				}
			}
			else{
				pw.println("<body onload='redirectfailPassword()'>"); // body for password change
        		pw.println("<h1 style='color: red; font-size: 26px; font-weight: bold; text-align: center;'>Password and Confirm Password are not Same..  Please Check it...</h1>");
        		pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'> please Wait Few Seconds...Automatically Redirect to Change password Form.... </h4>");
			}
		}else{
	  		pw.println("<body onload='redirectfailSomething()'>"); // body for username  onload='redirectfailUsername()' go to forgotform.html
        	pw.println("<h1 style='color: red; font-size: 26px; font-weight: bold; text-align: center;'>Username not Found..  Please Check it...</h1>");
        	pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'> please Wait Few Seconds...Automatically Redirect. </h4>");
	  		// RequestDispatcher rd=rq.getRequestDispatcher("/changepassword.html");
	  		// rd.include(rq,rs);
		}
		res.close();
		ps.close();
      	st.close();
      	cn.close();
      }  
      catch(Exception e)
      {
        pw.println(e);
      }
	  finally{
		pw.println("</body>");
	  }
	}
}