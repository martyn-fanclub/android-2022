package com.old.leopards.restaurant.ui.cart

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.databinding.FragmentCartBinding
import com.old.leopards.restaurant.ui.Global
import com.old.leopards.restaurant.ui.Global.Companion.showText
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
        binding.address.setText(Global.userAddress)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FoodAdapter()

        //binding.address.addTextChangedListener(AddressInputValidator())

        adapter.setOnItemClickListener { rubles ->
            binding.price.text = getString(R.string.total_price_template, rubles)
            lifecycleScope.launchWhenStarted {
                cartViewModel.currencyState.collect {
                    when (it) {
                        is CartViewModel.CurrencyUiState.Error -> {
                            binding.dollarPrice.text =
                                getString(
                                    R.string.total_dollars_price_template,
                                    BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN)
                                )
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
                            getString(
                                R.string.total_dollars_price_template,
                                BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN)
                            )
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

        binding.apply {
            removeAll.setOnClickListener {
                adapter.clearCart()
                binding.price.text = getString(R.string.total_price_template, adapter.getTotal())
                adapter.listener!!.onItemClick(adapter.getTotal())
            }

            val imm =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            editAddress.setOnClickListener {
                binding.address.isEnabled = !binding.address.isEnabled
                if (binding.address.isEnabled) {
                    address.addTextChangedListener(AddressInputValidator())
                    binding.address.requestFocus()
                    imm.showSoftInput(binding.address, InputMethodManager.SHOW_FORCED)
                } else {
                    binding.address.clearFocus()
                    imm.hideSoftInputFromWindow(
                        binding.address.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }
            }


            pay.setOnClickListener {
                if (!adapter.isEmpty()) {
                    val price = adapter.pay()
                    showText(context, getString(R.string.on_buy_toast_template, price))
                    binding.price.text =
                        getString(R.string.total_price_template, adapter.getTotal())
                    adapter.listener!!.onItemClick(adapter.getTotal())
                } else {
                    showText(context, getString(R.string.on_empty_cart_toast))
                }
            }

            pay.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_cart_to_payment_fragment)
            }
            
            price.text = getString(R.string.total_price_template, adapter.getTotal())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class AddressInputValidator : TextWatcher {

        override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int,
            after: Int
        ) {
            binding.address.setSelection(Global.userAddress.length)
            binding.address.isCursorVisible = true
        }

        override fun afterTextChanged(s: Editable) {
            Global.userAddress = s.toString()
        }

        override fun onTextChanged(
            s: CharSequence, start: Int, before: Int,
            count: Int
        ) {}
    }
}
