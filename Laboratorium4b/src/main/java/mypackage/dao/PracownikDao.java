package mypackage.dao;

import mypackage.beans.Pracownik;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PracownikDao {
    JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public int save(Pracownik p) {
        String sql = "insert into pracownik (nazwisko, pensja, firma) values('" + p.getNazwisko() + "'," + p.getPensja() + ",'" + p.getFirma() + "')";
        if (p.getNazwisko().matches(".*\\d.*")) {
            throw new IllegalArgumentException("Name cannot contain numbers: " + p.getNazwisko());
        }
        return template.update(sql);
    }

    public List<Pracownik> getAll() {
        return template.query("select * from pracownik", new RowMapper<Pracownik>() {
            @Override
            public Pracownik mapRow(ResultSet rs, int row) throws SQLException {
                Pracownik e = new Pracownik();
                e.setId(rs.getInt(1));
                e.setNazwisko(rs.getString(2));
                e.setPensja(rs.getFloat(3));
                e.setFirma(rs.getString(4));
                return e;
            }
        });
    }
    public int delete(int id) {
        String sql = "DELETE FROM pracownik WHERE id = ?";
        return template.update(sql, id);
    }

    public int update(Pracownik p) {
        String sql = "UPDATE pracownik SET nazwisko = ?, pensja = ?, firma = ? WHERE id = ?";
        if (p.getNazwisko().matches(".*\\d.*")) {
            throw new IllegalArgumentException("Name cannot contain numbers: " + p.getNazwisko());
        }   
        return template.update(sql, p.getNazwisko(), p.getPensja(), p.getFirma(), p.getId());
    }

    public Pracownik getPracownikById(int id) {
        List<Pracownik> prlist = template.query(
            "SELECT * FROM pracownik WHERE id = ?", 
            new Object[] {id},
            new RowMapper<Pracownik>() {
                @Override
                public Pracownik mapRow(ResultSet rs, int row) throws SQLException {
                    Pracownik e = new Pracownik();
                    e.setId(rs.getInt("id")); 
                    e.setNazwisko(rs.getString("nazwisko"));
                    e.setPensja(rs.getFloat("pensja"));
                    e.setFirma(rs.getString("firma"));
                    return e;
                }
            }
        );

        // Check if the list is empty before returning the first element
        if (prlist.isEmpty()) {
            return null; 
        }

        return prlist.get(0);
    }


}
