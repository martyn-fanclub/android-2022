package com.old.leopards.restaurant.ui.cart

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.entities.Order
import com.old.leopards.restaurant.database.entities.User
import com.old.leopards.restaurant.database.viewModels.OrderViewModel
import com.old.leopards.restaurant.database.viewModels.UserViewModel
import com.old.leopards.restaurant.databinding.FragmentPaymentBinding
import com.old.leopards.restaurant.ui.Global
import com.old.leopards.restaurant.ui.Global.Companion.showText
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * A simple [Fragment] subclass.
 * Use the [PaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentBinding? = null
    private lateinit var _OrderViewModel: OrderViewModel
    private var _context: Context? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _context = this.context?.applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _OrderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        binding.apply {
            btnPay.setOnClickListener {
                val adapter = FoodAdapter()
                val order = Order(price = adapter.getTotal().intValueExact(),
                orderDate = SimpleDateFormat("dd.MM.yyyy HH:mm").toString(),
                userId = Global.currentUser.id)
                _OrderViewModel.addOrder(order)
                adapter.pay()
                showText(context, getString(R.string.success))
                findNavController().navigate(R.id.action_payment_fragment_to_navigation_cart)
            }
            btnCancelPayment.setOnClickListener {
                findNavController().navigate(R.id.action_payment_fragment_to_navigation_cart)
            }
        }
    }
}
