package com.example.bloggerstictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LocalplayComputer extends AppCompatActivity implements View.OnClickListener {


    Button but1,but2,but3,but4,but5,but6,but7,but8,but9,reset;
    TextView top;
    int min= 0;
    int max= 9;

    boolean play=true;
    int k;

    int available[]= {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localplay_computer);

        top = findViewById(R.id.top);
        reset =findViewById(R.id.reset);

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

        if (play == false){return;}

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LocalplayComputer.class));
            }
        });


    }

    private void randomnumbergenerate() {
        k = (int) (Math.random() * 9);


        if (available[k]==-1){

        }else{
            if(available[0]!=-1&&available[1]!=-1&&available[2]!=-1&&available[3]!=-1&&available[4]!=-1&&available[5]!=-1&&available[6]!=-1&&available[7]!=-1&&available[8]!=-1){return;}
            else {randomnumbergenerate();}
        }
    }

    @Override
    public void onClick(View v) {

        if(play == false){return;}

        Button clickedbutton = findViewById(v.getId());
        int clickedtag = Integer.parseInt((v.getTag().toString()));

        if (available[clickedtag]==-1){
            clickedbutton.setText("O");
            available[clickedtag]=0;
        } else {return;}


        checkfordraw();
        checkforwin();
        if(play==false){return;}
        randomnumbergenerate();

        if(available[0]!=-1&&available[1]!=-1&&available[2]!=-1&&available[3]!=-1&&available[4]!=-1&&available[5]!=-1&&available[6]!=-1&&available[7]!=-1&&available[8]!=-1){return;}
        available[k] = 1;
        if(k==0){but1.setText("X");}if(k==1){but2.setText("X");}if(k==2){but3.setText("X");}
        if(k==3){but4.setText("X");}if(k==4){but5.setText("X");}if(k==5){but6.setText("X");}
        if(k==6){but7.setText("X");}if(k==7){but8.setText("X");}if(k==8){but9.setText("X");}



        checkfordraw();
        checkforwin();
    }

    private void checkfordraw() {
        if(available[0]!=-1&&available[1]!=-1&&available[2]!=-1&&available[3]!=-1&&available[4]!=-1&&available[5]!=-1&&available[6]!=-1&&available[7]!=-1&&available[8]!=-1){
            top.setText("Draw");
            play = false;
        }
    }

    private void checkforwin() {
        int arr[][] = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

        for(int i=0; i<8;i++){

            if (available[arr[i][0]] == available[arr[i][1]] && available[arr[i][1]] == available[arr[i][2]]) {
                if (available[arr[i][0]]!=-1){
                    if (available[arr[i][0]]==0){
                        top.setText("O is the winner");
                        play = false;
                    }
                    else{
                        top.setText("X is the winner");
                        play= false;

                    }
                }

            }


        }

    }
}