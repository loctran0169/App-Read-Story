package huuloc.uit.edu.truyenqq.activities.reading

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterImage
import huuloc.uit.edu.truyenqq.adapers.AdapterListChapSpinner
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

    var first = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)
        ViewModelProviders
            .of(
                this,
                ViewModelReadingFactory(
                    intent.getBundleExtra("manga")!!.getString("book_id")!!,
                    intent.getBundleExtra("manga")!!.getString("chap")!!,
                    this
                )
            )
            .get(ViewModelReading::class.java)
        first = intent.getBundleExtra("manga")!!.getString("first")!!
        rcvImage.run {
            adapter = adapterImage
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItem(4))
        }
        refreshReading.setOnRefreshListener {
            viewModel.loadImage()
        }
        btnBackReading.setOnClickListener {
            onBackPressed()
        }
        btnNext.setOnClickListener {
            viewModel.itemsImage.clear()
            adapterImage.notifyDataSetChanged()
            viewModel.loadImageNextOrPrev(viewModel.story.value!!.next)

        }
        btnPrev.setOnClickListener {
            viewModel.itemsImage.clear()
            adapterImage.notifyDataSetChanged()
            viewModel.loadImageNextOrPrev(viewModel.story.value!!.prev)
        }
        viewModel.story.observe(this@ActivityReading, Observer {
            if (!it.list.isNullOrEmpty()) {
                toolbarTextReading.text = "Chap ${viewModel.story.value?.order}"
                adapterImage.updateData(it.list!!)
                refreshReading.isRefreshing = false
                if (it.order.toFloat() > it.next.toFloat()) {
                    btnNext.visibility = View.INVISIBLE
                } else {
                    btnNext.visibility = View.VISIBLE
                }
                if (first.toFloat() == it.order.toFloat()) {
                    btnPrev.visibility = View.INVISIBLE
                } else {
                    btnPrev.visibility = View.VISIBLE
                }
            }
        })
        viewModel.listChap.observe(this@ActivityReading, Observer {
            spinnerListChap.adapter = AdapterListChapSpinner(this, it.list)
        })
        viewModel.position.observe(this@ActivityReading, Observer {
            spinnerListChap.setSelection(it)
        })
    }
}