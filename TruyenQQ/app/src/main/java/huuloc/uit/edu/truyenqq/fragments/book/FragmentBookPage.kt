package huuloc.uit.edu.truyenqq.fragments.book

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterHorizontal
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.fragment_list_item_vertical.*

class FragmentBookPage : Fragment() {
    private var isLoading = false
    val viewModel: ViewModelBook by lazy {
        ViewModelProviders
            .of(activity!!, ViewModelBookFactory(activity!!))
            .get(ViewModelBook::class.java)
    }
    private val adapterHorizontal: AdapterHorizontal by lazy {
        AdapterHorizontal(activity!!, mutableListOf())
    }
    var _tag: Int = 0
    var isLogin: Boolean = true
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bundle = arguments
        if (bundle != null) {
            _tag = bundle.getInt("tag")
            isLogin = bundle.getBoolean("login")
        }
        if (!isLogin)
            return inflater.inflate(R.layout.fragment_require, container, false)
        return inflater.inflate(R.layout.fragment_list_item_vertical, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isLogin) {
            rcvHorizontal.run {
                layoutManager = LinearLayoutManager(activity)
                addItemDecoration(SpaceItem(4))
            }
            when (_tag) {
                0 -> {
                    rcvHorizontal.adapter = adapterHorizontal
                    viewModel.subcribe.observe(this@FragmentBookPage, Observer {
                        if (it != null) {
                            adapterHorizontal.updateData(it)
                            progressBarRank.visibility = View.INVISIBLE
                            isLoading = false
                            refreshRank.isRefreshing = false
                        }
                    })
                    viewModel.loadMoreSub.observe(this@FragmentBookPage, Observer {
                        if (it.end - it.start > 0) {
                            adapterHorizontal.loadMore(it.start + 1, it.end)
                            isLoading = false
                            refreshRank.isRefreshing = false
                        }
                    })
                }
                1 -> {
                    rcvHorizontal.adapter = adapterHorizontal
                    viewModel.history.observe(this@FragmentBookPage, Observer {
                        if (it != null) {
                            adapterHorizontal.updateData(it)
                            progressBarRank.visibility = View.INVISIBLE
                            isLoading = false
                            refreshRank.isRefreshing = false
                        }
                    })
                    viewModel.loadMoreHis.observe(this@FragmentBookPage, Observer {
                        if (it.end - it.start > 0) {
                            adapterHorizontal.loadMore(it.start + 1, it.end)
                            isLoading = false
                            refreshRank.isRefreshing = false
                        }
                    })
                }
                2 -> {

                }
            }
            refreshRank.setOnRefreshListener {
                isLoading = true
                when (_tag) {
                    0 -> {
                        viewModel.itemsSub.clear()
                        viewModel.offsetSub = 0
                        adapterHorizontal.notifyDataSetChanged()
                        viewModel.loadSubsribe()
                    }
                    1 -> {
                        viewModel.itemsHis.clear()
                        viewModel.offsetHis = 0
                        adapterHorizontal.notifyDataSetChanged()
                        viewModel.loadHistory()
                    }
                    2 -> {

                    }
                }
            }
            initScrollListener()
        }
    }

    private fun initScrollListener() {
        rcvHorizontal.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("CheckResult")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                when (_tag) {
                    0 -> {
                        if (!isLoading && manager.findLastVisibleItemPosition() >= viewModel.subcribe.value!!.size - 5 && viewModel.itemsSub.size >= 20) {
                            isLoading = true
                            viewModel.loadSubsribe()
                        }
                    }
                    1 -> {
                        if (!isLoading && manager.findLastVisibleItemPosition() >= viewModel.history.value!!.size - 5 && viewModel.itemsHis.size >= 20) {
                            isLoading = true
                            viewModel.loadHistory()
                        }
                    }
                }
            }
        })
    }
}