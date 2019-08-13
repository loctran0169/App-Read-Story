package huuloc.uit.edu.truyenqq.activities.reading

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterImage
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.activity_reading.*

class ActivityReading : AppCompatActivity() {
    private val adapterImage: AdapterImage by lazy {
        AdapterImage(this, mutableListOf())
    }
    val viewModel: ViewModelReading by lazy {
        ViewModelProviders
            .of(this)
            .get(ViewModelReading::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)
        ViewModelProviders
            .of(
                this,
                ViewModelReadingFactory(
                    intent.getBundleExtra("manga")!!.getString("book_id")!!,
                    intent.getBundleExtra("manga")!!.getString("chap")!!
                )
            )
            .get(ViewModelReading::class.java)
        rcvImage.run {
            adapter = adapterImage
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItem(4))
        }
        btnBackReading.setOnClickListener {
            onBackPressed()
        }
        toolbarTextReading.text = "Chap ${intent.getBundleExtra("manga")!!.getString("chap")!!}"
        viewModel.loadImage().observe(this@ActivityReading, Observer {
            adapterImage.updateData(it.list)
        })
    }
}