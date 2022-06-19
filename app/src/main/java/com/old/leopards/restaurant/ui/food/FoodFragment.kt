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
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.viewModels.LocalizedFoodViewModel
import com.old.leopards.restaurant.databinding.FragmentFoodBinding
import kotlinx.coroutines.flow.collect
import kotlin.math.abs

class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null

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
            columns = 4
        }

        binding.rvFoodList.layoutManager = GridLayoutManager(
            context,
            columns,
            RecyclerView.VERTICAL,
            false
        )

        val adapter = FoodAdapter()
        binding.rvFoodList.adapter = adapter

        binding.btnNextFoodPage.setOnClickListener {
            profileViewModel.nextPage()
        }

        binding.btnPrevFoodPage.setOnClickListener {
            profileViewModel.prevPage()
        }

        lifecycleScope.launchWhenStarted {
            profileViewModel.foodListState.collect {
                Log.d("asd", it.first.toString())
                var page = it.first
                if (page == 0) {
                    binding.btnPrevFoodPage.visibility = View.INVISIBLE
                } else {
                    binding.btnPrevFoodPage.visibility = View.VISIBLE
                }

                if (page < 0) {
                    binding.btnNextFoodPage.visibility = View.INVISIBLE
                    page = abs(page)
                } else {
                    binding.btnNextFoodPage.visibility = View.VISIBLE
                }
                val perPage = profileViewModel.foodPerPage
                val food = it.second.drop(page * perPage).take(perPage)
                Log.d("asd", food.size.toString())
                adapter.submitList(food)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
