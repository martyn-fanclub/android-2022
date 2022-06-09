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
import com.old.leopards.restaurant.databinding.FragmentProfileBinding
import com.old.leopards.restaurant.databinding.FragmentRegistrationBinding
import com.old.leopards.restaurant.ui.Global
import com.old.leopards.restaurant.ui.Global.Companion.showText
import kotlinx.coroutines.*


/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private var _binding_profile: FragmentProfileBinding? = null
    private lateinit var _UserViewModel: UserViewModel
    private var _context: Context? = null

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
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        _binding_profile = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _UserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnReg.setOnClickListener {
            activity?.findViewById<View>(R.id.nav_view)?.visibility = View.VISIBLE

            val name = binding.regInputName.text.toString()
            val password = binding.regInputPassword.text.toString()
            val replyPassword = binding.regInputPasswordAgain.text.toString()
            val email = binding.regInputEmail.text.toString()
            val photoLink = null // TODO photoLink

            if (isValidRegInput(name, password, replyPassword, email)) {
                val user = User(login=name, password=password, email=email, photoLink=photoLink)
                _UserViewModel.createUser(user)
                Global.userId = user.id
                _binding_profile?.username?.text = name
                _binding_profile?.email?.text = email
                // TODO _binding_profile?.avatar? = ?
                val bundle = bundleOf(Pair(name, email))
                findNavController().navigate(R.id.action_registration_fragment_to_navigation_food, bundle)
            }
        }
    }

    private fun isValidRegInput(
        name: String,
        password: String,
        replyPassword: String,
        email: String
    ): Boolean {
        val user = _UserViewModel.getUser(name)
        var isValidRegInput = false
        if (user == null) {
            if (name.isNotBlank()) {
                if (password.isNotBlank()) {
                    if (password == replyPassword) {
                        if (email.isNotBlank()) {
                            isValidRegInput = true
                        } else {
                            showText(getString(R.string.invalid_email))
                        }
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
            showText(getString(R.string.user_conflict))
        }
        return isValidRegInput
    }

    fun showText(text: String) {
        showText(this.context?.applicationContext, text)
    }
}
