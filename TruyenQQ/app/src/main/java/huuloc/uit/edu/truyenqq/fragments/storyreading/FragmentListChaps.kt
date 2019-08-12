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
import huuloc.uit.edu.truyenqq.adapers.AdapterListChap
import huuloc.uit.edu.truyenqq.data.Chap
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list_item_vertical.*

class FragmentListChaps : Fragment() {
    val viewModel: ViewModelStory by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelStory::class.java)
    }
    private val apiManager: ApiManager by lazy { ApiManager() }
    val adapterListChap: AdapterListChap by lazy {
        AdapterListChap(activity!!, mutableListOf())
    }
    private var isLoading = false
    private var offset = 100
    private var list = ArrayList<Chap>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_item_vertical, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rcvHorizontal.run {
            adapter = adapterListChap
            layoutManager = LinearLayoutManager(activity!!)
        }
        viewModel.listChap.observe(this@FragmentListChaps, Observer {
            adapterListChap.updateData(it.list)
            list = it.list as ArrayList<Chap>
            progressBarRank.visibility = View.INVISIBLE
        })
        initScrollListener()
    }

    private fun initScrollListener() {
        rcvHorizontal.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("CheckResult")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && manager.findLastVisibleItemPosition() > list.size - 20) {
                    isLoading = true
                    apiManager.getListChaps(offset, 100, viewModel.book_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val position = list.size
                            list.addAll(it.list)
                            adapterListChap.notifyItemRangeInserted(position, it.list.size - 1)
                            isLoading = false
                            offset += 100
                        }, {

                        })
                }
            }
        })
    }
}