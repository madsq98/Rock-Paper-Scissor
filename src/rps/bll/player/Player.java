package rps.bll.player;

//Project imports
import rps.bll.game.IGameState;
import rps.bll.game.Move;
import rps.bll.game.Result;

//Java imports
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Example implementation of a player.
 *
 * @author smsj
 */
public class Player implements IPlayer {

    private String name;
    private PlayerType type;

    private ArrayList<Move> afterRock = new ArrayList<>();
    private ArrayList<Move> afterPaper = new ArrayList<>();
    private ArrayList<Move> afterScissor = new ArrayList<>();

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

        Move prevMove = getPreviousPlayerMove(results);
        Move secondPrevMove = getSecondPreviousPlayerMove(results);
        //Implement better AI here...

        return getBestNextMove(prevMove, secondPrevMove);
    }

    private Move getPreviousPlayerMove(ArrayList<Result> results) {
        Move lastMove = Move.Rock;
        if(results.size() > 0) {
            Result lastResult = results.get(results.size()-1);
            lastMove = lastResult.getWinnerPlayer().getPlayerType() == PlayerType.Human ? lastResult.getWinnerMove() : lastResult.getLoserMove();
        }

        return lastMove;
    }

    private Move getSecondPreviousPlayerMove(ArrayList<Result> results) {
        Move lastMove = Move.Rock;
        if(results.size() > 1) {
            Result lastResult = results.get(results.size()-2);
            lastMove = lastResult.getWinnerPlayer().getPlayerType() == PlayerType.Human ? lastResult.getWinnerMove() : lastResult.getLoserMove();
        }

        return lastMove;
    }

    private Move getBestNextMove(Move prevMove, Move secondPrevMove) {
        switch (secondPrevMove) {
            case Rock -> afterRock.add(prevMove);
            case Paper -> afterPaper.add(prevMove);
            case Scissor -> afterScissor.add(prevMove);
        }

        int[] prob = {0,0,0};

        switch(prevMove) {
            case Rock:
                for(Move m : afterRock) {
                    switch (m) {
                        case Rock -> prob[0]++;
                        case Paper -> prob[1]++;
                        case Scissor -> prob[2]++;
                    }
                }
                break;
            case Paper:
                for(Move m : afterPaper) {
                    switch (m) {
                        case Rock -> prob[0]++;
                        case Paper -> prob[1]++;
                        case Scissor -> prob[2]++;
                    }
                }
                break;
            case Scissor:
                for(Move m : afterScissor) {
                    switch (m) {
                        case Rock -> prob[0]++;
                        case Paper -> prob[1]++;
                        case Scissor -> prob[2]++;
                    }
                }
                break;
        }

        Move returnMove = Move.Rock;
        int index = getIndexOfMax(prob);

        switch (index) {
            case 0 -> returnMove = Move.Paper;
            case 1 -> returnMove = Move.Scissor;
            case 2 -> returnMove = Move.Rock;
        }

        return returnMove;
    }

    public int getIndexOfMax(int array[]) {
        if (array.length == 0) {
            return -1; // array contains no elements
        }
        int max = array[0];
        int pos = 0;

        for(int i=1; i<array.length; i++) {
            if (max < array[i]) {
                pos = i;
                max = array[i];
            }
        }
        return pos;
    }
}
