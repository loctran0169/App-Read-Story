package huuloc.uit.edu.truyenqq.fragments.storyreading

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.story.ViewModelStory
import huuloc.uit.edu.truyenqq.adapers.AdapterListChap
import kotlinx.android.synthetic.main.fragment_list_item_vertical.*

class FragmentListChaps : Fragment() {
    val viewModel: ViewModelStory by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelStory::class.java)
    }
    var load = false
    var chap = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_item_vertical, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rcvHorizontal.run {
            //adapter = adapterListChap
            layoutManager = LinearLayoutManager(activity!!)
        }
        refreshRank.isEnabled = false
        viewModel.listChap.observe(this@FragmentListChaps, Observer {
            chap = true
            if (load && !it.isNullOrEmpty()) {
                val adapterListChap = AdapterListChap(activity!!, it, viewModel.Story.value?.first_chap?.order)
                rcvHorizontal.adapter = adapterListChap
                adapterListChap.updateData(it)
                progressBarRank.visibility = View.INVISIBLE
            }
        })
        viewModel.refresh.observe(this@FragmentListChaps, Observer {
            if (viewModel.refresh.value!!) {
                viewModel.itemChap.clear()
                rcvHorizontal.adapter?.notifyDataSetChanged()
                viewModel.loadListChap(viewModel.bookId)
            }
        })
        viewModel.isLoading.observe(this@FragmentListChaps, Observer {
            load = true
            if (load && !viewModel.itemChap.isNullOrEmpty()) {
                val adapterListChap = AdapterListChap(
                    activity!!,
                    viewModel.itemChap,
                    viewModel.Story.value?.first_chap?.order
                )
                rcvHorizontal.adapter = adapterListChap
                adapterListChap.updateData(viewModel.listChap.value!!)
                progressBarRank.visibility = View.INVISIBLE
            }
        })
    }
}