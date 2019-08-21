package huuloc.uit.edu.truyenqq.fragments.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterHorizontal
import huuloc.uit.edu.truyenqq.network.ApiManager
import kotlinx.android.synthetic.main.fragment_search.*

class FragmentSearch : Fragment() {

    val adapterHorizontal: AdapterHorizontal by lazy {
        AdapterHorizontal(activity!!, mutableListOf())
    }

    private val apiManager: ApiManager by lazy { ApiManager() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    val viewModel: ViewModelSearch by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelSearch::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcvHistory.run {
            adapter = adapterHorizontal
            layoutManager = LinearLayoutManager(activity!!)
        }
        viewModel.data.observe(this@FragmentSearch, Observer {
            if (it != null)
                adapterHorizontal.updateData(it.list)
        })
        svBook.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("CheckResult")
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.loadQuery(query)
                return false
            }

            @SuppressLint("CheckResult")
            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty())
                    return false
                viewModel.loadQuery(query)
                return false
            }
        })
    }
}