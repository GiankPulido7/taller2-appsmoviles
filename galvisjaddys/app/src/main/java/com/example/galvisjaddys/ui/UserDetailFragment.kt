package com.example.galvisjaddys.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.galvisjaddys.databinding.FragmentUserDetailBinding
import com.example.galvisjaddys.viewmodel.UserViewModel
import androidx.navigation.fragment.navArgs

class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by activityViewModels()

    // Recibir argumentos con Safe Args
    private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔥 Cargar usuario con el ID recibido
        loadUserById(args.userId)

        // 👇 ESTO DEBE IR DENTRO DEL MÉTODO
        viewModel.selectedUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.textViewName.text = it.name
                binding.textViewEmail.text = it.email
                binding.textViewAge.text = "Edad: ${it.age}"
                binding.textViewId.text = "ID: ${it.id}"
            }
        }

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadUserById(userId: Int) {
        viewModel.users.value?.find { it.id == userId }?.let { user ->
            viewModel.selectUser(user)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}