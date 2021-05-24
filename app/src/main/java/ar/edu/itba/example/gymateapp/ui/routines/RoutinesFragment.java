//package ar.edu.itba.example.gymateapp.ui.routines;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//
//import ar.edu.itba.example.gymateapp.databinding.FragmentRoutinesBinding;
//
//public class RoutinesFragment extends Fragment {
//
//    private RoutinesViewModel routinesViewModel;
//    private FragmentRoutinesBinding binding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        routinesViewModel =
//                new ViewModelProvider(this).get(RoutinesViewModel.class);
//
//        binding = FragmentRoutinesBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textRoutines;
//        routinesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}