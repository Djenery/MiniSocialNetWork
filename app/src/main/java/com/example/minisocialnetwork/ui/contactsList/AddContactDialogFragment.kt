package com.example.minisocialnetwork.ui.contactsList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.minisocialnetwork.databinding.AddContactDialogBinding
import com.example.minisocialnetwork.domain.contracts.AddContactListener


/**
A dialog fragment used for adding a contact.
 */
class AddContactDialogFragment : DialogFragment() {

    private var listener: AddContactListener? = null
    private var _binding: AddContactDialogBinding? = null
    private val binding get() = _binding!!

    /**
     * Called to create the view hierarchy of the dialog fragment.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState This fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = AddContactDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * Called immediately after the view is created.
     * @param view The View object returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btAddContactSave.setOnClickListener {
                listener?.onAddContact(
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

    /**
     * Called when the fragment is first attached to its context.
     * @param context The context to which the fragment is attached.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as AddContactListener
    }


    /**
     * Called when the fragment's view is destroyed.
     * It releases references to the binding and listener to avoid memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listener = null
    }

    companion object {
        const val TAG = "AddContactDialogFragment"
    }


}