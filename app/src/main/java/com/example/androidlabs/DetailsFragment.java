package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    private Bundle dataFromActivity;
    private long id;
    private AppCompatActivity parentActivity;
    private View parent;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MESSAGE = "message",
                                IS_SEND = "isSend",
                                ITEM_ID = "itemId",
                                IS_PHONE = "isPhone";

    // TODO: Rename and change types of parameters

    Context context;

    private String message;
    private boolean isSend;
    private long dataId;
    private boolean isPhone;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, boolean param2, long param3, boolean param4) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(MESSAGE, param1);
        args.putBoolean(IS_SEND, param2);
        args.putLong(ITEM_ID, param3);
        args.putBoolean(IS_PHONE, param4);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(MESSAGE);
            isSend = getArguments().getBoolean(IS_SEND);
            dataId = getArguments().getLong(ITEM_ID);
            isPhone = getArguments().getBoolean(IS_PHONE);
            Log.d("DEV", isPhone ? "phone" : "other");
        }

        Log.d("FRAG", "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("FRAG", "onCreateView");

        View result = inflater.inflate(R.layout.fragment_details, container, false);

        TextView messageHere = result.findViewById(R.id.mgID);
        messageHere.setText(message);
        TextView idID = result.findViewById(R.id.idID);
        idID.setText( "ID = " + dataId);

        CheckBox cb = result.findViewById(R.id.checkBoxID);
        cb.setChecked(isSend); ;

        Button hide = (Button)result.findViewById(R.id.hideBtnID);
        hide.setOnClickListener( click ->  remove()  );

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d("FRAG", "onAttach");

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity) getActivity();

    }

    private void remove(){
        if(isPhone)
            Log.d("DEV", "phone");
        else
            Log.d("DEV", "other");
        if(!isPhone)
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();

        else{
            Intent nextActivity = new Intent(getActivity().getApplication(), ChatRoomActivity.class);
            startActivity(nextActivity); //make the transition
        }
    }
}