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

    public Pawn(Context context, float x, float y, int player){
        super(context, x, y, player);
        if(player == 1){
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_plt45));
        }else{
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_pdt45));
        }

    }

    @Override
    boolean validMove(int startX, int startY, int endX, int endY) {
        if(startX == endX && startY == endY){
            return false;
        }
        if(this.firstMove){
            this.firstMove = false;
            if(getPlayer() == 1){
                if(startX == endX && (endY-startY == -1 || endY-startY == -2)){
                    this.firstMove = false;
                    return true;
                }else{
                    return false;
                }
            }else{
                return (startX == endX && (endY-startY == 1 || endY-startY == 2));
            }
        }else{
            if(getPlayer() == 1){
                return (startX == endX && endY-startY == -1);
            }else{
                return (startX == endX && endY-startY == 1);
            }
        }
    }

    @Override
    boolean validTake(int startX, int startY, int endX, int endY) {
        if(startX == endX && startY == endY){
            return false;
        }
        if(getPlayer() == 1){
            if(Math.abs(startX - endX) == 1 && endY-startY == -1){
                this.firstMove = false;
                return true;
            }else{
                return false;
            }
        }else{
            if(Math.abs(startX - endX) == 1 && endY-startY == 1){
                this.firstMove = false;
                return true;
            }else{
                return false;
            }
        }
    }
}
