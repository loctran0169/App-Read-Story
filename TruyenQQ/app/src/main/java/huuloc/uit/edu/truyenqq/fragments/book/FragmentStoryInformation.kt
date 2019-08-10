package huuloc.uit.edu.truyenqq.fragments.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import huuloc.uit.edu.truyenqq.R

class FragmentStoryInformation(val id : String)  : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_information,container,false)
    }
}