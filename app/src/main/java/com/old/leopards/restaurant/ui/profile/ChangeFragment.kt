package com.old.leopards.restaurant.ui.profile

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentViewHolder
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.databinding.FragmentChangeProfileBinding

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangeFragment : Fragment() {
    private var _binding: com.old.leopards.restaurant.databinding.FragmentChangeProfileBinding? =
        null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        binding.apply {
            btnSaveProfile.setOnClickListener {
                navController.navigate(R.id.navigation_profile)
            }

            btnCancelProfile.setOnClickListener {
                navController.navigate(R.id.navigation_profile)
            }

            // TODO
            btnAddPhoto.setOnClickListener {

            }
        }
    }
}
