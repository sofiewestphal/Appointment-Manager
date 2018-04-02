package com.example.sofie.appointmentmanagementapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewMainFragment extends Fragment{


        // VARIABLES
        View btnEditChosenAppointment;
        TextView tvNumberToDelete;
        TextView tvTitle;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_view_main, container, false);

            btnEditChosenAppointment = rootView.findViewById(R.id.btnEditChosenAppointment);
            tvNumberToDelete = rootView.findViewById(R.id.tvNumberToDelete);
            tvTitle = rootView.findViewById(R.id.tvTitle);

            initButtons();
            setText();

            return rootView;
        }

    public void setText(){
        tvNumberToDelete.setText(R.string.txtNumberToEdit);
        tvTitle.setText(R.string.titleViewDisplayAppointments);
    }

        public void initButtons(){
            btnEditChosenAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // get the number of the appointment the user has chosen to delete
                    /*String siChosenIdToDelete = ChooseAppointmentFragment.etNumberToDelete.getText().toString();
                    int iChosenIdToDelete = Integer.parseInt(siChosenIdToDelete);

                    // replace the DeleteChooseFragment with DeleteConfirmFragment
                    Fragment DeleteConfirmFragment = new DeleteConfirmFragment();
                    // set the variable iChosenIdToDelete in DeleteActivity equal to the id of the appointment the user
                    // wants to delete
                    DeleteActivity.iChosenIdToDelete = ChooseAppointmentFragment.aiAppointmentIds[iChosenIdToDelete - 1];
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fl_delete_activity, DeleteConfirmFragment);

                    fragmentTransaction.commit();*/
                }
            });
        }
}
