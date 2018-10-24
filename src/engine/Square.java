package engine;

import javax.swing.text.Position;
import java.io.Serializable;

public class Square implements Serializable{

    private Disc currentDisc = null;

    public Disc getCurrentDisc() {
        return currentDisc;
    }

    public void setCurrentDisc(Disc currentDisc) {
        this.currentDisc = currentDisc;
    }
}
