package com.example.sofie.appointmentmanagementapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MoveMainFragment extends Fragment {
    // VARIABLES
    View btnMoveChosenAppointment;
    TextView tvNumberToDelete;
    TextView tvTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_move_main, container, false);

        btnMoveChosenAppointment = rootView.findViewById(R.id.btnMoveChosenAppointment);
        tvNumberToDelete = rootView.findViewById(R.id.tvNumberToDelete);
        tvTitle = rootView.findViewById(R.id.tvTitle);

        initButtons();
        setText();

        return rootView;
    }



    public void setText(){
        tvNumberToDelete.setText(R.string.txtNumberToMove);
        tvTitle.setText(R.string.titleMoveDisplayAppointments);
    }

    public void initButtons(){
        btnMoveChosenAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the number of the appointment the user has chosen to delete
                String siChosenIdToDelete = ChooseAppointmentFragment.etNumberToDelete.getText().toString();
                if(siChosenIdToDelete.trim().length() > 0){
                    int iChosenIdToDelete = Integer.parseInt(siChosenIdToDelete);

                    // replace the DeleteChooseFragment with DeleteConfirmFragment
                    Fragment MoveChooseDateFragment = new MoveChooseDateFragment();
                    // set the variable iChosenIdToDelete in DeleteActivity equal to the id of the appointment the user
                    // wants to delete
                    MoveActivity.iChosenIdToDelete = ChooseAppointmentFragment.aiAppointmentIds[iChosenIdToDelete - 1];
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fl_move_activity, MoveChooseDateFragment);

                    fragmentTransaction.commit();
                }
            }
        });
    }
}
