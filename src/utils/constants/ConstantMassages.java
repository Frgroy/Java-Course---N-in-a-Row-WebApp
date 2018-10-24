package utils.constants;

public class ConstantMassages {
    public static String k_LoadXMLNecessary = "HELLO MY FRIEND! Please load an XML file before trying any other commands";
    public static String k_LoadXMLUnNecessary = "HEY DUDE you have already loaded an Xml file for this game. NOW LET'S PLAY!";
    public static String k_GameStartedError= "You need to pay attention, the Game is already started.";
    public static String k_NoMovesMessage = "Illegal request. There are no moves in the game yet.";
    //public static String k_BoardState = "Board state: ";
    //public static String k_GameDetails = "Game details: ";
    public static String k_NoneActiveGameError = "NO NO NO... you must activate the game before trying to play.";
    public static String k_ActiveGameMessage = "WRONG INPUT! The game is already in motion.";
    public static String k_AskForFileName = "Enter XML file path: ";
    public static String k_XMLFinish = ".xml";
    public static String k_Menu = "Choose one of the following actions: " + "\n" +
            "[1] Read game data from XML file " + "\n" +
            "[2] Start game " + "\n" +
            "[3] Show game details " + "\n" +
            "[4] Execute turn " + "\n" +
            "[5] Show game history " + "\n" +
            "[6] Exit "  + "\n" +
            "[7] Undo "  + "\n" +
            "[8] Save Game "  + "\n" +
            "[9] Load Game ";
    public static String k_HumanPlayerNecessary = "wrong input! I am sorry but one of the players must be Human.";
    public static String k_FullColumn = "ILLEGAL INPUT! Selected column if full";
    public static String k_PlayerWon = "GAME FINISHED! The winner is: ";
    public static String k_GameDrawed = "GAME FINISHED! We have a tie. ";
    public static String k_InCorrectXmlName = "You entered an incorrect file path!";
    public static String k_InCorrectFileType = "Invalid file extension! file must end with .xml";
    public static String k_IllegalRows = "Illegal rows value";
    public static String k_IllegalCols = "Illegal columns value";
    public static String k_IllegalTarget = "Illegal target value";
    public static String k_IllegalXMLContent = "XML Content is Incorrect!";
    public static String k_XMLFileLoadedSuccessfully = "XML file loaded successfully!";
    public static String k_informUserWrongInputHumanOrComputer = "Illegal choice. Press 1 for Human or 2 for Computer";
    public static String k_informUserChoosenColumnOutOfRange = "Illegal choice. Enter number between 1 to ";
    public static String k_SaveFailed = "Save failed!";
    public static String k_SavedSuccesfully = "Game saved succesfully.";
    public static String k_LoadedSuccesfully = "Game loaded succesfully." ;
    public static String k_LoadFailed = "Load failed!";
    public static String k_NoHistoryError = "NO CAN DO... there is no history to show for this game";;
    public static String k_NoMoveToUndoError = "NO CAN DO... there are no moves to undo at this game";;
    public static String k_NoneActiveGameSaveError = "NO NO NO... You didn't activate the game, there is nothing to save yet.";
    public static String k_IllegalNumOfPlayers = "Illegal number of players";
    public static String k_IllegalIDOfPlayers = "There is an identical player IDs";
}