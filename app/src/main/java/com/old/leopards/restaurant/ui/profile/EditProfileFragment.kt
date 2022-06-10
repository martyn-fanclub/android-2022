package com.old.leopards.restaurant.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.data.Preferences
import com.old.leopards.restaurant.database.entities.User
import com.old.leopards.restaurant.database.viewModels.UserViewModel
import com.old.leopards.restaurant.databinding.FragmentEditProfileBinding
import com.old.leopards.restaurant.ui.Global
import com.old.leopards.restaurant.ui.Global.Companion.REQUEST_CODE
import com.old.leopards.restaurant.ui.Global.Companion.showText
import java.io.File
import java.io.FileOutputStream


/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? =
        null
    private lateinit var _UserViewModel: UserViewModel
    private var pref: Preferences? = null
    private var currentUser: User? = null

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

        pref = Preferences(requireContext())
        currentUser = pref!!.getCurrentUser()
        binding.editNameInput.setText(currentUser!!.login)
        binding.editEmailInput.setText(currentUser!!.email)
        binding.editPasswordInput.setText(currentUser!!.password)
        binding.editPasswordAgainInput.setText(currentUser!!.password)
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

                val newUser = currentUser

                if (name.isNotBlank() && name != currentUser!!.login) {
                    val user = _UserViewModel.getUserByName(name)
                    if (user == null) {
                        newUser!!.login = name
                    } else {
                        showText(getString(R.string.name_conflict))
                        return@setOnClickListener
                    }
                }

                if (password.isNotBlank()
                    && (password != currentUser!!.password || replyPassword != currentUser!!.password)) {
                    if (password == replyPassword) {
                        newUser!!.password = password
                    } else {
                        showText(getString(R.string.password_mismatch))
                        return@setOnClickListener
                    }
                }

                if (email.isNotBlank() && email != currentUser!!.email  && Global.emailPattern.matches(email)) {
                    val user = _UserViewModel.getUserByEmail(email)
                    if (user == null) {
                        newUser!!.email = email
                    } else {
                        showText(getString(R.string.email_conflict))
                        return@setOnClickListener
                    }
                }

                // TODO photoLink

                _UserViewModel.updateUser(newUser!!)
                pref!!.setCurrentUser(newUser)
                showText(appContext, getString(R.string.success))
            }

            // Filled fields will be deleted by themselves
            btnCancelProfile.setOnClickListener {
                navController.navigate(R.id.navigation_profile)
            }

            // TODO
            btnAddPhoto.setOnClickListener {
                openGalleryForImage()
            }
        }
    }

    fun showText(text: String) {
        showText(context, text)
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            currentUser!!.photoLink = data?.data.toString()
            _UserViewModel.updateUser(currentUser!!)
        }
    }
}
