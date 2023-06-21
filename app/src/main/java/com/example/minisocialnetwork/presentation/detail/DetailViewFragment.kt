package com.example.minisocialnetwork.presentation.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.minisocialnetwork.databinding.FragmentDetailViewBinding
import com.example.minisocialnetwork.presentation.base.BaseFragment
import com.example.minisocialnetwork.util.Flag.NAV_GRAPH
import com.example.minisocialnetwork.util.extentions.urlLoader
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param2"
private const val ARG_PARAM2 = "param3"
private const val ARG_PARAM3 = "param4"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailViewFragment :
    BaseFragment<FragmentDetailViewBinding>(FragmentDetailViewBinding::inflate) {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private val args: DetailViewFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (NAV_GRAPH) {
            sharedElementEnterTransition = TransitionInflater.from(context)
                .inflateTransition(android.R.transition.move)
            postponeEnterTransition(250, TimeUnit.MILLISECONDS)
        } else {
            arguments?.let {
                param1 = it.getString(ARG_PARAM1)
                param2 = it.getString(ARG_PARAM2)
                param3 = it.getString(ARG_PARAM3)
            }
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigate()
        binding.ivDetailViewArrowBack.setOnClickListener {
            onBackPress()
        }
    }

    private fun navigate() {
        with(binding) {
            if (NAV_GRAPH) {
                tvDetailViewUserName.text = args.Contact.name
                ivDetailViewIconUser.urlLoader(args.Contact.photo)
                ivDetailViewIconUser.transitionName = args.Contact.photo + args.Contact.id
                tvDetailViewCareer.text = args.Contact.profession

            } else {
                tvDetailViewUserName.text = param1
                ivDetailViewIconUser.urlLoader(param2)
                tvDetailViewCareer.text = param3
            }
        }

    }

    private fun onBackPress() {
        if (NAV_GRAPH) {
            findNavController().popBackStack()
        } else {
            requireActivity().supportFragmentManager
                .popBackStack()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
            DetailViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }
}