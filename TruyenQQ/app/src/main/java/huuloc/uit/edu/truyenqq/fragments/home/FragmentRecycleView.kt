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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
            0 -> viewModel.storyDay.observe(this@FragmentRecycleView, Observer {
                adapterHorizontal.updateData(it.list)
                list = it.list as ArrayList<StoryInformation>
                if (it.list.isNotEmpty())
                    progressBarRank.visibility = View.INVISIBLE
            })
            1 -> viewModel.storyWeek.observe(this@FragmentRecycleView, Observer {
                adapterHorizontal.updateData(it.list)
                list = it.list as ArrayList<StoryInformation>
                if (it.list.isNotEmpty())
                    progressBarRank.visibility = View.INVISIBLE
            })
            2 -> viewModel.storyMonth.observe(this@FragmentRecycleView, Observer {
                adapterHorizontal.updateData(it.list)
                list = it.list as ArrayList<StoryInformation>
                if (it.list.isNotEmpty())
                    progressBarRank.visibility = View.INVISIBLE
            })
            3 -> viewModel.storyLike.observe(this@FragmentRecycleView, Observer {
                adapterHorizontal.updateData(it.list)
                list = it.list as ArrayList<StoryInformation>
                if (it.list.isNotEmpty())
                    progressBarRank.visibility = View.INVISIBLE
            })
        }
        initScrollListener()
    }

    private fun initScrollListener() {
        rcvHorizontal.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("CheckResult")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && manager.findLastVisibleItemPosition() >= list.size - 5) {
                    isLoading = true
                    apiManager.getListNewUpdate(offset, _col = col, _arrayCategory = "")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val position = list.size
                            list.addAll(it.list)
                            adapterHorizontal.notifyItemRangeInserted(position, it.list.size - 1)
                            isLoading = false
                            offset += 20
                        }, {

                        })
                }
            }
        })
    }
}