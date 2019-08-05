package huuloc.uit.edu.truyenqq.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.rank.ViewModelRank
import huuloc.uit.edu.truyenqq.adapers.AdapterHorizontal
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.fragment_list_item_vertical.*

class FragmentRecycleView(private val sort: Int) : Fragment() {
    private val adapterHorizontal: AdapterHorizontal by lazy {
        AdapterHorizontal(activity!!, mutableListOf())
    }
    val viewModel: ViewModelRank by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelRank::class.java)
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
            })
            1 -> viewModel.storyWeek.observe(this@FragmentRecycleView, Observer {
                adapterHorizontal.updateData(it.list)
            })
            2 -> viewModel.storyMonth.observe(this@FragmentRecycleView, Observer {
                adapterHorizontal.updateData(it.list)
            })
            3 -> viewModel.storyLike.observe(this@FragmentRecycleView, Observer {
                adapterHorizontal.updateData(it.list)
            })
        }
    }

}