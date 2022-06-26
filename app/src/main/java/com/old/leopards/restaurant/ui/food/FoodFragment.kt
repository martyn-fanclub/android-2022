package com.old.leopards.restaurant.ui.food

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.viewModels.LocalizedFoodViewModel
import com.old.leopards.restaurant.databinding.FragmentFoodBinding
import kotlinx.coroutines.flow.collect
import kotlin.math.abs

class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null

    @Volatile
    private var loading = false

    @Volatile
    private var end = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val profileViewModel: FoodViewModel by lazy {
        val localizedFoodViewModel = ViewModelProvider(this).get(LocalizedFoodViewModel::class.java)
        val factory = FoodViewModelFactory(localizedFoodViewModel)
        ViewModelProvider(this, factory).get(FoodViewModel::class.java)
    }


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
        activity?.findViewById<View>(R.id.nav_view)?.visibility = View.VISIBLE
        val columns: Int
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            columns = 2
        } else {
            columns = 3
        }


        binding.rvFoodList.layoutManager = GridLayoutManager(
            context,
            columns,
            RecyclerView.VERTICAL,
            false
        )

        val adapter = FoodAdapter()
        binding.rvFoodList.adapter = adapter

        val layoutManager = binding.rvFoodList.layoutManager as GridLayoutManager
        binding.rvFoodList.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                Log.d("asd", newState.toString())
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE && !end) {
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    Log.d("asd", "$totalItemCount $lastVisibleItem")
                    binding.progressbar.visibility = VISIBLE
                    if (totalItemCount ==
                        (lastVisibleItem + 1) && !loading
                    ) {
                        Log.d("asd", "asddd")
                        loading = true
                        profileViewModel.nextPage()
                    }
                }
            }
        })


        lifecycleScope.launchWhenStarted {
            profileViewModel.foodListState.collect {
                Log.d("asd", "collect")
                var page = abs(it.first)
                val perPage = profileViewModel.foodPerPage
                val food = it.second.take(perPage * page)
                adapter.submitList(food)
                if (it.first < 0) {
                    end = true
                    binding.progressbar.visibility = INVISIBLE
                }
                loading = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
