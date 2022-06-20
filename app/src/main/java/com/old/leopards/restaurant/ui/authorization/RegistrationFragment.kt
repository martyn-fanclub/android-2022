package com.old.leopards.restaurant.ui.authorization

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.entities.User
import com.old.leopards.restaurant.database.viewModels.UserViewModel
import com.old.leopards.restaurant.databinding.FragmentRegistrationBinding
import com.old.leopards.restaurant.ui.Global
import com.old.leopards.restaurant.ui.Global.Companion.showText
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first


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
            regInputPassword.addTextChangedListener(PasswordInputValidator())
            regInputPasswordAgain.addTextChangedListener(PasswordInputValidator())

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
                    val id = _UserViewModel.createUser(user)
                    user.id = id
                    Global.currentUser = user
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
        if (_UserViewModel.getUserByName(name) != null) {
            showText(getString(R.string.name_conflict))
            return false
        }

        if (name.isBlank()) {
            showText(getString(R.string.invalid_name))
            return false
        }

        if (password.isBlank()) {
            showText(getString(R.string.invalid_password))
            return false
        }

        if (password.length < 4) {
            showText(getString(R.string.very_short_password))
            return false
        }

        if (password != replyPassword) {
            showText(getString(R.string.password_mismatch))
            return false
        }

        if (email.isBlank() || !Global.emailPattern.matches(email)) {
            showText(getString(R.string.invalid_email))
            return false
        }

        if (_UserViewModel.getUserByEmail(email) != null) {
            showText(getString(R.string.email_conflict))
            return false
        }

        return true
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

    private inner class PasswordInputValidator : TextWatcher {

        override fun afterTextChanged(s: Editable) {
        }

        override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int,
            after: Int
        ) {}

        override fun onTextChanged(
            s: CharSequence, start: Int, before: Int,
            count: Int
        ) {
            if (binding.regInputPassword.text.toString() != binding.regInputPasswordAgain.text.toString()
                || binding.regInputPassword.text.length < 4) {
                binding.regInputPassword.setBackgroundResource(R.drawable.btn_default_warning)
                binding.regInputPasswordAgain.setBackgroundResource(R.drawable.btn_default_warning)
            } else {
                binding.regInputPassword.setBackgroundResource(R.drawable.btn_default)
                binding.regInputPasswordAgain.setBackgroundResource(R.drawable.btn_default)
            }
            binding.regInputPasswordLowPanel.text = getString(R.string.cur_password_length, binding.regInputPassword.text.length)
            binding.regInputPasswordAgainLowPanel.text = getString(R.string.cur_password_length, binding.regInputPasswordAgain.text.length)
        }
    }
}
