package si.fri.prpo.skupina33.jdbc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UporabnikDaoImpl implements BaseDao {

    private static UporabnikDaoImpl instance;
    private static final Logger log = Logger.getLogger(UporabnikDaoImpl.class.getName());

    private Connection connection;

    public UporabnikDaoImpl(){
        connection = getConnection();
    }

    public static UporabnikDaoImpl getInstance() {
        if (instance == null) {
            instance = new UporabnikDaoImpl();
        }

        return instance;
    }

    private Uporabnik getUporabnikFromRS(ResultSet rs) throws SQLException {

        String ime = rs.getString("ime");
        String priimek = rs.getString("priimek");
        String uporabniskoIme = rs.getString("uporabniskoime");
        return new Uporabnik(ime, priimek, uporabniskoIme);
    }

    @Override
    public Connection getConnection() {
        try {

            InitialContext initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup("jdbc/SimpleJdbcDS");
            return ds.getConnection();

        } catch (NamingException | SQLException e) {
            log.severe("Cannot get connection: " + e.toString());
        }

        return null;
    }

    @Override
    public Entiteta vrni(int id) {
        PreparedStatement ps = null;

        try {

            if (connection == null) {
                connection = getConnection();
            }

            String sql = "SELECT * FROM uporabnik WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getUporabnikFromRS(rs);
            } else {
                log.info("Uporabnik ne obstaja");
            }

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe("Napaka " + e.toString());
                }
            }
        }
        return null;
    }

    @Override
    public void vstavi(Entiteta ent) {
        PreparedStatement ps = null;
        Uporabnik u = (Uporabnik) ent;

        try {

            if (connection == null) {
                connection = getConnection();
            }

            String sql = "INSERT INTO uporabnik (ime, priimek, uporabniskoime) VALUES (?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, u.getIme());
            ps.setString(2, u.getPriimek());
            ps.setString(3, u.getUporabniskoIme());
            ps.executeQuery();

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public void odstrani(int id) {
        PreparedStatement ps = null;

        try {

            if (connection == null) {
                connection = getConnection();
            }

            String sql = "DELETE FROM uporabnik WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public void posodobi(Entiteta ent) {
        PreparedStatement ps = null;
        Uporabnik u = (Uporabnik) ent;
        try {

            if (connection == null) {
                connection = getConnection();
            }

            String sql = "UPDATE uporabnik SET ime=?, priimek=?,uporabniskoime=? WHERE id=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, u.getIme());
            ps.setString(2, u.getPriimek());
            ps.setString(3, u.getUporabniskoIme());

            ps.executeUpdate();

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public List<Entiteta> vrniVse() {

        PreparedStatement ps = null;
        List<Entiteta> entitete = new ArrayList<>();

        try {

            if (connection == null) {
                connection = getConnection();
            }

            String sql = "SELECT * FROM uporabnik";
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                entitete.add(getUporabnikFromRS(rs));
            }

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
        return entitete;
    }
}
