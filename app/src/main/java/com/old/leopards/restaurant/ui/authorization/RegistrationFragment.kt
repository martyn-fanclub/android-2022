package com.old.leopards.restaurant.ui.authorization

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.entities.User
import com.old.leopards.restaurant.database.viewModels.UserViewModel
import com.old.leopards.restaurant.databinding.FragmentRegistrationBinding
import com.old.leopards.restaurant.ui.Global
import kotlinx.coroutines.*


/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
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
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
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
                findNavController().navigate(R.id.action_registration_fragment_to_navigation_food)
            } else {
                Toast.makeText(
                    this.context?.applicationContext,
                    getString(R.string.invalid_data),
                    Toast.LENGTH_LONG
                ).show()
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
        val isValidRegInput
            = user == null && password.isNotBlank() && password == replyPassword && email.isNotBlank()
        if (isValidRegInput) {
            Global.userId = user.id
        }
        return isValidRegInput
    }
}
