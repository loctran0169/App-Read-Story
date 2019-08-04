package huuloc.uit.edu.truyenqq.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterHorizontal
import huuloc.uit.edu.truyenqq.data.ListStory
import huuloc.uit.edu.truyenqq.network.ApiManager
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_newstory.*

class Activity_NewStory : AppCompatActivity() {
    private val apiManager: ApiManager by lazy { ApiManager() }
    private val adapterHorizontal: AdapterHorizontal by lazy {
        AdapterHorizontal(this, mutableListOf())
    }
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolBarNewStory)
        setContentView(R.layout.activity_newstory)
        toolbarTextNewStory.text = "Truyện Mới Ra Mắt"
        rcvNew.run{
            layoutManager = LinearLayoutManager(this@Activity_NewStory)
            adapter = adapterHorizontal
            addItemDecoration(SpaceItem(4))
        }
        btnBackNewStory.setOnClickListener {
            onBackPressed()
        }

        apiManager.geListTop(0, 20, "created")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapterHorizontal.updateData(it.list)
            }, {

            })
    }
}