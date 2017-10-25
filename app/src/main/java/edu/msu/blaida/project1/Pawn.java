package edu.msu.blaida.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;


/**
 * Created by power on 10/5/2017.
 */

public class Pawn extends Piece {


    public Pawn(Context context, float x, float y, int player){
        super(context, x, y, player);
        if(player == 1){
            this.setBitmap(R.drawable.chess_plt45);
        }else{
            this.setBitmap(R.drawable.chess_pdt45);
        }
    }
    @Override
    public boolean isPromotable() { return true;}

    @Override
    Point[] getMovePath(Point start, Point end) {
        ArrayList<Point> path = new ArrayList<Point>();
        if(start.x == end.x && start.y == end.y){
            return null;
        }
        if(this.isFirstMove()){
            if(getPlayer() == 1){
                if(start.x == end.x && (end.y-start.y == -1 || end.y-start.y == -2)){
                    for(int i=1;i<Math.abs(start.y-end.y)+1;i++){
                        path.add(new Point(start.x,start.y-i));
                    }
                }else{
                    return null;
                }
            }else{
                if(start.x == end.x && (end.y-start.y == 1 || end.y-start.y == 2)){
                    for(int i=1;i<Math.abs(start.y-end.y)+1;i++){
                        path.add(new Point(start.x,start.y+i));
                    }
                }else{
                    return null;
                }
            }
        }else{
            if(getPlayer() == 1){
                if(start.x == end.x && end.y-start.y == -1){
                    path.add(new Point(end.x,end.y));
                }else{
                    return null;
                }
            }else{
                if(start.x == end.x && end.y-start.y == 1){
                    path.add(new Point(end.x,end.y));
                }else{
                    return null;
                }
            }

        }
        Point[] vals = new Point[path.size()];
        path.toArray(vals);
        return vals;
    }

    @Override
    Point[] getTakePath(Point start, Point end){
        ArrayList<Point> path = new ArrayList<Point>();
        if(start.x == end.x && start.y == end.y){
            return null;
        }
        if(getPlayer() == 1){
            if(Math.abs(start.x - end.x) == 1 && end.y-start.y == -1){
                path.add(new Point(end.x,end.y));
            }else{
                return null;
            }
        }else{
            if(Math.abs(start.x - end.x) == 1 && end.y-start.y == 1){
                path.add(new Point(end.x,end.y));
            }else{
                return null;
            }
        }

        Point[] vals = new Point[path.size()];
        path.toArray(vals);
        return vals;
    }

}
