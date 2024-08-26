package com.example.java3.view;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.view.WindowManager;
import android.graphics.Point;
import android.view.Window;
import com.example.java3.R;

public class FilterDialogFragment extends DialogFragment {

    public interface FilterListener {
        void onFilterApplied(String priceFilter, boolean isDiscounted);
    }

    private FilterListener filterListener;
    private RadioGroup radioGroupPrice;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnApply = view.findViewById(R.id.btnApply);
        radioGroupPrice = view.findViewById(R.id.radioGroupPrice);
        CheckBox cbDiscounted = view.findViewById(R.id.cbDiscounted);

        btnApply.setOnClickListener(v -> {
            int selectedRadioButtonId = radioGroupPrice.getCheckedRadioButtonId();
            String priceFilter = "";
            if (selectedRadioButtonId == R.id.radioButtonLowToHigh) {
                priceFilter = "lowToHigh";
            } else if (selectedRadioButtonId == R.id.radioButtonHighToLow) {
                priceFilter = "highToLow";
            }
            boolean isDiscounted = cbDiscounted.isChecked();
            if (filterListener != null) {
                filterListener.onFilterApplied(priceFilter, isDiscounted);
            }
            dismiss();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            filterListener = (FilterListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Target fragment must implement FilterListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null) {
            Window window = getDialog().getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                params.gravity = Gravity.CENTER;
                window.setAttributes(params);
                window.setLayout(getDialogWidth(), WindowManager.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    private int getDialogWidth() {
        Point size = new Point();
        WindowManager windowManager = requireActivity().getWindowManager();
        windowManager.getDefaultDisplay().getSize(size);
        return (int) (size.x * 0.9); // Chiều rộng bằng 90% chiều rộng màn hình
    }
}
