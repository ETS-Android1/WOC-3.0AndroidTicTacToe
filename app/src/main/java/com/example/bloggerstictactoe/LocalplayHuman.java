package com.example.bloggerstictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocalplayHuman extends AppCompatActivity implements View.OnClickListener {

    Button but1,but2,but3,but4,but5,but6,but7,but8,but9,reset;
    TextView header;

    int player_O=0;
    int player_X=1;

    int activeplayer=player_O;

    boolean active=true;

    int filledposition[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localplay_human);

        header = findViewById(R.id.top);

        reset = findViewById(R.id.reset);


        but1 = findViewById(R.id.but1);
        but2 = findViewById(R.id.but2);
        but3 = findViewById(R.id.but3);
        but4 = findViewById(R.id.but4);
        but5 = findViewById(R.id.but5);
        but6 = findViewById(R.id.but6);
        but7 = findViewById(R.id.but7);
        but8 = findViewById(R.id.but8);
        but9 = findViewById(R.id.but9);

        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
        but3.setOnClickListener(this);
        but4.setOnClickListener(this);
        but5.setOnClickListener(this);
        but6.setOnClickListener(this);
        but7.setOnClickListener(this);
        but8.setOnClickListener(this);
        but9.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Button clickedbutton = findViewById(v.getId());
        int clickedtag = Integer.parseInt((v.getTag().toString()));

        if(active==false){

            return;
        }


        if (filledposition[clickedtag]!=-1){
            return;
        }

        filledposition[clickedtag] = activeplayer;





        if (activeplayer == player_O) {
            clickedbutton.setText("O");
            activeplayer = player_X;
            header.setText("X's turn");
        } else {
            clickedbutton.setText("X");
            activeplayer = player_O;
            header.setText("O's Turn");
        }


        checkforwin();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LocalplayHuman.class));
                Toast.makeText(LocalplayHuman.this, "O's Turn", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void checkforwin() {
        int arr[][] = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

        for(int i=0; i<8;i++){

            if (filledposition[arr[i][0]] == filledposition[arr[i][1]] && filledposition[arr[i][1]] == filledposition[arr[i][2]]) {
                if (filledposition[arr[i][0]]!=-1){
                    if (filledposition[arr[i][0]]==player_O){
                        header.setText("O is the winner");
                        active=false;
                    }
                    else{
                        header.setText("X is the winner");
                        active = false;
                    }
                }

            }


        }

    }


}