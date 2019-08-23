package huuloc.uit.edu.truyenqq.activities.readingdownload

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterImageDownload
import huuloc.uit.edu.truyenqq.database.ImageChapRepository
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.activity_reading.*


class ActivityReadingDownload : AppCompatActivity() {
    private val repo: ImageChapRepository by lazy {
        ImageChapRepository(application)
    }
    private val adapterImageDownload: AdapterImageDownload by lazy {
        AdapterImageDownload(this, mutableListOf())
    }
    var bookId = ""
    var chapId = ""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)
        botNavigationReading.visibility = View.INVISIBLE
        refreshReading.isRefreshing=false
        refreshReading.isEnabled=false
        bookId = intent.getBundleExtra("manga")!!.getString("book_id")!!
        chapId = intent.getBundleExtra("manga")!!.getString("chap")!!
        toolbarTextReading.text="Chương "+chapId
        rcvImage.run {
            adapter = adapterImageDownload
            layoutManager = LinearLayoutManager(context)
        }
        repo.getAllImageWithId(bookId,chapId)?.observe(this@ActivityReadingDownload, Observer {
            adapterImageDownload.updateData(it)
        })
        btnBackReading.setOnClickListener {
            onBackPressed()
        }
    }
}