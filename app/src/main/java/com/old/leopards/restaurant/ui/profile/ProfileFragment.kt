package com.old.leopards.restaurant.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.data.Preferences
import com.old.leopards.restaurant.database.entities.User
import com.old.leopards.restaurant.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private var pref: Preferences? = null
    private var currentUser: User? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        pref = Preferences(requireContext())
        currentUser = pref!!.getCurrentUser()
        binding.username.text = currentUser!!.login
        binding.email.text = currentUser!!.email
        val uri = currentUser!!.photoLink?.toUri()
        binding.avatar.setImageURI(uri)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        binding.apply {
            btnEditProfile.setOnClickListener {
                navController.navigate(R.id.action_navigation_profile_to_edit_profile_fragment)
            }

            btnExit.setOnClickListener {
                pref?.resetUser()
                navController.navigate(R.id.action_navigation_profile_to_start_fragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
