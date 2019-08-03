package huuloc.uit.edu.truyenqq.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterRankTabLayout
import kotlinx.android.synthetic.main.activity_rank.*

class Activity_NewStory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)
        val listCountry = listOf("Trung Quốc", "Việt Nam", "Hàn Quốc", "Nhật Bản", "Mỹ")
        val listUrl = listOf(
            "truyen-tranh-moi.html",
            "truyen-tranh-moi.html",
            "truyen-tranh-moi.html",
            "truyen-tranh-moi.html",
            "truyen-tranh-moi.html"
        )
        toolbarText.text = "Truyện Mới"
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        viewPager.adapter = AdapterRankTabLayout(
            title = listCountry,
            url = listUrl,
            tag = "Query",
            fragment = supportFragmentManager
        )
        tabLayout.setupWithViewPager(viewPager)
        btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}