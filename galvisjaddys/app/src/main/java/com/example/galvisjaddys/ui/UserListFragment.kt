package com.example.galvisjaddys.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.galvisjaddys.R
import com.example.galvisjaddys.databinding.FragmentUserListBinding
import com.example.galvisjaddys.viewmodel.UserViewModel

class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.users.observe(viewLifecycleOwner) { userList ->

            binding.textViewUserCount.text = "Total usuarios: ${userList.size}"

            val usersText = userList.joinToString("\n") {
                "${it.name} - ${it.email}"
            }

            binding.textViewUserList.text = usersText
        }

        binding.buttonAddUser.setOnClickListener {

            val user = viewModel.users.value?.firstOrNull()

            user?.let {
                viewModel.selectUser(it)
                findNavController().navigate(R.id.action_list_to_detail)
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        binding.buttonAddUser.setOnClickListener {
            // Seleccionar el primer usuario y navegar con Safe Args
            viewModel.users.value?.firstOrNull()?.let { user ->
                viewModel.selectUser(user)

                // Usar Safe Args generado automáticamente
                val action = UserListFragmentDirections
                    .actionListToDetail(userId = user.id)
                findNavController().navigate(action)
            }
        }
    }

}
