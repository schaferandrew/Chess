package edu.msu.blaida.project1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartGame(View view) {
        EditText pl1Name = (EditText)findViewById(R.id.playerOneInput);
        EditText p2Name = (EditText)findViewById(R.id.playerTwoInput);
        Intent intent = new Intent(this, ChessActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("p1Name",pl1Name.getText().toString());
        intent.putExtra("p2Name",p2Name.getText().toString());
        startActivity(intent);
    }

    public void onInstructions(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Chess Instructions")
                .setView(R.layout.activity_instructions)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int button) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
