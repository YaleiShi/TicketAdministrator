package frontEnd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataBase.GreatDataBase;

public class SearchServlet extends BaseServlet{
	
	/**
	 * call the partial search method in the data manager
	 * and display result list
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.checkLogin(request, response);
		
		GreatDataBase gdb = (GreatDataBase) getServletConfig().getServletContext().getAttribute(DATABASE);
		String searchType = request.getParameter("searchType");
		String query = request.getParameter("query");
		ResultSet res;
		
		PrintWriter out = prepareResponse(response);
		out.println(header("Search Events", request));
		
		out.println(TableStyle);
		out.println("<tr><th>Event Id</th><th>Event Name</th><th>Date</th><th>Number of Tickets</th></tr>");
		try {
			res = gdb.partialSearch(searchType, query);
			while(res != null && res.next()) {
				String eventId = res.getString("eventId");
				String eventName = res.getString("eventName");
				String date = res.getString("date");
				int num = res.getInt("numTickets");
				out.println("<tr><td><a href=\"event?eventId=" + eventId + "\">" + eventId + "</a></td>"
						      + "<td>" + eventName + "</td>"
						      + "<td>" + date + "</td>"
						      + "<td>" + num + "</td></tr>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.println("</table>");
		out.println(footer());
		
	}

}
