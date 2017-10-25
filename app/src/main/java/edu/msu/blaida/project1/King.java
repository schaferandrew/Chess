package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by power on 10/9/2017.
 */

public class King extends Piece {

    public King(Context context, float x, float y, int player) {
        super(context, x, y, player);
        if(player == 1){
            this.setBitmap(R.drawable.chess_klt45);
        }else{
            this.setBitmap(R.drawable.chess_kdt45);
        }
    }

    @Override
    Point[] getMovePath(Point start, Point end){
        if(start.x == end.x && start.y == end.y){
            return null;
        }
        if((Math.abs(start.x - end.x) < 2 && Math.abs(start.y - end.y) < 2)){ //Mathematically true, now get the path
            Point destination = new Point(end.x, end.y);
            Point[] path = new Point[1];
            path[0] = destination;
            return path;
        }
        return null;
    }

    @Override
    Point[] getTakePath(Point start, Point end) {
        return getMovePath(start,end);
    }
}
