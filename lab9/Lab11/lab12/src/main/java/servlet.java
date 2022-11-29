import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.*;

@WebServlet("/submit")
public class servlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;

	public void submit()
	{
		System.out.println("Hello");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	{
		String firstName = req.getParameter("fname");
		System.out.println(req.getParameter("cars"));
		requestedStuff rs = new requestedStuff();
		rs.cars = req.getParameter("cars");
		rs.fname = req.getParameter("fname");
		rs.lname = req.getParameter("lname");
		rs.lang = req.getParameter("start");
		rs.start = req.getParameter("start");
		rs.email = req.getParameter("email");
		rs.volume = req.getParameter("volume");
		rs.tel1 = req.getParameter("tel1");
		rs.tel2 = req.getParameter("tel2");
		rs.tel3 = req.getParameter("tel3");
		Gson gson = null;
		GsonBuilder Gb = new GsonBuilder();
		Gb.setPrettyPrinting();
		gson = Gb.create();
		String retString = gson.toJson(rs);
		resp.setContentType("text/html");
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		out.println("<html>");
		out.println("<body>");
		out.println(retString);
		out.println("</body>");
		out.println("</html>");
		
	}
}

class requestedStuff
{
	String fname;
	String lname;
	String cars;
	String lang;
	String start;
	String email;
	String volume;
	String tel1;
	String tel2;
	String tel3;
}