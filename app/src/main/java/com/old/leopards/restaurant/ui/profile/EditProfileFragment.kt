package com.old.leopards.restaurant.ui.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.viewModels.UserViewModel
import com.old.leopards.restaurant.databinding.FragmentEditProfileBinding
import com.old.leopards.restaurant.ui.Global
import com.old.leopards.restaurant.ui.Global.Companion.showText

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? =
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
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        binding.editNameInput.setText(Global.currentUser.login)
        binding.editEmailInput.setText(Global.currentUser.email)
        binding.editPasswordInput.setText(Global.currentUser.password)
        binding.editPasswordAgainInput.setText(Global.currentUser.password)
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

                var newUser = Global.currentUser

                if (name.isNotBlank() && name != Global.currentUser.login) {
                    val user = _UserViewModel.getUserByName(name)
                    if (user == null) {
                        newUser.login = name
                    } else {
                        showText(getString(R.string.name_conflict))
                        return@setOnClickListener
                    }
                }

                if (password.isNotBlank()
                    && (password != Global.currentUser.password || replyPassword != Global.currentUser.password)) {
                    if (password == replyPassword) {
                        newUser.password = password
                    } else {
                        showText(getString(R.string.password_mismatch))
                        return@setOnClickListener
                    }
                }

                if (email.isNotBlank() && email != Global.currentUser.email) {
                    val user = _UserViewModel.getUserByEmail(email)
                    if (user == null) {
                        newUser.email = email
                    } else {
                        showText(getString(R.string.email_conflict))
                        return@setOnClickListener
                    }
                }

                // TODO photoLink

                _UserViewModel.updateUser(newUser)
                Global.currentUser = newUser
                showText(appContext, getString(R.string.success))
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

    fun showText(text: String) {
        showText(context, text)
    }
}
