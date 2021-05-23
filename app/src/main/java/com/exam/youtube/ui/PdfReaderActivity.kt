package com.exam.youtube.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.exam.youtube.R
import com.github.barteksc.pdfviewer.util.FitPolicy
import kotlinx.android.synthetic.main.activity_pdf_reader.*

class PdfReaderActivity : Fragment(R.layout.activity_pdf_reader) {


    private val path: String? by lazy {
        arguments?.getString("path")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pdfView.fromAsset(path)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            .enableAnnotationRendering(true)
            .password(null)
            .scrollHandle(null)
            .enableAntialiasing(true)
            .spacing(5)
            .pageFitPolicy(FitPolicy.WIDTH)
            .load();
    }
}