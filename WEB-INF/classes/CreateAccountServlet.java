// desc portfolioproject1;
//  Name                                      Null?    Type
//  ----------------------------------------- -------- ----------------------------
//  USERNAME                                  NOT NULL VARCHAR2(30)
//  PASSWORD                                  NOT NULL VARCHAR2(15)
//  HINTQUESTION                              NOT NULL VARCHAR2(50)
//  HINTANSWER                                NOT NULL VARCHAR2(30)
/* signupform.html  to NewUserCreation.class*/
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CreateAccountServlet extends HttpServlet
{
  public void service(HttpServletRequest rq,HttpServletResponse rs) throws IOException
  {
    PrintWriter pw=rs.getWriter();
    rs.setContentType("text/html");
    String htmlUname,htmlPwd,htmlConfirm,htmlhintqu,htmlhintans;
    try
    {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection cn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","manager");
      Statement st=cn.createStatement();

      htmlUname=rq.getParameter("signUpUsername");
      htmlPwd=rq.getParameter("signUpPassword");
      htmlConfirm=rq.getParameter("signUpConfirmPassword");
      htmlhintqu=rq.getParameter("signUpFormHintQuestion");
      htmlhintans=rq.getParameter("signUpFormHintAnswer");

      pw.println("<head><script>");
      pw.println("function redirectSuccess(){");
      pw.println("setTimeout(function(){ window.location.href='loginpage.html';},3000);");
      pw.println("}");
      pw.println("function redirectfailPassword() {");
      pw.println("setTimeout(function(){ window.location.href='createAccount.html';},3000);");
      pw.println("}");
      pw.println("</script></head>");

      if(htmlPwd.equals(htmlConfirm)){
        st.executeUpdate("insert into portfolioproject1 values('"+htmlUname+"', '"+htmlPwd+"', '"+htmlhintqu+"', '"+htmlhintans+"')");
        pw.println("<body onload='redirectSuccess()'>"); // body for main one
        pw.println("<h1 style='color: green; font-size: 26px; font-weight: bold; text-align: center;'> Successfully Registered... </h1>");
        pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'> please Wait Few Seconds...Automatically Redirect.... </h4>");
	    }
	    else{
	      pw.println("<body onload='redirectfailPassword()'>"); // body for password change
        pw.println("<h1 style='color: red; font-size: 26px; font-weight: bold; text-align: center;'>Password and Confirm Password are not Match..  Please Check it...</h1>");
        pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'> please Wait Few Seconds...Automatically Redirect to Return. </h4>");
	      // pw.println("<hr size='4' color='red'><br>");
	      // RequestDispatcher rd=rq.getRequestDispatcher("/loginpage.html");
        // rd.include(rq, rs);
      }
      st.close();
      cn.close();
    }
    catch(SQLIntegrityConstraintViolationException e){
      pw.println("<body onload='redirectfailPassword()'>"); // body for password change
      pw.println("<h1 style='color: red; font-size: 26px; font-weight: bold; text-align: center;'> Username Already Exits, Change Your Username. </h1>");
      pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'> please Wait Few Seconds...Automatically Redirect to Return. </h4>");
    }  
    catch(Exception e){
      pw.println(e.getStackTrace());
    }
    finally{
      pw.println("</body>");
    }
  }
}