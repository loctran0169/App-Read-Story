package huuloc.uit.edu.truyenqq.activities.activitychapdownloaded

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterListChapDownload
import huuloc.uit.edu.truyenqq.database.ImageChapRepository
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.activity_reading.*

class ActivityChapDownload : AppCompatActivity(){
    val adapterListChapDownload : AdapterListChapDownload by lazy{
        AdapterListChapDownload(this,intent.getBundleExtra("manga")!!.getString("book_id")!!, mutableListOf())
    }
    private val repo: ImageChapRepository by lazy {
        ImageChapRepository(application)
    }
    val viewModel : ViewModelChapDownload by lazy {
        ViewModelProviders
            .of(this,ViewModelReadDownloadFactory(application,this,intent.getBundleExtra("manga")!!.getString("book_id")!!))
            .get(ViewModelChapDownload::class.java)
    }
    var bookId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)
        botNavigationReading.visibility= View.INVISIBLE
        toolbarTextReading.maxLines=1
        toolbarTextReading.text=intent.getBundleExtra("manga")!!.getString("name")!!
        refreshReading.isRefreshing=false
        refreshReading.isEnabled=false
        bookId=intent.getBundleExtra("manga")!!.getString("book_id")!!
        rcvImage.run {
            layoutManager= LinearLayoutManager(this@ActivityChapDownload)
            adapter=adapterListChapDownload
            addItemDecoration(SpaceItem(1))
        }
        repo.countStory(bookId)?.observe(this@ActivityChapDownload, Observer {
            if(it!=null)
                adapterListChapDownload.updateData(it)
        })
        btnBackReading.setOnClickListener {
            onBackPressed()
        }
    }
}