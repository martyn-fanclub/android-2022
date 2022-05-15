package com.old.leopards.restaurant.ui.food

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.databinding.FragmentFoodBinding
import com.old.leopards.restaurant.models.Food
import kotlinx.coroutines.flow.collect

class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val profileViewModel: FoodViewModel by viewModels<FoodViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.findViewById<View>(R.id.nav_view)?.visibility = View.VISIBLE
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFoodList.layoutManager = GridLayoutManager(
            context,
            2,
            RecyclerView.VERTICAL,
            false
        )

        val adapter = FoodAdapter { foodItem -> adapterOnClickDescription(foodItem) }
        binding.rvFoodList.adapter = adapter

        lifecycleScope.launchWhenStarted {
            profileViewModel.foodListState.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun adapterOnClickDescription(foodItem: Food) {
        Log.d("DEBUG", "Layout press")
        /*
        TODO open description.

           Example with activity:
            val intent = Intent(this, FoodDetailActivity()::class.java)
            intent.putExtra("FOOD_ID", food.id)
            startActivity(intent)
         */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}