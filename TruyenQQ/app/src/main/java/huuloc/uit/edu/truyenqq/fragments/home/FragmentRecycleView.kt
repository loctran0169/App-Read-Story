package huuloc.uit.edu.truyenqq.fragments.home

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
import huuloc.uit.edu.truyenqq.activities.rank.ViewModelRank
import huuloc.uit.edu.truyenqq.adapers.AdapterHorizontal
import huuloc.uit.edu.truyenqq.data.StoryInformation
import huuloc.uit.edu.truyenqq.network.ApiManager
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.fragment_list_item_vertical.*

class FragmentRecycleView(private val sort: Int, private var col: String) : Fragment() {
    private val adapterHorizontal: AdapterHorizontal by lazy {
        AdapterHorizontal(activity!!, mutableListOf())
    }
    val viewModel: ViewModelRank by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelRank::class.java)
    }
    private var isLoading = false
    private var offset = 20
    private var list = ArrayList<StoryInformation>()
    private val apiManager: ApiManager by lazy {
        ApiManager()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_item_vertical, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcvHorizontal.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterHorizontal
            addItemDecoration(SpaceItem(4))
        }
        when (sort) {
            0 -> {
                viewModel.loadDay(col)
                viewModel.storyDay.observe(this@FragmentRecycleView, Observer {
                    if (it != null) {
                        adapterHorizontal.updateData(it)
                        progressBarRank.visibility = View.INVISIBLE
                    }
                })
                viewModel.loadMoreDay.observe(this@FragmentRecycleView, Observer {
                    if (it.end - it.start > 0) {
                        adapterHorizontal.loadMore(it.start + 1, it.end)
                        isLoading = false
                    }
                })
            }
            1 -> {
                viewModel.loadWeek(col)
                viewModel.storyWeek.observe(this@FragmentRecycleView, Observer {
                    if (it != null) {
                        adapterHorizontal.updateData(it)
                        progressBarRank.visibility = View.INVISIBLE
                    }
                })
                viewModel.loadMoreWeek.observe(this@FragmentRecycleView, Observer {
                    if (it.end - it.start > 0) {
                        adapterHorizontal.loadMore(it.start + 1, it.end)
                        isLoading = false
                    }
                })
            }
            2 -> {
                viewModel.loadMonth(col)
                viewModel.storyMonth.observe(this@FragmentRecycleView, Observer {
                    if (it != null) {
                        adapterHorizontal.updateData(it)
                        progressBarRank.visibility = View.INVISIBLE
                    }
                })
                viewModel.loadMoreMonth.observe(this@FragmentRecycleView, Observer {
                    if (it.end - it.start > 0) {
                        adapterHorizontal.loadMore(it.start + 1, it.end)
                        isLoading = false
                    }
                })
            }
            3 -> {
                viewModel.loadLike(col)
                viewModel.storyLike.observe(this@FragmentRecycleView, Observer {
                    if (it != null) {
                        adapterHorizontal.updateData(it)
                        progressBarRank.visibility = View.INVISIBLE
                    }
                })
                viewModel.loadMoreLike.observe(this@FragmentRecycleView, Observer {
                    if (it.end - it.start > 0) {
                        adapterHorizontal.loadMore(it.start + 1, it.end)
                        isLoading = false
                    }
                })
            }
        }
        initScrollListener()
    }

    private fun initScrollListener() {
        rcvHorizontal.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("CheckResult")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                when (sort) {
                    0 -> {
                        if (!isLoading && manager.findLastVisibleItemPosition() >= viewModel.storyDay.value!!.size - 5) {
                            isLoading = true
                            viewModel.loadDay(col)
                        }
                    }
                    1 -> {
                        if (!isLoading && manager.findLastVisibleItemPosition() >= viewModel.storyWeek.value!!.size - 5) {
                            isLoading = true
                            viewModel.loadWeek(col)
                        }
                    }
                    2 -> {
                        if (!isLoading && manager.findLastVisibleItemPosition() >= viewModel.storyMonth.value!!.size - 5) {
                            isLoading = true
                            viewModel.loadMonth(col)
                        }
                    }
                    3 -> {
                        if (!isLoading && manager.findLastVisibleItemPosition() >= viewModel.storyLike.value!!.size - 5) {
                            isLoading = true
                            viewModel.loadLike(col)
                        }
                    }
                }

            }
        })
    }
}