package si.fri.prpo.skupina33.servlet;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.skupina33.jdbc.BaseDao;
import si.fri.prpo.skupina33.jdbc.Entiteta;
import si.fri.prpo.skupina33.jdbc.Uporabnik;
import si.fri.prpo.skupina33.jdbc.UporabnikDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("servlet")
public class PrviJdbcServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter writer = resp.getWriter();

        //branje konfiguracije
        ConfigurationUtil.getInstance().get("kumuluzee.name").ifPresent(s -> writer.println("Ime: " + s));
        ConfigurationUtil.getInstance().get("kumuluzee.version").ifPresent(s -> writer.println("Version: " + s));
        ConfigurationUtil.getInstance().get("kumuluzee.env.name").ifPresent(s -> writer.println("Izvajalno okolje: " + s));

        //dostop do DB
        BaseDao uporabnikDAO = UporabnikDaoImpl.getInstance();

        //dodajanje
        Uporabnik uporabnik = new Uporabnik("Bobi", "Brown","BobiB");

        writer.append("Dodajanje: \n" + uporabnik.toString());
        uporabnikDAO.vstavi(uporabnik);
        writer.append("\n\n");

        //izpis uporabnika
        writer.append("Izpis uporabnika: ");
        Entiteta pridobljenUporabnik = uporabnikDAO.vrni(1);
        writer.append(pridobljenUporabnik.toString());
        writer.append("\n\n");

        //izpis vseh uporabnikov
        writer.append("Seznam uporabnikov: \n");
        List<Entiteta> uporabniki = uporabnikDAO.vrniVse();

        writer.append("Dolzina seznama: " +  uporabniki.size() + "\n");
        uporabniki.stream().forEach(u -> writer.append(u.toString() + "\n"));
        writer.append("\n\n");

        //izbris uporabnika
        //writer.append("Izbris uporabnika z id 4\n");
        //uporabnikDAO.odstrani(4);

        //izpis vseh uporabnikov po izbrisu
        //writer.append("Seznam uporabnikov: \n");
        //uporabniki = uporabnikDAO.vrniVse();
        //writer.append("Dolzina seznama: " +  uporabniki.size());
        //writer.append("\n");
        //uporabniki.stream().forEach(u -> writer.append(u.toString() + "\n"));
        //writer.append("\n\n");
    }

}
