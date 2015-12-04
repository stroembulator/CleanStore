package de.exaxway.cleanstore;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class image
 */
@WebServlet("/image/*")
public class image extends HttpServlet {
	Logger LOG = Logger.getLogger("sfsf");
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public image() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileInputStream fis = new FileInputStream("/tmp/a2.png");
		byte[] b = new byte[1000];
		int i = fis.read(b);
		while (i > 0) {
			response.getOutputStream().write(b, 0, i);
			i = fis.read(b);
		}
		response.setContentType("image/png");
		
		LOG.log(Level.INFO, "xxx " + request.getPathInfo());
	}
}
