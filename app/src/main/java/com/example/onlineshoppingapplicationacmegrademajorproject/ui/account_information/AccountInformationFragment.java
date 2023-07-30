package com.example.onlineshoppingapplicationacmegrademajorproject.ui.account_information;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshoppingapplicationacmegrademajorproject.databinding.FragmentAccountInformationBinding;

public class AccountInformationFragment extends Fragment {

    private FragmentAccountInformationBinding binding;
    private TextView userNameTextView, userEmailTextView;
    private TextView textNameCard, textEmailCard;
    private SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccountInformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userNameTextView = binding.userNameDuplicate;
        userEmailTextView = binding.userEmailDuplicate;
        
        textNameCard = binding.userNameCard;
        textEmailCard = binding.userEmailCard;

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        String userName = sharedPreferences.getString("userName", "");
        String userEmail = sharedPreferences.getString("userEmail", "");

        userNameTextView.setText(userName);
        userEmailTextView.setText(userEmail);

        textNameCard.setText(userName);
        textEmailCard.setText(userEmail);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}