package de.exaxway.cleanstore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
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

	@EJB
    Helper photoCamView;

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
        File file = new File("/tmp/a2.png");
        response.setHeader("Content-Length", String.valueOf(file.length()));

		FileInputStream fis = new FileInputStream(file);
		//byte[] b = new byte[1000];
		byte[] b = photoCamView.getLD(request.getSession().getId());
//		int i = fis.read(b);
//		while (i > 0) {
//			response.getOutputStream().write(b, 0, i);
//			i = fis.read(b);
//		}
		response.getOutputStream().write(b, 0, b.length);
		
		response.setContentType("image/png");
		
		LOG.log(Level.INFO, "xxx " + request.getPathInfo());
	}
}
