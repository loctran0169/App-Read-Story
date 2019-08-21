package huuloc.uit.edu.truyenqq.activities.activitydownload

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterChapDownload
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.activity_download.*

class ActivityDownload : AppCompatActivity() {
    val viewModel: ViewModelDownload by lazy {
        ViewModelProviders
            .of(
                this,
                ViewModelDownloadFactory(application, this, intent.getBundleExtra("manga")!!.getString("book_id")!!)
            )
            .get(ViewModelDownload::class.java)
    }

    val adapterChap: AdapterChapDownload by lazy {
        AdapterChapDownload(this, mutableListOf(), viewModel.select)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        rcvDownload.run {
            adapter = adapterChap
            layoutManager = GridLayoutManager(this@ActivityDownload, 3)
            addItemDecoration(SpaceItem(5))
        }
        viewModel.listChap.observe(this@ActivityDownload, Observer {
            adapterChap.updateData(it)
        })
        btnDownload.setOnClickListener {
//            if (viewModel.select.isEmpty())
//            else {
                viewModel.downloadList("1")
//            }
        }
    }
}