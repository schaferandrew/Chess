package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by power on 10/9/2017.
 */

public class Rook extends Piece {
    public Rook(Context context, int x, int y, int player) {
        super(context, x, y, player);
        if(player == 1){
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_rlt45));
        }else{
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_rdt45));
        }
    }

    @Override
    boolean validMove(int x, int y) {
        return false;
    }

    @Override
    boolean validTake(int x, int y) {
        return false;
    }
}
