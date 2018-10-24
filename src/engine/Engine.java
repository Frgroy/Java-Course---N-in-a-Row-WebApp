package engine;

import utils.constants.ConstantMassages;
import utils.xsdClass.GameDescriptor;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class Engine {
    private GameManager gameManager;

    public Engine() {
        gameManager = new GameManager();
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public String loadXmlFile(InputStream xmlFile, String creator) {
        GameDescriptor g = (GameDescriptor) createGameDescriptorFromInputStream(xmlFile);
        String errorMsg = isLegalDescriptor(g);
        if (errorMsg == "") {
            setNewGameAfterXMLApproved(g, creator);
        }
        return errorMsg;
    }

    private GameDescriptor createGameDescriptorFromInputStream(InputStream xmlFile) {
        GameDescriptor g = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(GameDescriptor.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            g = (GameDescriptor) jaxbUnmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return g;
    }

    public String isLegalDescriptor(GameDescriptor g) {
        String errorMsg = "";
        boolean legalData = true;
        if (g.getGame().getBoard().getRows() > 50 || g.getGame().getBoard().getRows() < 5) {
            errorMsg += ConstantMassages.k_IllegalRows + "\n";
        }
        if (g.getGame().getBoard().getColumns().intValue() > 30 || g.getGame().getBoard().getColumns().intValue() < 6) {
            errorMsg += ConstantMassages.k_IllegalCols + "\n";
        }
        if ((g.getGame().getTarget().intValue() >= g.getGame().getBoard().getRows() ||
                g.getGame().getTarget().intValue() >= g.getGame().getBoard().getColumns().intValue()) ||
                g.getGame().getTarget().intValue() < 2) {
            errorMsg += ConstantMassages.k_IllegalTarget + "\n";
        }

        if ((g.getGame().getTarget().intValue() >= g.getGame().getBoard().getRows() ||
                g.getGame().getTarget().intValue() >= g.getGame().getBoard().getColumns().intValue()) ||
                g.getGame().getTarget().intValue() < 2) {
            errorMsg += ConstantMassages.k_IllegalTarget + "\n";
        }

        if (g.getDynamicPlayers().getGameTitle() == "") {
            errorMsg += "Empty game title" + "\n";
        }

        if (this.gameManager.getSpecificGame(g.getDynamicPlayers().getGameTitle()) != null ) {
            errorMsg += "Game name already exists" + "\n";
        }

        if (g.getDynamicPlayers().getTotalPlayers() > 6 ||
                g.getDynamicPlayers().getTotalPlayers() < 2) {
            errorMsg += "Illegal player number" + "\n";
        }


        if (errorMsg != ""){
            errorMsg = ConstantMassages.k_IllegalXMLContent + "\n" + errorMsg;
        }
        return errorMsg;
    }

    public GameDescriptor createGameDescriptor(String xmlPath) throws JAXBException, FileNotFoundException {
        GameDescriptor g;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(GameDescriptor.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            File file = new File(xmlPath);
            if (file.exists() == false) {

                throw new FileNotFoundException();
            }
            g = (GameDescriptor) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            throw e;
        }

        return g;
    }

    public void setNewGameAfterXMLApproved(GameDescriptor g, String creator) {
        if(g.getGame().getVariant().equals("Regular")){
            this.gameManager.addNewGame(new RegularGame(g), creator);
        }
        else if (g.getGame().getVariant().equals("Circular")) {
            this.gameManager.addNewGame(new CircularGame(g), creator);
        }
        else {
            this.gameManager.addNewGame(new PopoutGame(g), creator);
        }
    }

    public boolean ExecuteTurn(String gameName, String userName, int column, boolean isPopout) {
        return gameManager.ExecuteTurn(gameName, userName, column, isPopout);
    }

    public Game getGameByName(String gameName) {
        return gameManager.getSpecificGame(gameName);
    }

    public void addPlayer(Game game, String userName, Boolean isComputer) {
        gameManager.addPlayer(game, userName, isComputer);
    }

    public GameData getGameDataByUserName(String userName) {
        return gameManager.getSpecificGameDataByUserName(userName);
    }

    public void QuitGame(String gameName, String userName) {
        gameManager.QuitGame(gameName, userName);
    }
}
