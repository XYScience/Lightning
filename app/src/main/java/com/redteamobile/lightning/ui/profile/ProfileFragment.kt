package com.redteamobile.lightning.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.redteamobile.lightning.Global
import com.redteamobile.lightning.R
import com.redteamobile.lightning.ui.profile.adapter.ESimCardAdapter
import com.redteamobile.lightning.util.LogUtil
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    companion object {
        const val TAG = "ProfileFragment"
    }

    private var eSimCardAdapter : ESimCardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        list_profile.layoutManager = linearLayoutManager
        activity?.let { a ->
            eSimCardAdapter = ESimCardAdapter(a)
            list_profile.adapter = eSimCardAdapter
            Global.getInstance(a).euiccController.getDownloadableSubscriptionList(a).subscribe({
                it?.let {
                    eSimCardAdapter?.setData(it)
                }
            }, {
                LogUtil.e(TAG, "getDownloadableSubscriptionList exception: $it")
            })
        }
    }

}
