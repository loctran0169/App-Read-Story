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
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.story.ViewModelStory
import huuloc.uit.edu.truyenqq.adapers.AdapterComment
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.fragment_list_item_vertical.*

class FragmentComment  : Fragment(){
    val viewModel: ViewModelStory by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelStory::class.java)
    }
    val adapterComment : AdapterComment by lazy{
        AdapterComment(activity!!, mutableListOf())
    }
    var load = false
    var chap = false
    private var isLoading = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_item_vertical, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcvHorizontal.run {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = adapterComment
            addItemDecoration(SpaceItem(4))
        }
        viewModel.comment.observe(activity!!, Observer {
            if (it != null) {
                adapterComment.updateDate(it)
                progressBarRank.visibility = View.INVISIBLE
                isLoading = false
                refreshRank.isRefreshing = false
            }
        })
        viewModel.loadMore.observe(activity!!, Observer {
            if (it.end - it.start > 0) {
                adapterComment.loadMore(it.start + 1, it.end)
                isLoading = false
                refreshRank.isRefreshing = false
            }
        })
        refreshRank.setOnRefreshListener {
            isLoading = true
            viewModel.itemsCom.clear()
            viewModel.offsetCom = 0
            adapterComment.notifyDataSetChanged()
            viewModel.getListComment()
        }
        initScrollListener()
    }
    private fun initScrollListener() {
        rcvHorizontal.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("CheckResult")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && manager.findLastVisibleItemPosition() >= viewModel.comment.value!!.size - 5 && viewModel.itemsCom.size >= 20) {
                    isLoading = true
                    viewModel.getListComment()
                }
            }
        })
    }
}