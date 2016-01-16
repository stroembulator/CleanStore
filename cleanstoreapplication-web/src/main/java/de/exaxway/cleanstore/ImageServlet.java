package de.exaxway.cleanstore;

import de.exaxway.cleanstore.persist.PhotoData;
import de.exaxway.cleanstore.persist.PhotoDataFacadeLocal;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {
    private static final long serialVersionUID = -4695962497513334766L;

    private static final Logger LOG = Logger.getLogger(ImageServlet.class.getName());

    @EJB
    private PhotoDataFacadeLocal photoDataFacade;


    public ImageServlet() {
        super();
    }

    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String imageName = request.getPathInfo().substring(1);

        PhotoData photoData = photoDataFacade.findByName(imageName);
        byte[] imageData = photoData.getPhotoData();
        response.setHeader("Content-Length", String.valueOf(imageData.length));
        response.setContentType("image/png");
        response.getOutputStream().write(imageData, 0, imageData.length);

        LOG.log(Level.SEVERE, "doGet " + imageName);
    }
}
