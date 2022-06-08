package com.old.leopards.restaurant.ui.profile

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.entities.User
import com.old.leopards.restaurant.database.viewModels.UserViewModel
import com.old.leopards.restaurant.databinding.FragmentChangeProfileBinding
import com.old.leopards.restaurant.ui.Global

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangeFragment : Fragment() {
    private var _binding: FragmentChangeProfileBinding? =
        null
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
        _binding = FragmentChangeProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _UserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val navController = findNavController()
        val appContext = this.context?.applicationContext

        binding.apply {
            btnSaveProfile.setOnClickListener {
                val name = binding.editNameInput.text.toString()
                val password = binding.editPasswordInput.text.toString()
                val replyPassword = binding.editPasswordAgainInput.text.toString()
                val email = binding.editEmailInput.text.toString()
                val photoLink = null // TODO photoLink

                if (isValidEditProfileInput(name, password, replyPassword, email)) {
                    val user = User(Global.userId, name, password, email,photoLink)
                    _UserViewModel.updateUser(user)
                    Toast.makeText(
                        appContext,
                        getString(R.string.success),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        appContext,
                        getString(R.string.invalid_data),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            // Filled fields will be deleted by themselves
            btnCancelProfile.setOnClickListener {
                navController.navigate(R.id.navigation_profile)
            }

            // TODO
            btnAddPhoto.setOnClickListener {

            }
        }
    }

    private fun isValidEditProfileInput(
        name: String,
        password: String,
        replyPassword: String,
        email: String
    ): Boolean {
        return name.isNotBlank() && password.isNotBlank() && password == replyPassword && email.isNotBlank()
    }
}
