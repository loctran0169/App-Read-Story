package huuloc.uit.edu.truyenqq.activities

import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterRankTabLayout
import kotlinx.android.synthetic.main.activity_rank.*

class Activity_Rank : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)
        setSupportActionBar(toolBar)
        val listTop = listOf("Ngày", "Tuần", "Tháng", "Yêu Thích")
        val listUrl = listOf(
            "top-ngay.html",
            "top-tuan.html",
            "top-thang.html",
            "truyen-yeu-thich.html"
        )
        btnBack.setOnClickListener {
            onBackPressed()
        }
        viewPager.adapter = AdapterRankTabLayout(
            title = listTop, url = listUrl,
            fragment = supportFragmentManager
        )
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setupWithViewPager(viewPager)
    }
}