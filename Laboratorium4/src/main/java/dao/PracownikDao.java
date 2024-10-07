package dao;

import beans.Pracownik;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
public class PracownikDao {
    JdbcTemplate template;
    public void setTemplate(JdbcTemplate template) {
        this.template = template; //wstrzyknięcie przez metodę set
    }
    public int save(Pracownik p) {
        String sql = "insert into pracownik (nazwisko,pensja,firma) " + "values('" + p.getNazwisko() + "'," + p.getPensja() + ",'" + p.getFirma() + "')";
        return template.update(sql);
    }
    public List<Pracownik> getAll() {
        return template.query("select * from pracownik", (ResultSet rs, int row) -> {
            Pracownik e = new Pracownik();
            e.setId(rs.getInt(1));
            e.setNazwisko(rs.getString(2));
            e.setPensja(rs.getFloat(3));
            e.setFirma(rs.getString(4));
            return e;
        });
    }
}
