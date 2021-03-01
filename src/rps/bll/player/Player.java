package rps.bll.player;

//Project imports
import rps.bll.game.IGameState;
import rps.bll.game.Move;
import rps.bll.game.Result;

//Java imports
import java.util.ArrayList;

/**
 * Example implementation of a player.
 *
 * @author smsj
 */
public class Player implements IPlayer {

    private String name;
    private PlayerType type;

    /**
     * @param name
     */
    public Player(String name, PlayerType type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public String getPlayerName() {
        return name;
    }


    @Override
    public PlayerType getPlayerType() {
        return type;
    }


    /**
     * Decides the next move for the bot...
     * @param state Contains the current game state including historic moves/results
     * @return Next move
     */
    @Override
    public Move doMove(IGameState state) {
        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();

        //Implement better AI here...
        return getNextMove(results);
    }

    public Move getNextMove(ArrayList<Result> results){
        Move opponentsMostFrequentMove = getOpponentMove(results);
        return getBestMove(opponentsMostFrequentMove);
    }

    private Move getBestMove(Move opponentsMostFrequentMove) {
        if(opponentsMostFrequentMove == Move.Paper){
            return Move.Scissor;
        } else if(opponentsMostFrequentMove == Move.Rock){
            return Move.Paper;
        } else{
            return Move.Rock;
        }
    }

    private Move getOpponentMove(ArrayList<Result> results) {
        Move opponentMove = null;
        int rock = 0, paper = 0, scissor = 0;

        for (Result result : results){

                if(result.getWinnerPlayer().getPlayerType() == PlayerType.Human){
                    opponentMove = result.getWinnerMove();
                } else {
                    opponentMove = result.getLoserMove();
                }
            switch (opponentMove) {
                case Rock -> rock++;
                case Paper -> paper++;
                case Scissor -> scissor++;
            }
        }

        if( rock > paper && rock > scissor){
            return Move.Rock;
        } else if(paper > rock && paper > scissor){
            return Move.Paper;
        } else if(scissor > rock && scissor > paper){
            return Move.Scissor;
        } else {
            return Move.Rock;
        }

    }


}
