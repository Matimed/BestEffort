package aed;

import java.util.Comparator;

public class Ciudad {
    int id;
    int ganancia;
    int perdida;

    public Ciudad(int id){
        this.id = id;
        this.ganancia = 0;
        this.perdida = 0;
    }

    public int superavit() { return this.ganancia - this.perdida;}

    public static final Comparator<Ciudad> porSuperavit() { 
        return (c1, c2) -> {
            if (c1.superavit() != c2.superavit()){ return Integer.compare (c1.superavit(), c2.superavit()); }
            else{ return Integer.compare(c2.id, c1.id); }
        };
    }

}