package com.old.leopards.restaurant.ui.authorization

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.viewModels.UserViewModel
import com.old.leopards.restaurant.databinding.FragmentLoginBinding
import com.old.leopards.restaurant.ui.Global
import com.old.leopards.restaurant.ui.Global.Companion.showText
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

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
                Global.currentUser = _UserViewModel.getUserByName(name)!!
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigationFood())
                activity?.findViewById<View>(R.id.nav_view)?.visibility = View.VISIBLE
            }
        }
    }

    private fun isValidLoginInput(name: String, password: String): Boolean {
        val user = _UserViewModel.getUserByName(name)
        var isValidLoginInput = false
        if (user != null) {
            if (name.isNotBlank()) {
                if (user.password.isNotBlank()) {
                    if (user.password == password) {
                        isValidLoginInput = true
                    } else {
                        showText(getString(R.string.invalid_password))
                    }
                } else {
                    showText(getString(R.string.invalid_password))
                }
            } else {
                showText(getString(R.string.invalid_name))
            }
        } else {
            showText(getString(R.string.no_such_user))
        }
        return isValidLoginInput
    }

    fun showText(text: String) {
        showText(context, text)
    }
}
