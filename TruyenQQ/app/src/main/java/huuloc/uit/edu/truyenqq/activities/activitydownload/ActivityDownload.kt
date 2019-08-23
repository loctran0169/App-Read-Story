package huuloc.uit.edu.truyenqq.activities.activitydownload

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterChapDownload
import huuloc.uit.edu.truyenqq.data.Chap
import huuloc.uit.edu.truyenqq.database.ImageChapRepository
import huuloc.uit.edu.truyenqq.database.ImageStorageManager
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
        AdapterChapDownload(this, mutableListOf(), viewModel.select, viewModel.downloaded)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        rcvDownload.run {
            adapter = adapterChap
            layoutManager = GridLayoutManager(this@ActivityDownload, 3)
            addItemDecoration(SpaceItem(4))
        }
        viewModel.listChap.observe(this@ActivityDownload, Observer {
            if (it != null)
                adapterChap.updateData(it)
        })
        btnDownload.setOnClickListener {
            when {
                checkServiceRunning(ServiceDownload::class.java) -> Toast.makeText(
                    this,
                    "Vui lòng chờ tải xong danh sách trước",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.select.isEmpty() -> Toast.makeText(this, "Chưa chọn Chap", Toast.LENGTH_SHORT).show()
                else -> {
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
        var all = false
        btnSelectAll.setOnClickListener {
            if (viewModel.downloaded.size == viewModel.listChap.value?.size ?: 0)
            else if (!all) {
                all = true
                btnSelectAll.text = "Hủy chọn"
                viewModel.select.clear()
                viewModel.select.addAll(
                    viewModel.listChap.value!!.map { chap: Chap -> chap.order }.toCollection(arrayListOf())
                )
            } else {
                all = false
                btnSelectAll.text = "Chọn tất cả"
                viewModel.select.clear()
            }
            adapterChap.notifyDataSetChanged()
        }
        tvBackDownload.setOnClickListener {
            onBackPressed()
        }
    }

    fun <T : Any> checkServiceRunning(service: Class<T>): Boolean {
        return (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == service.name }
    }
}