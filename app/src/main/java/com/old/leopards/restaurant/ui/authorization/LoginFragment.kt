package com.old.leopards.restaurant.ui.authorization

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.entities.User
import com.old.leopards.restaurant.database.viewModels.UserViewModel
import com.old.leopards.restaurant.databinding.FragmentLoginBinding
import com.old.leopards.restaurant.databinding.FragmentProfileBinding
import com.old.leopards.restaurant.ui.Global
import com.old.leopards.restaurant.ui.Global.Companion.showText
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.jvm.internal.FunctionReference
import kotlin.reflect.KFunction

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private var _binding_profile: FragmentProfileBinding? = null
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
        _binding_profile = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _UserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnVerifyLogin.setOnClickListener {
            val name = binding.loginInputName.text.toString()
            val password = binding.loginInputPassword.text.toString()

            if (isValidLoginInput(name, password)) {
                val email = _UserViewModel.getUser(name)?.email
                val bundle = bundleOf(Pair(name, email))
                findNavController().navigate(R.id.action_login_fragment_to_navigation_food, bundle)
            }
        }
    }

    private fun isValidLoginInput(name: String, password: String): Boolean {
        val user = _UserViewModel.getUser(name)
        var isValidLoginInput = false
        if (user != null) {
            if (name.isNotBlank()) {
                if (user.password.isNotBlank()) {
                    if (user.password == password) {
                        Global.userId = user.id
                        isValidLoginInput = true
                    } else {
                        showText(getString(R.string.password_mismatch))
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
        showText(this.context?.applicationContext, text)
    }
}
