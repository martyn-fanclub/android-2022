package com.old.leopards.restaurant.ui.cart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.databinding.FragmentCartBinding
import kotlinx.coroutines.flow.collect
import java.math.BigDecimal

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var _context: Context? = null

    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _context = this.context?.applicationContext
    }

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

        val adapter = FoodAdapter()

        adapter.setOnItemClickListener { rubles ->
            binding.price.text = getString(R.string.total_price_template, rubles)
            lifecycleScope.launchWhenStarted {
                cartViewModel.currencyState.collect {
                    when (it) {
                        is CartViewModel.CurrencyUiState.Error -> {
                            binding.dollarPrice.text =
                                getString(R.string.total_dollars_price_template, BigDecimal.ZERO)
                        }
                        is CartViewModel.CurrencyUiState.Success -> {
                            val dollars = rubles.multiply(BigDecimal(it.currency.rates["USD"]!!))
                                .setScale(2, BigDecimal.ROUND_HALF_EVEN)
                            binding.dollarPrice.text =
                                getString(R.string.total_dollars_price_template, dollars)
                        }
                    }
                }
            }
        }

        binding.cartRv.layoutManager = LinearLayoutManager(context)
        binding.cartRv.adapter = adapter

        lifecycleScope.launchWhenStarted {
            cartViewModel.loadFromBD()
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
        lifecycleScope.launchWhenStarted {
            cartViewModel.fetchCurrency()
            cartViewModel.currencyState.collect {
                when (it) {
                    is CartViewModel.CurrencyUiState.Error -> {
                        binding.dollarPrice.text =
                            getString(R.string.total_dollars_price_template, BigDecimal.ZERO)
                    }
                    is CartViewModel.CurrencyUiState.Success -> {
                        val dollars =
                            adapter.getTotal().multiply(it.currency.rates["USD"]?.let { it1 ->
                                BigDecimal(
                                    it1
                                )
                            }).setScale(2, BigDecimal.ROUND_HALF_EVEN)
                        binding.dollarPrice.text =
                            getString(R.string.total_dollars_price_template, dollars)
                    }
                }
            }
        }


        binding.removeAll.setOnClickListener {
            adapter.clearCart()
        }

        binding.pay.setOnClickListener {
            val price = adapter.pay()
            Toast.makeText(
                context,
                "Куплено на сумму: $price",
                Toast.LENGTH_LONG
            ).show()
            binding.price.text = getString(R.string.total_price_template, adapter.getTotal())
            adapter.listener!!.onItemClick(adapter.getTotal())
        }

        binding.price.text = getString(R.string.total_price_template, adapter.getTotal())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
