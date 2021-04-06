package com.example.bloggerstictactoe;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_local extends Fragment {
    private onFragmentLocalHumanselected listner;
    private  onFragmentLocalComselected listner2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.local,container,false);
        Button humanbutton = view.findViewById(R.id.buthuman);
        humanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnHumanselected();
            }
        });

        Button compbutton = view.findViewById(R.id.comp);
        compbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner2.onCompselected();
            }
        });

        return view;


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof  onFragmentLocalHumanselected) {
            listner = (onFragmentLocalHumanselected) context;
        }else{
            throw new ClassCastException(context.toString()+" must implement listner");
        }

        if (context instanceof onFragmentLocalComselected){
            listner2= (onFragmentLocalComselected) context;
        }else {
            throw new ClassCastException(context.toString()+ " must implement listner");
        }
    }

    public  interface onFragmentLocalHumanselected{
        public void OnHumanselected();

    }

    public interface onFragmentLocalComselected{
        public void onCompselected();
    }

}

