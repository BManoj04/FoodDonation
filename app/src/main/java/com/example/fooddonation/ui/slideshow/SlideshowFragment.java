package com.example.fooddonation.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fooddonation.R;

public class SlideshowFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        String msg =
                "Welcome to Food Donation App, where we believe in the power of community and compassion.\n" +
                "\n" +
                "we are driven by the simple yet profound belief that no one should go hungry. Our mission is to bridge the gap between surplus food and those in need, ensuring that nutritious meals reach the tables of individuals and families facing food insecurity.\n" +
                "\n" +
                "Founded 2023, we have been tirelessly working towards this goal, guided by our commitment to making a positive impact in the lives of others. What started as a small initiative has now grown into a movement, thanks to the unwavering support of volunteers, donors, and partners who share our vision for a world where hunger is eradicated.\n" +
                "\n" +
                "We understand that hunger knows no bounds and affects individuals from all walks of life. That's why we operate with inclusivity and empathy at the forefront of everything we do. Whether you're a donor looking to contribute to our cause, a volunteer eager to lend a helping hand, or someone seeking assistance, you are an integral part of our community.\n" +
                "\n" +
                "Through our dedicated efforts, we've been able to rescue and redistribute thousands of pounds of food that would have otherwise gone to waste, turning it into nourishment for those who need it most. But our work doesn't stop there. We are continuously exploring innovative solutions and partnerships to expand our reach and make an even greater impact in the fight against hunger.\n" +
                "\n" +
                "Join us in our mission to create a world where hunger is a thing of the past. Together, we can make a difference, one meal at a time.\n" +
                "\n" +
                "Thank you for your support,\n" +
                "\n" +
                "Manoj B\n" +
                "bmanoj2364@gmail.com";
        TextView aboutus = root.findViewById(R.id.aboutUs);
        aboutus.setText(msg);
        return root;
    }


}