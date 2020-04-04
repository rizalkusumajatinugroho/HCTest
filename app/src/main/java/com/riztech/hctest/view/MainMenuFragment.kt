package com.riztech.hctest.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.riztech.hctest.R
import com.riztech.hctest.viewModel.MainMenuViewModel
import com.riztech.hctest.widget.DataAdapter
import kotlinx.android.synthetic.main.fragment_main_menu.*

/**
 * A simple [Fragment] subclass.
 */
class MainMenuFragment : Fragment() {
    private lateinit var dataAdapter: DataAdapter
    private lateinit var viewModel : MainMenuViewModel

    private var listDataObserver = Observer<ArrayList<Any>> {listDataObserver->
        listDataObserver?.let {
            dataAdapter.updateListData(listDataObserver)
        }
    }

    private var loadingObserver = Observer<Boolean> {isLoading ->
        if (isLoading)
            loadingVisit.visibility = View.VISIBLE
        else
            loadingVisit.visibility = View.GONE
    }

    private var errorObserver = Observer<Boolean> {isError ->
        if (isError)
            Toast.makeText(context, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataAdapter = DataAdapter(context, arrayListOf())
        viewModel = ViewModelProvider(this@MainMenuFragment).get(MainMenuViewModel::class.java)

        viewModel.listData.observe(this.viewLifecycleOwner, listDataObserver)
        viewModel.loading.observe(this.viewLifecycleOwner, loadingObserver)
        viewModel.loadError.observe(this.viewLifecycleOwner, errorObserver)
        viewModel.refresh()

        rvMainMenu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }

    }


}
