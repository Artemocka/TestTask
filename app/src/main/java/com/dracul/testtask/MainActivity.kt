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
import com.dracul.testtask.chips.ChipsAdapter
import com.dracul.testtask.data.FilterChip
import com.dracul.testtask.databinding.ActivityMainBinding
import com.dracul.testtask.recycler.MealAdapter
import com.dracul.testtask.repository.HomeRepository
import com.dracul.testtask.viewmodel.HomeViewModel
import com.dracul.testtask.viewmodel.HomeViewModelFactory
import com.dracul.testtask.viewmodel.poop
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ChipsAdapter.OnChipListner {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel
    private var adapter = MealAdapter()
    private var chipsAdapter = ChipsAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        poop("on create")
        binding = ActivityMainBinding.inflate(layoutInflater)
        val retrofitService = RetrofitService.getInstance()
        val homeRepository = HomeRepository(retrofitService)
        viewModel = ViewModelProvider(this, HomeViewModelFactory(homeRepository)).get(HomeViewModel::class.java)

        lifecycleScope.launch {
            viewModel.meals.collect{
                adapter.submitList(it)
            }
        }
        lifecycleScope.launch {
            viewModel.filterChipList.collect{
                Log.e("", it.size.toString())
                chipsAdapter.submitList(it)
                it.forEach{
                    if (it.isChecked)
                        Log.e("", it.category.strCategory)

                }
            }
        }

        lifecycleScope.launch {
            viewModel.errorMessage.collect {
                binding.tvInternetProblems.text = it
            }}
        binding.rvList.adapter = adapter
        binding.chipRv.adapter = chipsAdapter




        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onChipChecked(filterChip: FilterChip) {
        viewModel.setSelectedFilterChip(filterChip)
    }




}