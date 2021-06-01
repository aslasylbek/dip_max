package com.exam.youtube.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.exam.youtube.R
import com.exam.youtube.utils.setupWithNavController
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    var currentNavController: LiveData<Pair<Int, NavController>>? = null

    private var selectedId = -1

    companion object {
        /**
         * Если нужно открыть определенную вкладку
         *  передать в аргументах [PAGE_ARGUMENT]
         */
        val PAGE_ARGUMENT = "PAGE_ARGUMENT"

        val PAGE_HOME = R.id.tab_home
        val PAGE_PRODUCTS = R.id.tab_products
        val PAGE_TRANSFERS = R.id.tab_transfers
        val PAGE_PAYMENTS = R.id.tab_payments
        val PAGE_MENU = R.id.tab_menu
    }

    /**
     * Жуткий хак, нужен из-за кривой реализации [setupWithNavController]
     * баг 1: при переходе в авторизацию с любой вкладки, кроме первой
     *  и при возврате обратно загружается первая вкладка и [nav_view] тупит
     * баг 2: появляется, если пофиксить баг 1 - делаем то же самое, только
     *  при возврате с авторизации всё ок - мы переходим на первую вкладку
     *  и она не грузится (HBKG-432)
     *
     * в итоге нам надо вызвать [setupBottomNavigationBar] ровно один раз
     * плюс хранить последнее выбранное значение таба ([selectedId]) в [viewModel]
     */
    private var called = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selectedId = currentNavController?.value?.first ?: -1
        called = false
        if (savedInstanceState == null && selectedId == -1) {
            called = true
            setupBottomNavigationBar()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (arguments?.getInt(PAGE_ARGUMENT) != null) {
                checkSelectedPage()
            } else if (currentNavController?.value?.second?.navigateUp() != true
                && !findNavController().navigateUp()
            ) {
                checkSelectedPage()
            }
        }
    }

    private fun checkSelectedPage(){
        nav_view.selectedItemId = R.id.tab_home
    }

    /**
     * чтобы [nav_view.selectedItemId] не потерялся
     */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (!called || savedInstanceState != null || selectedId != nav_view.selectedItemId) {
            called = true
            setupBottomNavigationBar()
        }
        arguments?.getInt(PAGE_ARGUMENT)?.let {
            nav_view.selectedItemId = it
        }
    }

    private fun setupBottomNavigationBar() {
        currentNavController = nav_view.setupWithNavController(
            listOf(
                R.navigation.tab_home,
                R.navigation.tab_products,
                R.navigation.tab_transfers,
                R.navigation.tab_payments,
                R.navigation.tab_menu
            ), childFragmentManager, R.id.container_view, activity?.intent
        )
    }
}
