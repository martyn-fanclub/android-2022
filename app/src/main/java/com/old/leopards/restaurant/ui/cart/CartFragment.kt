package com.old.leopards.restaurant.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.old.leopards.restaurant.api.CurrencyService
import com.old.leopards.restaurant.databinding.FragmentCartBinding
import kotlinx.coroutines.flow.collect
import java.util.*

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val cartViewModel: CartViewModel by viewModels()

    private val currencyService = CurrencyService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        cartViewModel.fetchCurrency(currencyService.api)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FoodAdapter(Collections.emptyList())
        binding.cartRv.layoutManager = LinearLayoutManager(context)
        binding.cartRv.adapter = adapter

        lifecycleScope.launchWhenStarted {
            cartViewModel.cartUiState.collect {
                when (it) {
                    is CartViewModel.CartUiState.Empty -> {
                        adapter.food.clear()
                    }
                    is CartViewModel.CartUiState.HasFood -> {
                        adapter.food = it.food
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }


        //TODO
        binding.apply {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
