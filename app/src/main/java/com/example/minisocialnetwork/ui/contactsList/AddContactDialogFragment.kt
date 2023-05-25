package com.example.minisocialnetwork.ui.contactsList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.minisocialnetwork.databinding.AddContactDialogBinding
import com.example.minisocialnetwork.domain.contracts.AddContactListener


class AddContactDialogFragment: DialogFragment() {

    private lateinit var listener: AddContactListener
    private var _binding: AddContactDialogBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddContactDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btAddContactSave.setOnClickListener {
                listener.onAddContact(
                    binding.etAddContactUsername.text.toString(),
                    binding.etAddContactProfession.text.toString()
                )
                dismiss()
            }
            ibAddContactArrow.setOnClickListener {
                dismiss()
            }
        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as AddContactListener
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val TAG = "AddContactDialogFragment"



    }


}