package com.old.leopards.restaurant.ui.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.databinding.FragmentAuthorizationStartBinding
import com.old.leopards.restaurant.databinding.FragmentCartBinding

class StartFragment : Fragment() {

    private var _binding: FragmentAuthorizationStartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthorizationStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        binding.btnLogin.setOnClickListener {
            navController.navigate(R.id.action_fragment_authorization_start_to_fragment_authorization_login)
        }
        binding.btnLogin.setOnClickListener {
            navController.navigate(R.id.action_fragment_authorization_start_to_fragment_authorization_registration)
        }
    }

}