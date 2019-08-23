package huuloc.uit.edu.truyenqq.activities.reading

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterImage
import huuloc.uit.edu.truyenqq.data.*
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.activity_reading.*

class ActivityReading : AppCompatActivity() {

    private val adapterImage: AdapterImage by lazy {
        AdapterImage(this, mutableListOf())
    }
    val viewModel: ViewModelReading by lazy {
        ViewModelProviders
            .of(this)
            .get(ViewModelReading::class.java)
    }

    var first = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)
        ViewModelProviders
            .of(
                this,
                ViewModelReadingFactory(
                    intent.getBundleExtra("manga")!!.getString("book_id")!!,
                    intent.getBundleExtra("manga")!!.getString("chap")!!,
                    this
                )
            )
            .get(ViewModelReading::class.java)
        first = intent.getBundleExtra("manga")!!.getString("first")!!
        val share = MysharedPreferences(this)
        rcvImage.run {
            adapter = adapterImage
            layoutManager = LinearLayoutManager(context)
        }
        rcvImage.setHasFixedSize(true)
        refreshReading.setOnRefreshListener {
            viewModel.loadImage()
        }
        btnBackReading.setOnClickListener {
            onBackPressed()
        }
        btnNext.setOnClickListener {
            viewModel.loadImageNextOrPrev(viewModel.story.value!!.next)
        }
        btnPrev.setOnClickListener {
            viewModel.loadImageNextOrPrev(viewModel.story.value!!.prev)
        }
        viewModel.story.observe(this@ActivityReading, Observer {
            if (!it.list.isNullOrEmpty()) {
                toolbarTextReading.text = "Chap ${viewModel.story.value?.order}"
                adapterImage.updateData(it.list)
                refreshReading.isRefreshing = false
                if (it.order.toFloat() > it.next.toFloat()) {
                    btnNext.visibility = View.INVISIBLE
                } else {
                    btnNext.visibility = View.VISIBLE
                }
                if (first.toFloat() == it.order.toFloat()) {
                    btnPrev.visibility = View.INVISIBLE
                } else {
                    btnPrev.visibility = View.VISIBLE
                }
            }
        })
        imgSend.setOnClickListener {
            if(editTextCommentChap.text.toString().isNullOrEmpty())
                Toast.makeText(this,"Nội dung không được để trống",Toast.LENGTH_SHORT).show()
            else if(share.getShare.getString(USER_ID,null).isNullOrEmpty()){
                val dialog = AlertDialog.Builder(this)
                val view : View = LayoutInflater.from(this).inflate(R.layout.name_comment,null)
                val name = view.findViewById<EditText>(R.id.tvNamePostComment)
                dialog.setMessage("Nhập tên để tiếp tục")
                    .setView(view)
                    .setPositiveButton("Chấp nhận") { _: DialogInterface, i: Int ->
                        if(name.text.isEmpty()){
                            Toast.makeText(this,"Tên không được để trống",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            viewModel.postComment(editTextCommentChap,
                                name.text.toString(),
                                "",
                                editTextCommentChap.text.toString(),
                                "0","2","1","0")
                        }
                    }
                val dislay = dialog.create()
                dislay.show()
            }
            else if(!share.getShare.getString(USER_ID,null).isNullOrEmpty()){
                viewModel.postComment(editTextCommentChap,
                    share.getShare.getString(FIRST_NAME,null)?:"" +share.getShare.getString(LAST_NAME,null),
                    share.getShare.getString(EMAIL,null)!!,
                    editTextCommentChap.text.toString(),
                    "0","2","1","0")
            }
        }
    }
}