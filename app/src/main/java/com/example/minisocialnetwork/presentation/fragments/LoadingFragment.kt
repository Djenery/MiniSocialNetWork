package com.example.minisocialnetwork.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.FragmentAuthBinding
import com.example.minisocialnetwork.presentation.fragments.base.BaseFragment
import com.example.minisocialnetwork.presentation.viewmodels.AuthViewModel
import com.example.minisocialnetwork.util.Flag.NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint

/**

 * A loading fragment that displays a loading screen and handles navigation based
 * on the auto login state.
 *
 * This fragment extends the BaseFragment class and
 * provides functionality for observing the auto login state and navigating
 * to different fragments based on the state.
 * It uses the AuthViewModel to retrieve the auto login state.
 *
 * If the auto login state is enabled, it navigates to the MyProfileFragment;
 * otherwise, it navigates to the SingUpFragment.
 * The navigation is performed either using the Navigation component or the FragmentManager,
 * depending on the value of NAV_GRAPH.
 * @see BaseFragment
 * @see AuthViewModel
 */

@AndroidEntryPoint
class LoadingFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val mViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.isAutoLoginEnabled.observe(viewLifecycleOwner) {
            it?.let {
                if (NAV_GRAPH) {
                    navigateByNavGraph(it)
                } else {
                    navigateByFragmentManager(it)
                }
            }
        }
    }

    /**

     * Navigates to the appropriate fragment using the Navigation component.
     * If the auto login state is enabled, it navigates to the MyProfileFragment.
     * Otherwise, it navigates to the SingUpFragment.
     * @param isEnabled The flag indicating if auto login is enabled or not.
     */
    private fun navigateByNavGraph(isEnabled: Boolean) {
        with(findNavController()) {
            if (isEnabled) {
                navigate(LoadingFragmentDirections.actionLoadingFragmentToMyProfileFragment())
            } else {
                navigate(LoadingFragmentDirections.actionLoadingFragmentToSingUpFragment())

            }
        }

    }

    /**

     * Navigates to the appropriate fragment using the FragmentManager.
     * If the auto login state is enabled,
     * it replaces the authFragmentContainer with an instance of MyProfileFragment.
     * Otherwise, it replaces the authFragmentContainer with an instance of SingUpFragment.
     * @param isEnabled The flag indicating if auto login is enabled or not.
     */
    private fun navigateByFragmentManager(isEnabled: Boolean) {
        parentFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            if (isEnabled) {
                replace(R.id.authFragmentContainer, MyProfileFragment.newInstance())
            } else {
                replace(R.id.authFragmentContainer, SingUpFragment.newInstance())
            }
        }
    }

    companion object {
        /**
         * Creates a new instance of the LoadingFragment.
         * @return A new instance of LoadingFragment.
         */
        @JvmStatic
        fun newInstance(): LoadingFragment = LoadingFragment()
    }
}