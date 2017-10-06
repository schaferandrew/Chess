package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * Created by power on 10/5/2017.
 */

public class Pawn extends Piece {

    /**
     * Used to monitor if it is the first move. If so, the pawn can move 2 spaces forward.
     */
    private boolean firstMove = true;

    public Pawn(Context context, int x, int y, int player){
        super(context, x, y, player);
        if(player == 1){
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_plt45));
        }else{
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_pdt45));
        }

    }

    @Override
    boolean validMove(int x, int y) {
        if(this.firstMove){

        }
        this.firstMove = false;
        return true;
    }

    @Override
    boolean validTake(int x, int y) {
        if(this.firstMove){

        }
        this.firstMove = false;
        return true;
    }
}
