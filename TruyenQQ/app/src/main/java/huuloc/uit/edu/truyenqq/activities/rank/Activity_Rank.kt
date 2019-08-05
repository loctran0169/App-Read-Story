package huuloc.uit.edu.truyenqq.activities.rank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterRankTabLayout
import kotlinx.android.synthetic.main.activity_rank.*

class Activity_Rank : AppCompatActivity() {
    val viewModel: ViewModelRank by lazy {
        ViewModelProviders
            .of(this)
            .get(ViewModelRank::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)
        setSupportActionBar(toolBarRank)
        val listTop = listOf("Ngày", "Tuần", "Tháng", "Yêu Thích")
        btnBackRank.setOnClickListener {
            onBackPressed()
        }
        viewPager.adapter = AdapterRankTabLayout(
            title = listTop,
            fragment = supportFragmentManager
        )
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setupWithViewPager(viewPager)
    }
}