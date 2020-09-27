package com.a.amp.user.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.a.amp.R
import com.a.amp.databinding.FragmentProfileBinding
import com.a.amp.storage.setting
import com.a.amp.user.apimodel1.followResponse
import com.a.amp.user.data.ProfileDataItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var username = ""
    private var id = ""
    lateinit var result: Response<followResponse>

    lateinit var currentUser: String
    var isfollow = false

    lateinit var profileViewModel: ProfileListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = arguments?.let { ProfileFragmentArgs.fromBundle(it).username }.toString()
        id = arguments?.let { ProfileFragmentArgs.fromBundle(it).id }.toString()

        val setting = setting()
        currentUser = setting.getString("username")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        profileViewModel = ViewModelProvider(this).get(ProfileListViewModel::class.java)

        profileViewPagerInit()
        binding.profileBind = ProfileDataItem(username, id)

        if (currentUser == username) {
            profile_cv_btn_1.visibility = View.GONE
        }

//        myAdapter = profileViewModel.writeList.value?.let {
//            WritingCvAdapter(
//                it, this, currentUser, username
//                //        ,
//                //            {
//                //              call back body
//                //            }
//            )
//        }

//        profile_recycler.apply {
//            adapter = myAdapter
//            setHasFixedSize(true)
//        }

//        profileViewModel.writeList.observe(viewLifecycleOwner, { list ->
//            if (list != null) {
//                myAdapter?.list = list
//                myAdapter?.notifyDataSetChanged()
//            }
//        })
//
//        profileViewModel.fillWrite(username)

        profile_appbar_start_icon.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        profileViewModel.getProfile(username)

        profileViewModel.isFollowing.observe(viewLifecycleOwner, { isfollowing ->
            if (isfollowing != null) {
                if (isfollowing) {
                    isfollow = true
                    profile_cv_btn_1.text = "لغو دنبال کردن"
                    profile_cv_btn_1.setBackgroundColor(Color.RED)
                } else if (!isfollowing) {
                    isfollow = false
                    profile_cv_btn_1.text = "دنبال کردن"
                    profile_cv_btn_1.setBackgroundColor(Color.parseColor("#286de6"))
                }
            }
        })

        profile_cv_btn_1.setOnClickListener {
            if (!isfollow) {
                profileViewModel.followOtherProfile(username)
            } else if (isfollow) {
                profileViewModel.unFollowOtherProfile(username)
            }
        }

    }

    private fun profileViewPagerInit() {
        val viewPagerAdapter = ProfileViewPagerAdapter(childFragmentManager, lifecycle)
        viewPagerAdapter.addFragment(ProfileTabFragment(username), "نوشته ها")
        viewPagerAdapter.addFragment(ProfileTabFragment2(username), "علاقه مندی ها")
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator((tab_profile as TabLayout), (viewPager as ViewPager2)) { tab, position ->
            tab.text = viewPagerAdapter.getName(position)
        }.attach()
//        viewPager.addFragment()
    }

//    override fun onClick(id: String, layoutPosition: Int) {
//        val buttonSheetDialog = BottomSheetDialog(requireContext())
//        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
//
//        view.findViewById<MaterialButton>(R.id.botton_sheet_2).setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO) {
//                val delResult = ArticleRemote().deleteArticleFormServer(id)
//                if (delResult.status == Status.SUCCESS) {
//                    buttonSheetDialog.dismiss()
//                    val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
//                    db.myDao().deleteArticles(ArticleEntity(id, "", "", ""))
//                    profileViewModel.fillWrite(username)
//                }
//            }
//        }
//        view.findViewById<MaterialButton>(R.id.botton_sheet_1).setOnClickListener {
//            findNavController().navigate(
//                ProfileFragmentDirections.actionProfileFragmentToWriteFragment(
//                    id
//                )
//            )
//            buttonSheetDialog.dismiss()
//        }
//
//        buttonSheetDialog.setContentView(view)
//
//        buttonSheetDialog.show()
//    }

//    override fun deleteUserArticle(id: String, layoutPosition: Int){
//        lifecycleScope.launch(Dispatchers.IO){
//            val result = ArticleRemote().deleteArticleFormServer(id)
//        }
//    }


}