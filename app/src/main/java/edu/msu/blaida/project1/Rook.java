package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by power on 10/9/2017.
 */

public class Rook extends Piece {
    public Rook(Context context, float x, float y, int player) {
        super(context, x, y, player);
        if(player == 1){
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_rlt45));
        }else{
            this.setBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.chess_rdt45));
        }
    }

    @Override
    Point[] getMovePath(Point start, Point end) {
        if(start.x == end.x && start.y == end.y){
            return null;
        }
        if((start.x == end.x && start.y != end.y)||(start.x != end.x && start.y == end.y)){
            ArrayList<Point> path = new ArrayList<Point>();
            if(start.x==end.x){
                //Y is changing
                for(int i=1;i<Math.abs(start.y-end.y)+1;i++){
                    if(start.y > end.y){
                        //Going down
                        path.add(new Point(start.x,start.y-i));
                    }else{
                        //Goin up
                        path.add(new Point(start.x,start.y+i));
                    }

                }
            }else{
                //X is changing
                for(int i=1;i<Math.abs(start.x-end.x)+1;i++){
                    if(start.x > end.x){
                        //Going left
                        path.add(new Point(start.x-i,start.y));
                    }else{
                        //Going right
                        path.add(new Point(start.x+i,start.y));
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
    Point[] getTakePath(Point start, Point end) {
        return getMovePath(start, end);
    }
}
