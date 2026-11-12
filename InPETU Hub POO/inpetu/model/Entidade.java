package inpetu.model;

/** Base de todas as entidades persistiveis. ID 0 indica "ainda nao gravada". */
public abstract class Entidade {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
