package com.example.choupisport;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChronomterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChronometerFragment extends Fragment {

    public static final int INIT_TIMER = 30;

    private int iChronoTime = INIT_TIMER;
    private int iCounter    = INIT_TIMER;

    private ToneGenerator toneGEndBip = new ToneGenerator(AudioManager.STREAM_ALARM, 90);

    public ChronometerFragment() {
        // Required empty public constructor
    }

    public static ChronometerFragment newInstance() {
        ChronometerFragment fragment = new ChronometerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_chronometer, container, false);

        Button buttonChronoStart      = view.findViewById(R.id.buttonChronoStart);
        TextView textChronoTime       = view.findViewById(R.id.textChronoTime);


        textChronoTime.setText(String.valueOf(iCounter));

        buttonChronoStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                iChronoTime = Integer.parseInt(textChronoTime.getText().toString());
                iCounter    = iChronoTime;

                new CountDownTimer(iChronoTime*1000, 1000){

                    public void onTick(long millisUntilFinished){
                        textChronoTime.setText(String.valueOf(iCounter));
                        iCounter--;
                    }
                    public  void onFinish(){
                        textChronoTime.setText(Integer.toString(INIT_TIMER));

                        Context context = getContext();
                        CharSequence text = "STOP";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        iChronoTime = iChronoTime/1000;

                        //emission d'un bip
                        toneGEndBip.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 1000);
                    }
                }.start();
            }
        });



        return view;

    }
}