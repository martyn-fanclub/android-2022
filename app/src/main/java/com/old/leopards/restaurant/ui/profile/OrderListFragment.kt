package com.old.leopards.restaurant.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.viewModels.OrderViewModel
import com.old.leopards.restaurant.databinding.FragmentOrderListBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


/**
 * A simple [Fragment] subclass.
 * Use the [OrderListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderListFragment : Fragment() {
    private var _binding: FragmentOrderListBinding? = null
    private var _context: Context? = null

    private val orderViewModel: OrderViewModel by viewModels()

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
        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = OrderAdapter()


        binding.apply {
            btnReturn.setOnClickListener {
                findNavController().navigate(R.id.action_order_list_fragment_to_navigation_profile)
            }
            orderRv.layoutManager = LinearLayoutManager(context)
            orderRv.adapter = adapter
        }

        lifecycleScope.launchWhenStarted {
            orderViewModel.getAllOrdersByUserId.collect {
                adapter.submitList(it)
            }
        }


    }
}
