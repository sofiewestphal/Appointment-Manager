package com.example.sofie.appointmentmanagementapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DeleteChooseFragment extends Fragment {

    // VARIABLES
    View btnDeleteChosenAppointment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_delete_choose, container, false);

        btnDeleteChosenAppointment = rootView.findViewById(R.id.btnDeleteChosenAppointment);

        initButtons();

        return rootView;
    }

    public void initButtons(){
        btnDeleteChosenAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the number of the appointment the user has chosen to delete
                String siChosenIdToDelete = ChooseAppointmentFragment.etNumberToDelete.getText().toString();
                if(siChosenIdToDelete.trim().length() > 0){
                    int iChosenIdToDelete = Integer.parseInt(siChosenIdToDelete);

                    // replace the DeleteChooseFragment with DeleteConfirmFragment
                    Fragment DeleteConfirmFragment = new DeleteConfirmFragment();
                    // set the variable iChosenIdToDelete in DeleteActivity equal to the id of the appointment the user
                    // wants to delete
                    DeleteActivity.iChosenIdToDelete = ChooseAppointmentFragment.aiAppointmentIds[iChosenIdToDelete - 1];
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fl_delete_activity, DeleteConfirmFragment);

                    fragmentTransaction.commit();
                }

            }
        });
    }
}
