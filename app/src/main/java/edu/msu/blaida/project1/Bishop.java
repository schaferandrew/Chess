package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.path;

/**
 * Created by power on 10/9/2017.
 */

public class Bishop extends Piece {
    public Bishop(Context context, float x, float y, int player) {
        super(context, x, y, player);
        if(player == 1){
            this.setBitmap(R.drawable.chess_blt45);
        }else{
            this.setBitmap(R.drawable.chess_bdt45);
        }

    }

    @Override
    Point[] getMovePath(Point start, Point end) {
        if(start.x == end.x && start.y == end.y){
            return null;
        }
        if(Math.abs(end.x- start.x) == Math.abs(end.y-start.y)){ //Mathematically, this is a valid move.
            ArrayList<Point> path = new ArrayList<Point>();
            for(int i=1;i<Math.abs(end.x-start.x)+1;i++){
                if(start.x > end.x){
                    //Going left
                    if(start.y > end.y){
                        //Going up
                        path.add(new Point(start.x-i,start.y-i));
                    }else{
                        //Going down
                        path.add(new Point(start.x-i,start.y+i));
                    }
                }else{
                    //Going right
                    if(start.y > end.y){
                        //going up
                        path.add(new Point(start.x+i,start.y-i));
                    }else{
                        //going down
                        path.add(new Point(start.x+i,start.y+i));
                    }
                }
            }

            Point[] vals = new Point[path.size()];
            path.toArray(vals);
            return vals;

        }
        return null;
    }

    @Override
    Point[] getTakePath(Point start, Point end){
        return getMovePath(start, end);
    }
}
