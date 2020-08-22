package com.hacybeyker.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hacybeyker.todoapp.R
import com.hacybeyker.todoapp.data.viewmodel.SharedViewModel
import com.hacybeyker.todoapp.data.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            mSharedViewModel.checkIfDatabaseEmpty(it)
            adapter.setData(it)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            view?.noDataImageView?.visibility = View.VISIBLE
            view?.noDataTextView?.visibility = View.VISIBLE
        } else {
            view?.noDataImageView?.visibility = View.GONE
            view?.noDataTextView?.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmAllDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmAllDelete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            deleteAllDataToDB()
        }
        builder.setNegativeButton("NO") { _, _ -> }
        builder.setTitle("Delete everything")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }

    private fun deleteAllDataToDB() {
        mToDoViewModel.deleteAllData()
        Toast.makeText(requireContext(), "Successfully Everything!", Toast.LENGTH_SHORT).show()
    }
}