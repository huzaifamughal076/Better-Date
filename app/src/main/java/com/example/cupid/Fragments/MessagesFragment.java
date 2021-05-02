package com.example.cupid.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cupid.MessagesFragments.messages_All;
import com.example.cupid.MessagesFragments.messages_NewMatches;
import com.example.cupid.MessagesFragments.messages_yourTurn;
import com.example.cupid.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagesFragment extends Fragment {
    LinearLayout all;
    LinearLayout yourturn;
    LinearLayout newmatches;

    LinearLayout btn_pannel;

    TextView alls,turn,matches;

    messages_All messages_all;

    messages_NewMatches messages_newMatches;

   messages_yourTurn messages_yourTurn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessagesFragment newInstance(String param1, String param2) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_messages, container, false);



        all=v.findViewById(R.id.all);
        yourturn=v.findViewById(R.id.your_turn);
        newmatches=v.findViewById(R.id.new_matchs);

        alls =v.findViewById(R.id.alls);
        turn=v.findViewById(R.id.turn);
        matches = v.findViewById(R.id.match);


        messages_all = new messages_All();
        messages_yourTurn = new messages_yourTurn();
        messages_newMatches = new messages_NewMatches();

        btn_pannel = v.findViewById(R.id.btn_pannel);
        btn_pannel.setBackgroundResource(R.drawable.roundings);


    //Layout set listner
        all.setBackgroundColor(Color.parseColor("#0504AA"));
        alls.setTextColor(Color.parseColor("#FFFFFF"));

        setfragment(messages_all);
        all.setBackgroundResource(R.drawable.rounds);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all.setBackgroundResource(R.drawable.rounds);
                alls.setTextColor(Color.parseColor("#FFFFFF"));

                yourturn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                turn.setTextColor(Color.parseColor("#000000"));

                newmatches.setBackgroundResource(R.drawable.roundings);
                matches.setTextColor(Color.parseColor("#000000"));

                setfragment(messages_all);


            }
        });

        yourturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all.setBackgroundResource(R.drawable.roundings);
                alls.setTextColor(Color.parseColor("#000000"));

                yourturn.setBackgroundResource(R.drawable.rounds);
                turn.setTextColor(Color.parseColor("#FFFFFF"));

                newmatches.setBackgroundResource(R.drawable.roundings);
                matches.setTextColor(Color.parseColor("#000000"));

                setfragment(messages_yourTurn);
            }
        });
        newmatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setBackgroundResource(R.drawable.roundings);
                alls.setTextColor(Color.parseColor("#000000"));

                yourturn.setBackgroundResource(R.drawable.roundings);
                turn.setTextColor(Color.parseColor("#000000"));

                newmatches.setBackgroundResource(R.drawable.rounds);
                matches.setTextColor(Color.parseColor("#FFFFFF"));

                setfragment(messages_newMatches);
            }
        });

        ////////////////////////////////////////////////////

        //Text Listener

        return  v;
    }

    private void setfragment(Fragment page) {

        FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame2,page);
        fragmentTransaction.commit();

    }
}