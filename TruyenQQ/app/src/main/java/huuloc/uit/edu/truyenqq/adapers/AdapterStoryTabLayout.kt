package huuloc.uit.edu.truyenqq.adapers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import huuloc.uit.edu.truyenqq.fragments.storyreading.FragmentComment
import huuloc.uit.edu.truyenqq.fragments.storyreading.FragmentInforStory
import huuloc.uit.edu.truyenqq.fragments.storyreading.FragmentListChaps

class AdapterStoryTabLayout(fragment: FragmentManager) :
    FragmentPagerAdapter(fragment) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> return FragmentInforStory()
            1 -> FragmentListChaps()
            else -> FragmentComment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (position == 0)
            return "Chi Tiết"
        else if (position == 1)
            return "Chapter"
        return "Bình Luận"
    }

}