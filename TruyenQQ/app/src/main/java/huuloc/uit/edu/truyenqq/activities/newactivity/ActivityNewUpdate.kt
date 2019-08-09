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
import huuloc.uit.edu.truyenqq.network.ApiManager
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_newstory.*

class ActivityNewUpdate : AppCompatActivity() {
    var name = ""
    var category = ""
    var col = ""
    private val adapterHorizontal: AdapterHorizontal by lazy {
        AdapterHorizontal(this, mutableListOf())
    }
    private var isLoading = false
    private var offset = 20
    private var list = ArrayList<StoryInformation>()
    private val apiManager: ApiManager by lazy {
        ApiManager()
    }
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
        viewModel.loadData(category, col).observe(this@ActivityNewUpdate, Observer {
            adapterHorizontal.updateData(it.list)
            list = it.list as ArrayList<StoryInformation>
            progressBarNew.visibility= View.INVISIBLE
        })
        initScrollListener()
    }

    private fun initScrollListener() {
        rcvNew.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("CheckResult")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && manager.findLastVisibleItemPosition() == list.size - 5) {
                    isLoading = true
                    apiManager.getListNewUpdate(offset, _col = col, _arrayCategory = category)
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