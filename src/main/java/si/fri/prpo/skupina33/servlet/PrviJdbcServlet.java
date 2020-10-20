package si.fri.prpo.skupina33.servlet;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("servlet")
public class PrviJdbcServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter writer = resp.getWriter();
        ConfigurationUtil.getInstance().get("kumuluzee.name").ifPresent(s -> writer.println("Ime: " + s));
        ConfigurationUtil.getInstance().get("kumuluzee.version").ifPresent(s -> writer.println("Version: " + s));
        ConfigurationUtil.getInstance().get("kumuluzee.env.name").ifPresent(s -> writer.println("Izvajalno okolje: " + s));
    }

}
