package Entity;
import jakarta.persistence.*;

@Entity
public class Zadanie{
    @Id
    @GeneratedValue()
    private Long id;

    @Column(nullable = false)
    private String nazwa;

    @Lob
    @Column(nullable = false)
    private String opis;

    @Column(nullable = false)
    private Double koszt;

    @Column(nullable = false)
    private Boolean wykonane = false;

    // Default constructor
    public Zadanie() {
        this.koszt = 2000.0;
        this.nazwa = "Zadanie";
        this.opis = "Zadanie do wykonania";
    }
    // toString method
    @Override
    public String toString() {
        return "Encja Zadanie{ id=" + id + ", " + nazwa + ", " + opis + ", koszt=" + koszt + ", wykonane=" + wykonane + "}";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Double getKoszt() {
        return koszt;
    }

    public void setKoszt(Double koszt) {
        this.koszt = koszt;
    }

    public Boolean getWykonane() {
        return wykonane;
    }

    public void setWykonane(Boolean wykonane) {
        this.wykonane = wykonane;
    }
}