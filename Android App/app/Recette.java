public class Recette {
    private int id;
    private String nom;
    private String description;
    private int temps;
    private int etapes;
    private String image;

    // Constructeur

    public Recette(int id, String nom, String description, int temps, int etapes, String image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.temps = temps;
        this.etapes = etapes;
        this.image = image;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public int getEtapes() {
        return etapes;
    }

    public void setEtapes(int etapes) {
        this.etapes = etapes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
