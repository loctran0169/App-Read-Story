package huuloc.uit.edu.truyenqq.activities.newstory

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterHorizontal
import huuloc.uit.edu.truyenqq.data.StoryInformation
import huuloc.uit.edu.truyenqq.network.ApiManager
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_newstory.*

class ActivityNewStory : AppCompatActivity() {

    private val adapterHorizontal: AdapterHorizontal by lazy {
        AdapterHorizontal(this, mutableListOf())
    }
    private var isLoading = false
    private var offset = 20
    private var list = ArrayList<StoryInformation>()
    private val apiManager: ApiManager by lazy {
        ApiManager()
    }
    val viewModel: ViewModelNewStory by lazy {
        ViewModelProviders
            .of(this)
            .get(ViewModelNewStory::class.java)
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelProviders
            .of(this)
            .get(ViewModelNewStory::class.java)
        setSupportActionBar(toolBarNewStory)
        setContentView(R.layout.activity_newstory)
        toolbarTextNewStory.text = "Truyện Mới Ra Mắt"
        rcvNew.run {
            layoutManager = LinearLayoutManager(this@ActivityNewStory)
            adapter = adapterHorizontal
            addItemDecoration(SpaceItem(4))
        }
        btnBackNewStory.setOnClickListener {
            onBackPressed()
        }
        viewModel.story.observe(this@ActivityNewStory, Observer {
            adapterHorizontal.updateData(it.list)
            list = it.list as ArrayList<StoryInformation>
        })
        initScrollListener()
    }

    private fun initScrollListener() {
        rcvNew.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("CheckResult")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                if (isLoading == false && manager.findLastVisibleItemPosition() == list.size - 4) {
                    isLoading = true
                    apiManager.geListTop(offset, 20, "created")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val position = list.size
                            list.addAll(it.list)
                            adapterHorizontal.notifyItemRangeInserted(position - 1, it.list.size)
                            isLoading = false
                            offset += 20
                        }, {

                        })
                }
            }
        })
    }
}