package huuloc.uit.edu.truyenqq.activities.newupdatestory

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.newstory.ViewModelNewUpdateStory
import huuloc.uit.edu.truyenqq.adapers.AdapterHorizontal
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.activity_newstory.*

class ActivityNewUpdateStory : AppCompatActivity() {
    var name = ""
    var category = ""
    private val adapterHorizontal: AdapterHorizontal by lazy {
        AdapterHorizontal(this, mutableListOf())
    }

    val viewModel: ViewModelNewUpdateStory by lazy {
        ViewModelProviders
            .of(this)
            .get(ViewModelNewUpdateStory::class.java)
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.getBundleExtra("kind")
        name=bundle.getString("name")!!
        category = bundle.getString("category")!!
        ViewModelProviders
            .of(this)
            .get(ViewModelNewUpdateStory::class.java)
        setContentView(R.layout.activity_newstory)
        setSupportActionBar(toolBarNewStory)
        toolbarTextNewStory.text = name

        rcvNew.run {
            layoutManager = LinearLayoutManager(this@ActivityNewUpdateStory)
            adapter = adapterHorizontal
            addItemDecoration(SpaceItem(4))
        }
        btnBackNewStory.setOnClickListener {
            onBackPressed()
        }
        viewModel.loadData(category).observe(this@ActivityNewUpdateStory, Observer {
            adapterHorizontal.updateData(it.list)
        })
    }

}