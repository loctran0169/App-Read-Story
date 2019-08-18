package huuloc.uit.edu.truyenqq.activities.story

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.reading.ActivityReading
import huuloc.uit.edu.truyenqq.adapers.AdapterFlexBoxLayout
import huuloc.uit.edu.truyenqq.adapers.AdapterStoryTabLayout
import huuloc.uit.edu.truyenqq.data.MysharedPreferences
import huuloc.uit.edu.truyenqq.data.StatusRead
import huuloc.uit.edu.truyenqq.data.StoryRead
import huuloc.uit.edu.truyenqq.data.USER_ID
import kotlinx.android.synthetic.main.activity_story.*
import kotlin.math.abs

class ActivityStory : AppCompatActivity() {
    val viewModel: ViewModelStory by lazy {
        ViewModelProviders
            .of(this)
            .get(ViewModelStory::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        ViewModelProviders
            .of(this, ViewModelStoryFactory(intent.getBundleExtra("kind")!!.getString("book_id")!!, this))
            .get(ViewModelStory::class.java)
        collapsingStory.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        viewPagerStory.adapter = AdapterStoryTabLayout(fragment = supportFragmentManager)
        tabLayoutStory.tabMode = TabLayout.MODE_FIXED
        tabLayoutStory.setupWithViewPager(viewPagerStory)
        val share = MysharedPreferences(this).gẹtShare
        var orderReaded = "0"
        var status: StatusRead? = null
        viewModel.isReaded.observe(this@ActivityStory, Observer {
            status = it
        })
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { p0, p1 ->
            if (abs(p1) - appBar.totalScrollRange == 0) {
                btnBackStory.visibility = View.VISIBLE

            } else {
                btnBackStory.visibility = View.INVISIBLE
                refreshStory.isEnabled = false
            }
            if (abs(p1) == 0)
                refreshStory.isEnabled = true
        })
        btnBackStory.setOnClickListener {
            onBackPressed()
        }
        btnReadNow.setOnClickListener {
            if (status == null)
            else if (status!!.status == "1") {
                val dialog = AlertDialog.Builder(this)
                dialog.setMessage("Đọc tiếp tục chap ${status!!.data.chap_order}")
                    .setNegativeButton("Không") { _: DialogInterface, i: Int ->

                    }
                    .setPositiveButton("Đọc tiếp") { _: DialogInterface, i: Int ->
                        val intent1 = Intent(this, ActivityReading::class.java)
                        val bundle = Bundle()
                        bundle.putString("book_id", status!!.data.book_id)
                        bundle.putString("chap", status!!.data.chap_order)
                        bundle.putString("first", viewModel.Story.value!!.first_chap.order)
                        intent1.putExtra("manga", bundle)
                        this.startActivity(intent1)
                    }
                val dislay = dialog.create()
                dislay.setTitle("Thông báo")
                dislay.show()
            } else {
                val intent1 = Intent(this, ActivityReading::class.java)
                val bundle = Bundle()
                println()
                bundle.putString("book_id", intent.getBundleExtra("kind")!!.getString("book_id"))
                bundle.putString("chap", orderReaded)
                bundle.putString("first", viewModel.Story.value!!.first_chap.order)
                intent1.putExtra("manga", bundle)
                startActivity(intent1)
            }
        }
        viewModel.Story.observe(this, Observer {
            bindingData(it)
            orderReaded = it.first_chap.order
        })
        btnSubscribe.setOnClickListener {
            if (share.getString(USER_ID, null) != null)
                viewModel.loadSubscribe().observe(this, Observer {
                    if (it.success == 0) {
                        btnSubscribe.isSelected = true
                        btnSubscribe.text = "Theo dõi"
                    } else {
                        btnSubscribe.isSelected = true
                        btnSubscribe.text = "Hủy theo dõi"
                    }
                })
            else {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Vui lòng đăng nhập để sử dụng dịch vụ")
                val show = dialog.create()
                show.show()
            }
        }
        viewModel.refresh.observe(this@ActivityStory, Observer {
            if (it) {
                viewModel.loadStoryReading(viewModel.bookId)
                if (share.getString(USER_ID, null) != null)
                    viewModel.loadHistoryReading(viewModel.bookId)
            }
        })
        refreshStory.setOnRefreshListener {
            viewModel.refresh.value = true
        }
    }


    fun bindingData(story: StoryRead) {
        collapsingStory.title = story.name
        tvNameStory.text = story.name
        tvAuthor.text = "Tác giả: " + story.author
        if (story.pending == "0")
            tvStatus.text = "Trạng trái : Đang cập nhật"
        else
            tvStatus.text = "Trạng trái : Hoàn thành"
        rcvStory.run {
            layoutManager = FlexboxLayoutManager(context)
            adapter = AdapterFlexBoxLayout(context, story.category)
        }
        tvView.text = story.total_view
        tvLike.text = story.like_book
        tvSubscribe.text = story.total_subscribe
        try {
            Glide.with(this)
                .load("http://i.mangaqq.com/ebook/190x247/" + story.image + "?thang=t2121")
                .into(imgStory)
        } finally {
            Glide.with(this)
                .load("http://i.mangaqq.com/ebook/190x247/" + story.image + "?thang=t515")
                .into(imgStory)
        }
        if (story.subscribe == "1") {
            btnSubscribe.isSelected = true
            btnSubscribe.text = "Hủy theo dõi"
        } else {
            btnSubscribe.isSelected = true
            btnSubscribe.text = "Theo dõi"
        }
    }
}