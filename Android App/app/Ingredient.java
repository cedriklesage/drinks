public class Ingredient {

    private int id;
    private String nom;
    private String type;
    private double alcool;
    private String pays;
    private String image;


    // Constructeur

    public Ingredient(int id, String nom, String type, double alcool, String pays, String image) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.alcool = alcool;
        this.pays = pays;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAlcool() {
        return alcool;
    }

    public void setAlcool(double alcool) {
        this.alcool = alcool;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
