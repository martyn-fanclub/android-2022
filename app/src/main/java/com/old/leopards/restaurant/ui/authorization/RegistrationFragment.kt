package com.old.leopards.restaurant.ui.authorization

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentViewHolder
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.databinding.FragmentAuthorizationLoginBinding
import com.old.leopards.restaurant.databinding.FragmentAuthorizationRegistrationBinding
import com.old.leopards.restaurant.databinding.FragmentAuthorizationStartBinding


/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFragment : Fragment() {
    private var _binding: FragmentAuthorizationRegistrationBinding? = null

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
        _binding = FragmentAuthorizationRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        binding.btnReg.setOnClickListener {
            activity?.findViewById<View>(R.id.nav_view)?.visibility = View.VISIBLE
            navController.navigate(R.id.action__registration_fragment__to__navigation_food)
        }
    }
}
