package com.old.leopards.restaurant.ui.authorization

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.data.Preferences
import com.old.leopards.restaurant.database.entities.User
import com.old.leopards.restaurant.database.viewModels.UserViewModel
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
    private lateinit var _UserViewModel: UserViewModel
    private var _context: Context? = null
    private var _avatarUri: String? = null

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _UserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.apply {
            regAddPhotoBtn.setOnClickListener {
                openGalleryForImage()
            }

            btnReg.setOnClickListener {
                val name = binding.regInputName.text.toString()
                val password = binding.regInputPassword.text.toString()
                val replyPassword = binding.regInputPasswordAgain.text.toString()
                val email = binding.regInputEmail.text.toString()
                val photoLink = _avatarUri

                if (isValidRegInput(name, password, replyPassword, email)) {
                    val user = User(login=name, password=password, email=email, photoLink=photoLink)
                    _UserViewModel.createUser(user)
                    Preferences(requireContext()).setCurrentUser(user)

                    findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToNavigationFood())
                    activity?.findViewById<View>(R.id.nav_view)?.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun isValidRegInput(
        name: String,
        password: String,
        replyPassword: String,
        email: String
    ): Boolean {
        val user = _UserViewModel.getUserByName(name)
        var isValidRegInput = false
        if (user == null) {
            if (name.isNotBlank()) {
                if (password.isNotBlank()) {
                    if (password == replyPassword) {
                        if (email.isNotBlank() && Global.emailPattern.matches(email)) {
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
            showText(getString(R.string.name_conflict))
        }
        return isValidRegInput
    }

    fun showText(text: String) {
        showText(context, text)
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, Global.REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Global.REQUEST_CODE) {
            _avatarUri = data?.data.toString()
        }
    }
}
