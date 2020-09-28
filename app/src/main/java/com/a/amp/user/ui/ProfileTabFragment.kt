package com.a.amp.user.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.a.amp.MyApp
import com.a.amp.R
import com.a.amp.article.data.ArticleEntity
import com.a.amp.article.data.ArticleRemote
import com.a.amp.core.resource.Status
import com.a.amp.database.AppDataBase
import com.a.amp.storage.setting
import com.a.amp.user.data.MoreClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_profile_tab.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileTabFragment(private val username: String) : Fragment(), MoreClickListener {

    lateinit var profileViewModel: ProfileListViewModel
    var myAdapter: WritingCvAdapter? = null
    lateinit var currentUser: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return layoutInflater.inflate(R.layout.fragment_profile_tab, container, false)
    }

    override fun onResume() {
        super.onResume()

        profileViewModel = ViewModelProvider(this).get(ProfileListViewModel::class.java)

        myAdapter = profileViewModel.writeList.value?.let {
            WritingCvAdapter(
                it, this, currentUser, username,
            )
        }

        profile_recycler.apply {
            adapter = myAdapter
            setHasFixedSize(true)
        }

        profileViewModel.writeList.observe(viewLifecycleOwner, { list ->
            if (list != null) {
                myAdapter?.list = list
                myAdapter?.notifyDataSetChanged()
            }
        })

        CoroutineScope(Dispatchers.IO).launch {
            profileViewModel.fillWrite(username)
        }




    }

    override fun onStart() {
        super.onStart()
        val setting = setting()
        currentUser = setting.getString("username")
    }

    @SuppressLint("InflateParams")
    override fun onClick(id: String, layoutPosition: Int,text : String) {
        if (text == "more") {
            val buttonSheetDialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.bottom_sheet, null)

            view.findViewById<MaterialButton>(R.id.botton_sheet_2).setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val delResult = ArticleRemote().deleteArticleFormServer(id)
                    if (delResult.status == Status.SUCCESS) {
                        buttonSheetDialog.dismiss()
                        val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
                        db.myDao().deleteArticles(ArticleEntity(id, "", "", ""))
                        profileViewModel.fillWrite(username)
                    }
                }

            }
            view.findViewById<MaterialButton>(R.id.botton_sheet_1).setOnClickListener {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToWriteFragment(
                        id
                    )
                )
                buttonSheetDialog.dismiss()
            }

            buttonSheetDialog.setContentView(view)

            buttonSheetDialog.show()
        }else if (text == "like"){

        }
    }

}