package huuloc.uit.edu.truyenqq.activities.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.ActivityUser
import huuloc.uit.edu.truyenqq.activities.WaitingActivity
import huuloc.uit.edu.truyenqq.activities.changepassword.ActivityChangePassWord
import huuloc.uit.edu.truyenqq.activities.newactivity.ActivityNewUpdate
import huuloc.uit.edu.truyenqq.activities.personal.ActivityPersonalInformation
import huuloc.uit.edu.truyenqq.activities.rank.ActivityRank
import huuloc.uit.edu.truyenqq.data.MysharedPreferences
import huuloc.uit.edu.truyenqq.data.USER_ID
import huuloc.uit.edu.truyenqq.fragments.book.FragmentBook
import huuloc.uit.edu.truyenqq.fragments.category.FragmentCategory
import huuloc.uit.edu.truyenqq.fragments.home.FragmentHome
import huuloc.uit.edu.truyenqq.fragments.search.FragmentSearch
import huuloc.uit.edu.truyenqq.fragments.user.FragmentUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long=3000
    val viewModel: ViewModelHome by lazy {
        ViewModelProviders
            .of(this)
            .get(ViewModelHome::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewModelProviders
            .of(this)
            .get(ViewModelHome::class.java)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frmMain, FragmentHome())
                .commit()
        }
        viewModel.isShow.observe(this@MainActivity, Observer {
            botNavigation.selectedItemId = it
        })
        val share = MysharedPreferences(this)
        viewModel.dataLogin.value = share.loadData()
        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                viewModel.isShow.value -> true
                R.id.navHome -> {
                    showFragment(FragmentHome())
                    viewModel.isShow.value = it.itemId
                    true
                }
                R.id.navCategory -> {
                    showFragment(FragmentCategory())
                    viewModel.isShow.value = it.itemId
                    true
                }
                R.id.navSearch -> {
                    showFragment(FragmentSearch())
                    viewModel.isShow.value = it.itemId
                    true
                }
                R.id.navBookcase -> {
                    showFragment(FragmentBook())
                    viewModel.isShow.value = it.itemId
                    true
                }
                R.id.navUser -> {
                    if (share.getShare.getString(USER_ID, null) == null) {
                        //viewModel.isShow.value = it.itemId
                        val intend1 = Intent(this, ActivityUser::class.java)
                        startActivity(intend1)
                    } else {
                        showFragment(FragmentUser())
//                        supportFragmentManager.beginTransaction()
//                            .add(R.id.frmMain, FragmentUser())
//                            .addToBackStack("user")
//                            .commit()
                        viewModel.isShow.value = it.itemId
                    }
                    true
                }

                else -> true
            }
        }
        //FirebaseInstanceId.getInstance().instanceId
        //            .addOnCompleteListener(OnCompleteListener { task ->
        //                if (!task.isSuccessful) {
        //                    Log.w("###", "getInstanceId failed", task.exception)
        //                    return@OnCompleteListener
        //                }
        //                // Get new Instance ID token
        //                val token = task.result?.token
        //            })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permission, 101)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 101) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        val share = MysharedPreferences(this)
        if (share.getShare.getString(USER_ID, null) == null) {
            viewModel.dataLogin.value = share.loadData()
            botNavigation.selectedItemId = viewModel.isShow.value!!
        } else {
            viewModel.dataLogin.value = share.loadData()
            if (viewModel.dataLogin.value == null) {
                showFragment(FragmentHome())
                viewModel.isShow.value = R.id.navHome
            } else if (share.getShare.getString(USER_ID, null) != null) {
                botNavigation.selectedItemId = viewModel.isShow.value!!
                showFragment(FragmentHome())
                viewModel.isShow.value = R.id.navHome
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("SelectedItemId", viewModel.isShow.value!!)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        botNavigation.selectedItemId = savedInstanceState.getInt("SelectedItemId")
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frmMain, fragment)
            .commit()
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.frmMain, fragment)
            .commit()
    }

    fun openActivityRank(view: View) {
        val intent = Intent(this, ActivityRank::class.java)
        startActivity(intent)
    }

    fun openActivityNewUpdateStory(view: View) {
        val intent = Intent(this, ActivityNewUpdate::class.java)
        val bundle = Bundle()
        bundle.putString("name", "Truyện mới cập nhật")
        bundle.putString("category", "")
        bundle.putString("col", "modified")
        intent.putExtra("kind", bundle)
        startActivity(intent)
    }

    fun openActivityMale(view: View) {
        val intent = Intent(this, ActivityNewUpdate::class.java)
        val bundle = Bundle()
        bundle.putString("name", "Truyện con trai")
        bundle.putString("category", "26,27,30,31,32,41,43,47,48,50,57,85,97")
        bundle.putString("col", "modified")
        intent.putExtra("kind", bundle)
        startActivity(intent)
    }

    fun openActivityFemale(view: View) {
        val intent = Intent(this, ActivityNewUpdate::class.java)
        val bundle = Bundle()
        bundle.putString("name", "Truyện con gái")
        bundle.putString("category", "28,29,36,37,38,39,42,46,51,52,54,75,90,93")
        bundle.putString("col", "modified")
        intent.putExtra("kind", bundle)
        startActivity(intent)
    }

    fun showNewStory(view: View) {
        val intent = Intent(this, ActivityNewUpdate::class.java)
        val bundle = Bundle()
        bundle.putString("name", "Truyện mới")
        bundle.putString("category", "")
        bundle.putString("col", "created")
        intent.putExtra("kind", bundle)
        startActivity(intent)
    }

    fun logOut(view: View) {
        val share = MysharedPreferences(this)
        share.removeAll()
        showFragment(FragmentHome())
        viewModel.isShow.value = R.id.navHome
    }

    fun changePassWord(view: View) {
        val intent = Intent(this, ActivityChangePassWord::class.java)
        startActivity(intent)
    }

    fun changeInfor(view: View) {
        val intent = Intent(this, ActivityPersonalInformation::class.java)
        startActivity(intent)
    }

    fun openFacebook(view: View) {
        val inten = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/fantruyenqq/"))
        startActivity(inten)
    }
}