package huuloc.uit.edu.truyenqq.activities.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterStoryTabLayout
import kotlinx.android.synthetic.main.activity_story.*

class ActivityStory : AppCompatActivity() {
    val viewModel: ViewModelStory by lazy {
        ViewModelProviders
            .of(this, ViewModelStoryFactory(intent.getStringExtra("book_id")!!))
            .get(ViewModelStory::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        println("### ${intent.getBundleExtra("kind")!!.getString("book_id")}")
        ViewModelProviders
            .of(this, ViewModelStoryFactory(intent.getBundleExtra("kind")!!.getString("book_id")!!))
            .get(ViewModelStory::class.java)

        val listTop = listOf("Ngày", "Tuần", "Tháng", "Yêu Thích")
        collapsingStory.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        viewPagerStory.adapter = AdapterStoryTabLayout(fragment = supportFragmentManager)
        tabLayoutStory.tabMode = TabLayout.MODE_FIXED
        tabLayoutStory.setupWithViewPager(viewPagerStory)
    }
}