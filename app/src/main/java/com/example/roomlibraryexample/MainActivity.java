package com.example.roomlibraryexample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.roomlibraryexample.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = binding.edtTitle.getText().toString();
                String amount = binding.edtAmount.getText().toString();

                if (!title.isEmpty() && !amount.isEmpty()) {

                    // Add data in database
                    databaseHelper.expenseDAO().addTx(
                            new ExpenseModel(title, amount)
                    );

                    binding.edtTitle.setText("");
                    binding.edtAmount.setText("");

                    // Fetch data from database
                    ArrayList<ExpenseModel> arrExpense = (ArrayList<ExpenseModel>) databaseHelper.expenseDAO().getAllExpense();
                    for (int i = 0; i < arrExpense.size(); i++) {
                        Log.d("Data", "Title : " + arrExpense.get(i).getTitle() + ", Amount : " + arrExpense.get(i).getAmount());

                    }
                }else{
                    Toast.makeText(MainActivity.this, "Please Field all details...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}