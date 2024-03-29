package com.dracul.testtask

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dracul.testtask.api.RetrofitService
import com.dracul.testtask.databinding.ActivityMainBinding
import com.dracul.testtask.recycler.MealAdapter
import com.dracul.testtask.repository.HomeRepository
import com.dracul.testtask.viewmodel.HomeViewModel
import com.dracul.testtask.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: HomeViewModel
    private var adapter = MealAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val retrofitService = RetrofitService.getInstance()
        val homeRepository = HomeRepository(retrofitService)
        viewModel = ViewModelProvider(this, HomeViewModelFactory(homeRepository)).get(HomeViewModel::class.java)

        viewModel.getMeals()
        viewModel.getCategories()
        lifecycleScope.launch {
            viewModel.mealList.collect{
                adapter.submitList(it)
            }
        }

        binding.rvList.adapter = adapter

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


}