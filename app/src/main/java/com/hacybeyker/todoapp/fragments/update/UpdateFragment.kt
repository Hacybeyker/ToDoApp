package com.hacybeyker.todoapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hacybeyker.todoapp.R
import com.hacybeyker.todoapp.data.models.ToDoData
import com.hacybeyker.todoapp.data.viewmodel.SharedViewModel
import com.hacybeyker.todoapp.data.viewmodel.ToDoViewModel
import com.hacybeyker.todoapp.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.args = args
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.updatePrioritySpinner.onItemSelectedListener = mSharedViewModel.listener
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateDataToDB()
            R.id.menu_delete -> confirmItemDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateDataToDB() {
        val mTitle = binding.updateTitleEdit.text.toString()
        val mPriority = binding.updatePrioritySpinner.selectedItem.toString()
        val mDescription = binding.updateDescriptionEdit.text.toString()
        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {
            val updateData =
                ToDoData(
                    args.currentItem.id,
                    mTitle,
                    mSharedViewModel.parsePriority(mPriority),
                    mDescription
                )
            mToDoViewModel.updateData(updateData)
            Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun confirmItemDelete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            deleteDataToDB()
        }
        builder.setNegativeButton("NO") { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.title}'")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}' ?")
        builder.create().show()
    }

    private fun deleteDataToDB() {
        mToDoViewModel.deleteData(args.currentItem)
        Toast.makeText(
            requireContext(),
            "Successfully Removed: ${args.currentItem.title}",
            Toast.LENGTH_SHORT
        ).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }
}