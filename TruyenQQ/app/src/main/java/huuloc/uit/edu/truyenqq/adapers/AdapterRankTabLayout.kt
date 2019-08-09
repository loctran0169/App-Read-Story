package huuloc.uit.edu.truyenqq.adapers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import huuloc.uit.edu.truyenqq.fragments.home.FragmentRecycleView

class AdapterRankTabLayout(
    private val title: List<String>,
    fragment: FragmentManager
) :
    FragmentPagerAdapter(fragment) {

    override fun getItem(position: Int): Fragment {
        return FragmentRecycleView(position,getCol(position))

    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

    fun getCol(position: Int): String {
        return when (position) {
            0 -> "views_day"
            1 -> "views_week"
            2 -> "views_month"
            else -> "like_book"
        }
    }
}
