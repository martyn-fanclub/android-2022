package com.old.leopards.restaurant.ui.authorization

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.viewModels.UserViewModel
import com.old.leopards.restaurant.databinding.FragmentLoginBinding
import com.old.leopards.restaurant.ui.Global
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private lateinit var _UserViewModel: UserViewModel

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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _UserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnVerifyLogin.setOnClickListener {
            val name = binding.loginInputName.text.toString()
            val password = binding.loginInputPassword.text.toString()

            if (isValidLoginInput(name, password)) {
                findNavController().navigate(R.id.action_login_fragment_to_navigation_food)
            } else {
                Toast.makeText(
                    this.context?.applicationContext,
                    getString(R.string.invalid_data),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun isValidLoginInput(name: String, password: String): Boolean {
        val user = _UserViewModel.getUser(name)
        val isValidLoginInput = user != null && user.password == password
        if (isValidLoginInput) {
            Global.userId = user.id
        }
        return isValidLoginInput
    }
}
