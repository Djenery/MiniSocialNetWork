package com.example.minisocialnetwork.presentation.fragments.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**

 * An abstract base class for fragments that provides binding functionality.
 * @param vBinding The type of the view binding.
 * @property inflaterMethod The method reference for inflating the view binding.
 */
abstract class BaseFragment<vBinding : ViewBinding>(
    private val inflaterMethod: (LayoutInflater, ViewGroup?, Boolean) -> vBinding
) : Fragment() {

    private var _binding: vBinding? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflaterMethod.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}

