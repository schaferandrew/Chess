package edu.msu.blaida.project1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ChessActivity extends AppCompatActivity {

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

        if(bundle != null) {
            // We have saved state
            ChessView view = (ChessView)this.findViewById(R.id.chessView);
            view.loadInstanceState(bundle);
        }

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
                intent = new Intent(this, activity_end.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
}
