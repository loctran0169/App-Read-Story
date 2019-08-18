package huuloc.uit.edu.truyenqq.activities.newactivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.newstory.ViewModelNewUpdate
import huuloc.uit.edu.truyenqq.adapers.AdapterHorizontal
import huuloc.uit.edu.truyenqq.data.StoryInformation
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.activity_newstory.*

class ActivityNewUpdate : AppCompatActivity() {
    var name = ""
    var category = ""
    var col = ""
    private val adapterHorizontal: AdapterHorizontal by lazy {
        AdapterHorizontal(this, mutableListOf())
    }
    private var isLoading = false
    private var list = ArrayList<StoryInformation>()
    val viewModel: ViewModelNewUpdate by lazy {
        ViewModelProviders
            .of(this)
            .get(ViewModelNewUpdate::class.java)
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.getBundleExtra("kind")
        name = bundle!!.getString("name")!!
        category = bundle.getString("category")!!
        col = bundle.getString("col")!!
        ViewModelProviders
            .of(this)
            .get(ViewModelNewUpdate::class.java)
        setContentView(R.layout.activity_newstory)
        setSupportActionBar(toolBarNewStory)
        toolbarTextNewStory.text = name

        rcvNew.run {
            layoutManager = LinearLayoutManager(this@ActivityNewUpdate)
            adapter = adapterHorizontal
            addItemDecoration(SpaceItem(4))
        }
        btnBackNewStory.setOnClickListener {
            onBackPressed()
        }
        viewModel.loadSubsribe(category, col)
        viewModel._New.observe(this@ActivityNewUpdate, Observer {
            if (it != null) {
                adapterHorizontal.updateData(it)
                progressBarNew.visibility = View.INVISIBLE
                isLoading = false
                refreshNew.isRefreshing = false
            }
        })
        viewModel.loadMore.observe(this@ActivityNewUpdate, Observer {
            if (it.end - it.start > 0) {
                adapterHorizontal.loadMore(it.start + 1, it.end)
                isLoading = false
                refreshNew.isRefreshing = false
            }
        })
        refreshNew.setOnRefreshListener {
            isLoading = true
            viewModel.itemsNew.clear()
            viewModel.offsetNew = 0
            adapterHorizontal.notifyDataSetChanged()
            viewModel.loadSubsribe(category, col)
        }
        initScrollListener()
    }

    private fun initScrollListener() {
        rcvNew.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("CheckResult")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && manager.findLastVisibleItemPosition() >= viewModel._New.value!!.size - 5) {
                    isLoading = true
                    viewModel.loadSubsribe(category, col)
                }
            }
        })
    }
}