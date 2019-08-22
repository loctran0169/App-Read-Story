package huuloc.uit.edu.truyenqq.activities.activitydownload

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterChapDownload
import huuloc.uit.edu.truyenqq.database.*
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import huuloc.uit.edu.truyenqq.services.ServiceDownload
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
    private val repo: ImageChapRepository by lazy {
        ImageChapRepository(application)
    }
    private val adapterChap: AdapterChapDownload by lazy {
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
            if (it != null)
                adapterChap.updateData(it)
        })
        var click = true
        btnDownload.setOnClickListener {
            if (viewModel.select.isEmpty() || !click)
            else {
                click=false
                repo.getDataStory(intent.getBundleExtra("manga")!!.getString("book_id")!!)
                    ?.observe(this@ActivityDownload, Observer {
                        if (it == null) {
                            if (viewModel.bitmap.value != null) {
                                viewModel.insertStory()
                                ImageStorageManager.saveToInternalStorage(
                                    this,
                                    viewModel.bitmap.value!!,
                                    viewModel.bookId
                                )
                            }
                        }
                        viewModel.saveAndRunServiceDownload(viewModel.select)
                    })
                Toast.makeText(this, "Đang tải vui lòng không thoát app", Toast.LENGTH_SHORT).show()
            }
        }
    }
}