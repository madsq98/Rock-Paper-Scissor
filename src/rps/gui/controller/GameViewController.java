package rps.gui.controller;

// Java imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import rps.bll.game.GameManager;
import rps.bll.game.Move;
import rps.bll.game.Result;
import rps.bll.game.ResultType;
import rps.bll.player.IPlayer;
import rps.bll.player.Player;
import rps.bll.player.PlayerType;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author smsj
 */
public class GameViewController implements Initializable {

    public TextField playerMoveTextField;
    public TextField botMoveTextField;
    public TextField playerPoints;
    public TextField botPoints;

    private IPlayer human;
    private IPlayer bot;

    private GameManager gm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        human = new Player("Human", PlayerType.Human);
        bot = new Player("Bot",PlayerType.AI);
        gm = new GameManager(human,bot);

        playerPoints.setText("0");
        botPoints.setText("0");
    }

    public void playerMoveRock(ActionEvent actionEvent) {
        play(Move.Rock);
    }

    public void playerMovePaper(ActionEvent actionEvent) {
        play(Move.Paper);
    }

    public void playerMoveScissors(ActionEvent actionEvent) {
        play(Move.Scissor);
    }

    private void play(Move m) {
        Result doMove = gm.playRound(m);

        playerMoveTextField.setText(m.name());

        if(doMove.getType() == ResultType.Tie) {
            //IT WAS A TIE
            botMoveTextField.setText(doMove.getLoserMove().name());
        }
        else if(doMove.getWinnerPlayer() == human) {
            //HUMAN WON
            int currentPoints = Integer.parseInt(playerPoints.getText());
            int newPoints = currentPoints+1;
            playerPoints.setText(String.valueOf(newPoints));

            botMoveTextField.setText(doMove.getLoserMove().name());
        } else {
            //BOT WON
            int currentPoints = Integer.parseInt(botPoints.getText());
            int newPoints = currentPoints+1;
            botPoints.setText(String.valueOf(newPoints));

            botMoveTextField.setText(doMove.getWinnerMove().name());
        }
    }
}
