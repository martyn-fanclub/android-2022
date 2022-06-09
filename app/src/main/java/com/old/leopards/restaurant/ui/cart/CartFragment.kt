package com.old.leopards.restaurant.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.old.leopards.restaurant.databinding.FragmentCartBinding
import com.old.leopards.restaurant.models.Food
import kotlinx.coroutines.flow.collect

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FoodAdapter { item -> onClick(item) }
        binding.cartRv.layoutManager = LinearLayoutManager(context)
        binding.cartRv.adapter = adapter

        lifecycleScope.launchWhenStarted {
            cartViewModel.cartUiState.collect {
                when (it) {
                    is CartViewModel.CartUiState.Empty -> {
                        adapter.currentList.clear()
                    }
                    is CartViewModel.CartUiState.HasFood -> {
                        adapter.submitList(it.food)
                    }
                }
            }
        }


        //TODO
        binding.apply {
        }
    }

    fun onClick(foodEntry: Pair<Food, Int>) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
