package huuloc.uit.edu.truyenqq.activities.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.tabs.TabLayout
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterFlexBoxLayout
import huuloc.uit.edu.truyenqq.adapers.AdapterStoryTabLayout
import huuloc.uit.edu.truyenqq.data.StoryRead
import kotlinx.android.synthetic.main.activity_story.*

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
            .of(this, ViewModelStoryFactory(intent.getBundleExtra("kind")!!.getString("book_id")!!, "24901"))
            .get(ViewModelStory::class.java)

        collapsingStory.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        viewPagerStory.adapter = AdapterStoryTabLayout(fragment = supportFragmentManager)
        tabLayoutStory.tabMode = TabLayout.MODE_FIXED
        tabLayoutStory.setupWithViewPager(viewPagerStory)

        viewModel.Story.observe(this, Observer {
            bindingData(it)
        })
        btnSubscribe.setOnClickListener {
            viewModel.loadSubscribe().observe(this, Observer {
                if(it.success==0){
                    btnSubscribe.isSelected = true
                    btnSubscribe.text = "Theo dõi"
                }
                else
                {
                    btnSubscribe.isSelected = true
                    btnSubscribe.text = "Hủy theo dõi"
                }
            })
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
        }
        else
        {
            btnSubscribe.isSelected = true
            btnSubscribe.text = "Theo dõi"
        }
    }
}