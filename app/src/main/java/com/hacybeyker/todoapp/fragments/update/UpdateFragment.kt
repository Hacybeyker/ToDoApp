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
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.updateTitleEdit.setText(args.currentItem.title)
        view.updatePrioritySpinner.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        view.updatePrioritySpinner.onItemSelectedListener = mSharedViewModel.listener
        view.updateDescriptionEdit.setText(args.currentItem.description)
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
        val mTitle = updateTitleEdit.text.toString()
        val mPriority = updatePrioritySpinner.selectedItem.toString()
        val mDescription = updateDescriptionEdit.text.toString()
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