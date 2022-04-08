package com.old.leopards.restaurant.ui.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.old.leopards.restaurant.databinding.FragmentFoodBinding
import kotlinx.coroutines.flow.collect
import java.util.*

class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val profileViewModel: FoodViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//
//        val adapter = FoodAdapter(Collections.emptyList())
//        binding.cartRv.layoutManager = LinearLayoutManager(context)
//        binding.cartRv.adapter = adapter

        binding.rvFoodList.layoutManager = GridLayoutManager(
            context,
            2,
            RecyclerView.VERTICAL,
            false
        )

        val adapter = FoodAdapter(Collections.emptyList())
        binding.rvFoodList.adapter = adapter

        lifecycleScope.launchWhenStarted {
            profileViewModel.foodListState.collect {
                adapter.foodList = it
                // TODO
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = FoodFragment()
    }
}