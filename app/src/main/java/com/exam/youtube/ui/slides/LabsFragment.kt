package com.exam.youtube.ui.slides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exam.youtube.R
import kotlinx.android.synthetic.main.fragment_slides_list.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SlidesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LabsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val slidesAdapter = SlidesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slides_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_slides.adapter = slidesAdapter
        slidesAdapter.items = listOf(
            ConfirmationItemDisplay("1 Зертханалық жұмыс", "lab1.pdf", "1 Зертханалық жұмыс") {
                navigateToPdfFragment(it.path)
            },
            ConfirmationItemDisplay("2 Зертханалық жұмыс", "lab2.pdf", "2 Зертханалық жұмыс") {
                navigateToPdfFragment(it.path)
            },
            ConfirmationItemDisplay("3 Зертханалық жұмыс", "lab3.pdf", "3 Зертханалық жұмыс") {
                navigateToPdfFragment(it.path)
            },
            ConfirmationItemDisplay("4 Зертханалық жұмыс", "lab4.pdf", "4 Зертханалық жұмыс") {
                navigateToPdfFragment(it.path)
            }
        )
    }

    private fun navigateToPdfFragment(path: String?) {
        findNavController().navigate(
            R.id.pdfReader, bundleOf(
                "path" to path
            )
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SlidesListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LabsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}