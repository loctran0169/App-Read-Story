package huuloc.uit.edu.truyenqq.adapers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import huuloc.uit.edu.truyenqq.fragments.home.FragmentRecycleView

class AdapterRankTabLayout(
    private val title: List<String>,
    fragment: FragmentManager,
    private val tag: String? = null
) :
    FragmentPagerAdapter(fragment) {

    override fun getItem(position: Int): Fragment {
        return FragmentRecycleView(position)

    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}
