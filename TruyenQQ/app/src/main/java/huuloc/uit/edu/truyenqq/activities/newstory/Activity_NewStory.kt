package huuloc.uit.edu.truyenqq.activities.newstory

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterHorizontal
import huuloc.uit.edu.truyenqq.network.ApiManager
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_newstory.*

class Activity_NewStory : AppCompatActivity() {

    private val adapterHorizontal: AdapterHorizontal by lazy {
        AdapterHorizontal(this, mutableListOf())
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
            layoutManager = LinearLayoutManager(this@Activity_NewStory)
            adapter = adapterHorizontal
            addItemDecoration(SpaceItem(4))
        }
        btnBackNewStory.setOnClickListener {
            onBackPressed()
        }
        viewModel.story.observe(this@Activity_NewStory, Observer {
            adapterHorizontal.updateData(it.list)
        })
    }
}