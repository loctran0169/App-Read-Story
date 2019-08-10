package huuloc.uit.edu.truyenqq.activities.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterRankTabLayout
import kotlinx.android.synthetic.main.activity_story.*

class ActivityStory  : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        val listTop = listOf("Ngày", "Tuần", "Tháng", "Yêu Thích")
        collapsingStory.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        viewPagerStory.adapter = AdapterRankTabLayout(
            title = listTop,
            fragment = supportFragmentManager
        )
        tabLayoutStory.tabMode = TabLayout.MODE_FIXED
        tabLayoutStory.setupWithViewPager(viewPagerStory)
    }
}