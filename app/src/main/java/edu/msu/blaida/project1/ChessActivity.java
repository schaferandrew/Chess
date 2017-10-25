package edu.msu.blaida.project1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChessActivity extends AppCompatActivity {

    String name1;

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    String name2;

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        ChessView view = (ChessView)this.findViewById(R.id.chessView);
        view.saveInstanceState(bundle);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_chess);

        String n1;
        String n2;
        Bundle extra = getIntent().getExtras();
        if(extra == null){
            n1="White";
            n2="Black";
        }else{
            n1 = extra.getString("p1Name");
            n2 = extra.getString("p2Name");
        }

        if(n1 == null){
            n1 = "White";
        }
        if(n2 == null){
            n2 = "Black";
        }

        setName1(n1);
        setName2(n2);

        final ChessView view = (ChessView)this.findViewById(R.id.chessView);
        final Button resign = (Button) findViewById(R.id.btnResign);
        final Button done = (Button) findViewById(R.id.btnDone);
        final Board board = view.getBoard();
        board.setActivity(this);
        board.setPlayerIndicator((TextView) findViewById(R.id.playerIndicator));

        if(bundle != null) {
            // We have saved state
            view.loadInstanceState(bundle);
        }

        resign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                board.resign();
            }

        });

        done.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                board.swapTurns();
                view.invalidate();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_end:
                startEndActivity(((ChessView)this.findViewById(R.id.chessView)).getBoard().getPlayerTurn());
                return true;
            case R.id.menu_main:
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.menu_instr:
                new AlertDialog.Builder(this)
                        .setTitle("Chess Instructions")
                        .setView(R.layout.activity_instructions)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int button) {
                                dialog.cancel();
                            }
                        })
                        .show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startEndActivity(int winner){
        Intent intent;
        intent = new Intent(ChessActivity.this, activity_end.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("p1Name",getName1());
        intent.putExtra("p2Name",getName2());
        intent.putExtra("winner",winner);
        startActivity(intent);
    }

}
