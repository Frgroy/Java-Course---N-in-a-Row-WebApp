package engine;

import utils.xsdClass.*;

import java.io.Serializable;

public class RegularGame extends Game implements Serializable {
    public RegularGame(GameDescriptor g) {
        super(g);
    }

    public RegularGame(Game g) {super(g);}
}
