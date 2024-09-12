import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class OrderServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get the values from the form
        String food = request.getParameter("food");
        String extraCheese = request.getParameter("extra_cheese");
        String extraToppings = request.getParameter("extra_toppings");
        String takeaway = request.getParameter("takeaway");

        // Base prices
        int price = 0;
        switch (food) {
            case "veg_pizza": price = 300; break;
            case "nonveg_pizza": price = 400; break;
            case "delux_veg_pizza": price = 550; break;
            case "delux_nonveg_pizza": price = 650; break;
            case "fries": price = 100; break;
            case "wings": price = 250; break;
        }

        // Add extra charges
        if (extraCheese != null) {
            price += 100;
        }
        if (extraToppings != null) {
            price += 150;
        }
        if (takeaway != null) {
            price += 20;
        }

        // Calculate tip (10%)
        double tip = price * 0.1;
        double mainTotalPrice = price - tip;

        // Output the bill with CSS styling
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Welcome to Pizzamania</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; padding: 20px; background-color: #f8f9fa; }");
        out.println("h1 { color: #28a745; text-align:center }");
        out.println("p { font-size: 18px; }");
        out.println(".container { border: 1px solid #ccc; padding: 20px; border-radius: 10px; background-color: #fff; max-width: 600px; margin: auto; }");
        out.println(".button { margin-top: 20px; padding: 10px 20px; background-color: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer; transition: background-color 0.3s; width: 100%; }");
        out.println(".button:hover { background-color: #0056b3; }");
        out.println(".calculator { display: flex; justify-content: space-between; margin-top: 20px; }");
        out.println(".calc-block { width: 48%; }");
        out.println("input[type='text'] { padding: 5px; width: calc(100% - 10px); }");
        out.println(".total-price { font-weight: bold; }");
        out.println(".redirect-button { position: fixed; bottom: 20px; right: 20px; width: 200px; }");
	    out.println("#customResult { color: green;}");
	    out.println("#calcResult { color: green;}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");

        // Display the order summary
        out.println("<h1>Your Order Summary</h1>");
        out.println("<p>Food: " + food + "</p>");
        if (extraCheese != null) {
            out.println("<p>Extra Cheese: Yes</p>");
        }
        if (extraToppings != null) {
            out.println("<p>Extra Toppings: Yes</p>");
        }
        if (takeaway != null) {
            out.println("<p>Takeaway: Yes</p>");
        }
        out.println("<p class='total-price'>Total Price: &#8377;" + price + "</p>");
        out.println("<p class='total-price'>10% Tip: &#8377;" + Math.round(tip) + "</p>");
        out.println("<p class='total-price'>Main Total Price: &#8377;" + Math.round(mainTotalPrice) + "</p>");

        // Quick Calculator section
        out.println("<div class='calculator'>");
        out.println("<div class='calc-block'>");
        out.println("<h3>Quick Calculator</h3>");
        out.println("<input type='text' id='calcInput' value='" + price + "' readonly />");
        out.println("<button onclick='calculate()' class='button'>Calculate 10% Tip</button>");
        out.println("<p id='calcResult'></p>");
        out.println("</div>");

        // Custom Calculator section
        out.println("<div class='calc-block'>");
        out.println("<h3>Custom Calculator</h3>");
        out.println("<input type='text' id='customInput' placeholder='Enter Amount' />");
        out.println("<button onclick='calculateChange()' class='button'>Calculate Change</button>");
        out.println("<p id='customResult'></p>");
        out.println("</div>");
        out.println("</div>");

        // Add a button to redirect to the billing project page
        out.println("<button onclick=\"window.location.href='orderproject.html'\" class='button redirect-button'>Order Some More Food</button>");

        // Close container div
        out.println("</div>");

        // JavaScript for calculators
        out.println("<script>");
        out.println("function calculate() {");
        out.println("  var amount = parseFloat(document.getElementById('calcInput').value);");
        out.println("  var tip = amount * 0.1;");
        out.println("  document.getElementById('calcResult').innerHTML = '10% Tip: &#8377;'+ Math.round(tip); ");
        out.println("}");
        out.println("function calculateChange() {");
        out.println("  var mainTotal = parseFloat(document.getElementById('calcInput').value);");
        out.println("  var customAmount = parseFloat(document.getElementById('customInput').value);");
        out.println("  if (!isNaN(customAmount)) {");
        out.println("    var change = customAmount - mainTotal;");
        out.println("    document.getElementById('customResult').innerHTML = 'Change: &#8377;' + change.toFixed(2);");
        out.println("  } else {");
        out.println("    document.getElementById('customResult').innerHTML = 'Please enter a valid amount';");
	    out.println("	 document.getElementById('customResult').style.color = 'red';");
        out.println("  }");
        out.println("}");
        out.println("</script>");

        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
