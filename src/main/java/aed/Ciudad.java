package aed;

import java.util.Comparator;

public class Ciudad {
    
    int idCiudad;
    int ganancia;
    int perdida;
    int superavit;


    public Ciudad(){
        this.idCiudad = 0;
        this.ganancia = 0;
        this.perdida = 0;
        this.superavit = 0;
    }

    public static final Comparator<Ciudad> porSuperavit() { return (c1, c2) -> 
        { return Integer.compare(c1.superavit, c2.superavit);};
    }
}