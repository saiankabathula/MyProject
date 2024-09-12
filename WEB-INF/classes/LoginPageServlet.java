// desc portfolioproject1;
//  Name                                      Null?    Type
//  ----------------------------------------- -------- ----------------------------
//  USERNAME                                  NOT NULL VARCHAR2(30)
//  PASSWORD                                  NOT NULL VARCHAR2(15)
//  HINTQUESTION                              NOT NULL VARCHAR2(50)
//  HINTANSWER                                NOT NULL VARCHAR2(30)
/* loginpage.html  to LoginPageServlet.class*//*String name=hs.getAttribute("....").toString();*/
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginPageServlet extends HttpServlet {
    public void service(HttpServletRequest rq, HttpServletResponse rs) throws IOException {
        PrintWriter pw = rs.getWriter();
        rs.setContentType("text/html");
        HttpSession hs = rq.getSession();
        String htmlUsername, htmlPassword, uname, pwd, que, ans;
        htmlUsername = rq.getParameter("loginusername");
        htmlPassword = rq.getParameter("loginpassword");
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection cn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "manager");
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM portfolioproject1 WHERE USERNAME=?");
            ps.setString(1, htmlUsername);
            ResultSet rset = ps.executeQuery();
            
            pw.println("<html><head>");
            pw.println("<script>");
            pw.println("function redirectSuccess() {");
            pw.println("setTimeout(function() { window.location.href = 'mainpage.html'; }, 3000);");
            pw.println("}");
            pw.println("function redirectFailed(){");
            pw.println("setTimeout(function() { window.location.href = 'loginpage.html';},3000);");
            pw.println("}");
            pw.println("</script>");
            pw.println("</head>");
            
            if (rset.next()) {
                uname = rset.getString(1);
                pwd = rset.getString(2);
                que = rset.getString(3);
                ans = rset.getString(4);
                if (htmlPassword.equals(pwd)) {
                    hs.setAttribute("hsLoginUsername", uname);
                    pw.println("<body onload='redirectSuccess()'>"); // Redirect on body load
                    pw.println("<h1 style='color: green; font-size: 26px; font-weight: bold; text-align: center;'>Login Successful.</h1>");
                    pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'>You will be redirected to the Main Page...</h4>");
                } else {
                    pw.println("<body onload='redirectFailed()'>"); // body for invalid password
                    pw.println("<h1 style='color: red; font-size: 26px; font-weight: bold; text-align: center;'> Invalid Password... </h1>");
                    pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'>You will be redirected to the Return...</h4>");
                }
            } else {
                pw.println("<body onload='redirectFailed()'>"); // body for invalid username
                pw.println("<h1 style='color: red; font-size: 26px; font-weight: bold; text-align: center;'> Invalid Username...(OR) Please create an Account.</h1>");
                pw.println("<h4 style='color: black; font-size: 21px; font-weight: bold; text-align: center;'>You will be redirected to the Return...</h4>");
            }
            pw.println("</body></html>");

            rset.close();
            ps.close();
            cn.close();
        } catch (Exception e) {
            pw.println(e.getMessage());
        } finally {
            pw.close();
        }
    }
}
