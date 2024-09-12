// desc portfolioproject1;
//  Name                                      Null?    Type
//  ----------------------------------------- -------- ----------------------------
//  USERNAME                                  NOT NULL VARCHAR2(30)
//  PASSWORD                                  NOT NULL VARCHAR2(15)
//  HINTQUESTION                              NOT NULL VARCHAR2(50)
//  HINTANSWER                                NOT NULL VARCHAR2(30)
/* ForgotPasswordServlet2.class  to ForgotPasswordServlet3.class*/
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ForgotPasswordServlet extends HttpServlet
{
  public void service(HttpServletRequest rq,HttpServletResponse rs) throws IOException
    {
      String forgothtmlUname,forgothtmlquestion,forgothtmlans,uname,pwd,que,ans;
      PrintWriter pw=rs.getWriter();
      rs.setContentType("text/html");
      HttpSession hs=rq.getSession();
      forgothtmlUname=rq.getParameter("forgotFormUsername");
      forgothtmlquestion=rq.getParameter("forgotFormHintque");
      forgothtmlans=rq.getParameter("forgotFormHintans");
      // serv1ans=hs.getAttribute("forgotdbans").toString();
      try
      {
	Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection cn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "manager");
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM portfolioproject1 WHERE USERNAME=?");
        ps.setString(1, forgothtmlUname);
        ResultSet rset = ps.executeQuery();

        pw.println("<html><head>");
        pw.println("<script>");
        pw.println("function redirect() {");
        pw.println("setTimeout(function() { window.location.href = 'forgotform.html'; }, 3000);");
        pw.println("}");
        pw.println("function redirectchange() {");
        pw.println("setTimeout(function() { window.location.href = 'changepassword.html'; }, 3000);");
        pw.println("}");
        pw.println("</script>");
        pw.println("</head>");
        if(rset.next())
        {
          uname=rset.getString("USERNAME");
          pwd=rset.getString("PASSWORD");
          que=rset.getString("HINTQUESTION");
          ans=rset.getString("HINTANSWER");
          if(forgothtmlquestion.equals(que)){

            if(forgothtmlans.equals(ans)){
	            hs.setAttribute("hsForgotUsername",uname);
              // hs.setAttribute("hsForgotPassword",pwd);
              // hs.setAttribute("hsForgotHintQuestion",que);
              // hs.setAttribute("hsForgotHintAnswer",ans);

              pw.println("<body onload='redirectchange()'>"); // Redirect on change password page
              pw.println("<h1 style='color: green; font-size: 26px; font-weight: bold; text-align: center;'>Login Successful.</h1>");
              pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'>You will be redirected to the Change Password Page...</h4>");


            }else{
              pw.println("<body onload='redirect()'>"); // body for invalid hint answer
              pw.println("<h1 style='color: red; font-size: 26px; font-weight: bold; text-align: center;'>  Hint answer is incorrect..  Please Check it...</h1>");
              pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'> please Wait Few Minutes...Automatically Redirect.... </h4>");
            }
          }else{
            pw.println("<body onload='redirect()'>"); // body for invalid Hint Question
            pw.println("<h1 style='color: red; font-size: 26px; font-weight: bold; text-align: center;'> Hint Question is incorrect.. Please Check it...</h1>");
            pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'> please Wait Few Minutes...Automatically Redirect.... </h4>");
          }
	      }else{
            pw.println("<body onload='redirect()'>"); // body for invalid username
            pw.println("<h1 style='color: red; font-size: 26px; font-weight: bold; text-align: center;'>Username is incorrect..(OR) Please create an Account. </h1>");
            pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'> please Wait Few Minutes...Automatically Redirect.... </h4>");
        }
        pw.println("</body></html>");
        rset.close();
        ps.close();
        cn.close();
      }
      catch(Exception e)
      {
        pw.println(e);
      }
   }
}