package com.example.minisocialnetwork.presentation.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.minisocialnetwork.databinding.FragmentDetailViewBinding
import com.example.minisocialnetwork.presentation.fragments.base.BaseFragment
import com.example.minisocialnetwork.util.Flag
import com.example.minisocialnetwork.util.extentions.urlLoader
import java.util.concurrent.TimeUnit

/**

 * A fragment that displays the detailed view of a contact.
 */
class DetailViewFragment :
    BaseFragment<FragmentDetailViewBinding>(FragmentDetailViewBinding::inflate) {

    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private val args: DetailViewFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Flag.NAV_GRAPH) {
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

    /**

     * Performs the navigation and sets up the views.
     */
    private fun navigate() {
        with(binding) {
            if (Flag.NAV_GRAPH) {
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

    /**

     * Handles the back press event and navigates back.
     */
    private fun onBackPress() {
        if (Flag.NAV_GRAPH) {
            findNavController().popBackStack()
        } else {
            requireActivity().supportFragmentManager
                .popBackStack()
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param2"
        private const val ARG_PARAM2 = "param3"
        private const val ARG_PARAM3 = "param4"

        /**
         * Creates a new instance of DetailViewFragment with the provided parameters.
         *
         * @param param1 The first parameter.
         * @param param2 The second parameter.
         * @param param3 The third parameter.
         * @return A new instance of DetailViewFragment.
         */
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