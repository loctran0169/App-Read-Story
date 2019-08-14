package huuloc.uit.edu.truyenqq.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterHorizontal
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*

class FragmentSearch : Fragment() {

    val adapterHorizontal: AdapterHorizontal by lazy {
        AdapterHorizontal(activity!!, mutableListOf())
    }
    val handle: Handler by lazy {
        Handler()
    }
    private val apiManager: ApiManager by lazy { ApiManager() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcvHistory.run {
            adapter = adapterHorizontal
            layoutManager = LinearLayoutManager(activity!!)
        }
        svBook.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("CheckResult")
            override fun onQueryTextSubmit(query: String): Boolean {
                apiManager.getListSearch(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        adapterHorizontal.updateData(it.list)
                    }, {

                    })
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty())
                    return false
                handle.removeCallbacksAndMessages(null)
                handle.post {
                    apiManager.getListSearch(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            adapterHorizontal.updateData(it.list)
                        }, {

                        })
                }
                return false
            }
        })
    }
}