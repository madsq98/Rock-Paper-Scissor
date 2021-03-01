package rps.bll.player;

import rps.bll.game.Move;
import rps.bll.game.Result;

import java.util.ArrayList;

public class MoveAnalyzer {


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
