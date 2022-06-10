package com.old.leopards.restaurant.ui.authorization

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.data.Preferences
import com.old.leopards.restaurant.databinding.FragmentStartBinding
import com.old.leopards.restaurant.ui.Global

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {
    private var _binding: com.old.leopards.restaurant.databinding.FragmentStartBinding? =
        null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.findViewById<View>(R.id.nav_view)?.visibility = View.INVISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        if (Preferences(requireContext()).isNotFirstAuthorizedLaunch()) {
            navController.navigate(R.id.action_start_fragment_to_navigation_food)
        }

        binding.apply {
            btnLogin.setOnClickListener {
                navController.navigate(R.id.action_start_fragment_to_login_fragment)
            }

            btnReg.setOnClickListener {
                navController.navigate(R.id.action_start_fragment_to_registration_fragment)
            }
        }
    }
}
